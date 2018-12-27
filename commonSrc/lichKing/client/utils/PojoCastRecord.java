package lichKing.client.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import lichKing.client.entity.BaseDomain;
import lichKing.client.entityAnnotation.EntityAnn;
import lichKing.client.entityAnnotation.MyClassTypeUtil;
import lichKing.client.server.MsgServer;

import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.Field;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tree.TreeNode;

/**
 * 把java对象转成smartgwt的数据行Record
 * @author catPan
 */
public class PojoCastRecord {
	/**
	 * 实体中是否存在某个字段
	 * @param clsName 实体类的全路径名
	 * @param fieldName 字段的名称
	 * @return
	 */
	public static boolean isHadDomainField(String clsName,String fieldName){

        Field[] fields = MyClassTypeUtil.getDomainClassFields(clsName);
        String fieldStr = "";
        if(fields!=null){
	        for(Field field:fields){
	        	if(field.getName().equals(fieldName)){
	        		fieldStr="finded";
	        		break;
	        	}
	        }
        }
        if (fieldStr.length() == 0) {
//            SC.say(cls.getName()+"没有找到"+fieldName+"字段");
            return false;
        }
    	return true;
    }
	/**
	 * 实体中是否存在某个字段
	 * @param cls 实体类的类型
	 * @param fieldName 字段的名称
	 * @return
	 */
	public static boolean isHadDomainField(Class cls,String fieldName){

        Field[] fields = MyClassTypeUtil.getDomainClassFields(cls);
        String fieldStr = "";
        for(Field field:fields){
        	if(field.getName().equals(fieldName)){
        		fieldStr="finded";
        		break;
        	}
        }
        if (fieldStr.length() == 0) {
//            SC.say(cls.getName()+"没有找到"+fieldName+"字段");
            return false;
        }
    	return true;
    }
	
	/**
	 * 获取实体的主键的值，有@BaseDomainAnnotation(IsPrimaryKey = true)标注
	 * @param domain
	 * @return
	 */
	public static String getDomainIdValue(BaseDomain domain){

        ClassType classType = MyClassTypeUtil.getDomainClassType(domain.getClass());
        return (String) classType.invoke(domain, "get" + getDomainIdField(domain.getClass()));
	}

	/**
	 * 获取实体的主键字段
	 * @param cls 实体类的类型
	 * @return
	 */
    @SuppressWarnings("rawtypes")
	public static String getDomainIdField(Class cls){

        Field[] fields = MyClassTypeUtil.getDomainClassFields(cls);
        boolean hasAnnotation = false;
        EntityAnn annotation;
        String idField = "";
            for (int i = 0; i < fields.length; i++) {
                hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
                if (hasAnnotation) {
                    annotation = fields[i].getAnnotation(EntityAnn.class);

                    if (annotation.IsPrimaryKey()) {
                        if (idField.length() == 0) {
                        	idField = annotation.ResourceKey();
                        	break;
                        }
                    }
                }
            }
        if (idField.length() == 0) {
            SC.say(cls.getName()+MsgServer.getMsg("UnableToGetPrimaryKey"));
            return "";
        }
        return idField;
    }
    
