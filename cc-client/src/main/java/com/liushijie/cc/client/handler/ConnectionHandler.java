package com.liushijie.cc.client.handler;

import com.liushijie.cc.client.test.Client;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 断线重连handler
 * Created by liushijie on 16-9-8.
 */
@ChannelHandler.Sharable
public class ConnectionHandler extends SimpleChannelInboundHandler {
    private final Logger logger = LoggerFactory.getLogger(ConnectionHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        final EventLoop loop = ctx.channel().eventLoop();
        loop.schedule(() -> {
            logger.info("channelUnregistered , reconnecting...");
            Client.connect(Client.configureBootstrap(new Bootstrap(), loop));
        }, 5, TimeUnit.SECONDS);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
