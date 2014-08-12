package name.yumao.zhanqi.mina;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;

import name.yumao.zhanqi.utils.NumUtils;
import name.yumao.zhanqi.mina.factory.HexCodecFactory;
import name.yumao.zhanqi.vo.RoomFlashvars;
import name.yumao.zhanqi.vo.ServersVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import sun.misc.BASE64Decoder;

public class ContentMinaThread implements Runnable{
	private static Logger logger = Logger.getLogger(ContentMinaThread.class);
	private RoomFlashvars flashVars;
	private JTextField inNum;
	private JButton butnSure;
	private String Server_Host;
	private int Server_Port;
	public ContentMinaThread(RoomFlashvars flashVars,JTextField inNum,JButton butnSure) throws Exception{
		//获取弹幕服务器地址
		String base64Server = flashVars.getServers();
		BASE64Decoder base64Decoder = new BASE64Decoder();
		String servers = new String(base64Decoder.decodeBuffer(base64Server));
		servers = servers.substring(servers.indexOf("[{\"ip\""),servers.lastIndexOf("}"));
		List<ServersVo> serverList = new ArrayList<ServersVo>();
	    JSONArray jsonArray = JSONArray.fromObject(servers);
	    for ( int i = 0 ; i<jsonArray.size(); i++){          
	    	JSONObject jsonObject = jsonArray.getJSONObject(i);
	    	ServersVo vo = (ServersVo) JSONObject.toBean(jsonObject,ServersVo.class);
	    	serverList.add(vo);	            
	    }	
	    //随机选择弹幕服务器
	    ServersVo serversVo = serverList.get((int)(Math.random()*serverList.size()));
		Server_Host = serversVo.getIp();
		Server_Port = Integer.parseInt(serversVo.getPort());
		//拉起进程同时随机选择登陆服务器
		logger.info("随机连接弹幕服务器 " + Server_Host + ":" + Server_Port);
		//初始化
		this.flashVars = flashVars;
		this.inNum = inNum;
		this.butnSure = butnSure;
	}
	@Override
	public void run() {
		IoConnector connector = new NioSocketConnector();
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();
		chain.addLast("codec", new ProtocolCodecFilter(new HexCodecFactory()));
		connector.setHandler(new ContentMinaHandler(inNum,butnSure));
		IoSession session = null;
		try{
			ConnectFuture future = connector.connect(new InetSocketAddress(Server_Host,Server_Port));
			future.awaitUninterruptibly();
			session=future.getSession();
			//准备登录信息
			String timestamp = ((new Date().getTime())+"").substring(0,10);
			String roomId = flashVars.getRoomId();
//			logger.info("RoomID:"+roomId);
			double random = 0;
			while (random < 10000000){
				random = Math.random()*100000000;
			}
			String randomId = (int)random+"";
			String hexStr = "bbcc00000000"+(roomId.length()+92)+"00000010277b2266686f7374223a22222c22756964223a302c22636d646964223a226c6f67696e726571222c2274223a302c22676964223a"+
					NumUtils.strNum2Utf8(randomId)+"2c22736964223a224e7a6b30597a56694d544e6d4f47597a4d474930596d4a6c4e6a6b335a4752684f44677a596a49795a6a6b3d222c2274696d657374616d70223a"+
					NumUtils.strNum2Utf8(timestamp)+"2c22726f6f6d6964223a"+
					NumUtils.strNum2Utf8(roomId)+"7d";
			//发送登录信息
//			logger.info(hexStr);
			session.write(hexStr);
		}catch (Exception e) {
			logger.info("弹幕服务器连接失败!");
		}
	}
}
