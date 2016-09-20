package com.liushijie.cc.client.test;

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
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    private static final StringDecoder DECODER = new StringDecoder();
    private static final StringEncoder ENCODER = new StringEncoder();

    private static final CCClientHandler CLIENT_HANDLER = new CCClientHandler();

    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = 8023;

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
//                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));

                        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                        pipeline.addLast(DECODER, ENCODER);

                        pipeline.addLast(CLIENT_HANDLER, connectionHandler);
                        // TODO 增加心跳handler
                    }
                });

        return b;
    }

    public static void connect(Bootstrap b) {
        b.connect().addListener((ChannelFuture future) -> {
            if (future.cause() != null) {
                logger.error("Failed to connect: " + future.cause());
            }
        });
    }

}