    /**
     * 获取实体的id字段，@BaseDomainAnnotation注释中，必须标注IsPrimaryKey = true
     * @param domainClass
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static String getDomainPrimaryKey(Class domainClass) {
        Field[] fields = MyClassTypeUtil.getDomainClassFields(domainClass);
        boolean hasAnnotation = false;
        EntityAnn annotation;

        String primaryKey = "";
        for (int i = 0; i < fields.length; i++) {
            hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {
                annotation = fields[i].getAnnotation(EntityAnn.class);

                if (annotation.IsPrimaryKey()) {
                    primaryKey = annotation.ResourceKey();
                    break;
                }
            }
        }
        if (primaryKey.length() == 0) {
            SC.say(domainClass.getName()+MsgServer.getMsg("UnableToGetPrimaryKey"));
            return "";
        }
        return primaryKey;
    }

    /**
     * 将实体类转成ListGridRecord，树结构的，带有LEVEL的标识<br />
     * 参见ResultBuild，PojoCastRecord.toTreeRecord(resultList.get(i), record,level)
     * @param domain
     * @param lgr
     * @param level 用于区分不同表的数据
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static ListGridRecord toTreeRecord(Object domain, ListGridRecord lgr, String level) {

        ClassType classType = MyClassTypeUtil.getDomainClassType(domain.getClass());
        Field[] fields = MyClassTypeUtil.getDomainClassFields(domain.getClass());
        boolean hasAnnotation = false;
        EntityAnn annotation;

        for (int i = 0; i < fields.length; i++) {
            hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {
                annotation = fields[i].getAnnotation(EntityAnn.class);

                if (annotation.IsTreeId()) {
                    lgr.setAttribute("ID",
                            classType.invoke(domain, "get" + annotation.ResourceKey()));
                } else if (annotation.IsTreePId()) {
                    lgr.setAttribute("PID",
                            classType.invoke(domain, "get" + annotation.ResourceKey()));
                } else if (annotation.IsTreeName()) {
                    lgr.setAttribute("NAME",
                            classType.invoke(domain, "get" + annotation.ResourceKey()));
                }
                lgr.setAttribute("LEVEL", level);
            }
        }
        return lgr;
    }

    /**
     * 将实体类转成ListGridRecord，树结构的<br />
     * 参见ResultBuild，PojoCastRecord.toTreeRecord(resultList.get(i), record)
     * @param domain
     * @param lgr
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static ListGridRecord toTreeRecord(Object domain, ListGridRecord lgr) {

        ClassType classType = MyClassTypeUtil.getDomainClassType(domain.getClass());
        Field[] fields = MyClassTypeUtil.getDomainClassFields(domain.getClass());
        boolean hasAnnotation = false;
        EntityAnn annotation;

        for (int i = 0; i < fields.length; i++) {
            hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {
                annotation = fields[i].getAnnotation(EntityAnn.class);

                if (annotation.IsTreeId()) {
                    lgr.setAttribute("ID",
                            classType.invoke(domain, "get" + annotation.ResourceKey()));
                } else if (annotation.IsTreePId()) {
                    lgr.setAttribute("PID",
                            classType.invoke(domain, "get" + annotation.ResourceKey()));
                } else if (annotation.IsTreeName()) {
                    lgr.setAttribute("NAME",
                            classType.invoke(domain, "get" + annotation.ResourceKey()));
                }
            }
        }
        return lgr;
    }

    /**
     * 将实体类转成TreeNode
     * @param domain
     * @param tn
     * @return 
     */
    @SuppressWarnings("rawtypes")
	public static TreeNode toTreeRecord(Object domain, TreeNode tn) {

        ClassType classType = MyClassTypeUtil.getDomainClassType(domain.getClass());
        Field[] fields = MyClassTypeUtil.getDomainClassFields(domain.getClass());
        boolean hasAnnotation = false;
        EntityAnn annotation;

        for (int i = 0; i < fields.length; i++) {
            hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {
                annotation = fields[i].getAnnotation(EntityAnn.class);

//              if (!annotation.IsOpCell()) {
                    tn.setAttribute(annotation.ResourceKey(),
                            classType.invoke(domain, "get" + annotation.ResourceKey()));
//                }
            }
        }

        return tn;
    }

    /**
     * 将实体类更新到列表的数据行<br />
     * 参见ResultBuild，PojoCastRecord.updateToListGrid(result, record);
     * @param domain
     * @param lgr
     * @return
     */
    public static ListGridRecord updateToListGrid(Object domain, ListGridRecord lgr) {
        return toListGridRecord(domain, lgr, "Modify");

//        ClassType classType = MyClassTypeUtil.getDomainClassType(domain.getClass());
//        Field[] fields = MyClassTypeUtil.getDomainClassFields(domain.getClass());
//        boolean hasAnnotation = false;
//        BaseDomainAnnotation annotation;
//
//        for (int i = 0; i < fields.length; i++) {
//            hasAnnotation = fields[i].isAnnotationPresent(BaseDomainAnnotation.class);
//            if (hasAnnotation) {
//                annotation = fields[i].getAnnotation(BaseDomainAnnotation.class);
//
//                if (!annotation.IsOpCell()) {
//                    if (annotation.FieldType().equals("boolean") && classType.invoke(domain, "get" + annotation.ResourceKey()) != null) {
//                        if (classType.invoke(domain, "get" + annotation.ResourceKey()).equals("Y")) {
//                            lgr.setAttribute(annotation.ResourceKey(), true);
//                        } else {
//                            lgr.setAttribute(annotation.ResourceKey(), false);
//                        }
//                    } else {
//                        lgr.setAttribute(annotation.ResourceKey(),
//                                classType.invoke(domain, "get" + annotation.ResourceKey()));
//                    }
//                }
//            }
//        }
//        return lgr;
    }
    
