package com.chen.tools.platform.frame.ws;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.chen.tools.platform.business.worker.FileContentQuerier;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.util.ReferenceCountUtil;

@Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = Logger.getLogger(ServerHandler.class);
	
	private final Semaphore RequestLimit = new Semaphore(50);
	
	private ExecutorService threadPool = new ThreadPoolExecutor(20, 50, 10, TimeUnit.MINUTES, 
			new LinkedBlockingQueue<Runnable>(), 
			new ThreadFactoryBuilder()
			.setDaemon(true)
			.setNameFormat("task-thread-%d")
			.setUncaughtExceptionHandler((t, e) -> {
				logger.error("Thread t.getName() got exception", e);
			})
			.build(),
			(r, e) -> {
				logger.error("reject task");
			});
	
	public void shutdown() {
		logger.info("task manager shutdown finish");
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object request) throws Exception {	// NOPMD
		super.channelRead(ctx, request);		
		try 
		{
			
			String msg = (String) request;
			
			logger.info("request received. request: " + request);
			
			Future<String> resultFuture = (Future<String>) threadPool.submit(() -> {
//				Thread.sleep(10000);
//				String threadName = Thread.currentThread().getName();
//				logger.info(threadName + ": msg got.");
//				return threadName + " done.";
				
				FileContentQuerier fcq = new FileContentQuerier();
				
				String result = fcq.call(StringUtils.split(msg, " "));
				
				return result;
			});
			
//			resultFuture.addListener(new GenericFutureListener<Future<? super String>>() {
//
//				@Override
//				public void operationComplete(Future<? super String> future) throws Exception {
//					// TODO Auto-generated method stub
//					
//				}
//				
//			});
			
//			resultFuture.addListener((f) -> logger.info("listener got done."));
			
			String result = resultFuture.get();
			
//			logger.info(result);
			
			ctx.write("received. result is:\n" + result + "\0");
			ctx.flush();	
		} 
		catch (Exception e) 
		{
			logger.error("Exception occured on channelRead", e);
			ctx.write("error");
			ctx.flush();
		}
		finally {
			ReferenceCountUtil.release(request);
			RequestLimit.release();
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {// NOPMD
		super.exceptionCaught(ctx, cause);
	}

}
