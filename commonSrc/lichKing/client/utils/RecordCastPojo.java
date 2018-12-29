package lichKing.client.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lichKing.client.entity.BaseDomain;
import lichKing.client.entityAnnotation.EntityAnn;
import lichKing.client.entityAnnotation.MyClassTypeUtil;
import lichKing.client.server.MsgServer;

import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.Constructor;
import com.gwtent.reflection.client.Field;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * 把smartgwt的数据行Record转成java对象
 * @author catPan
 */
public class RecordCastPojo {

	/**
	 * 返回lgr的attribute值，并转成float类型，为空时返回0f
	 * @param lgr
	 * @param fieldName 要返回的字段name
	 * @return
	 */
	public static float getAttributeAsFloat(ListGridRecord lgr,String fieldName){
		if(lgr.getAttribute(fieldName)==null){
			return 0.0f;
		}else{
			return lgr.getAttributeAsFloat(fieldName);
		}
	}
	/**
	 * 返回lgr的attribute值，并转成int类型，为空时返回0
	 * @param lgr
	 * @param fieldName 要返回的字段name
	 * @return
	 */
	public static int getAttributeAsInt(ListGridRecord lgr,String fieldName){
		if(lgr.getAttribute(fieldName)==null||lgr.getAttribute(fieldName).length()==0){
			return 0;
		}else{
			return lgr.getAttributeAsInt(fieldName);
		}
	}

