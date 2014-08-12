package name.yumao.zhanqi.http;

import name.yumao.zhanqi.vo.RoomFlashvars;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JavaIdentifierTransformer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

public class HttpClientFromZhanqi {
	public static RoomFlashvars QueryZhanqiVars(String roomName){
		String jsonStr = "";
		try{
			HttpGet get = new HttpGet("http://www.zhanqi.tv/"+roomName);
			HttpResponse response = ThreadHttpClient.getInstance().getHttpclient().execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
			    HttpEntity entity = response.getEntity(); 
			    String htmlEntity = EntityUtils.toString(entity);
			    jsonStr = htmlEntity.substring(htmlEntity.indexOf("\"flashvars\":")+"\"flashvars\":".length());
			    jsonStr = jsonStr.substring(0,jsonStr.indexOf("},")+1);
			}
			get.abort();
	    	JSONObject jsonObject = JSONObject.fromObject(jsonStr); 
	    	JsonConfig config = new JsonConfig();
	    	config.setJavaIdentifierTransformer(new JavaIdentifierTransformer() {
	    		public String transformToJavaIdentifier(String str) {
	    	    	char[] chars = str.toCharArray();
	    	    	chars[0] = Character.toLowerCase(chars[0]);
	    	        return new String(chars);
	    	    }
	    	});
	    	config.setRootClass(RoomFlashvars.class);
	    	Object obj = JSONObject.toBean(jsonObject , config);
	    	return (RoomFlashvars)obj;
		}catch (Exception e) {
			return null;
		}
	}
//	public static List<LoginServerVo> QueryLoginServer(String roomNum){
//		
//		List<LoginServerVo> loginServerList = null;
//		try{
//			HttpGet get = new HttpGet("http://www.douyu.tv/"+roomNum);
//			HttpResponse response = ThreadHttpClient.getInstance().getHttpclient().execute(get);
//			if (response.getStatusLine().getStatusCode() == 200) {
//				HttpEntity entity = response.getEntity(); 
//			    String htmlEntity = EntityUtils.toString(entity);
//			    String server_vars;
//			    server_vars = htmlEntity.substring(htmlEntity.indexOf("\"server_config\":\"")+"\"server_config\":\"".length());
//			    server_vars = server_vars.substring(0,server_vars.indexOf("\"};"));
//			    String serverList = URLDecoder.decode(server_vars,"UTF-8");
//			    loginServerList = ServerUtils.QueryLoginServerList(serverList);
//			}
//		}catch (Exception e) {
//			return loginServerList;
//		}
//		return loginServerList;
//	}
//	public static String QueryDouyuDownloadUrl(String roomNum){
//		String downloadUrl = "";
//		try{
//			HttpGet get = new HttpGet("http://www.douyu.tv/"+roomNum);
//			HttpResponse response = ThreadHttpClient.getInstance().getHttpclient().execute(get);
//			if (response.getStatusLine().getStatusCode() == 200) {
//				HttpEntity entity = response.getEntity(); 
//			    String htmlEntity = EntityUtils.toString(entity);
//			    String rtmp_addr = htmlEntity.substring(htmlEntity.indexOf("{\"rtmp_url\":"));
//			    rtmp_addr = rtmp_addr.substring(0,rtmp_addr.indexOf("},") + 1);
//			    downloadUrl = ServerUtils.QueryDouyuDownloadUrl(rtmp_addr);
//			}
//		}catch (Exception e) {
//			return downloadUrl;
//		}
//		return downloadUrl;
//	}
//	public static boolean isOnline(String roomNum){
//		String status = "";
//		try{
//			HttpGet get = new HttpGet("http://www.douyu.tv/"+roomNum);
//			HttpResponse response = ThreadHttpClient.getInstance().getHttpclient().execute(get);
//			if (response.getStatusLine().getStatusCode() == 200) {
//			    HttpEntity entity = response.getEntity(); 
//			    String htmlEntity = EntityUtils.toString(entity);
//			    status = htmlEntity.substring(htmlEntity.indexOf("flashvars.Status = ")+"flashvars.Status = ".length());
//			    status = status.substring(0,status.indexOf(";"));
//			}
//			get.abort();
//		}catch (Exception e) {
//			return false;
//		}
//		return Boolean.parseBoolean(status);
//	}
//	public static void main(String ar[]){
//		System.out.println(QueryLoginServer("582"));
//	}
}