    public static ListGridRecord[] toListGridRecords(List<BaseDomain> domains) {
        ListGridRecord[] lgrs=new ListGridRecord[domains.size()];
        for(int i=0;i<domains.size();i++){
            lgrs[i]=toListGridRecord(domains.get(i),new ListGridRecord(),"Fetch");
        }
        return lgrs;
    }

    /**
     * 将实体类转成ListGridRecord
     * @param domain
     * @param lgr
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static ListGridRecord toListGridRecord(Object domain, ListGridRecord lgr, String type) {

    	if(domain!=null){
	        ClassType classType = MyClassTypeUtil.getDomainClassType(domain.getClass());
	        Field[] fields = MyClassTypeUtil.getDomainClassFields(domain.getClass());
	        boolean hasAnnotation = false;
	        EntityAnn annotation;
	
	        for (int i = 0; i < fields.length; i++) {
	            hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
	            if (hasAnnotation) {
	                annotation = fields[i].getAnnotation(EntityAnn.class);
	
	
	                if (annotation.DomainMap().equals("otm")) {
	                    String[] joinNameKey = annotation.JoinNameKey();
	                    if (joinNameKey.length > 0) {
	                        List list = (List) classType.invoke(domain, "get" + annotation.ResourceKey());
	                        if (list != null && list.size() > 0) {
	                            Object objOtm = list.get(0);
	                            ClassType classTypeOtm = MyClassTypeUtil.getDomainClassType(objOtm.getClass());
	                            lgr.setAttribute(annotation.ResourceKey(), list);
	                            if (!joinNameKey[0].equals("onlySelf") && classTypeOtm != null) {
	                                for (int j = 0; j < joinNameKey.length; j++) {
	                                    lgr.setAttribute(joinNameKey[j],
	                                            classTypeOtm.invoke(objOtm, "get" + joinNameKey[j]));
	                                }
	                            }
	
	                        }
	                    }
	                } else if (annotation.DomainMap().equals("oto")) {
	                    String[] joinNameKey = annotation.JoinNameKey();
	                    if (joinNameKey.length > 0) {
	                        Object objOto = classType.invoke(domain, "get" + annotation.ResourceKey());
	                        if (objOto != null) {
	                            lgr.setAttribute(annotation.ResourceKey(),objOto);
	                            ClassType classTypeOto = MyClassTypeUtil.getDomainClassType(objOto.getClass());
	                            if (!joinNameKey[0].equals("onlySelf") && classTypeOto != null) {
	                                for (int j = 0; j < joinNameKey.length; j++) {
	                                    lgr.setAttribute(joinNameKey[j],
	                                            classTypeOto.invoke(objOto, "get" + joinNameKey[j]));
	                                }
	                                LinkedHashMap<String, String> secondJoinNameValue = get2ndJoinNameValue(objOto, joinNameKey);
	                                if (secondJoinNameValue != null && secondJoinNameValue.size() > 0) {
	                                    Set<String> keys = secondJoinNameValue.keySet();
	                                    for (String key : keys) {
	                                        lgr.setAttribute(key, secondJoinNameValue.get(key));
	                                    }
	                                }
	                            }
	                        }
	                    }
	//                }else if(annotation.IsRequired()&&!annotation.IsOpCell()){
	//                } else if (!annotation.IsOpCell()) {
	                } else{
	                	if(annotation.IsPrintCol()){
	                		if(classType.invoke(domain, "get" + annotation.ResourceKey())==null){
	                			lgr.setAttribute(annotation.ResourceKey(),(Object)null);
	                		}else{
	                			if (annotation.FieldType().equals("float")&&(Float)classType.invoke(domain, "get" + annotation.ResourceKey())==0) {
		                			lgr.setAttribute(annotation.ResourceKey(),(Object)null);
	                            } else if (annotation.FieldType().equals("integer")&&(Integer)classType.invoke(domain, "get" + annotation.ResourceKey())==0) {
		                			lgr.setAttribute(annotation.ResourceKey(),(Object)null);
	                            }else{
		                			lgr.setAttribute(annotation.ResourceKey(),
		                                    classType.invoke(domain, "get" + annotation.ResourceKey()));
	                            }
	                		}
	                    }else{
		                    if (annotation.FieldType().equals("boolean") && classType.invoke(domain, "get" + annotation.ResourceKey()) != null) {
		                        if (classType.invoke(domain, "get" + annotation.ResourceKey()).toString().equals("1")) {
		                            lgr.setAttribute(annotation.ResourceKey(), true);
		                        } else {
		                            lgr.setAttribute(annotation.ResourceKey(), false);
		                        }
		                    } else {
		//                        if (annotation.IsSelectItem()|| annotation.IsMupSelectItem()) {
			                        if (annotation.ClassName().length() == 0 || annotation.IsSelectItem()|| annotation.IsMupSelectItem()) {
			                            if (type.equals("Fetch")) {
			                                if(annotation.IsMupSelectItem()){
			                                    Object mupSelectValues=classType.invoke(domain, "get" + annotation.ResourceKey());
			                                    if(mupSelectValues!=null){
			                                        lgr.setAttribute(annotation.ResourceKey(),
			                                                mupSelectValues.toString().split(","));
			                                    }else{
			                                        lgr.setAttribute(annotation.ResourceKey(), (Object) null);
			                                    }
			                                }else{
			                                        lgr.setAttribute(annotation.ResourceKey(),
			                                                classType.invoke(domain, "get" + annotation.ResourceKey()));
		                                    }
			                            } else {
			                                        lgr.setAttribute(annotation.ResourceKey(),
			                                                classType.invoke(domain, "get" + annotation.ResourceKey()));
		                                }
		                            }else if(annotation.isSqlPojoField()){
		                            	if(lgr.getAttribute(annotation.ResourceKey())==null){
			                                lgr.setAttribute(annotation.ResourceKey(),
			                                        classType.invoke(domain, "get" + annotation.ResourceKey()));
		                            	}
		                            }else if("AutoComplete".equals(type)&&annotation.ClassName().length() > 0){
	            	                        Object objOto = classType.invoke(domain, "get" + OnlyClassName.removePackage(annotation.ClassName()));
	            	                        if (objOto != null) {
	            	                            ClassType classTypeOto = MyClassTypeUtil.getDomainClassType(objOto.getClass());
	            	                            if(classTypeOto.invoke(objOto, "get" + annotation.ResourceKey())==null){
		            	                			lgr.setAttribute(annotation.ResourceKey(),(Object)null);
	            	                            }
	            	                        }else{
	            	                			lgr.setAttribute(annotation.ResourceKey(),(Object)null);
	            	                        }
		                            }
		                        }
	                    }
	
	                }
	            }
	        }
    	}
        return lgr;
    }

    /**
     * 将实体类中的实体类，通过给定的字符串，获取对应属性的值
     * @param domain
     * @param JoinNameKey 注释中给定的字符串，映射关联字段的ResourceKey
     * @return 
     */
    @SuppressWarnings("rawtypes")
	public static LinkedHashMap<String, String> get2ndJoinNameValue(Object domain, String[] JoinNameKey) {

        ClassType classType = MyClassTypeUtil.getDomainClassType(domain.getClass());
        Field[] fields = MyClassTypeUtil.getDomainClassFields(domain.getClass());
        boolean hasAnnotation = false;
        EntityAnn annotation;

        for (int i = 0; i < fields.length; i++) {
            hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {
                annotation = fields[i].getAnnotation(EntityAnn.class);

                if (annotation.Is2ndJoin()) {

                    if (annotation.DomainMap().equals("oto")) {
                        String[] joinNameKye = annotation.JoinNameKey();
                        if (joinNameKye.length > 0) {
                            LinkedHashMap<String, String> joinNameValue = new LinkedHashMap<String, String>();
                            Object objOto = classType.invoke(domain, "get" + annotation.ResourceKey());
                            if (objOto != null) {
                                ClassType classTypeOto = MyClassTypeUtil.getDomainClassType(objOto.getClass());
                                if (classTypeOto != null) {
                                    for (int j = 0; j < joinNameKye.length; j++) {
                                        joinNameValue.put(joinNameKye[j], classTypeOto.invoke(objOto, "get" + joinNameKye[j]).toString());
                                    }
                                    return joinNameValue;
                                }
                            }
                        }
                    }
                }
            }
        }

        return null;
    }
}