    /**
     * 多个页签中的多个表单合成一个实体<br />
     * @param domain 实体类
     * @param forms 表单数组
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static Object toMixPojo(Object domain, DynamicForm[] forms) {

        ClassType classType = MyClassTypeUtil.getDomainClassType(domain.getClass());
        Field[] fields = MyClassTypeUtil.getDomainClassFields(domain.getClass());
        boolean hasAnnotation = false;
        EntityAnn annotation;

        for (DynamicForm form : forms) {
            for (int i = 0; i < fields.length; i++) {
                hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
                if (hasAnnotation) {
                    annotation = fields[i].getAnnotation(EntityAnn.class);

                    if (form.getItem(annotation.ResourceKey()) != null && form.getValue(annotation.ResourceKey()) != null) {
                        if (annotation.FieldType().equals("boolean")) {
                            if (Boolean.valueOf(form.getValue(annotation.ResourceKey()).toString()) == false) {
                                classType.invoke(domain, "set" + annotation.ResourceKey(), "0");
                            } else {
                                classType.invoke(domain, "set" + annotation.ResourceKey(), "1");
                            }
                        } else {
                            if (annotation.FieldType().equals("text")) {
                                classType.invoke(domain, "set" + annotation.ResourceKey(),
                                        form.getValue(annotation.ResourceKey()).toString());
                            } else if (annotation.FieldType().equals("date")) {
                                classType.invoke(domain, "set" + annotation.ResourceKey(),
                                        (Date) form.getValue(annotation.ResourceKey()));
                            } else if (annotation.FieldType().equals("datetime")) {
                                classType.invoke(domain, "set" + annotation.ResourceKey(),
                                        (Date) form.getValue(annotation.ResourceKey()));
                            } else if (annotation.FieldType().equals("float")) {
                                classType.invoke(domain, "set" + annotation.ResourceKey(),
                                        Float.valueOf(form.getValue(annotation.ResourceKey()).toString()));
                            } else if (annotation.FieldType().equals("integer")) {
                                classType.invoke(domain, "set" + annotation.ResourceKey(),
                                        Integer.valueOf(form.getValue(annotation.ResourceKey()).toString()));
                            }else {
                                classType.invoke(domain, "set" + annotation.ResourceKey(),
                                        form.getValue(annotation.ResourceKey()).toString());
                            }
                        }
                    }
                }
            }
        }
        return domain;
    }

    /**
     * 从表单数据转成对象，通过EditOnForm注释反射字段<br />
     * @param domain 实体类
     * @param form 表单
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static Object toMixPojo(Object domain, DynamicForm form) {

        ClassType classType = MyClassTypeUtil.getDomainClassType(domain.getClass());
        Field[] fields = MyClassTypeUtil.getDomainClassFields(domain.getClass());
        boolean hasAnnotation = false;
        EntityAnn annotation;

        for (int i = 0; i < fields.length; i++) {
            hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {
                annotation = fields[i].getAnnotation(EntityAnn.class);

//                if (annotation.EditOnForm() && annotation.DomainMap().equals("noMap")) {
                if (annotation.DomainMap().equals("noMap")) {
                    if (form.getValue(annotation.ResourceKey()) != null) {
                        if (annotation.FieldType().equals("boolean")) {
                            if (Boolean.valueOf(form.getValue(annotation.ResourceKey()).toString()) == false) {
                                classType.invoke(domain, "set" + annotation.ResourceKey(), "0");
                            } else {
                                classType.invoke(domain, "set" + annotation.ResourceKey(), "1");
                            }
                        } else {
                            if (annotation.FieldType().equals("date")) {
                                classType.invoke(domain, "set" + annotation.ResourceKey(),
                                        (Date) form.getValue(annotation.ResourceKey()));
                            } else if (annotation.FieldType().equals("datetime")) {
                                classType.invoke(domain, "set" + annotation.ResourceKey(),
                                        (Date) form.getValue(annotation.ResourceKey()));
                            } else if (annotation.FieldType().equals("float")) {
                                classType.invoke(domain, "set" + annotation.ResourceKey(),
                                        Float.valueOf(form.getValue(annotation.ResourceKey()).toString()));
                            } else if (annotation.FieldType().equals("integer")) {
                                classType.invoke(domain, "set" + annotation.ResourceKey(),
                                        Integer.valueOf(form.getValue(annotation.ResourceKey()).toString()));
                            } else {
                                classType.invoke(domain, "set" + annotation.ResourceKey(),
                                        form.getValue(annotation.ResourceKey()).toString());
                            }
                        }
                    } else {
                        if (annotation.FieldType().equals("boolean")) {
                            classType.invoke(domain, "set" + annotation.ResourceKey(), "0");
                        } else if (annotation.FieldType().equals("float")) {
                            classType.invoke(domain, "set" + annotation.ResourceKey(), Float.valueOf("0"));
                        } else if (annotation.FieldType().equals("integer")) {
                            classType.invoke(domain, "set" + annotation.ResourceKey(), 0);
                        } else if (annotation.FieldType().equals("date")) {
                            classType.invoke(domain, "set" + annotation.ResourceKey(),
                                    (Date) form.getValue(annotation.ResourceKey()));
                        } else if (annotation.FieldType().equals("datetime")) {
                            classType.invoke(domain, "set" + annotation.ResourceKey(),
                                    (Date) form.getValue(annotation.ResourceKey()));
                        } else {
                            classType.invoke(domain, "set" + annotation.ResourceKey(), "");
                        }
                    }
                }
            }
        }
        return domain;
    }

    /**
     * 从表单数据转成对象，剔除不需要赋值的属性<br />
     * 参见SYSUI010_5，roles.add((SYS_ROLE) RecordCastPojo.toMixPojo(roleG, editForm,removeFileds));
     * @param domain 实体类
     * @param form 表单
     * @param removeFileds 剔除不需要赋值的属性，字符串数组
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static Object toMixPojo(Object domain, DynamicForm form, String[] removeFileds) {

        ClassType classType = MyClassTypeUtil.getDomainClassType(domain.getClass());
        Field[] fields = MyClassTypeUtil.getDomainClassFields(domain.getClass());
        boolean hasAnnotation = false;
        EntityAnn annotation;

        boolean nextFiled = false;
        for (int i = 0; i < fields.length; i++) {
            hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {
                annotation = fields[i].getAnnotation(EntityAnn.class);

                if (annotation.DomainMap().equals("noMap")) {
                    for (String str : removeFileds) {
                        if (annotation.ResourceKey().equals(str)) {
                            nextFiled = true;
                            break;
                        } else {
                            nextFiled = false;
                        }
                    }
//                    if (!nextFiled && !annotation.IsPrimaryKey() && !annotation.IsOpCell()) {
                        if (!nextFiled && !annotation.IsPrimaryKey() ) {
                        if (form.getValue(annotation.ResourceKey()) != null) {
                            if (annotation.FieldType().equals("boolean")) {
                                if (Boolean.valueOf(form.getValue(annotation.ResourceKey()).toString()) == false) {
                                    classType.invoke(domain, "set" + annotation.ResourceKey(), "0");
                                } else {
                                    classType.invoke(domain, "set" + annotation.ResourceKey(), "1");
                                }
                            } else {
                                if (annotation.FieldType().equals("date")) {
                                    classType.invoke(domain, "set" + annotation.ResourceKey(),
                                            (Date) form.getValue(annotation.ResourceKey()));
                                } else if (annotation.FieldType().equals("datetime")) {
                                    classType.invoke(domain, "set" + annotation.ResourceKey(),
                                            (Date) form.getValue(annotation.ResourceKey()));
                                } else if (annotation.FieldType().equals("float")) {
                                    classType.invoke(domain, "set" + annotation.ResourceKey(),
                                            Float.valueOf(form.getValue(annotation.ResourceKey()).toString()));
                                } else if (annotation.FieldType().equals("integer")) {
                                    classType.invoke(domain, "set" + annotation.ResourceKey(),
                                            Integer.valueOf(form.getValue(annotation.ResourceKey()).toString()));
                                } else {
                                    classType.invoke(domain, "set" + annotation.ResourceKey(),
                                            form.getValue(annotation.ResourceKey()).toString());
                                }
                            }
                        } else {
                            if (annotation.FieldType().equals("boolean")) {
                                classType.invoke(domain, "set" + annotation.ResourceKey(), "0");
                            } else if (annotation.FieldType().equals("float")) {
                                classType.invoke(domain, "set" + annotation.ResourceKey(), Float.valueOf("0"));
                            } else if (annotation.FieldType().equals("integer")) {
                                classType.invoke(domain, "set" + annotation.ResourceKey(), 0);
                            } else {
                                classType.invoke(domain, "set" + annotation.ResourceKey(), "");
                            }
                        }
                    }
                }
            }
        }
        return domain;
    }

    /**
     * 从表单数据转成对象<br />
     * 参见SysRoleServer，(SYS_ROLE)RecordCastPojo.toPojo(domain,form)
     * @param domain 实体类
     * @param form 表单
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static BaseDomain toPojo(Object domain, DynamicForm form) {
      return toPojo(domain, form, "");
    }

	public static BaseDomain toPojo(Object domain, DynamicForm form,String type) {

        ClassType classType = MyClassTypeUtil.getDomainClassType(domain.getClass());
        Field[] fields = MyClassTypeUtil.getDomainClassFields(domain.getClass());
        boolean hasAnnotation = false;
        EntityAnn annotation;

        for (int i = 0; i < fields.length; i++) {
            hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {
                annotation = fields[i].getAnnotation(EntityAnn.class);

                if(annotation.IsPrimaryKey()&&type.equals("Copy")){
                	continue;
                }
//                if (!annotation.IsOpCell() && annotation.DomainMap().equals("noMap")) {
                if (annotation.DomainMap().equals("noMap")) {
                    if (form.getValue(annotation.ResourceKey()) == null) {
                        if(classType.invoke(domain, "get" + annotation.ResourceKey())==null){
                            classType.invoke(domain, "set" + annotation.ResourceKey(), (Object) null);
                        }
                    } else {
                        if (annotation.FieldType().equals("boolean")) {
                            if (Boolean.valueOf(form.getValue(annotation.ResourceKey()).toString()) == false) {
                                classType.invoke(domain, "set" + annotation.ResourceKey(), "0");
                            } else {
                                classType.invoke(domain, "set" + annotation.ResourceKey(), "1");
                            }
                        } else {
                            if (annotation.FieldType().equals("date")) {
                                classType.invoke(domain, "set" + annotation.ResourceKey(),
                                        (Date) form.getValue(annotation.ResourceKey()));
                            }   else if (annotation.FieldType().equals("time")) {
                                classType.invoke(domain, "set" + annotation.ResourceKey(),
                                		(Date)form.getValue(annotation.ResourceKey()));
                            }else if (annotation.FieldType().equals("datetime")) {
                                classType.invoke(domain, "set" + annotation.ResourceKey(),
                                        (Date) form.getValue(annotation.ResourceKey()));
                            } else if (annotation.FieldType().equals("float")) {
                                classType.invoke(domain, "set" + annotation.ResourceKey(),
                                        Float.valueOf(form.getValue(annotation.ResourceKey()).toString()));
                            } else if (annotation.FieldType().equals("integer")) {
                                classType.invoke(domain, "set" + annotation.ResourceKey(),
                                        Integer.valueOf(form.getValue(annotation.ResourceKey()).toString()));
                            } else if (annotation.FieldType().equals("image")) {
                                String fileName = form.getValue(annotation.ResourceKey()).toString();
                                if (fileName.indexOf("\\") != -1) {
                                    fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
                                }
                                classType.invoke(domain, "set" + annotation.ResourceKey(),
                                        GwtUUID.uuid32() + fileName);
                            } else if (annotation.FieldType().equals("file")) {
                                String fileName = form.getValue(annotation.ResourceKey()).toString();
                                if (fileName.indexOf("\\") != -1) {
                                    fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
                                }
                                classType.invoke(domain, "set" + annotation.ResourceKey(),
                                        GwtUUID.uuid32() + fileName);
                            } else {
                                classType.invoke(domain, "set" + annotation.ResourceKey(),
                                        form.getValue(annotation.ResourceKey()).toString());
                            }
                        }
                    }
                }
            }
        }
//      return domain;
      return SetCommonData.setData((BaseDomain) domain);
    }
	
	public static List toPojos(Class domainClass,ListGridRecord[] lgrs){
        List list=new ArrayList();
        ClassType classType;
        for(ListGridRecord lgr:lgrs){
            classType=MyClassTypeUtil.getDomainClassType(domainClass);
            Constructor constructor=classType.findConstructor();
            list.add(toPojo(constructor.newInstance(), lgr));
        }
        return list;
    }
    /**
     * 把ListGrid中的数据，转成对应domainClass类型的数据集（List）
     * @param domainClass 实体类类型
     * @param lg ListGrid UI组件
     * @return 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toPojos(Class domainClass,ListGrid lg){
        List list=new ArrayList();
        ClassType classType;
        for(ListGridRecord lgr:lg.getRecords()){
            classType=MyClassTypeUtil.getDomainClassType(domainClass);
            Constructor constructor=classType.findConstructor();
            list.add(toPojo(constructor.newInstance(), lgr));
        }
        return list;
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toPojos(ClassType classType,ListGrid lg){
        List list=new ArrayList();
        for(ListGridRecord lgr:lg.getRecords()){
            Constructor constructor=classType.findConstructor();
            list.add(toPojo(constructor.newInstance(), lgr));
        }
        return list;
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toPojos(ClassType classType,ListGrid lg,String type){
        List list=new ArrayList();
        for(ListGridRecord lgr:lg.getRecords()){
            Constructor constructor=classType.findConstructor();
            list.add(toPojo(constructor.newInstance(), lgr,type));
        }
        return list;
    }
    
	public static List toPojosModifyType(ClassType classType,ListGrid lg){
        List list=new ArrayList();
        for(ListGridRecord lgr:lg.getRecords()){
            Constructor constructor=classType.findConstructor();
            BaseDomain d=(BaseDomain) toPojo(constructor.newInstance(), lgr);
        	d.setType(lgr.getAttribute("type"));
            list.add(d);
        }
        return list;
    }
    /**
     * 从ListGridRecord数据转成对象<br />
     * 参见SysRoleServer，(SYS_ROLE) RecordCastPojo.toPojo(new SYS_ROLE(), lgrs[i])
     * @param domain 实体类
     * @param lgr ListGridRecord
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static BaseDomain toPojo(Object domain, ListGridRecord lgr) {
        return toPojo(domain, lgr, "");
    }

    
	  @SuppressWarnings("rawtypes")
		public static BaseDomain toPojo(Object domain, ListGridRecord lgr,String type) {

	        ClassType classType = MyClassTypeUtil.getDomainClassType(domain.getClass());
	        Field[] fields = MyClassTypeUtil.getDomainClassFields(domain.getClass());
	        boolean hasAnnotation = false;
	        EntityAnn annotation;

	        for (int i = 0; i < fields.length; i++) {
	            hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
	            if (hasAnnotation) {
	                annotation = fields[i].getAnnotation(EntityAnn.class);

	                if(annotation.IsPrimaryKey()&&type.equals("Copy")){
	                	continue;
	                }
//	                if (!annotation.IsOpCell() && lgr.getAttribute(annotation.ResourceKey()) != null) {
	                    if (lgr.getAttribute(annotation.ResourceKey()) != null) {
	                    if (annotation.DomainMap().equals("noMap")) {
	                        if (annotation.FieldType().equals("boolean")) {

	                            if (Boolean.valueOf(lgr.getAttribute(annotation.ResourceKey()).toString()) == false) {
	                                classType.invoke(domain, "set" + annotation.ResourceKey(), "0");
	                            } else {
	                                classType.invoke(domain, "set" + annotation.ResourceKey(), "1");
	                            }
	                        } else {
	                            if (annotation.FieldType().equals("date")) {
	                                classType.invoke(domain, "set" + annotation.ResourceKey(),
	                                        lgr.getAttributeAsDate(annotation.ResourceKey()));
	                            } else if (annotation.FieldType().equals("datetime")) {
	                                classType.invoke(domain, "set" + annotation.ResourceKey(),
	                                        lgr.getAttributeAsDate(annotation.ResourceKey()));
	                            }  else if (annotation.FieldType().equals("time")) {
	                                classType.invoke(domain, "set" + annotation.ResourceKey(),
	                                        lgr.getAttributeAsDate(annotation.ResourceKey()));
	                            } else if (annotation.FieldType().equals("float")) {
	                            	
	                                classType.invoke(domain, "set" + annotation.ResourceKey(),
	                                        Float.parseFloat(lgr.getAttribute(annotation.ResourceKey())));
	                            } else if (annotation.FieldType().equals("integer")) {
	                                if (!annotation.ResourceKey().equals("FIELDS_WIDTH")) {
	                                    classType.invoke(domain, "set" + annotation.ResourceKey(),
	                                            Integer.parseInt(lgr.getAttribute(annotation.ResourceKey())));
	                                } else {
	                                    classType.invoke(domain, "set" + annotation.ResourceKey(),
	                                            lgr.getAttribute(annotation.ResourceKey()));
	                                }
	                            } else {
	                                classType.invoke(domain, "set" + annotation.ResourceKey(),
	                                        lgr.getAttribute(annotation.ResourceKey()));
	                            }
	                        }
	                    } else {
	                        classType.invoke(domain, "set" + annotation.ResourceKey(),
	                                lgr.getAttributeAsObject(annotation.ResourceKey()));
	                    }
	                }
	            }
	        }
//	        return domain;
	        return SetCommonData.setData((BaseDomain) domain);
	    }
    /**
     * ListGrid中编辑，从编辑行数据转成对象<br />
     * 参见SysRoleServer，(SYS_ROLE) RecordCastPojo.toPojo(domain, editLg, editIdx)
     * @param domain 实体类
     * @param listGrid
     * @param editIdx 编辑行index
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static BaseDomain toPojo(Object domain, ListGrid listGrid, int editIdx) {

        ListGridRecord lgr = (ListGridRecord) listGrid.getEditedRecord(editIdx);

        ClassType classType = MyClassTypeUtil.getDomainClassType(domain.getClass());
        Field[] fields = MyClassTypeUtil.getDomainClassFields(domain.getClass());
        boolean hasAnnotation = false;
        EntityAnn annotation;

        for (int i = 0; i < fields.length; i++) {
            hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {
                annotation = fields[i].getAnnotation(EntityAnn.class);

//                if (!annotation.IsOpCell() && lgr.getAttribute(annotation.ResourceKey()) != null&& annotation.DomainMap().equals("noMap")) {
                    if (lgr.getAttribute(annotation.ResourceKey()) != null&& annotation.DomainMap().equals("noMap")) {
                    if (annotation.FieldType().equals("boolean")) {

                        if (Boolean.valueOf(lgr.getAttribute(annotation.ResourceKey()).toString()) == false) {
                            classType.invoke(domain, "set" + annotation.ResourceKey(), "0");
                        } else {
                            classType.invoke(domain, "set" + annotation.ResourceKey(), "1");
                        }
                    } else {
                        if (annotation.FieldType().equals("date")) {
                            classType.invoke(domain, "set" + annotation.ResourceKey(),
                                    lgr.getAttributeAsDate(annotation.ResourceKey()));
                        } else if (annotation.FieldType().equals("datetime")) {
                            classType.invoke(domain, "set" + annotation.ResourceKey(),
                                    lgr.getAttributeAsDate(annotation.ResourceKey()));
                        } else if (annotation.FieldType().equals("float")) {
                            classType.invoke(domain, "set" + annotation.ResourceKey(),
                                    lgr.getAttributeAsFloat(annotation.ResourceKey()));
                        } else if (annotation.FieldType().equals("integer")) {
                            classType.invoke(domain, "set" + annotation.ResourceKey(),
                                    Integer.valueOf(lgr.getAttributeAsInt(annotation.ResourceKey())));
                        } else {
                            classType.invoke(domain, "set" + annotation.ResourceKey(),
                                    lgr.getAttribute(annotation.ResourceKey()));
                        }
                    }
                }
            }
        }
//      return domain;
      return SetCommonData.setData((BaseDomain) domain);
    }

    /**
     * 获取删除指定id的记录的sql拼接字符串<br />
     * @param domain 实体类
     * @param ids
     * @return DeleteSql
     */
    public static String getPojoDeleteSql(Object domain, String[] ids) {

        Field[] fields = MyClassTypeUtil.getDomainClassFields(domain.getClass());
        boolean hasAnnotation = false;
        EntityAnn annotation;

        String fClassN = domain.getClass().getName();
        String tableName = fClassN.substring(fClassN.lastIndexOf(".") + 1, fClassN.length());
        String deleteSql = "delete " + tableName + " where ";
        String deleteIds = "";
        String idKey = "";
        for (int i = 0; i < fields.length; i++) {
            hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {
                annotation = fields[i].getAnnotation(EntityAnn.class);

                if (annotation.IsPrimaryKey()) {
                    idKey = annotation.ResourceKey();
                    break;
                }
            }
        }
        if (idKey.length() > 0) {
            for (String id : ids) {
                deleteIds += "'" + id + "',";
            }
            deleteIds = deleteIds.substring(0, deleteIds.length() - 1);
            deleteSql = deleteSql + idKey + " in(" + deleteIds + ")";
        } else {
            SC.say(domain.getClass().getName()+MsgServer.getMsg("UnableToGetPrimaryKey")+"，"+MsgServer.getMsg("CanNotDelete"));
            return "";
        }
        return deleteSql;
    }


