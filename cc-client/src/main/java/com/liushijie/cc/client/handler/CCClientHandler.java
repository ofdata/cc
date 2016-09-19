package com.liushijie.cc.client.handler;

import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class CCClientHandler extends SimpleChannelInboundHandler<String> {

    private final Logger logger = LoggerFactory.getLogger(CCClientHandler.class);
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String o) throws Exception {
        logger.info("get message from server:" + o);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage(), cause);
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
}
