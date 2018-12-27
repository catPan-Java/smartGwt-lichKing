package lichKing.client.remote;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lichKing.client.pojo.PageSql;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 基础数据的remoteAsync
 * @author catPan
 */
public interface BasicDataRemoteAsync
{
    public void getAppCodes(String headerStr,String COMPANY_CODE_FK,AsyncCallback<List<String>> defaultCallback);

    public void getUUID(AsyncCallback<List<String>> defaultCallback);
    /**
     * 获取基础数据的通用查询
     * @param domainClassName 实体类的的类名
     * @param pageSql 附加的sql
     * @param asyncCallback 
     */
    @SuppressWarnings("rawtypes")
	public void getBasicData(String domainClassName, ArrayList<PageSql> pageSql, AsyncCallback<List> asyncCallback);

	void getSeverDate(AsyncCallback<Date> callback);
    
}
