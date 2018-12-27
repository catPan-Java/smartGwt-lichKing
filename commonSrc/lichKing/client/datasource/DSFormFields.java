package lichKing.client.datasource;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import lichKing.client.entityAnnotation.EntityAnn;
import lichKing.client.entityAnnotation.MyClassTypeUtil;
import lichKing.client.pojo.PageSql;
import lichKing.client.server.BasicDataServer;
import lichKing.client.server.MsgServer;
import lichKing.client.utils.FloatItemFormatter;
import lichKing.client.utils.OnlyClassName;
import lichKing.client.utils.ReflectionField;

import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.Field;
import com.gwtent.reflection.client.TypeOracle;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.BooleanItem;
import com.smartgwt.client.widgets.form.fields.ColorPickerItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.UploadItem;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;

/**
 * 表单datasource的构造类
 * @author catPan
 */
public class DSFormFields extends DataSource {

    //id和datasource的map，通常存放页面名称和实体类反射生产的datasource
    private static LinkedHashMap<String, DataSource> DSMap = new LinkedHashMap<String, DataSource>();

    /**
     * 通过id生成或者获取实体类反射生产的datasource
     * @param id 页面名称（如SYSUI010……）
     * @param domianClass 实体类的类型
     * @return DataSource
     */
    @SuppressWarnings("rawtypes")
	public static DSFormFields getYourFiedsFormDS(String id, String[] yourFields, Class domianClass) {
    	if(yourFields!=null&&yourFields.length>0){
	        if (DSMap.isEmpty() || !DSMap.containsKey(id)) {
	            DSMap.put(id, new DSFormFields(yourFields, domianClass));
	        }
	        return (DSFormFields) DSMap.get(id);
    	}
    	return null;
    }


