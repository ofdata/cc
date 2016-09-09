package com.liushijie.cc.client.hadnler;

import com.liushijie.cc.client.test.Client;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.TimeUnit;

/**
 * 断线重连handler
 * Created by liushijie on 16-9-8.
 */
@ChannelHandler.Sharable
public class ConnectionHandler extends SimpleChannelInboundHandler {

    private Client client;

    public ConnectionHandler(Client client) {
        this.client = client;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(() -> client.createBootstrap(new Bootstrap(), eventLoop), 1L, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }
}
