package com.liushijie.cc.server.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ChannelHandler.Sharable
public class ServerChannelHandler extends SimpleChannelInboundHandler<String> {
    private final Logger logger = LoggerFactory.getLogger(ServerChannelHandler.class);

//    private Map<String, Channel> channelMap = new ConcurrentHashMap<>();
    private Map<String, Channel> channelMap = new HashMap<>();

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

        new Thread(()->{
            while (true) {
                if (!channelMap.isEmpty()) {
                    Iterator<Map.Entry<String, Channel>> iterator = channelMap.entrySet().iterator();
                    long now = System.nanoTime();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Channel> entry = iterator.next();
                        String k = entry.getKey();
                        Channel v= entry.getValue();
                        if (v.isActive()) {
                            logger.info("send msg to {}", k);
                            v.writeAndFlush(now + "\r\n");
                        } else {
                            iterator.remove();
                            logger.error("key {} is inactive, removed...", k);
                        }
                    }
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
        if (!channelMap.containsKey(msg) || !channelMap.get(msg).isActive()) {
            channelMap.put(msg, ctx.channel());
            ctx.writeAndFlush("touch ok!\r\n");
        }
    }
}