	public static DataSource getCommonSearchFDS(List<PageSql> pageSqlList,Class domainClass) {

		String id="comSearch_"+OnlyClassName.removePackage(domainClass.getName());
		if(pageSqlList==null){
	        return DSMap.get(id);
		}else{
	        if (DSMap.isEmpty() || !DSMap.containsKey(id)) {
				DataSource ds=new DataSource();
				ds.setClientOnly(true);
		        Field[] fields = MyClassTypeUtil.getDomainClassFields(domainClass);
		        boolean hasAnnotation = false;
		        EntityAnn annotation;
		
		        DataSourceField tempField = new DataSourceField();
		        DataSourceField[] myFields = new DataSourceField[pageSqlList.size()];
		        String tmpFN="";
		        for (int y = 0; y < pageSqlList.size(); y++) {
		            for (int i = 0; i < fields.length; i++) {
		                hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
		                if (hasAnnotation) {
		                    annotation = fields[i].getAnnotation(EntityAnn.class);
		                    tmpFN=pageSqlList.get(y).getField().replace(".", ";");// .号好像有问题 用;号代替
		                    if(tmpFN.indexOf(";")!=-1){
		                    	tmpFN=tmpFN.split(";")[1];// psList.add(new PageSql.Builder("sw.SWINERY_NAME", "iStartsWith")……
		                    }
		                    if (annotation.ResourceKey().equals(tmpFN)) {
		                        if (annotation.IsCommonSelect()) {
		                            tempField = new DataSourceField(annotation.ResourceKey(),
		                                    MyFieldType.getFieldType(annotation.FieldType()));
		                            tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey()));
		                            //                        tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey2()));
		                            ArrayList<PageSql> pageSqls = new ArrayList<PageSql>();
		                            pageSqls.add(new PageSql.Builder("COMMON_TYPE_PCODE", "=").content(annotation.ResourceKey()).build());
		                            pageSqls.add(new PageSql.Builder("VALD_FLAG", "=").content("1").build());
		
		//                            SelectItem sItem=new SelectItem(annotation.ResourceKey(),MsgServer.getMsg(annotation.ResourceKey()));
		//                            tempField.setEditorType(sItem);
		                            BasicDataServer.setCommonSelectItem(tempField, "ans.client.domain.APP_COMMON_TYPE", pageSqls);
		
		                        } else if (annotation.IsSelectItem() || annotation.IsfixMapSelectItem()) {
		                            tempField = new DataSourceField(annotation.ResourceKey(),
		                                    MyFieldType.getFieldType(annotation.FieldType()));
		                            tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey()));
		                            if (annotation.IsSelectItem()) {
		                                tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey2()));
		                                ArrayList<PageSql> pageSqls = new ArrayList<PageSql>();
		                                BasicDataServer.setSelectItem4CommonDB(tempField, "ans.client." + annotation.ClassName(), pageSqls);
		                            } else if (annotation.IsfixMapSelectItem()) {
		                                ClassType classType = MyClassTypeUtil.getDomainClassType("ans.client.datasource.AppDataMap");
//		                                if(classType.invoke(null, annotation.ResourceKey()+"2")!=null){
		                                if(classType.getMethod(annotation.ResourceKey()+"2", null)!=null){
		                                	tempField.setValueMap((LinkedHashMap) classType.invoke(null, annotation.ResourceKey()+"2"));
		                                }else{
		                                	tempField.setValueMap((LinkedHashMap) classType.invoke(null, annotation.ResourceKey()));
		                                }
		                            }
		                        } else if (annotation.IsMupSelectItem() || annotation.IsFixSelectItem().length > 0) {
		                            tempField = new DataSourceField(annotation.ResourceKey(),
		                                    MyFieldType.getFieldType(annotation.FieldType()));
		                            tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey()));
		                            if (annotation.IsMupSelectItem()) {
		                                tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey2()));
		                                tempField.setMultiple(true);
		                                ArrayList<PageSql> pageSqls = new ArrayList<PageSql>();
		                                BasicDataServer.setSelectItem4CommonDB(tempField, "ans.client." + annotation.ClassName(), pageSqls);
		                            } else if (annotation.IsFixSelectItem().length > 0) {
		                                String[] valueMap = new String[annotation.IsFixSelectItem().length];
		                                for (int v = 0; v < annotation.IsFixSelectItem().length; v++) {
		                                    valueMap[v] = MsgServer.getMsg(annotation.IsFixSelectItem()[v]);
		                                }
		                                tempField.setValueMap(valueMap);
		                            }
		//                        } else if (annotation.IsRequired() && !annotation.IsOpCell()) {
		                        } else if (annotation.IsPrimaryKey()) {
		                            tempField = new DataSourceField(annotation.ResourceKey(),
		                                    MyFieldType.getFieldType(annotation.FieldType()),
		                                    MsgServer.getMsg(annotation.ResourceKey()));
		                            tempField.setHidden(!annotation.IsShowF());
		                        } else if (annotation.FieldType().equals("date")||annotation.FieldType().equals("datetime")) {
		                            tempField = new DataSourceField(annotation.ResourceKey(),
		                                    MyFieldType.getFieldType(annotation.FieldType()),
		                                    MsgServer.getMsg(annotation.ResourceKey()));
		                            tempField.setAttribute("useTextField", true);
		                            tempField.setType(FieldType.DATE);
		                        }
		                        else if (annotation.IsRequired()) {
		                            tempField = new DataSourceField(annotation.ResourceKey(),
		                                    MyFieldType.getFieldType(annotation.FieldType()));
		                            tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey()));
		
