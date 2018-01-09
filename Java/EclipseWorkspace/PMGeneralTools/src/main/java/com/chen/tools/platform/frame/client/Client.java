package com.chen.tools.platform.frame.client;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.ReferenceCountUtil;

public class Client {
	
	static String HOST_IP = "127.0.0.1";//"10.110.2.104"; 
	
//	static String HOST_IP = "10.110.2.104";
	
	static String HOST_PORT = "12700";
	
	public static void main(String[] args) throws Exception {
		
		String host = HOST_IP;
		int port = Integer.parseInt(HOST_PORT);
		
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap(); // (1)
			b.group(workerGroup); // (2)
			b.channel(NioSocketChannel.class); // (3)
			b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("framer", new LengthFieldPrepender(8));
					ch.pipeline().addLast("encoder", new StringEncoder());
					ch.pipeline().addLast("decoder", new StringDecoder());
					ch.pipeline().addLast(new ClientHandler());
				}
			});

			// Start the client.
			ChannelFuture f = b.connect(host, port).sync(); // (5)
			
			f.channel().writeAndFlush(StringUtils.join(args, " "));//"cfg");
//			f.channel().writeAndFlush("cfg");

			// Wait until the connection is closed.
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}
}

class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
	public void channelRead(ChannelHandlerContext ctx, Object request) {
		String msg = (String) request;
		try {

			System.out.print(msg);
			
			if (msg.endsWith("\0")) {				
				ctx.close();	
			}
		} finally {
			ReferenceCountUtil.release(request);
		}
	}
    
    

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println(cause.getStackTrace());
        
        ctx.executor().schedule(
                // This exception can still be fired once the channel is closed and all handlers removed
                () -> ctx.fireExceptionCaught(new TimeoutException()), 100, TimeUnit.SECONDS);
        try {
			super.channelRegistered(ctx);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
//        Channel channel = ctx.channel();
//        if(channel.isActive()) {
//        		ctx.close();
//        }
    }
}