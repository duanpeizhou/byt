package cn.zectec.contraceptive.management.system.sender.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;
import cn.zectec.contraceptive.management.system.sender.service.IGetMedicineService;
import cn.zectec.contraceptive.management.system.sender.util.Encoder;

@Component
public class QuartzJob {
	
	@Autowired
	Encoder encoder;
	@Autowired
	IGetMedicineService getMedicineService;
	Bootstrap bootstrap = new Bootstrap();
	private static Logger logger = Logger.getLogger(QuartzJob.class);
	List<GetMedicineRecord> records;
	List<GetMedicineRecord> temp;
	Channel channel2;
	@PostConstruct
	public void init(){
		
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.group(new NioEventLoopGroup());
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.handler(new ChannelInitializer<SocketChannel>(){
			
			@Override
			protected void initChannel(SocketChannel channel)  {
				
				ChannelPipeline cp  = channel.pipeline();
				cp.addLast(new StringDecoder(Charset.forName("UTF-8")));
				cp.addLast(new StringEncoder(Charset.forName("UTF-8")));
				//这个写在这里不合适，这里只创建一次
				cp.addLast(new IdleStateHandler(60*5,10,0));
							
				cp.addLast(new SimpleChannelInboundHandler<String>(){
					@Override
					protected void channelRead0(ChannelHandlerContext ctx, String arg1) throws Exception {
						if(arg1.equals("ok")){
							
							//把false改为true
							for(int i = 0;i<temp.size();i++){
								temp.get(i).setSent(true);
							}
							getMedicineService.saveGetMedicineRecords(temp);
							////////////////////////////////////////////////////////////////////////////////////////////
							if(temp.size()==10){
								temp = getMedicineService.find10NotSentRecords();
								//上次大于10条，这一次，可能只有0条或1条
								if(temp.size() <=1 ){
									ctx.close();
									logger.debug("end...");
									return;
								}
							}else{
								//已经发送完毕，无需再发送
								ctx.close();
								logger.debug("end...");
								return;
							}							
							sendEncode(channel2);
						}else{
							//重新发送
							//在这里，要把链接断开，然后重新链接，再发送
							ctx.close();
							channel2 = bootstrap.connect("123.125.247.101", 8098).sync().channel();
							sendEncode(channel2);
						}									
					}

					@Override
					public void userEventTriggered(ChannelHandlerContext ctx,
							Object evt) throws Exception {
						// TODO Auto-generated method stub
						//super.userEventTriggered(ctx, evt);
						if(evt instanceof IdleStateEvent){
							IdleStateEvent e = (IdleStateEvent)evt;
							if(e.state() == IdleState.WRITER_IDLE){
								
								logger.debug("timeout....................");
								//在这里，要把链接断开，然后重新链接，再发送
								logger.debug("关闭以前的链接");
								ctx.close();
								logger.debug("打开了新的链接，重新发送该数据，begin...");
								channel2 = bootstrap.connect("123.125.247.101", 8098).sync().channel();
								sendEncode(channel2);
							}
						}
					}	
				});
			}
			 
		 });
	
	}
	
	public QuartzJob(){
		
	}
	@Scheduled(fixedRate=1000*60*30)
	public void work(){		
		
		try {
			logger.debug("上传begin...");
			temp = getMedicineService.find10NotSentRecords();
			//至少接受两条数据，所以如果只有一条数据的话，则留到下一次一块发送
			if(temp.size() <= 1)
			{
				logger.debug("无数据要上传end....");
				return;
			}
			channel2 = bootstrap.connect("123.125.247.101", 8098).sync().channel();
//			channel2 = bootstrap.connect("192.168.1.222", 6000).sync().channel();
			sendEncode(channel2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	private void sendEncode(Channel channel){
		try {
			try {
				logger.debug(encoder.encode2String(temp));
					channel.writeAndFlush(encoder.encode2String(temp)).addListener(new ChannelFutureListener() {
						@Override
						public void operationComplete(ChannelFuture arg0) throws Exception {
//							logger.debug("数据发送成功........"+arg0.isSuccess());
						}
					});
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
