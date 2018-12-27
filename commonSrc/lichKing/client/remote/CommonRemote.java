/**
 * 
 */
package lichKing.client.remote;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lichKing.client.entity.BaseDomain;
import lichKing.client.pojo.Page;
import lichKing.client.pojo.PageSql;
import lichKing.client.pojo.TB;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * 通用操作的remote
 * @author catPan
 */
public interface CommonRemote extends RemoteService {

    /**
     * Utility class for simplifing access to the instance of async service.
     */
    public static class Util {

        private static CommonRemoteAsync instance;

        public static CommonRemoteAsync getInstance() {
            if (instance == null) {
                instance = (CommonRemoteAsync) GWT.create(CommonRemote.class);
                ServiceDefTarget target = (ServiceDefTarget) instance;
                target.setServiceEntryPoint(GWT.getModuleBaseURL() + "commonRemote.rpc");
            }
            return instance;
        }
    }

    public void batchDeleteDomains(String sql);

    public Date getServiceTime();
    
    public void batchDeleteDomains(List<TB> tbs);

    public void batchCascadeDeleteDomains(String domainClassName, ArrayList<PageSql> pageSql);

    @SuppressWarnings("rawtypes")
	public ArrayList commonSearch(Page page, String domainClassName);

    @SuppressWarnings("rawtypes")
	public List commonSearch(String domainClassName, ArrayList<PageSql> pageSql);

    public void batchDeleteThenBatchAdd(TB tb, List<BaseDomain> domains);

    public <T> BaseDomain cascadeUpdateItems(TB tb, BaseDomain domain);

    public <T> BaseDomain addOrModify(BaseDomain domain);

    public void addOrModifyNoReturn(BaseDomain domain);

    public <T> List<BaseDomain> batchAddOrModify(List<BaseDomain> domains);

    public void batchAddOrModifyNoReturn(List<BaseDomain> domains);


    public List searchCount(String clsName,String fnList,List valList,Map<String,String> conditions);
    
    public int searchCountNum(String clsName,String fnList,List valList,Map<String,String> conditions);
//    public void addLog(MyLogger log);

	void batchAddOrModifyNoReturn(List<BaseDomain> delDomains,
			List<BaseDomain> domains);

	List<BaseDomain> batchAddOrModify(List<BaseDomain> delDomains,
			List<BaseDomain> domains);
	public List<BaseDomain> batchAddOrModify(String delSql,List<BaseDomain> domains);
}
