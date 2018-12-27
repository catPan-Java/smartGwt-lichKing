package lichKing.client.server;

import java.util.Date;

import lichKing.client.pojo.AppParameters;
import lichKing.client.remote.BasicDataRemote;
import lichKing.server.sysServer.user.COMPANY;
import lichKing.server.sysServer.user.SYS_USER;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author catPan
 */
public class AppParsServer {


	/**
	 * 获取登录时的服务器时间
	 * @return
	 */
	public static Date getServerDate(){
		return AppParameters.getInstance().getUser().getLoginDate();
	}
    
    /**
     * 返回客户端当前登录的用户实体
     * @return 
     */
    public static SYS_USER getClientUser(){
        return AppParameters.getInstance().getUser();
    }
    /**
     * 客户端保存登录用户信息
     * @param user 
     */
    public static void setClientUser(SYS_USER user){
    	setLoginDate(user);
        AppParameters.getInstance().setUser(user);
    }
    /**
     * 客户端保存登录本公司信息
     * @param user 
     */
    public static void setClientCom(COMPANY com){
        AppParameters.getInstance().setCom(com);
    }

    public static COMPANY getCompany(){
        return AppParameters.getInstance().getCom();
    }

    
    /**
     * 清空客户端人员、组织、项目的信息
     */
    public static void clearAllCurrentPars(){
        AppParameters.clearPars();
    }
    
    public static void setLoginDate(final SYS_USER user){
    	BasicDataRemote.Util.getInstance().getSeverDate(new AsyncCallback<Date>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Date result) {
				user.setLoginDate(result);
			}
    		
		});
    }
}
