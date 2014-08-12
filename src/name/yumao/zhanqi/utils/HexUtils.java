package name.yumao.zhanqi.utils;


import org.apache.mina.core.buffer.IoBuffer;

public class HexUtils {
//    private static byte[] decodeHex(String hex)
//    {
//        char[] chars = hex.toCharArray();
////        for (int i = 0; i < chars.length; i++)
////        {
////            Console.WriteLine(chars[i]);
////        }
////        Console.WriteLine();
//        byte[] bytes = new byte[chars.length / 2];
//        int byteCount = 0;
//        for (int i = 0; i < chars.length; i += 2)
//        {
//            byte newByte = 0x00;
//            newByte |= hexCharToByte(chars[i]);
//            newByte <<= 4;
//            newByte |= hexCharToByte(chars[i + 1]);
//            bytes[byteCount] = newByte;
//            byteCount++;
//        }
//        return bytes;
//    } 
//    private static byte hexCharToByte(char ch)
//    {
//        switch (ch)
//        {
//            case '0': return 0x00;
//            case '1': return 0x01;
//            case '2': return 0x02;
//            case '3': return 0x03;
//            case '4': return 0x04;
//            case '5': return 0x05;
//            case '6': return 0x06;
//            case '7': return 0x07;
//            case '8': return 0x08;
//            case '9': return 0x09;
//            case 'a': return 0x0A;
//            case 'b': return 0x0B;
//            case 'c': return 0x0C;
//            case 'd': return 0x0D;
//            case 'e': return 0x0E;
//            case 'f': return 0x0F;
//        }
//        return 0x00;
//    }
    
    public static IoBuffer hexString2IoBuffer(String hexString){
//    	System.out.println(hexString);
    	IoBuffer ioBuffer = IoBuffer.allocate(8);
    	ioBuffer.setAutoExpand(true);
    	ioBuffer.put(HexString2Bytes(hexString));
    	ioBuffer.flip(); 
    	return ioBuffer;
    }
    public static String ioBufferToString(Object message) throws Exception   
    {   
          if (!(message instanceof IoBuffer))   
          {   
            return "";   
          }   
          IoBuffer ioBuffer = (IoBuffer) message;   
          byte[] b = new byte [ioBuffer.limit()];   
          ioBuffer.get(b);   
          String bb = new String(b,"utf-8");
//          bb = bb.substring(bb.indexOf("type"),bb.indexOf("@Srg"));
//          StringBuffer stringBuffer = new StringBuffer();   
//          for (int i = 12; i < b.length; i++)   
//          {   
//           stringBuffer.append((char) b [i]);   
//          }   
           return bb;
//          return new String(stringBuffer.toString().getBytes());
    }  
    
    
    private final static byte[] hex = "0123456789ABCDEF".getBytes();  
    private static int parse(char c) {  
        if (c >= 'a')  
            return (c - 'a' + 10) & 0x0f;  
        if (c >= 'A')  
            return (c - 'A' + 10) & 0x0f;  
        return (c - '0') & 0x0f;  
    }  
    // 从字节数组到十六进制字符串转换  
    public static String Bytes2HexString(byte[] b) {  
        byte[] buff = new byte[3 * b.length];  
        for (int i = 0; i < b.length; i++) {  
            buff[3 * i] = hex[(b[i] >> 4) & 0x0f];  
            buff[3 * i + 1] = hex[b[i] & 0x0f];  
            buff[3 * i + 2] = 45;  
        }  
        String re = new String(buff);  
        return re.replace("-", " ");  
    }  
    // 从字节数组到十六进制字符串转换  
    public static String Bytes2HexStringLower(byte[] b) {  
        byte[] buff = new byte[3 * b.length];  
        for (int i = 0; i < b.length; i++) {  
            buff[3 * i] = hex[(b[i] >> 4) & 0x0f];  
            buff[3 * i + 1] = hex[b[i] & 0x0f];  
            buff[3 * i + 2] = 45;  
        }  
        String re = new String(buff);  
        return re.replace("-", "").toLowerCase();  
    } 
    // 从十六进制字符串到字节数组转换  
    public static byte[] HexString2Bytes(String hexstr) {  
        hexstr = hexstr.replace(" ", "");  
        byte[] b = new byte[hexstr.length() / 2];  
        int j = 0;  
        for (int i = 0; i < b.length; i++) {  
            char c0 = hexstr.charAt(j++);  
            char c1 = hexstr.charAt(j++);  
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));  
        }  
        return b;  
    }  
    
    public static String setStringHeader(String hexstr){
    	String length = Integer.toHexString((hexstr.length()+8)/2)+"000000" ;
    	return length+length+hexstr;
    }
}
