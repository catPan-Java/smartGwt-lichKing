package lichKing.client.server;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import lichKing.client.datasource.AppDataMap;
import lichKing.client.entity.APP_COMMON_TYPE;
import lichKing.client.entityAnnotation.EntityAnn;
import lichKing.client.entityAnnotation.MyClassTypeUtil;
import lichKing.client.myException.DefaultCallback;
import lichKing.client.pojo.Page;
import lichKing.client.pojo.PageSql;
import lichKing.client.remote.BasicDataRemote;
import lichKing.client.remote.CommonRemote;
import lichKing.client.utils.ResultBuild;
import lichKing.client.utils.SetCommonFilter;

import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.Field;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 *
 * @author catPan
 */
public class BasicDataServer {



	public static void setCommonSelectItem(final SelectItem sText, final String className, ArrayList<PageSql> pageSql,final String type) {
        CommonRemote.Util.getInstance().commonSearch(new Page.Builder().pageSql(pageSql).maxResults(30).build(),className, new DefaultCallback<ArrayList>() {

            @SuppressWarnings("unchecked")
			@Override
            public void onSuccess(ArrayList results) {
                LinkedHashMap<String, String> selectMap = new LinkedHashMap<String, String>();
                List<APP_COMMON_TYPE> commonTypes=results;
                for (APP_COMMON_TYPE res : commonTypes) {
                        selectMap.put(res.getCOMMON_TYPE_ID()+"", res.getCOMMON_TYPE_NAME());
                }
                if (results.size() > 0) {
                	if(type.equals("condition")){
                		selectMap.put("all", "-- 所有 --");
                	}
                	sText.setValueMap(selectMap);
                }
            }
        });
    }
	
	public static void setCommonSelectItem(final SelectItem sText, final String className, ArrayList<PageSql> pageSql) {
        CommonRemote.Util.getInstance().commonSearch(new Page.Builder().pageSql(pageSql).maxResults(30).build(),className, new DefaultCallback<ArrayList>() {

            @SuppressWarnings("unchecked")
			@Override
            public void onSuccess(ArrayList results) {
                LinkedHashMap<String, String> selectMap = new LinkedHashMap<String, String>();
                List<APP_COMMON_TYPE> commonTypes=results;
                for (APP_COMMON_TYPE res : commonTypes) {
                        selectMap.put(res.getCOMMON_TYPE_ID()+"", res.getCOMMON_TYPE_NAME());
                }
                if (results.size() > 0) {
                	sText.setValueMap(selectMap);
                }
            }
        });
    }
	
	public static void setCommonSelectItem(final StaticTextItem sText, final String className, ArrayList<PageSql> pageSql) {
        CommonRemote.Util.getInstance().commonSearch(new Page.Builder().pageSql(pageSql).maxResults(30).build(),className, new DefaultCallback<ArrayList>() {

            @SuppressWarnings("unchecked")
			@Override
            public void onSuccess(ArrayList results) {
                LinkedHashMap<String, String> selectMap = new LinkedHashMap<String, String>();
                List<APP_COMMON_TYPE> commonTypes=results;
                for (APP_COMMON_TYPE res : commonTypes) {
                        selectMap.put(res.getCOMMON_TYPE_ID()+"", res.getCOMMON_TYPE_NAME());
                }
                if (results.size() > 0) {
                	sText.setValueMap(selectMap);
                }
            }
        });
    }
    @SuppressWarnings("rawtypes")
	public static void setCommonSelectItem(final ListGridField lgf, final String className, ArrayList<PageSql> pageSql) {
        CommonRemote.Util.getInstance().commonSearch(new Page.Builder().pageSql(pageSql).maxResults(30).build(),className, new DefaultCallback<ArrayList>() {

            @SuppressWarnings("unchecked")
			@Override
            public void onSuccess(ArrayList results) {
                LinkedHashMap<String, String> selectMap = new LinkedHashMap<String, String>();
                List<APP_COMMON_TYPE> commonTypes=results;
                for (APP_COMMON_TYPE res : commonTypes) {
                        selectMap.put(res.getCOMMON_TYPE_ID()+"", res.getCOMMON_TYPE_NAME());
                }
                if (results.size() > 0) {
                    lgf.setValueMap(selectMap);
                }
            }
        });
    }
    