//		                            if(annotation.FieldType().equals("date")||annotation.FieldType().equals("datetime")){
//		                            	tempField.setAttribute("useTextField", true);
//		                            	tempField.setEditorType(new DateRangeItem(annotation.ResourceKey(), MsgServer.getMsg(annotation.ResourceKey())));
//		                            }
		                        }
		                        if(annotation.ResourceKey2().length()>0){
		                        	tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey2()));
		                        }
		                        if(annotation.hint().length()>0){
		                        	tempField.setAttribute("hint", annotation.hint());
		                        	tempField.setAttribute("showHintInField", true);
		                        }
		                        myFields[y] = tempField;
		                        break;
		                    }
		                }
		            }
		        }
		        ds.setFields(myFields);
		
		        DSMap.put(id, ds);
	        }
		}
        return DSMap.get(id);
    } 

    /**
     * 获取按照指定的字符串数组生成datasource
     * @param yourFields 指定的字符串数组
     * @param domainClass 实体类的类型
     * @return datasource
     */
    @SuppressWarnings("rawtypes")
	public static DSFormFields getFieldsByFieldNames(String[] yourFields, Class domainClass) {
        return new DSFormFields(yourFields, domainClass);
    }

    /**
     * 构造datasource，按照指定的字符串数组生成
     * @param yourFields 指定的字符串数组
     * @param domainClass 实体类的类型
     */
    @SuppressWarnings("rawtypes")
	public DSFormFields(String[] yourFields, Class domainClass) {

        Field[] fields = MyClassTypeUtil.getDomainClassFields(domainClass);
        boolean hasAnnotation = false;
        EntityAnn annotation;

        DataSourceField tempField = new DataSourceField();
        DataSourceField[] myFields = new DataSourceField[yourFields.length];
        for (int y = 0; y < yourFields.length; y++) {
            for (int i = 0; i < fields.length; i++) {
                hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
                if (hasAnnotation) {
                    annotation = fields[i].getAnnotation(EntityAnn.class);
                    if (annotation.ResourceKey().equals(yourFields[y])) {
                        if (annotation.IsCommonSelect()) {
                            tempField = new DataSourceField(annotation.ResourceKey(),
                                    MyFieldType.getFieldType(annotation.FieldType()));
                            tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey()));
                            tempField.setRequired(!annotation.IsNullable());
                            //                        tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey2()));
                            ArrayList<PageSql> pageSqls = new ArrayList<PageSql>();
                            pageSqls.add(new PageSql.Builder("COMMON_TYPE_PCODE", "=").content(annotation.ResourceKey()).build());
                            pageSqls.add(new PageSql.Builder("VALD_FLAG", "=").content("1").build());

                            BasicDataServer.setCommonSelectItem(tempField, "ans.client.domain.APP_COMMON_TYPE", pageSqls);

                        } else if (annotation.IsSelectItem() || annotation.IsFixSelectItem().length > 0) {
                            tempField = new DataSourceField(annotation.ResourceKey(),
                                    MyFieldType.getFieldType(annotation.FieldType()));
                            tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey()));
                            tempField.setRequired(!annotation.IsNullable());
                            if (annotation.IsSelectItem()) {
                                tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey2()));
                                BasicDataServer.setSelectItem4CommonDB(tempField, "ans.client." + annotation.ClassName(), null);
                            } else if (annotation.IsFixSelectItem().length > 0) {
                                String[] valueMap = new String[annotation.IsFixSelectItem().length];
                                for (int v = 0; v < annotation.IsFixSelectItem().length; v++) {
                                    valueMap[v] = MsgServer.getMsg(annotation.IsFixSelectItem()[v]);
                                }
                                tempField.setValueMap(valueMap);
                            }
                        } else if (annotation.IsMupSelectItem() || annotation.IsFixSelectItem().length > 0) {
                            tempField = new DataSourceField(annotation.ResourceKey(),
                                    MyFieldType.getFieldType(annotation.FieldType()));
                            tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey()));
                            tempField.setRequired(!annotation.IsNullable());
                            if (annotation.IsMupSelectItem()) {
                                tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey2()));
                                tempField.setMultiple(true);
                                BasicDataServer.setSelectItem4CommonDB(tempField, "ans.client." + annotation.ClassName(), null);
                            } else if (annotation.IsFixSelectItem().length > 0) {
                                String[] valueMap = new String[annotation.IsFixSelectItem().length];
                                for (int v = 0; v < annotation.IsFixSelectItem().length; v++) {
                                    valueMap[v] = MsgServer.getMsg(annotation.IsFixSelectItem()[v]);
                                }
                                tempField.setValueMap(valueMap);
                            }
