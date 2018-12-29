package lichKing.client.server;

import lichKing.client.i18n.AppMessages;
import lichKing.client.i18n.SysMessages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.ConstantsWithLookup;

/**
 *
 * @author catPan
 */
public class MsgServer {

    private static ConstantsWithLookup MESSAGES=(AppMessages)GWT.create(AppMessages.class);
    private static ConstantsWithLookup SYSMSG=(SysMessages)GWT.create(SysMessages.class);
    
    /**
     * 多语言信息
     * @param key
     * @return 
     */
    public static String getMsg(String key) {
        try {
            return MsgServer.getSysMsg(key);
        } catch (java.util.MissingResourceException sysMsgEx) {
            try {
                return MsgServer.getAppMsg(key);
            } catch (java.util.MissingResourceException appMsgEx) {
//              return "多语言：<b>" + key + "</b> 未定义";
            	return key;
            }
        }
    }

    /**
     * 业务上的多语言信息
     * @param key
     * @return 
     */
    public static String getAppMsg(String key){
        if(key==null||key.length()==0){
            return "";
        }
        return MESSAGES.getString(key);
    }
    /**
     * 系统上的多语言信息
     * @param key
     * @return 
     */
    public static String getSysMsg(String key){
        if(key==null||key.length()==0){
            return "";
        }
        return SYSMSG.getString(key);
    }
}
