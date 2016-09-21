package com.liushijie.cc.client.boostrap;

import com.liushijie.cc.client.handler.CCClientHandler;
import com.liushijie.cc.client.handler.ConnectionHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 *
 * Created by liushijie on 16-9-8.
 */
public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    private static final StringDecoder DECODER = new StringDecoder(Charset.forName("UTF-8"));
    private static final StringEncoder ENCODER = new StringEncoder(Charset.forName("UTF-8"));

    private static final CCClientHandler CLIENT_HANDLER = new CCClientHandler();

    private static final String HOST = System.getProperty("host", "127.0.0.1");
    private static final int PORT = 8023;

    private static int readerIdleTimeSeconds = 5;
    private static int writerIdleTimeSeconds = 5;
    private static int allIdleTimeSeconds = 5;
    public static int retry_delay = 5;

    public static void main(String[] args) {
        Client.configureBootstrap(new Bootstrap()).connect();
    }

    private static Bootstrap configureBootstrap(Bootstrap b) {
        return configureBootstrap(b, new NioEventLoopGroup());
    }

    public static Bootstrap configureBootstrap(Bootstrap b, EventLoopGroup eventLoop) {
        ConnectionHandler connectionHandler = new ConnectionHandler();
        b.group(eventLoop)
                .channel(NioSocketChannel.class)
                .remoteAddress(HOST, PORT)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                        pipeline.addLast(DECODER, ENCODER);
                        pipeline.addLast(new IdleStateHandler(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds));
                        pipeline.addLast(CLIENT_HANDLER, connectionHandler);

                    }
                });

        return b;
    }

    public static void connect(Bootstrap b) {
        b.connect().addListener((ChannelFuture future) -> {
            if (!future.isSuccess()) {
                final EventLoop loop = future.channel().eventLoop();
                loop.schedule(() -> connect(configureBootstrap(new Bootstrap(), loop)), retry_delay, TimeUnit.SECONDS);
            }
            if (future.cause() != null) {
                logger.error("Failed to connect: " + future.cause());
            }
        });
    }
}