	public static String getPojoDisableSql(Class domainClass, ListGrid listGrid,String field,String val) {
        return getPojoDisableSql(domainClass,listGrid.getSelectedRecords(),field,val);
    }
	public static String getPojoDisableSql(Class domainClass, ListGridRecord[] lgrs,String field,String val) {
        Field[] fields = MyClassTypeUtil.getDomainClassFields(domainClass);
        boolean hasAnnotation = false;
        EntityAnn annotation;

        String deleteSql = "update " + OnlyClassName.removePackage(domainClass.getName()) + " set "+field+"='"+val+"' where ";
        String deleteIds = "";
        String idKey = "";
        for (int n = 0; n < lgrs.length; n++) {
            lab:
            for (int i = 0; i < fields.length; i++) {
                hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
                if (hasAnnotation) {
                    annotation = fields[i].getAnnotation(EntityAnn.class);

                    if (annotation.IsPrimaryKey()) {
                        if (idKey.length() == 0) {
                            idKey = annotation.ResourceKey();
                        }
                        deleteIds += "'" + lgrs[n].getAttribute(annotation.ResourceKey()) + "',";
                        break lab;
                    }
                }
            }
        }
        if(lgrs.length>0){
	        if (idKey.length() > 0) {
	            deleteIds = deleteIds.substring(0, deleteIds.length() - 1);
	            deleteSql = deleteSql + idKey + " in(" + deleteIds + ")";
	        } else {
	            SC.say(domainClass.getName()+MsgServer.getMsg("UnableToGetPrimaryKey")+"，"+MsgServer.getMsg("CanNotDelete"));
	            return "";
	        }
        }else{
        	return "";
        }
        return deleteSql;
    }
    /**
     * 获取禁用ListGrid指定记录的sql拼接字符串<br />
     * @param domainClass 实体类的Class
     * @param listGrid
     * @param val VALD_FLAG的值
     * @return DisableSql
     */
    @SuppressWarnings("rawtypes")
	public static String getPojoDisableSql(Class domainClass, ListGrid listGrid,String val) {
        return getPojoDisableSql(domainClass,listGrid.getSelectedRecords(),val);
    }
    /**
     * 获取禁用ListGrid指定记录的sql拼接字符串<br />
     * @param domainClass 实体类的Class
     * @param ListGridRecord[] LG中指定的数据行
     * @param val VALD_FLAG的值
     * @return DisableSql
     */
    @SuppressWarnings("rawtypes")
	public static String getPojoDisableSql(Class domainClass, ListGridRecord[] lgrs,String val) {
        return getPojoDisableSql(domainClass, lgrs, "VALD_FLAG", val);
    }
    
