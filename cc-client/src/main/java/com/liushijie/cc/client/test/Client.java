package com.liushijie.cc.client.test;

import com.liushijie.cc.client.hadnler.CCClientHandler;
import com.liushijie.cc.client.hadnler.ConnectionHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Client {
    private final Logger logger = LoggerFactory.getLogger(Client.class);

    private static final StringDecoder DECODER = new StringDecoder();
    private static final StringEncoder ENCODER = new StringEncoder();

    private static final CCClientHandler CLIENT_HANDLER = new CCClientHandler();

    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = 8023;

    public String id = "test1";

    public static void main(String[] args) throws InterruptedException {
        new Client().start();
        Thread.sleep(Long.MAX_VALUE);
    }

    private void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            createBootstrap(new Bootstrap(), group);

        } finally {
            group.shutdownGracefully();
        }
    }



    public Channel createBootstrap(Bootstrap bootstrap, EventLoopGroup eventLoop) throws InterruptedException {
        ConnectionHandler connectionHandler = new ConnectionHandler(this);
        bootstrap.group(eventLoop)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        // Add the text line codec combination first,
                        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                        pipeline.addLast(DECODER);
                        pipeline.addLast(ENCODER);

                        // and then business logic.
                        pipeline.addLast(CLIENT_HANDLER);
                        pipeline.addLast(connectionHandler);
                    }
                });

        // 连接
        ChannelFuture channelFuture = bootstrap.connect(HOST, PORT).sync();
        // 增加断线重连监听
        Channel channel = channelFuture.channel();
        channelFuture.addListener((ChannelFuture future) -> {
            if (!future.isSuccess()) {
                logger.info("cc : the connection is down, reconnect begin...");
                eventLoop.schedule(() -> createBootstrap(new Bootstrap(), channel.eventLoop()), 1L, TimeUnit.SECONDS);
            } else {
                logger.info("cc : the connection is startup...");
            }
        });

        Thread.sleep(100L);

        process(channel);

        return channel;
    }


    private void process(Channel ch) {

        // Sends the received line to the server.
        ChannelFuture lastWriteFuture = ch.writeAndFlush(id + "\r\n");


        if (1!=1) { // TODO 关闭条件
            try {
                ch.closeFuture().sync();
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
                e.printStackTrace();
            }
        }

        // Wait until all messages are flushed before closing the channel.
        if (lastWriteFuture != null) {
            try {
                lastWriteFuture.sync();
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
                e.printStackTrace();
            }

        }
    }
}
