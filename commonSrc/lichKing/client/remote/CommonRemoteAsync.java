package lichKing.client.remote;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lichKing.client.entity.BaseDomain;
import lichKing.client.pojo.Page;
import lichKing.client.pojo.PageSql;
import lichKing.client.pojo.TB;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 通用操作的remoteAsync
 * @author catPan
 */
public interface CommonRemoteAsync {
	
	/**
     * 批量删除、新增或修改实体类对象(假删除)
     * @param delDomains
     * @param domains
     * @param asyncCallback
     */
    public void batchAddOrModify(String delSql,List<BaseDomain> domains,AsyncCallback<List<BaseDomain>> defaultCallback);

    
    /**
     * 通用删除，一个实体有多个一对多时
     * @param tbs 要删除的实体定义的列表
     * @param defaultCallback 
     */
    public void batchDeleteDomains(List<TB> tbs, AsyncCallback<Void> defaultCallback);

    /**
     * 通用删除
     * @param pojoDeleteSql 拼接的sql语句
     * @param defaultCallback 
     */
    public void batchDeleteDomains(String pojoDeleteSql, AsyncCallback<Void> defaultCallback);

    /**
     * 获取服务端的时间
     * @param asyncCallback 
     */
    public void getServiceTime(AsyncCallback<Date> asyncCallback);

    /**
     * 级联删除实体类
     * @param domainClassName
     * @param pageSql
     * @param asyncCallback 
     */
    public void batchCascadeDeleteDomains(String domainClassName, ArrayList<PageSql> pageSql, AsyncCallback<Void> asyncCallback);

    @SuppressWarnings("rawtypes")
	public void commonSearch(Page page, String domainClassName, AsyncCallback<ArrayList> asyncCallback);

    /**
     * 通用查询
     * @param domainClassName 实体类的的类名
     * @param pageSql 附加的sql
     * @param asyncCallback 
     */
    @SuppressWarnings("rawtypes")
	public void commonSearch(String domainClassName, ArrayList<PageSql> pageSql, AsyncCallback<List> asyncCallback);

    /**
     * 批量删除后批量新增
     * @param tb 要删除的数据，tb对象包括表名、列和列的值，构成sql的删除语句
     * @param domains 要保存的对List
     * @param asyncCallback 
     */
    public void batchDeleteThenBatchAdd(TB tb, List<BaseDomain> domains, AsyncCallback<Void> asyncCallback);
 
    /**
     * 一对多时，更新many端时，删除原有的，再保存新的
     * @param tb 要删除many端的数据格式
     * @param domain one端的实体
     * @param asyncCallback 
     */
    public void cascadeUpdateItems(TB tb, BaseDomain domain, AsyncCallback<BaseDomain> asyncCallback);


    /**
     * 保存客户操作日志
     * @param log 日志对象
     * @param asyncCallback 
     */
//    public void addLog(MyLogger log, AsyncCallback<Void> asyncCallback);

    /**
     * 新增或修改实体类对象
     * @param domain 实体类对象
     * @param asyncCallback
     */
    public void addOrModify(BaseDomain domain, AsyncCallback<BaseDomain> asyncCallback);

    /**
     * 新增或修改实体类对象，无返回值（NoReturn）
     * @param domain 实体类对象
     * @param asyncCallback
     */
    public void addOrModifyNoReturn(BaseDomain domain, AsyncCallback<Void> asyncCallback);

    /**
     * 批量新增或修改实体类对象
     * @param domains 实体类对象List
     * @param asyncCallback
     */
    public void batchAddOrModify(List<BaseDomain> domains, AsyncCallback<List<BaseDomain>> asyncCallback);

    /**
     * 批量删除、新增或修改实体类对象
     * @param delDomains
     * @param domains
     * @param asyncCallback
     */
    public void batchAddOrModify(List<BaseDomain> delDomains,List<BaseDomain> domains, AsyncCallback<List<BaseDomain>> asyncCallback);

    /**
     * 批量新增或修改实体类对象，无返回值（NoReturn）
     * @param domains 实体类对象List
     * @param asyncCallback
     */
    public void batchAddOrModifyNoReturn(List<BaseDomain> domains, AsyncCallback<Void> asyncCallback);

    /**
     * 批量删除、新增或修改实体类对象，无返回值（NoReturn）
     * @param delDomains
     * @param domains
     * @param asyncCallback
     */
    public void batchAddOrModifyNoReturn(List<BaseDomain> delDomains,List<BaseDomain> domains, AsyncCallback<Void> asyncCallback);
    


    /**
     * 类似这样的需求 select u.USER_CODE,count(*) from sys_user u where u.USER_CODE in('1','2','3') GROUP BY u.USER_CODE;
     * @param clsName
     * @param fnList
     * @param valList
     * @param conditions
     * @param callback
     */
    public void searchCount(String clsName,String fnList,List valList,Map<String,String> conditions, AsyncCallback<List> callback);

    /**
     * 类似这样的需求 select count(*) from sys_user u where u.USER_CODE in('1','2','3');
     * @param clsName
     * @param fnList
     * @param valList
     * @param conditions
     * @param callback
     */
	void searchCountNum(String clsName, String fnList, List valList,Map<String, String> conditions, AsyncCallback<Integer> callback);
}