//                        } else if (annotation.IsRequired() && !annotation.IsOpCell()) {
                        } else if (annotation.IsRequired()) {
                            tempField = new DataSourceField(annotation.ResourceKey(),
                                    MyFieldType.getFieldType(annotation.FieldType()));
                            tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey()));
                            tempField.setRequired(!annotation.IsNullable());
                            if (annotation.FieldType().equals("datetime")) {
                                tempField.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATETIME);
                            } else {
                                if (annotation.FieldType().equals("textarea")) {
                                    tempField.setEditorType(new TextAreaItem());
                                }
                                tempField.setLength(annotation.Length());
                            }
                        } else if (annotation.IsPrimaryKey()) {
                            tempField = new DataSourceField(annotation.ResourceKey(),
                                    MyFieldType.getFieldType(annotation.FieldType()),
                                    MsgServer.getMsg(annotation.ResourceKey()));
                            tempField.setHidden(!annotation.IsShowF());
                        }
                        if(annotation.ResourceKey2().length()>0){
                        	tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey2()));
                        }
                        myFields[y] = tempField;
                        break;
                    }
                }
            }
        }
        setFields(myFields);
    }

    /**
     * 获取按照实体类的类型生成的datasource
     * @param domainClass 实体类的类型
     * @return datasource
     */
    @SuppressWarnings("rawtypes")
	public static DSFormFields getInstance(Class domainClass) {
        return new DSFormFields(domainClass);
    }

    /**
     * 构造表单的datasource，按照实体类的类型，并排序
     * @param domainClass 实体类的类型
     */
    @SuppressWarnings("rawtypes")
	public DSFormFields(Class domainClass) {
        //获取反射类类型
        Field[] fields = MyClassTypeUtil.getDomainClassFields(domainClass);
        boolean hasAnnotation = false;
        EntityAnn annotation;

        int count = getFieldsLen(fields, hasAnnotation);

        DataSourceField tempField = null;
        DataSourceField[] orderField = new DataSourceField[count];
        for (Field field : fields) {
            hasAnnotation = field.isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {

                annotation = field.getAnnotation(EntityAnn.class);
                //绑定字段并设置属性
//                if (annotation.IsRequired() && !annotation.IsOpCell() && annotation.FOrderNum() > 0) {
                    if (annotation.IsRequired() && annotation.FOrderNum() > 0) {
                    tempField = new DataSourceField(annotation.ResourceKey(),
                            MyFieldType.getFieldType(annotation.FieldType()),
                            MsgServer.getMsg(annotation.ResourceKey()),
                            annotation.Length(),
                            !annotation.IsNullable());
                    if (annotation.FieldType().equals("textarea")) {
                        tempField.setEditorType(new TextAreaItem());
                    }else if (annotation.IsfixMapSelectItem()) {
                        ClassType classType = MyClassTypeUtil.getDomainClassType("ans.client.datasource.AppDataMap");
                        tempField.setValueMap((LinkedHashMap) classType.invoke(null, annotation.ResourceKey()));
                    }else if(annotation.FieldType().equals("date")){
                        tempField.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
                        tempField.setAttribute("useTextField", true);
                    }else if(annotation.FieldType().equals("datetime")){
                        tempField.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATETIME);
                        tempField.setAttribute("useTextField", true);
                    }else if(annotation.formUI().equals("ComboBoxItem")){
                    	tempField.setAttribute("editorType", "ComboBoxItem");
                    }else if(annotation.formUI().equals("ColorPickerItem")){
                    	tempField.setAttribute("editorType", "ColorPickerItem");
                    }
                    if(annotation.ResourceKey2().length()>0){
                    	tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey2()));
                    }
                    tempField.setHidden(!annotation.IsShowF());
                    //排序
                    outF:
                    for (int n = 0; n < count; n++) {
                        if (annotation.FOrderNum() == indexOrder[n]) {
                            orderField[n] = tempField;
                            break outF;
                        }
                    }
                }

            }
        }
        setFields(orderField);
    }
    public static int[] indexOrder;

    /**
     * 字段排序，根据注释中给定的顺序
     * @param fields 字段（属性）
     * @param hasAnnotation 是否有注释特性
     * @return
     */
    public static int getFieldsLen(Field[] fields, boolean hasAnnotation) {
        int fieldsLen = 0;
        String orderStr = ",";
        EntityAnn annotation;
        for (int i = 0; i < fields.length; i++) {
            hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {
                annotation = fields[i].getAnnotation(EntityAnn.class);
//                if (annotation.IsRequired() && !annotation.IsOpCell() && annotation.FOrderNum() > 0) {
                    if (annotation.IsRequired() && annotation.FOrderNum() > 0) {
                    if (orderStr.indexOf("," + annotation.FOrderNum() + ",") == -1) {
                        orderStr += annotation.FOrderNum() + ",";
                        fieldsLen++;
                    }
                }

            }
        }
        if (fieldsLen > 0) {
            indexOrder = new int[fieldsLen];
            String[] strOrder = orderStr.substring(1).split(",");
            for (int i = 0; i < fieldsLen; i++) {
                indexOrder[i] = Integer.parseInt(strOrder[i]);
            }
            ReflectionField.insertSort(indexOrder);
        }
        return fieldsLen;
    }

