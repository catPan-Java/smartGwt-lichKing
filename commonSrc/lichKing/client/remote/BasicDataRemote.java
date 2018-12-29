package lichKing.client.remote;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lichKing.client.pojo.PageSql;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * 基础数据的remote
 * @author catPan
 */
public interface BasicDataRemote extends RemoteService
{
	/**
	 * Utility class for simplifing access to the instance of async service.
	 */
	public static class Util {
		private static BasicDataRemoteAsync instance;
		public static BasicDataRemoteAsync getInstance(){
			if (instance == null) {
				instance = (BasicDataRemoteAsync) GWT.create(BasicDataRemote.class);
				ServiceDefTarget target = (ServiceDefTarget) instance;
				target.setServiceEntryPoint(GWT.getModuleBaseURL() + "basicDataRemote.rpc");
			}
			return instance;
		}
	}
        public List<String> getAppCodes(String headerStr,String COMPANY_CODE_FK);

        public List<String> getUUID();

        @SuppressWarnings("rawtypes")
		public List getBasicData(String domainClassName,ArrayList<PageSql> pageSql);

        public Date getSeverDate();

}
