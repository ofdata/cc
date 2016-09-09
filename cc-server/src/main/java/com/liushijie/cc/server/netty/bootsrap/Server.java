package com.liushijie.cc.server.netty.bootsrap;

import com.liushijie.cc.server.netty.handler.ServerChannelHander;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liushijie on 16-9-5.
 */
public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    private static final StringDecoder DECODER = new StringDecoder();
    private static final StringEncoder ENCODER = new StringEncoder();
    private static ServerChannelHander SERVER_HANDLER = new ServerChannelHander();

    public void start() throws Exception {
        logger.info("start.....");

        // 工作线程
        NioEventLoopGroup workGroup = new NioEventLoopGroup(2);
        // 数据量不会太大，读写不用分离
        NioEventLoopGroup parentGroup = workGroup;

        int port = 8023;



        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer() {
                        protected void initChannel(Channel ch) throws Exception {

                            ChannelPipeline pipeline = ch.pipeline();
                            // Add the text line codec combination first,
                            pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                            // the encoder and decoder are static as these are sharable
                            pipeline.addLast(DECODER).addLast(ENCODER);

                            // and then business logic.
                            pipeline.addLast(SERVER_HANDLER);
                        }
                    });

            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            new Thread(() -> {
                ;
            }).start();
            channelFuture.channel().closeFuture().sync();

        } finally {
            parentGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            logger.info("end...");
        }

    }


    public static void main(String[] args) throws Exception {
        new Server().start();
    }

}
