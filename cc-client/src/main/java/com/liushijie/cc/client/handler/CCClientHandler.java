package com.liushijie.cc.client.handler;

import com.liushijie.cc.common.BaseMessage;
import com.liushijie.cc.common.DataType;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class CCClientHandler extends SimpleChannelInboundHandler<BaseMessage<Long>> {

    private final Logger logger = LoggerFactory.getLogger(CCClientHandler.class);
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BaseMessage<Long> msg) throws Exception {
        logger.info("message from server     " + msg.getData());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String clientId = ctx.channel().localAddress().toString();
        BaseMessage<String> register = new BaseMessage<String>(clientId, DataType.REGISTER);
        logger.info("channelActive..." + System.currentTimeMillis());
        ctx.channel().writeAndFlush(register).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                logger.info("channelActive.operationComplete time " + System.currentTimeMillis());
            }
        });
        logger.info("clientId is {}", clientId);

        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage(), cause);
        super.exceptionCaught(ctx, cause);
    }

}
