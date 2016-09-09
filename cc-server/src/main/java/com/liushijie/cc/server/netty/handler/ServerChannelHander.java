package com.liushijie.cc.server.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@ChannelHandler.Sharable
public class ServerChannelHander extends SimpleChannelInboundHandler<String> {
    private final Logger logger = LoggerFactory.getLogger(ServerChannelHander.class);

    private Map<String, Channel> channelMap = new HashMap<>();

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

        new Thread(()->{
            while (true) {
                if (!channelMap.isEmpty()) {
                    channelMap.forEach((k ,v) -> {
                        logger.info("send msg to {}", k);
                        v.writeAndFlush(System.nanoTime() + "\r\n");
                    });
                }
                try {
                    Thread.sleep(10000L);
                } catch (InterruptedException e) {
                    ;
                }
            }
        }).start();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        logger.info("cc : add msg({})'s channel to the channelMap...", msg);
        channelMap.put(msg, ctx.channel());

        ctx.writeAndFlush(msg + "\r\n");
    }
}
