package name.yumao.zhanqi.utils;

public class NumUtils {
	public static String strNum2Utf8(String numbers){
		String u8s = "";
		for(int i=0;i<numbers.length();i++){
			u8s = u8s+ "3" + numbers.charAt(i);
		}
		return u8s;
	}
	
}
