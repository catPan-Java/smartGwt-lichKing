package lichKing.client.pojo;

import lichKing.server.sysServer.user.COMPANY;
import lichKing.server.sysServer.user.SYS_USER;
  
/**
 * 全局对象
 * @author catPan
 */
public class AppParameters {

    private static AppParameters instance = null;

    /**
     * 实例化控制
     * @return 
     */
    public static AppParameters getInstance() {
        if (instance == null) {
            instance = new AppParameters();
        }
        return instance;
    }
	private SYS_USER user;

    public SYS_USER getUser() {
        return user;
    }

    public void setUser(SYS_USER user) {
        this.user = user;
    }
    
    private COMPANY com;

    public COMPANY getCom() {
		return com;
	}

	public void setCom(COMPANY com) {
		this.com = com;
	}
	
    /**
     * 修改实例为空，并重新初始化
     */
    public static void clearPars(){
        instance = null;
        getInstance();
    }

    /**
     * 情况全局对象，设为null
     */
    private AppParameters() {
        user=null;
        com=null;
    }
}
