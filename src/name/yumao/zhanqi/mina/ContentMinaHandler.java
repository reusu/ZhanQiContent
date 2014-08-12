package name.yumao.zhanqi.mina;

//import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JTextField;

import name.yumao.zhanqi.swing.ToolTip;
import name.yumao.zhanqi.vo.ContentVo;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class ContentMinaHandler implements IoHandler {
	private ToolTip tip;
	private JTextField inNum;
	private JButton butnSure;
	private static Logger logger = Logger.getLogger(ContentMinaHandler.class);
	public ContentMinaHandler(JTextField inNum,JButton butnSure){
		this.inNum = inNum;
		this.butnSure = butnSure;
	}
	@Override
	public void exceptionCaught(IoSession session, Throwable arg1)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void messageReceived(IoSession session, Object massage) throws Exception {
		if(tip == null){
			tip = new ToolTip();
		}
//		logger.info("受到返回的信息: " + massage.toString().substring(11,massage.toString().length()-1));
		String resMsg = massage.toString().substring(11,massage.toString().length()-1);
    	JSONObject jsonObject = JSONObject.fromObject(resMsg); 
    	ContentVo vo = (ContentVo) JSONObject.toBean(jsonObject,ContentVo.class);
    	if(vo.getCmdid().equals("loginresp")){
			tip.setToolTip("登陆的房间号为: "+inNum.getText());
			logger.info("登陆的房间号为: "+inNum.getText());
			Thread.sleep(100);
			tip.setToolTip("获取匿名登录的用户名为 : " + vo.getNickname());
			logger.info("获取匿名登录的用户名为 : " + vo.getNickname());
			Thread.sleep(100);
			//启动弹幕获取进程
			tip.setToolTip("启动弹幕获取进程...");
			logger.info("启动弹幕获取进程...");
			Thread.sleep(100);
			tip.setToolTip("战旗TV评论小助手启动完毕!");
			logger.info("战旗TV评论小助手启动完毕!");
    	}else if(vo.getCmdid().equals("useprop")){
    		tip.setToolTip(vo.getFromname() + " 送给 " + vo.getToname() + " ["+vo.getCount()+"]个桃子");
    		logger.info(vo.getFromname() + " 送给 " + vo.getToname() + " ["+vo.getCount()+"]一个桃子");
    	}else if(vo.getCmdid().equals("chatmessage")){
    		tip.setToolTip(vo.getFromname() + " : " + vo.getContent());
    		logger.info(vo.getFromname() + " : " + vo.getContent());
    	}
	}
	@Override
	public void messageSent(IoSession session, Object massage) throws Exception {
		// TODO Auto-generated method stub
//		String msg =new String(HexUtils.HexString2Bytes(massage.toString().substring(11,massage.toString().length()-1)),"utf-8") ;
//		logger.info("发给服务器的信息: " + msg);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		tip.setToolTip("失去与弹幕服务器的连接");
		logger.info("失去与弹幕服务器的连接");
		inNum.setEditable(true);
		butnSure.setEnabled(true);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
