package com.chen.tools.platform.frame.ws;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class SocketServer {

	private static final Logger logger = Logger.getLogger(SocketServer.class);
	
	private String host;
	private int port;
	private ServerHandler handler;
	private ChannelFuture future;
	private Status status = Status.Stop;

	enum Status {
		Start, Stop;
	}

	public SocketServer(String host, int port, ServerHandler handler) {
		this.host = host;
		this.port = port;
		this.handler = handler;
	}

	public void stop() {
		if (this.status == Status.Stop) { 
			return;
		}
		this.status = Status.Stop;
		logger.info("state start => stop");
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			logger.error("interrupted in socket service.sleep", e);
		}
		try {
			this.future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			logger.error("interrupted in socket service.closeFuture", e);
		}
		logger.info("close socket server finish");
		this.handler.shutdown();
		logger.info("server shutdown finish");
	}

	public void start() {
		if (this.status == Status.Start) {
			return;
		}
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast("framer", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 8, 0, 8));
						ch.pipeline().addLast("decoder", new StringDecoder());
						ch.pipeline().addLast("encoder", new StringEncoder());
						ch.pipeline().addLast(handler);
					}
				})
			.option(ChannelOption.SO_BACKLOG, 128)
			.childOption(ChannelOption.SO_KEEPALIVE, true);

			// 绑定端口，开始接收进来的连接
			this.future = b.bind(this.host, this.port).sync();
			logger.info("Server start listen at : " + port);
			this.status = Status.Start;
			while (this.status == Status.Start) {
				logger.info("server await.");
				TimeUnit.SECONDS.sleep(10);
			}
		} catch (InterruptedException e) {
			logger.error("interrupted in socket service.start", e);
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

}