    @SuppressWarnings("rawtypes")
	public static void setCommonSelectItem(final DataSourceField SDF, final String className, ArrayList<PageSql> pageSql) {
        CommonRemote.Util.getInstance().commonSearch(new Page.Builder().pageSql(pageSql).maxResults(100).build(),className , new DefaultCallback<ArrayList>() {

            @SuppressWarnings("unchecked")
			@Override
            public void onSuccess(ArrayList results) {
                LinkedHashMap<String, String> selectMap = new LinkedHashMap<String, String>();

                List<APP_COMMON_TYPE> commonTypes=results;
                for (APP_COMMON_TYPE res : commonTypes) {
                        selectMap.put(res.getCOMMON_TYPE_ID()+"", res.getCOMMON_TYPE_NAME());
                }
                if (results.size() > 0) {
                    SDF.setValueMap(selectMap);
                }
            }
        });
    }

    @SuppressWarnings("rawtypes")
	public static void setCascadeSelect(final DynamicForm form,final String parentCode,final String subCode,final Class subClass,String parentValue){
		ArrayList<PageSql> pageSqls=new ArrayList<PageSql>();
        pageSqls.add(new PageSql.Builder(parentCode, "=").content(parentValue).build());
    	setSelectItem4CommonDB(form.getItem(subCode), subClass.getName(), pageSqls,"Modify");
    }
    
	public static void setCascadeSelect(FormItem FItem,final String parentCode,final String subCode,final Class subClass,String parentValue){
		ArrayList<PageSql> pageSqls=new ArrayList<PageSql>();
        pageSqls.add(new PageSql.Builder(parentCode, "=").content(parentValue).build());
    	setSelectItem4CommonDB(FItem, subClass.getName(), pageSqls,"Modify");
    }

    /**
     * 添加级联下拉框事件
     * @param form 下拉框所在的表单
     * @param parentCode 父下拉框的ResourceKey
     * @param subCode 级联下拉框的ResourceKey
     * @param subClass 记录下拉框的实体类的类型
     */
    @SuppressWarnings("rawtypes")
	public static void addSelectChangeHandler(final DynamicForm form,final String parentCode,final String subCode,final Class subClass){
    	form.getItem(parentCode).addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				BasicDataServer.cascadeSelect(form, parentCode, subCode, subClass);
			}
		});
    }
    
    /**
     * 联动数据
     * @param form 下拉框所在的表单
     * @param parentCode 父下拉框的ResourceKey
     * @param subCode 级联下拉框的ResourceKey
     * @param subClass 记录下拉框的实体类的类型
     */
    @SuppressWarnings("rawtypes")
	public static void cascadeSelect(DynamicForm form,String parentCode,String subCode,Class subClass){
		ArrayList<PageSql> pageSqls=new ArrayList<PageSql>();
        pageSqls.add(new PageSql.Builder(parentCode, "=").content(form.getValueAsString(parentCode)).build());
        BasicDataServer.setSelectItem4CommonDB(form.getItem(subCode), subClass.getName(), pageSqls,"Add");
    }
    
//    public static LinkedHashMap<String, Object> selectDB=new LinkedHashMap<String, Object>();

