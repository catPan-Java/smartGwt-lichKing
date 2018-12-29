package lichKing.client.utils;

import java.util.ArrayList;
import java.util.List;

import lichKing.client.myException.DefaultCallback;
import lichKing.client.remote.BasicDataRemote;
import lichKing.client.server.AppParsServer;


/**
 *客户端生成UUID
 * @author catPan
 */
public class GwtUUID {

	private static final char[] CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray(); 
	/**
	 * Generate a random uuid of the specified length. Example: uuid(15) returns
	 * "VcydxgltxrVZSTV"
	 * 
	 * @param len
	 *            the desired number of characters
	 */
	public static String uuid(int len) {
		return uuid(len, CHARS.length);
	}
	/**
	 * Generate a random uuid of the specified length, and radix. Examples:
	 * <ul>
	 * <li>uuid(8, 2) returns "01001010" (8 character ID, base=2)
	 * <li>uuid(8, 10) returns "47473046" (8 character ID, base=10)
	 * <li>uuid(8, 16) returns "098F4D35" (8 character ID, base=16)
	 * </ul>
	 * 
	 * @param len
	 *            the desired number of characters
	 * @param radix
	 *            the number of allowable values for each character (must be <=
	 *            62)
	 */
	public static String uuid(int len, int radix) {
		if (radix > CHARS.length) {
			throw new IllegalArgumentException();
		}
		char[] uuid = new char[len];
		// Compact form
		for (int i = 0; i < len; i++) {
			uuid[i] = CHARS[(int)(Math.random()*radix)];
		}
		return new String(uuid);
	}
	/**
	 * Generate a RFC4122, version 4 ID. Example:
	 * "92329D39-6F5C-4520-ABFC-AAB64544E172"
	 */
	public static String uuid() {
		char[] uuid = new char[36];
		int r;

		// rfc4122 requires these characters
		uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
		uuid[14] = '4';

		// Fill in random data.  At i==19 set the high bits of clock sequence as
		// per rfc4122, sec. 4.1.5
		for (int i = 0; i < 36; i++) {
			if (uuid[i] == 0) {
				r = (int) (Math.random()*16);
				uuid[i] = CHARS[(i == 19) ? (r & 0x3) | 0x8 : r & 0xf];
			}
		}
		return new String(uuid);
	}
	
	public static String uuid32() {
		char[] uuid = new char[32];
		int r;

		// Fill in random data.  At i==19 set the high bits of clock sequence as
		// per rfc4122, sec. 4.1.5
		for (int i = 0; i < 32; i++) {
			if (uuid[i] == 0) {
				r = (int) (Math.random()*16);
				uuid[i] = CHARS[(i == 19) ? (r & 0x3) | 0x8 : r & 0xf];
			}
		}
		return new String(uuid);
	}
	
    
    private static List<String> appCodes=new ArrayList<String>();

    public static List<String> getAppCodes() {
        return appCodes;
    }

    public static void setAppCodes(List<String> appCodes) {
        GwtUUID.appCodes = appCodes;
    }
    
    private static String appCode;
    /**
     * 通过首字母获取下一个订单号
     * @param headerStr 首字母，用于区分不同单号，限4位
     * @return 
     */
    public static String getAppCode(String headerStr,String COMPANY_CODE_FK) {
        String header=headerStr.length()>4?headerStr.substring(0, 4):headerStr;
        appCode = getAppCodes().get(0);
        getAppCodes().remove(appCode);
        if (getAppCodes().isEmpty()) {
            createGwtAppCodes(header,COMPANY_CODE_FK);
        }
        return header+appCode;
    }

    public static String getAppCode(String headerStr) {
        String header=headerStr.length()>4?headerStr.substring(0, 4):headerStr;
        appCode = getAppCodes().get(0);
        getAppCodes().remove(appCode);
        if (getAppCodes().isEmpty()) {
            createGwtAppCodes(header,AppParsServer.getClientUser().getCOMPANY_CODE_FK());
        }
        return header+appCode;
    }
    
    /**
     * 生成订单页面相关的订单号
     * @param headerStr 首字母，用于区分不同单号，限4位
     */
    public static void createGwtAppCodes(String headerStr,String COMPANY_CODE_FK){
        BasicDataRemote.Util.getInstance().getAppCodes(headerStr.length()>4?headerStr.substring(0, 4):headerStr,COMPANY_CODE_FK,new DefaultCallback<List<String>>(){
            @Override
			public void onSuccess(List<String> result) {
                setAppCodes(result);
            }
        });
    }
//    
//    //UUID的List
//    private static List<String> uuids=new ArrayList<String>();
//    //UUID当前使用到的序号
//    private static int index=0;
//    private static String uuid="";
//
//    public static List<String> getUuids() {
//        return uuids;
//    }
//
//    public static void setUuids(List<String> uuids) {
//        GwtUUID.uuids = uuids;
//    }
//    /**
//     * 获取UUID
//     * @return 
//     */
//    public static String getUuid(){
//        uuid=getUuids().get(index);
//        index++;
//        if(index==9){
//            createGwtUUIDs();
//            index=0;
//        }
//        return uuid;
//    }
//    
//    /**
//     * 生成gwt客户端的UUID，默认生成10个，某些功能中可能需要一个唯一的ID
//     */
//    public static void createGwtUUIDs(){
//        BasicDataRemote.Util.getInstance().getUUID(new DefaultCallback<List<String>>(){
//            @Override
//			public void onSuccess(List<String> result) {
//                setUuids(result);
//            }
//        });
//    }
}