//    /**
//     * 多项编辑的表单的datasource，根据注释中给定的EditOnForm注释，并排序
//     * @param domainClass 实体类的类型
//     * @param other 无意义
//     * @return 
//     */
//    @SuppressWarnings("rawtypes")
//	public static DSFormFields getInstanceEditOnForm(Class domainClass, String other) {
//        return new DSFormFields(domainClass, other);
//    }

//    /**
//     * 构造多项编辑的表单的datasource
//     * @param domain 实体类的类型
//     * @param other 无意义
//     */
//    @SuppressWarnings("rawtypes")
//	public DSFormFields(Class domainClass, String other) {
//        Field[] fields = MyClassTypeUtil.getDomainClassFields(domainClass);
//        boolean hasAnnotation = false;
//        BaseDomainAnnotation annotation;
//
//        int count = getFieldsLenEditOnForm(fields, hasAnnotation);
//
//        DataSourceField tempField = null;
//        DataSourceField[] orderField = new DataSourceField[count];
//        for (Field field : fields) {
//            hasAnnotation = field.isAnnotationPresent(BaseDomainAnnotation.class);
//            if (hasAnnotation) {
//
//                annotation = field.getAnnotation(BaseDomainAnnotation.class);
//
//                //绑定字段并设置属性
//                if (annotation.EditOnForm()) {
//                    tempField = new DataSourceField(annotation.ResourceKey(),
//                            MyFieldType.getFieldType(annotation.FieldType()),
//                            MsgServer.getMsg(annotation.ResourceKey()),
//                            annotation.Length(),
//                            !annotation.IsNullable());
//                    if (annotation.FieldType().equals("textarea")) {
//                        tempField.setEditorType(new TextAreaItem());
//                    }
//                    tempField.setHidden(!annotation.IsShowF());
//                    //排序
//                    outF:
//                    for (int n = 0; n < count; n++) {
//                        if (annotation.FOrderNum() == indexOrder[n]) {
//                            orderField[n] = tempField;
//                            break outF;
//                        }
//                    }
//                }
//            }
//        }
//        setFields(orderField);
//    }
//
//    /**
//     * 需要生成字段的个数
//     * @param fields 字段（表单）、类（属性）
//     * @param hasAnnotation 是否有注释特性
//     * @return
//     */
//    public static int getFieldsLenEditOnForm(Field[] fields, boolean hasAnnotation) {
//        int fieldsLen = 0;
//        String orderStr = ",";
//        BaseDomainAnnotation annotation;
//        for (int i = 0; i < fields.length; i++) {
//            hasAnnotation = fields[i].isAnnotationPresent(BaseDomainAnnotation.class);
//            if (hasAnnotation) {
//                annotation = fields[i].getAnnotation(BaseDomainAnnotation.class);
//                if (annotation.EditOnForm()) {
//                    if (orderStr.indexOf("," + annotation.FOrderNum() + ",") == -1) {
//                        orderStr += annotation.FOrderNum() + ",";
//                        fieldsLen++;
//                    }
//                }
//            }
//        }
//        if (fieldsLen > 0) {
//            indexOrder = new int[fieldsLen];
//            String[] strOrder = orderStr.substring(1).split(",");
//            for (int i = 0; i < fieldsLen; i++) {
//                indexOrder[i] = Integer.parseInt(strOrder[i]);
//            }
//            ReflectionField.insertSort(indexOrder);
//        }
//        return fieldsLen;
//    }

    /**
     * 剔除removeFileds给定的字符串数组，构造表单的datasource，并排序
     * @param domainClass 实体类的类型
     * @param removeFileds 要剔除的字符串数组
     * @return 
     */
    @SuppressWarnings("rawtypes")
	public static DSFormFields getInstanceByRemoveFields(Class domainClass, String[] removeFileds) {
        return new DSFormFields(domainClass, removeFileds);
    }

    /**
     * 构造表单的datasource
     * @param domain 实体类的类型
     * @param removeFileds 要剔除的字段
     */
    @SuppressWarnings("rawtypes")
	public DSFormFields(Class domainClass, String[] removeFileds) {
        Field[] fields = MyClassTypeUtil.getDomainClassFields(domainClass);
        boolean hasAnnotation = false;
        EntityAnn annotation;

        int count = getFieldsLen(fields, hasAnnotation, removeFileds);

        DataSourceField tempField = null;
        DataSourceField[] orderField = new DataSourceField[count];
        for (Field field : fields) {
            hasAnnotation = field.isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {

                annotation = field.getAnnotation(EntityAnn.class);

                boolean isRemoveFileds = false;
                for (int s = 0; s < removeFileds.length; s++) {
                    if (annotation.ResourceKey().equals(removeFileds[s])) {
                        isRemoveFileds = true;
                        break;
                    }
                }
                if (!isRemoveFileds) {
                    //绑定字段并设置属性
//                    if (annotation.IsRequired() && !annotation.IsOpCell() && annotation.FOrderNum() > 0) {
                        if (annotation.IsRequired() && annotation.FOrderNum() > 0) {
                        tempField = new DataSourceField(annotation.ResourceKey(),
                                MyFieldType.getFieldType(annotation.FieldType()),
                                MsgServer.getMsg(annotation.ResourceKey()),
                                annotation.Length(),
                                !annotation.IsNullable());
                        if (annotation.FieldType().equals("textarea")) {
                            tempField.setEditorType(new TextAreaItem());
                        }
                        tempField.setHidden(!annotation.IsShowF());
                        //排序
                        outF:
                        for (int n = 0; n < count; n++) {
                            if (annotation.FOrderNum() == indexOrder[n]) {
                                orderField[n] = tempField;
                                break outF;
                            }
                        }
                    }
                }
            }
        }
        setFields(orderField);
    }

    /**
     * 需要生成字段的个数
     * @param fields 字段（表单）、类（属性）
     * @param hasAnnotation 是否有注释特性
     * @param removeFileds 要剔除的字段
     * @return
     */
    public static int getFieldsLen(Field[] fields, boolean hasAnnotation, String[] removeFileds) {
        int fieldsLen = 0;
        String orderStr = ",";
        EntityAnn annotation;
        for (int i = 0; i < fields.length; i++) {
            hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {
                annotation = fields[i].getAnnotation(EntityAnn.class);
                boolean isRemoveFileds = false;
                for (int s = 0; s < removeFileds.length; s++) {
                    if (annotation.ResourceKey().equals(removeFileds[s])) {
                        isRemoveFileds = true;
                        break;
                    }
                }
                if (!isRemoveFileds) {
//                    if (annotation.IsRequired() && !annotation.IsOpCell() && annotation.FOrderNum() > 0) {
                        if (annotation.IsRequired() && annotation.FOrderNum() > 0) {
                        if (orderStr.indexOf("," + annotation.FOrderNum() + ",") == -1) {
                            orderStr += annotation.FOrderNum() + ",";
                            fieldsLen++;
                        }
                    }
                }
            }
        }
        if (fieldsLen > 0) {
            indexOrder = new int[fieldsLen];
            String[] strOrder = orderStr.substring(1).split(",");
            for (int i = 0; i < fieldsLen; i++) {
                indexOrder[i] = Integer.parseInt(strOrder[i]);
            }
            ReflectionField.insertSort(indexOrder);
        }
        return fieldsLen;
    }

    /**
     * 获取表单的表单元素数组FormItem[],通过实体类的类型反射生成
     * @param domainClass 实体类的类型
     * @return FormItem[]
     */
    @SuppressWarnings("rawtypes")
	public static FormItem[] getFormItems(Class domainClass) {

        Field[] fields = MyClassTypeUtil.getDomainClassFields(domainClass);
        boolean hasAnnotation = false;
        EntityAnn annotation;

        int count = getFieldsLen(fields, hasAnnotation);

        FormItem tempItem = null;
        FormItem[] formItems = new FormItem[count];
        for (Field field : fields) {
            hasAnnotation = field.isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {

                annotation = field.getAnnotation(EntityAnn.class);
                //生成各种formItem
//                if (annotation.IsRequired() && !annotation.IsOpCell() && annotation.IsShowF() && annotation.FOrderNum() > 0) {
                    if (annotation.IsRequired() && annotation.IsShowF() && annotation.FOrderNum() > 0) {
                    if (annotation.FieldType().equals("text")) {
                        TextItem text = new TextItem(annotation.ResourceKey(), MsgServer.getMsg(annotation.ResourceKey()));
                        text.setLength(annotation.Length());
                        text.setRequired(!annotation.IsNullable());
//                        if(!annotation.IsShowF()){
//                            text.hide();
//                        }
                        tempItem = text;
                    } else if (annotation.FieldType().equals("integer")) {
                        IntegerItem integerItem = new IntegerItem(annotation.ResourceKey(), MsgServer.getMsg(annotation.ResourceKey()));
                        integerItem.setRequired(!annotation.IsNullable());
                        integerItem.setLength(annotation.Length());
                        tempItem = integerItem;
                    } else if (annotation.FieldType().equals("color")) {
                        ColorPickerItem color = new ColorPickerItem(annotation.ResourceKey(), MsgServer.getMsg(annotation.ResourceKey()));
                        color.setRequired(!annotation.IsNullable());
                        tempItem = color;
                    } else if (annotation.FieldType().equals("boolean")) {
                        BooleanItem booleanItem = new BooleanItem(annotation.ResourceKey(), MsgServer.getMsg(annotation.ResourceKey()));
                        booleanItem.setRequired(!annotation.IsNullable());
                        tempItem = booleanItem;
                    } else if (annotation.FieldType().equals("textarea")) {
                        TextAreaItem areaItem = new TextAreaItem(annotation.ResourceKey(), MsgServer.getMsg(annotation.ResourceKey()));
                        areaItem.setRequired(!annotation.IsNullable());
                        tempItem = areaItem;
                    } else if (annotation.FieldType().equals("image")) {
//                        
                        RegExpValidator validator = new RegExpValidator("(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$");

                        UploadItem fileItem = new UploadItem(annotation.ResourceKey(), MsgServer.getMsg(annotation.ResourceKey()));
                        fileItem.setRequired(!annotation.IsNullable());
                        fileItem.setValidators(validator);
                        fileItem.setHint("小于500KB");
                        tempItem = fileItem;
                    } else if (annotation.FieldType().equals("float")) {
                        FloatItem floatItem = new FloatItem(annotation.ResourceKey(), MsgServer.getMsg(annotation.ResourceKey()));
                        floatItem.setRequired(!annotation.IsNullable());
                        tempItem = floatItem;
                    }else if(annotation.FieldType().equals("date")){
                    	DateItem dateItem=new DateItem(annotation.ResourceKey(), MsgServer.getMsg(annotation.ResourceKey()));
                    	tempItem=dateItem;
                    }else if (annotation.FieldType().equals("datetime")) {
                    	DateItem dateItem=new DateItem(annotation.ResourceKey(), MsgServer.getMsg(annotation.ResourceKey()));
                    	dateItem.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATETIME);
                    	dateItem.setUseTextField(true);
                    	tempItem=dateItem;
                    }
//                        else if(annotation.FieldType().equals("Muifile")){
//                        MultiFilePicker filePicker=new MultiFilePicker();
//
//                        tempItem=fileItem;
//                    }
                    //排序
                    outF:
                    for (int n = 0; n < count; n++) {
                        if (annotation.FOrderNum() == indexOrder[n]) {
                            formItems[n] = tempItem;
                            break outF;
                        }
                    }

                }

            }
        }


        return formItems;
    }

    /**
     * 获取表单的表单元素数组FormItem[]，不可编辑的表单,通过实体类的类型反射生成
     * @param domainClass 实体类的类型
     * @return 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static FormItem[] getStaticTextItems(Class domainClass) {

        ClassType classType = TypeOracle.Instance.getClassType(domainClass);

        Field[] fields = classType.getFields();
        boolean hasAnnotation = false;
        int count = getFieldsLen(fields, hasAnnotation);

        StaticTextItem tempItem = null;
        FormItem[] formItems = new FormItem[count];
        EntityAnn annotation;
        String resourceKey = "";
        for (Field field : fields) {
            hasAnnotation = field.isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {

                annotation = field.getAnnotation(EntityAnn.class);
                //生成StaticTextItem
                if (annotation.FOrderNum() > 0) {
	                StaticTextItem text = new StaticTextItem();
	                text.setName(annotation.ResourceKey());
	                resourceKey = annotation.ResourceKey();
                    if (annotation.IsSelectItem()) {
                        resourceKey = annotation.ResourceKey2();
                    }else if (annotation.IsCommonSelect()) {
                        ArrayList<PageSql> pageSqls = new ArrayList<PageSql>();
                        pageSqls.add(new PageSql.Builder("COMMON_TYPE_PCODE", "=").content(annotation.ResourceKey()).build());
                        pageSqls.add(new PageSql.Builder("VALD_FLAG", "=").content("1").build());

                        BasicDataServer.setCommonSelectItem(text, "ans.client.domain.APP_COMMON_TYPE", pageSqls);

                    }
                    if(annotation.FieldType().equals("date")){
                    	text.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
                    }else if(annotation.FieldType().equals("float")){
                    	text.setValueFormatter(new FloatItemFormatter());
                    }
                    if(annotation.ResourceKey2().length()>0){
                    	resourceKey=annotation.ResourceKey2();
                    }
                    if(annotation.hint().length()>0){
                    	text.setHint("<nobr>"+annotation.hint()+"<nobr>");
                    }
                    text.setTitle(MsgServer.getMsg(resourceKey));
                    tempItem = text;
                    //排序
                    outF:
                    for (int n = 0; n < count; n++) {
                        if (annotation.FOrderNum() == indexOrder[n]) {
                            formItems[n] = tempItem;
                            break outF;
                        }
                    }
	            }
            }
        }

        return formItems;
    }
}
