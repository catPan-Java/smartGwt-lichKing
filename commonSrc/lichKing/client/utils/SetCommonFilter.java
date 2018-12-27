package lichKing.client.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import lichKing.client.entityAnnotation.EntityAnn;
import lichKing.client.pojo.PageSql;
import lichKing.client.server.AppParsServer;

/**
 * java对象中的共有字段，进行通用的赋值
 * @author catPan
 */
public class SetCommonFilter {
	
	public static void addValdFlag(ArrayList<PageSql> pageSql,String dmClsName){
		
		deleteExistSql(pageSql, new String[]{"domain.VALD_FLAG"});
        if(PojoCastRecord.isHadDomainField(dmClsName, "VALD_FLAG")){
        	pageSql.add(new PageSql.Builder("domain.VALD_FLAG", "=").content("1").build());
        }
	}
	public static void addValdFlag(ArrayList<PageSql> pageSql,Class dmCls){

		deleteExistSql(pageSql, new String[]{"domain.VALD_FLAG"});
         if(PojoCastRecord.isHadDomainField(dmCls, "VALD_FLAG")){
         	pageSql.add(new PageSql.Builder("domain.VALD_FLAG", "=").content("1").build());
         }
	}
	
	/**
	 * 为拼接的sql中加入公司和是否可用，2个常用条件
	 * @param pageSql
	 * @param dm
	 */
	public static void addComValSql(ArrayList<PageSql> pageSql,Class dmCls){
		deleteExistSql(pageSql, new String[]{"COMPANY_CODE_FK","domain.VALD_FLAG"});
        //每个主页面几乎都要验证，VALD_FLAG="1" 和 COMPANY_CODE_FK公司ID
		addValdFlag(pageSql, dmCls);
        if(PojoCastRecord.isHadDomainField(dmCls, "COMPANY_CODE_FK")){
        	pageSql.add(new PageSql.Builder("COMPANY_CODE_FK", "=").content(AppParsServer.getClientUser().getCOMPANY_CODE_FK()).build());
        }
		
	}
	public static void addComValSql(ArrayList<PageSql> pageSql,String dmClsName){
		deleteExistSql(pageSql, new String[]{"COMPANY_CODE_FK","domain.VALD_FLAG"});
        //每个主页面几乎都要验证，VALD_FLAG="1" 和 COMPANY_CODE_FK公司ID
		addValdFlag(pageSql, dmClsName);
        if(PojoCastRecord.isHadDomainField(dmClsName, "COMPANY_CODE_FK")){
        	//pageSql.add(new PageSql.Builder("COMPANY_CODE_FK", "=").content(AppParsServer.getClientUser().getCOMPANY_CODE_FK()).build());
        }
	}
	
	public static void deleteExistSql(ArrayList<PageSql> pageSql,String[] fields){
		ArrayList<PageSql> delSqls = new ArrayList<PageSql>();
		for(String field:fields){
			for(PageSql sql:pageSql){
				if(field.equals(sql.getField())){
					delSqls.add(sql);
				}
			}
		}
		if(!delSqls.isEmpty()){
			pageSql.removeAll(delSqls);
		}
	}

	public static void deleteExistSql(ArrayList<PageSql> pageSql,String[] fields,String dmClsName){
		ArrayList<PageSql> delSqls = new ArrayList<PageSql>();
		for(String field:fields){
			for(PageSql sql:pageSql){
				if(field.equals(sql.getField())){
					delSqls.add(sql);
				}
			}
		}
		if(!delSqls.isEmpty()){
			pageSql.removeAll(delSqls);
		}
	}