    /**
     * 获取删除ListGrid指定记录的sql拼接字符串<br />
     * @param domainClass 实体类的Class
     * @param listGrid
     * @return DeleteSql
     */
    @SuppressWarnings("rawtypes")
	public static String getPojoDeleteSql(Class domainClass, ListGrid listGrid) {
        return getPojoDeleteSql(domainClass,listGrid.getSelectedRecords());
    }
    
    /**
     * 获取删除ListGrid指定记录的sql拼接字符串<br />
     * @param domainClass 实体类的Class
     * @param ListGridRecord[] LG中指定的数据行
     * @return DeleteSql
     */
    @SuppressWarnings("rawtypes")
	public static String getPojoDeleteSql(Class domainClass, ListGridRecord[] lgr) {
        Field[] fields = MyClassTypeUtil.getDomainClassFields(domainClass);
        boolean hasAnnotation = false;
        EntityAnn annotation;

        String deleteSql = "delete " + OnlyClassName.removePackage(domainClass.getName()) + " where ";
        String deleteIds = "";
        String idKey = "";
        for (int n = 0; n < lgr.length; n++) {
            lab:
            for (int i = 0; i < fields.length; i++) {
                hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
                if (hasAnnotation) {
                    annotation = fields[i].getAnnotation(EntityAnn.class);

                    if (annotation.IsPrimaryKey()) {
                        if (idKey.length() == 0) {
                            idKey = annotation.ResourceKey();
                        }
                        deleteIds += "'" + lgr[n].getAttribute(annotation.ResourceKey()) + "',";
                        break lab;
                    }
                }
            }
        }
        if (idKey.length() > 0) {
            deleteIds = deleteIds.substring(0, deleteIds.length() - 1);
            deleteSql = deleteSql + idKey + " in(" + deleteIds + ")";
        } else {
            SC.say(domainClass.getName()+MsgServer.getMsg("UnableToGetPrimaryKey")+"，"+MsgServer.getMsg("CanNotDelete"));
            return "";
        }
        return deleteSql;
    }
    
