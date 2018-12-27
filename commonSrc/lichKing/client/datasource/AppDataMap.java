package lichKing.client.datasource;

import java.util.LinkedHashMap;

import com.google.gwt.core.client.GWT;
import com.gwtent.reflection.client.Reflection;

/**
 *
 * @author catPan
 */
public class AppDataMap implements Reflection{
	
    private static final LinkedHashMap<String, String> WEEKMap = new LinkedHashMap<String, String>();
    /**
     * 星期
     * @return
     */
    public static LinkedHashMap<String, String> WEEK() {
        return WEEKMap;
    }
    static {
    	WEEKMap.put("0", "不限");
    	WEEKMap.put("1", "星期一");
    	WEEKMap.put("2", "星期二");
    	WEEKMap.put("3", "星期三");
    	WEEKMap.put("4", "星期四");
    	WEEKMap.put("5", "星期五");
    	WEEKMap.put("6", "星期六");
    	WEEKMap.put("7", "星期日");
    }

    private static final LinkedHashMap<String, String> MONTHMap = new LinkedHashMap<String, String>();
    /**
     * 月份
     * @return
     */
    public static LinkedHashMap<String, String> MONTH() {
        return MONTHMap;
    }
    static {
    	MONTHMap.put("0", "不限");
    	MONTHMap.put("1", "1月");
    	MONTHMap.put("2", "2月");
    	MONTHMap.put("3", "3月");
    	MONTHMap.put("4", "4月");
    	MONTHMap.put("5", "5月");
    	MONTHMap.put("6", "6月");
    	MONTHMap.put("7", "7月");
    	MONTHMap.put("8", "8月");
    	MONTHMap.put("9", "9月");
    	MONTHMap.put("10", "10月");
    	MONTHMap.put("11", "11月");
    	MONTHMap.put("12", "12月");
    }



    private static final LinkedHashMap<String, String> SYS_FLAGMap = new LinkedHashMap<String, String>();
    /**
     * 用户类型
     * @return
     */
    public static LinkedHashMap<String, String> SYS_FLAG() {
        return SYS_FLAGMap;
    }
    static {
    	SYS_FLAGMap.put("0", "普通用户");
    	SYS_FLAGMap.put("1", "系统管理员");
    	SYS_FLAGMap.put("11", "超级管理员");
    }

    private static final LinkedHashMap<String, String> OPEN_WAYMap = new LinkedHashMap<String, String>();
    /**
     * 注册的开通方式
     * @return
     */
    public static LinkedHashMap<String, String> OPEN_WAY() {
        return OPEN_WAYMap;
    }
    static {
    	OPEN_WAYMap.put("0", "平台注册");
    	OPEN_WAYMap.put("11", "管理员添加");
    }

    private static final LinkedHashMap<String, String> SEXMap = new LinkedHashMap<String, String>();
    /**
     * 性别
     * @return
     */
    public static LinkedHashMap<String, String> SEX() {
        return SEXMap;
    }
    static {
    	SEXMap.put("0", "男");
    	SEXMap.put("1", "女");
    }


    public static String getLoadingEmptyMessage() {
        return "<image src='"+GWT.getHostPageBaseURL()+"images/other/loading.gif' />Loading ...";
    }

    
}