	/**
	 * 为通用下拉框，code表添加条件过滤
	 * @param annotation
	 * @return
	 */
	public static ArrayList<PageSql> commonSelectSql(EntityAnn annotation){
        ArrayList<PageSql> pageSqls=new ArrayList<PageSql>();
        pageSqls.add(new PageSql.Builder("COMMON_TYPE_PCODE", "=").
        		content(annotation.ResourceKeySame().length()>0?annotation.ResourceKeySame():annotation.ResourceKey()).build());
//        if(AppParsServer.getClientUser()!=null&&AppParsServer.getCompany()!=null){
//            pageSqls.add(new PageSql.Builder("COMPANY_CODE_FK", "=").content("1000").leftLevel("(").build());
//            pageSqls.add(new PageSql.Builder("COMPANY_CODE_FK", "=").content(AppParsServer.getCompany().getCOMPANY_CODE_ID()).andOr("or").rightLevel(")").build());
//        }else{
//            pageSqls.add(new PageSql.Builder("COMPANY_CODE_FK", "=").content("1000").build());
//        }
        pageSqls.add(new PageSql.Builder("VALD_FLAG", "=").content("1").build());
		
        return pageSqls;
	}
	

	private static Map<String,String> sFilterMap=new LinkedHashMap();
	
	public static void putFilterMapVal(String key,String val) {
		sFilterMap.put(key, val);
	}
	/**
	 * 为通用下拉框，不定实体表添加条件过滤
	 * @param annotation
	 * @return
	 */
	public static ArrayList<PageSql> domainSelectSql(EntityAnn annotation){
        ArrayList<PageSql> pageSqls = new ArrayList<PageSql>();

//        if(PojoCastRecord.isHadDomainField("ans.client." + annotation.ClassName(), "COMPANY_CODE_FK")){
//            pageSqls.add(new PageSql.Builder("domain.COMPANY_CODE_FK", "=").content(AppParsServer.getCompany().getCOMPANY_CODE_ID()).build());
//        }
        pageSqls.add(new PageSql.Builder("domain.VALD_FLAG", "=").content("1").build());
        if(annotation.onlyMineData()) {
            pageSqls.add(new PageSql.Builder("USER_ID", "=").content(AppParsServer.getClientUser().getUSER_ID()).build());
        }
//        70	1	正常
//        71	1	锁定
//        121	1	创建
//        123	1	关闭
        if(PojoCastRecord.isHadDomainField("ans.client." + annotation.ClassName(), "STATE")){
            pageSqls.add(new PageSql.Builder("STATE", "=").content("70").build());// 正常
        }
        
        if(annotation.searchFilterKV().length==1) {
        	pageSqls.add(new PageSql.Builder(annotation.searchFilterKV()[0], "=").content(sFilterMap.get(annotation.searchFilterKV()[0])).build());
        }else if(annotation.searchFilterKV().length==2) {
//        	if("info.swinery.Swinery_SIMPLE".equals(annotation.ClassName())){
//        		pageSqls.add(new PageSql.Builder(annotation.searchFilterKV()[0], "=").content(sFilterMap.get(annotation.searchFilterKV()[0])).build());
//        		pageSqls.add(new PageSql.Builder(annotation.searchFilterKV()[1], "=").content(sFilterMap.get(annotation.searchFilterKV()[1])).build());
//	    	}else{
	    		pageSqls.add(new PageSql.Builder(annotation.searchFilterKV()[0], "=").content(annotation.searchFilterKV()[1]).build());
//	    	}
        }else if(annotation.searchFilterKV().length>2) {
        	String key=annotation.searchFilterKV()[0];
            for (int i = 1; i < annotation.searchFilterKV().length; i++) {
                if (i == 1) {
                	pageSqls.add(new PageSql.Builder(key, "=").content(annotation.searchFilterKV()[i]).andOr("and").leftLevel("(").build());
                } else if (i == annotation.searchFilterKV().length - 1) {
                	pageSqls.add(new PageSql.Builder(key, "=").content(annotation.searchFilterKV()[i]).andOr("or").rightLevel(")").build());
                }else{
                	pageSqls.add(new PageSql.Builder(key, "=").content(annotation.searchFilterKV()[i]).andOr("or").build());
                }
            }
        }

        return pageSqls;
	}
}