    @SuppressWarnings("rawtypes")
	public static String getPojoDeleteSqlNoApproval(Class domainClass, ListGrid listGrid) {
        return getPojoDeleteSqlNoApproval(domainClass,listGrid.getSelectedRecords(),listGrid);
    }
    
    @SuppressWarnings("rawtypes")
	public static String getPojoDeleteSqlNoApproval(Class domainClass, ListGridRecord[] lgr, ListGrid listGrid) {
        Field[] fields = MyClassTypeUtil.getDomainClassFields(domainClass);
        boolean hasAnnotation = false;
        EntityAnn annotation;

        String deleteSql = "delete " + OnlyClassName.removePackage(domainClass.getName()) + " where ";
        String deleteIds = "";
        String idKey = "";
        boolean hadNoApproval=false;
        for (int n = 0; n < lgr.length; n++) {
            if(lgr[n].getAttribute("APPROVAL_STATE")==null||lgr[n].getAttribute("APPROVAL_STATE").equals("0")){
                lab:
                for (int i = 0; i < fields.length; i++) {
                    hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
                    if (hasAnnotation) {
                        annotation = fields[i].getAnnotation(EntityAnn.class);

                        if (annotation.IsPrimaryKey()) {
                            if (idKey.length() == 0) {
                                idKey = annotation.ResourceKey();
                            }
                            deleteIds += "'" + lgr[n].getAttribute(annotation.ResourceKey()) + "',";
                            break lab;
                        }
                    }
                }
            }else{
                listGrid.deselectRecord(lgr[n]);
                 hadNoApproval=true;
            }
        }
        if (idKey.length() > 0 || hadNoApproval) {
            if(deleteIds.length()>0){
                deleteIds = deleteIds.substring(0, deleteIds.length() - 1);
                deleteSql = deleteSql + idKey + " in(" + deleteIds + ")";
            }else{
                SC.say("审批中的数据无法删除");
                return "";
            }
        } else {
            SC.say(domainClass.getName()+MsgServer.getMsg("UnableToGetPrimaryKey")+"，"+MsgServer.getMsg("CanNotDelete"));
            return "";
        }
        return deleteSql;
    }
    
    
    public static String getDomainIdsStr(Object domain, ListGrid listGrid){
        
        ListGridRecord[] lgr = listGrid.getSelectedRecords();

        Field[] fields = MyClassTypeUtil.getDomainClassFields(domain.getClass());
        boolean hasAnnotation = false;
        EntityAnn annotation;

        String domainIds = "";
        String idKey = "";
        for (int n = 0; n < lgr.length; n++) {
            lab:
            for (int i = 0; i < fields.length; i++) {
                hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
                if (hasAnnotation) {
                    annotation = fields[i].getAnnotation(EntityAnn.class);

                    if (annotation.IsPrimaryKey()) {
                        if (idKey.length() == 0) {
                            idKey = annotation.ResourceKey();
                        }
                        domainIds += "'" + lgr[n].getAttribute(annotation.ResourceKey()) + "',";
                        break lab;
                    }
                }
            }
        }
        if (idKey.length() > 0) {
            domainIds = domainIds.substring(0, domainIds.length() - 1);
        } else {
            SC.say(domain.getClass().getName()+MsgServer.getMsg("UnableToGetPrimaryKey"));
            return "";
        }
        return domainIds;
    }
    
    /**
     * 获取实体类的ID的List，从ListGrid列表中
     * @param domain 实体类
     * @param listGrid 列表
     * @return ID的List
     */
    public static List<String> getDomainIdsList(Object domain, ListGrid listGrid){
        
        ListGridRecord[] lgr = listGrid.getSelectedRecords();

        Field[] fields = MyClassTypeUtil.getDomainClassFields(domain.getClass());
        boolean hasAnnotation = false;
        EntityAnn annotation;

        List<String> domainIds = new ArrayList<String>();
        String idKey = "";
        for (int n = 0; n < lgr.length; n++) {
            lab:
            for (int i = 0; i < fields.length; i++) {
                hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
                if (hasAnnotation) {
                    annotation = fields[i].getAnnotation(EntityAnn.class);

                    if (annotation.IsPrimaryKey()) {
                        if (idKey.length() == 0) {
                            idKey = annotation.ResourceKey();
                        }
                        domainIds.add(lgr[n].getAttribute(annotation.ResourceKey()));
                        break lab;
                    }
                }
            }
        }
        if (idKey.length() == 0) {
            SC.say(domain.getClass().getName()+MsgServer.getMsg("UnableToGetPrimaryKey"));
            return null;
        }
        return domainIds;
    }
    
}
