package com.liushijie.cc.client.boostrap;

import com.liushijie.cc.client.handler.CCClientHandler;
import com.liushijie.cc.client.handler.ConnectionHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Created by liushijie on 16-9-8.
 */
public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    private static final CCClientHandler CLIENT_HANDLER = new CCClientHandler();

    private static final String HOST = System.getProperty("host", "127.0.0.1");
    private static final int PORT = 8023;

    private static int readerIdleTimeSeconds = 20;
    private static int writerIdleTimeSeconds = 60;
    private static int allIdleTimeSeconds = 60;
    public static int retry_delay = 5;

    public static void main(String[] args) {
        Client.configureBootstrap(new Bootstrap()).connect();
    }

    private static Bootstrap configureBootstrap(Bootstrap b) {
        return configureBootstrap(b, new NioEventLoopGroup());
    }

    public static Bootstrap configureBootstrap(Bootstrap b, EventLoopGroup eventLoop) {
        final ConnectionHandler connectionHandler = new ConnectionHandler();
        b.group(eventLoop)
                .channel(NioSocketChannel.class)
                .remoteAddress(HOST, PORT)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)), new ObjectEncoder());

                        pipeline.addLast(new IdleStateHandler(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds));
                        pipeline.addLast(CLIENT_HANDLER, connectionHandler);

                    }
                });

        return b;
    }

    public static void connect(Bootstrap b) {
        b.connect().addListener(new GenericFutureListener<ChannelFuture>() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.cause() != null) {
                    logger.error("Failed to connect: " + future.cause());
                }
            }
        });
    }
}