//    public static LinkedHashMap<String, Object> getSelectDB() {
//		return selectDB;
//	}

	@SuppressWarnings("rawtypes")
	public static void setSelectItem4CommonDB(final FormItem FItem, final String className, ArrayList<PageSql> pageSql,final String type) {
		if(FItem!=null){
			SetCommonFilter.addComValSql(pageSql, className);
	//        CommonRemote.Util.getInstance().commonSearch(className, pageSql, new DefaultCallback<List>() {
	        CommonRemote.Util.getInstance().commonSearch(new Page.Builder().pageSql(pageSql).maxResults(100).build(),className, new DefaultCallback<ArrayList>() {
	
	            @Override
	            public void onSuccess(ArrayList results) {
	                ClassType classType = MyClassTypeUtil.getDomainClassType(className);
	
	                final LinkedHashMap<String, String> selectMap = new LinkedHashMap<String, String>();
	                String key = "";
	                String value = "";
	                String selectCodeField = "";
	                String selectTextField = "";
	                Field[] fields = MyClassTypeUtil.getDomainClassFields(className);
	                boolean hasAnnotation = false;
	                EntityAnn annotation;
	
	                for (int i = 0; i < fields.length; i++) {
	                    hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
	                    if (hasAnnotation) {
	                        annotation = fields[i].getAnnotation(EntityAnn.class);
	                        if (annotation.IsSelectCode()) {
	                            selectCodeField = annotation.ResourceKey();
	                        } else if (annotation.IsSelectText()) {
	                            selectTextField = annotation.ResourceKey();
	                        }
	                    }
	                }
	                for (Object res : results) {
	                    if (selectCodeField.length() > 0 && selectTextField.length() > 0) {
	                        key = classType.invoke(res, "get" + selectCodeField).toString();
	                        value = classType.invoke(res, "get" + selectTextField).toString();
	                        selectMap.put(key, value);
	                        FItem.setAttribute(key, res);
	                    }
	                }
	                if (results.size() > 0) {
	                	if(type.equals("Add")){
	                		FItem.setValueMap(selectMap);
	                		FItem.setValue(selectMap.keySet().toArray()[0]);
	                		FItem.fireEvent(new ChangedEvent(FItem.getConfig()));
	                	}else if(type.equals("kong")){
	                		selectMap.put("kong", " ");
	                		FItem.setValueMap(selectMap);
	                	}else{
	                		FItem.setValueMap(selectMap);
	                	}
	                }else{
	                	FItem.clearValue();
	                	FItem.setValueMap(new LinkedHashMap<String, String>());
	                }
	            }
	        });
		}
    }
	
	/**
	 * 为自动提示的下拉框赋值
	 * @param FItem
	 * @param className
	 * @param pageSql
	 */
	public static void setCbI4DB(final FormItem FItem, final String className, ArrayList<PageSql> pageSql) {
		SetCommonFilter.addComValSql(pageSql, className);
		Page page=new Page.Builder().pageSql(pageSql).maxResults(10).build();
        CommonRemote.Util.getInstance().commonSearch(page,className, new DefaultCallback<ArrayList>() {

            @Override
            public void onSuccess(ArrayList results) {
                ClassType classType = MyClassTypeUtil.getDomainClassType(className);

                String key = "";
                String value = "";
                String selectCodeField = "";
                String selectTextField = "";
                Field[] fields = MyClassTypeUtil.getDomainClassFields(className);
                boolean hasAnnotation = false;
                EntityAnn annotation;

                for (int i = 0; i < fields.length; i++) {
                    hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
                    if (hasAnnotation) {
                        annotation = fields[i].getAnnotation(EntityAnn.class);
                        if (annotation.IsSelectCode()) {
                            selectCodeField = annotation.ResourceKey();
                        } else if (annotation.IsSelectText()) {
                            selectTextField = annotation.ResourceKey();
                        }
                    }
                }
                if (results!=null&&results.size() > 0) {

    				DataSource ds = new DataSource();
    				ds.setClientOnly(true);
    				DataSourceTextField codeF = new DataSourceTextField(selectCodeField, "code");
    				DataSourceTextField valF = new DataSourceTextField(selectTextField, "value");
    				codeF.setPrimaryKey(true);
    				ds.setFields(codeF,valF);
    				
	                for (Object res : results) {
	                    if (selectCodeField.length() > 0 && selectTextField.length() > 0) {
	                        key = classType.invoke(res, "get" + selectCodeField).toString();
	                        value = classType.invoke(res, "get" + selectTextField).toString();

	        					ListGridRecord record = new ListGridRecord();
	        					record.setAttribute(selectCodeField, key);
	        					record.setAttribute(selectTextField, value);
	        					ds.addData(record);
	                            FItem.setAttribute(key, res);
	                    }
	                }
	                FItem.setOptionDataSource(ds);
	                if("ComboBoxItem".equals(FItem.getType())){
		                FItem.setValueField(selectCodeField);
		                FItem.setDisplayField(selectTextField);
	                }
                }else{
                	FItem.setValueMap(new LinkedHashMap<String, String>());
                }
            }
        });
    }

	public static void setCbI4DB_LG(final FormItem FItem, final String className, ArrayList<PageSql> pageSql) {
		SetCommonFilter.addComValSql(pageSql, className);
		Page page=new Page.Builder().pageSql(pageSql).maxResults(10).build();
        CommonRemote.Util.getInstance().commonSearch(page,className, new DefaultCallback<ArrayList>() {

            @Override
            public void onSuccess(ArrayList results) {
                ClassType classType = MyClassTypeUtil.getDomainClassType(className);

                String key = "";
                String value = "";
//                String selectCodeField = "";
//                String selectTextField = "";
                Field[] fields = MyClassTypeUtil.getDomainClassFields(className);
                boolean hasAnnotation = false;
                EntityAnn annotation;
				DataSource ds = new DataSource();
				ds.setClientOnly(true);

                for (int i = 0; i < fields.length; i++) {
                    hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
                    if (hasAnnotation) {
                        annotation = fields[i].getAnnotation(EntityAnn.class);
                        DataSourceTextField field=new DataSourceTextField(
                        		annotation.ResourceKey(), annotation.ResourceKey());
                        ds.addField(field);
                        if(annotation.IsPrimaryKey()){
                        	field.setPrimaryKey(true);
                        }
                    }
                }
                if (results!=null&&results.size() > 0) {
    				
	                for (Object res : results) {
                        ListGridRecord record = new ListGridRecord();
	                    for (int i = 0; i < fields.length; i++) {
	                        hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
	                        if (hasAnnotation) {
	                            annotation = fields[i].getAnnotation(EntityAnn.class);
	                            record.setAttribute(annotation.ResourceKey(), 
	                            		classType.invoke(res, "get" + annotation.ResourceKey()).toString());
	                        }
	                    }
	        			ds.addData(record);
	        			record.setAttribute("domain", res);
	                }
	                FItem.setOptionDataSource(ds);
                }
            }
        });
    }
	
    @SuppressWarnings("rawtypes")
	public static void setSelectItem4CommonDB(final DataSourceField SDF, final String className, ArrayList<PageSql> pageSql) {
    	SetCommonFilter.addComValSql(pageSql, className);
//        CommonRemote.Util.getInstance().commonSearch(className, pageSql, new DefaultCallback<List>() {
        CommonRemote.Util.getInstance().commonSearch(new Page.Builder().pageSql(pageSql).maxResults(100).build(),className, new DefaultCallback<ArrayList>() {

            @Override
            public void onSuccess(ArrayList results) {
                ClassType classType = MyClassTypeUtil.getDomainClassType(className);
//                Constructor constructor = classType.findConstructor(new String[]{});
//                Object domain = constructor.newInstance();

                LinkedHashMap<String, String> selectMap = new LinkedHashMap<String, String>();
                
                String key = "";
                String value = "";
                String selectCodeField = "";
                String selectTextField = "";
                Field[] fields = MyClassTypeUtil.getDomainClassFields(className);
                boolean hasAnnotation = false;
                EntityAnn annotation;

                for (int i = 0; i < fields.length; i++) {
                    hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
                    if (hasAnnotation) {
                        annotation = fields[i].getAnnotation(EntityAnn.class);
                        if (annotation.IsSelectCode()) {
                            selectCodeField = annotation.ResourceKey();
                        } else if (annotation.IsSelectText()) {
                            selectTextField = annotation.ResourceKey();
                        }
                    }
                }
                for (Object res : results) {
                    if (selectCodeField.length() > 0 && selectTextField.length() > 0) {
                        key = classType.invoke(res, "get" + selectCodeField).toString();
                        value = classType.invoke(res, "get" + selectTextField).toString();
                        selectMap.put(key, value);
                        SDF.setAttribute(key, res);
                    }
                }
                if (results.size() > 0) {
                    SDF.setValueMap(selectMap);
                }
            }
        });
    }

    @SuppressWarnings("rawtypes")
	public static void setSelectItem4CommonDB(final ListGridField LGF, final String className, ArrayList<PageSql> pageSql) {
    	SetCommonFilter.addComValSql(pageSql, className);
    	Page page=new Page.Builder().pageSql(pageSql).maxResults(100).build();
        CommonRemote.Util.getInstance().commonSearch(page,className, new DefaultCallback<ArrayList>() {

            @Override
            public void onSuccess(ArrayList results) {
                ClassType classType = MyClassTypeUtil.getDomainClassType(className);

                LinkedHashMap<String, String> selectMap = new LinkedHashMap<String, String>();
                String key = "";
                String value = "";
                String selectCodeField = "";
                String selectTextField = "";
                Field[] fields = MyClassTypeUtil.getDomainClassFields(className);
                boolean hasAnnotation = false;
                EntityAnn annotation;
//                for(Object res:results){
//                    for (int i=0;i<fields.length;i++) {
//                        hasAnnotation = fields[i].isAnnotationPresent(BaseDomainAnnotation.class);
//                        if (hasAnnotation) {
//                            annotation = fields[i].getAnnotation(BaseDomainAnnotation.class);
//                            if(annotation.IsSelectCode()){
//                                key=classType.invoke(res, "get" + annotation.ResourceKey()).toString();
//                            }else if(annotation.IsSelectText()){
//                                value=classType.invoke(res, "get" + annotation.ResourceKey()).toString();
//                            }
//                            if(key!=null&&key.length()>0){
//                                selectMap.put(key, value);
//                            }
//                        }
//                    }
//                }

                for (int i = 0; i < fields.length; i++) {
                    hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
                    if (hasAnnotation) {
                        annotation = fields[i].getAnnotation(EntityAnn.class);
                        if (annotation.IsSelectCode()) {
                            selectCodeField = annotation.ResourceKey();
                        } else if (annotation.IsSelectText()) {
                            selectTextField = annotation.ResourceKey();
                        }
                    }
                }
                for (Object res : results) {
                    if (selectCodeField.length() > 0 && selectTextField.length() > 0) {
                        key = classType.invoke(res, "get" + selectCodeField).toString();
                        value = classType.invoke(res, "get" + selectTextField).toString();
                        selectMap.put(key, value);
                    }
                }
                if (results.size() > 0) {
                    LGF.setValueMap(selectMap);
                }else{
                	LGF.setValueMap(new LinkedHashMap<String, String>());
                }
            }
        });
    }


    @SuppressWarnings("rawtypes")
	public static void setLG4CommonDB(final ListGrid lg, String className, ArrayList<PageSql> pageSql,String acSearch) {
        lg.setData(new ListGridRecord[0]);
        lg.setEmptyMessage(AppDataMap.getLoadingEmptyMessage());
        SetCommonFilter.addComValSql(pageSql, className);
		Page page=new Page.Builder().pageSql(pageSql).maxResults(20).build();

        CommonRemote.Util.getInstance().commonSearch(page,className, new DefaultCallback<ArrayList>() {

            @Override
            public void onSuccess(ArrayList result) {
                ResultBuild.buildListGrid(result, lg);
            }
        });
    }


    @SuppressWarnings("rawtypes")
	public static void setLG4Data(final ListGrid lg, Object domain, ArrayList<PageSql> pageSql) {
        BasicDataRemote.Util.getInstance().getBasicData(domain.getClass().getName(), pageSql, new DefaultCallback<List>() {

			@Override
            public void onSuccess(List result) {
                ResultBuild.buildListGrid(result, lg);
            }
        });

    }

}
