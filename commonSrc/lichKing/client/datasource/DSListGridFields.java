package lichKing.client.datasource;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import lichKing.client.entityAnnotation.EntityAnn;
import lichKing.client.entityAnnotation.MyClassTypeUtil;
import lichKing.client.pojo.PageSql;
import lichKing.client.server.AppParsServer;
import lichKing.client.server.BasicDataServer;
import lichKing.client.server.MsgServer;
import lichKing.client.utils.FloatCellFormatter;
import lichKing.client.utils.ReflectionField;
import lichKing.client.utils.SetCommonFilter;

import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.Field;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.TimeDisplayFormat;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * 列表datasource的构造类
 * @author catPan
 */
public class DSListGridFields {

    /**
     * 获取按照实体类的类型生成的datasource
     * @param domainClass 实体类的类型
     * @return 
     */
    @SuppressWarnings("rawtypes")
	public static DataSource getListGridDS(Class domainClass) {

        Field[] fields = MyClassTypeUtil.getDomainClassFields(domainClass);
        boolean hasAnnotation = false;
        EntityAnn annotation;

        DataSource ds = new DataSource();
        ds.setClientOnly(true);

        for (int i = 0; i < fields.length; i++) {
            hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {
                annotation = fields[i].getAnnotation(EntityAnn.class);
                if (annotation.IsRequired() && annotation.IsShowG() && annotation.GOrderNum() > 0) {
                    DataSourceField asField = new DataSourceField();
                    asField.setName(annotation.ResourceKey());
                    asField.setTitle(MsgServer.getMsg(annotation.ResourceKey()));
                    asField.setRequired(!annotation.IsNullable());
                    if (annotation.IsPrimaryKey()) {
                        asField.setCanEdit(false);
                    }
                    if (annotation.IsSelectItem() || annotation.IsMupSelectItem() || annotation.IsFixSelectItem().length > 0) {
                        if (annotation.IsSelectItem() || annotation.IsMupSelectItem()) {
                            asField.setTitle(MsgServer.getMsg(annotation.ResourceKey2()));
                            BasicDataServer.setSelectItem4CommonDB(asField, "ans.client." + annotation.ClassName(), null);
                        } else if (annotation.IsFixSelectItem().length > 0) {
                            String[] valueMap = new String[annotation.IsFixSelectItem().length];
                            for (int v = 0; v < annotation.IsFixSelectItem().length; v++) {
                                valueMap[v] = MsgServer.getMsg(annotation.IsFixSelectItem()[v]);
                            }
                            asField.setValueMap(valueMap);
                        }
                    } else {
                        asField.setType(MyFieldType.getFieldType(annotation.FieldType()));
                    }
                    ds.addField(asField);
                }
            }
        }


        return ds;
    }

    /**
     * 获取按照实体类的类型生成列名ListGridField[]，并且列名排序
     * @param domainClass 实体类的类型
     * @return 
     */
    @SuppressWarnings("rawtypes")
	public static ListGridField[] getListGridFieldsByOrder(Class domainClass) {


        Field[] fields = MyClassTypeUtil.getDomainClassFields(domainClass);
        boolean hasAnnotation = false;
        EntityAnn annotation;

        int count = getFieldsLenByOrder(fields, hasAnnotation);
        ListGridField tempField = new ListGridField();
        ListGridField[] orderField = new ListGridField[count];
        for (int i = 0; i < fields.length; i++) {
            hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {
                annotation = fields[i].getAnnotation(EntityAnn.class);

                if (annotation.IsRequired() && annotation.IsShowG() && annotation.GOrderNum() > 0) {
                    tempField = new ListGridField(annotation.ResourceKey(),
                            MsgServer.getMsg(annotation.ResourceKey()));
                    tempField.setWidth(annotation.GWidth());
                    tempField.setRequired(!annotation.IsNullable());
                    
                    if (annotation.FieldType().equals("integer")) {
                        tempField.setCellAlign(Alignment.RIGHT);
                    } else if (annotation.FieldType().equals("color")) {
                        tempField.setType(MyListGridFieldType.getFieldType("text"));
                    } else if (annotation.FieldType().equals("image")) {
                        tempField.setType(MyListGridFieldType.getFieldType("image"));
                        tempField.setCellAlign(Alignment.CENTER);
                        tempField.setImgDir("TAGS/");
                    } else if (annotation.FieldType().equals("file")) {
                        tempField.setType(MyListGridFieldType.getFieldType("text"));
                        tempField.setImgDir("TAGS/");
                    } else if (annotation.FieldType().equals("datetime")) {
                        tempField.setType(MyListGridFieldType.getFieldType("datetime"));
                        tempField.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATETIME);
                    } else if (annotation.FieldType().equals("date")) {
                        tempField.setType(MyListGridFieldType.getFieldType("date"));
                        tempField.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
                    } else if (annotation.FieldType().equals("time")) {
                        tempField.setType(MyListGridFieldType.getFieldType("time"));
                        tempField.setTimeFormatter(TimeDisplayFormat.TO24HOURTIME);
//                        TimeItem di=new TimeItem();
//                        di.setUseMask(true);
//                        tempField.setEditorType(di);
                    } else if (annotation.FieldType().equals("textarea")) {
                        tempField.setShowHover(true);
                        tempField.setHoverCustomizer(new HoverCustomizer() {

                            @Override
							public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
                                return value.toString();
                            }
                        });
                    }else if (annotation.FieldType().equals("float")||annotation.FieldType().equals("decimal")) {
                        tempField.setType(MyListGridFieldType.getFieldType("float"));
                        tempField.setCellFormatter(new FloatCellFormatter());
                        tempField.setCellAlign(Alignment.RIGHT);
                    }
                    if (annotation.IsCommonSelect()) {
                        tempField.setAttribute("selectUI", "select");

                        BasicDataServer.setCommonSelectItem(tempField, "ans.client.domain.APP_COMMON_TYPE", SetCommonFilter.commonSelectSql(annotation));

                    } else if (annotation.IsSelectItem() || annotation.IsMupSelectItem() || annotation.IsFixSelectItem().length > 0) {
                        tempField.setAttribute("selectUI", "select");
                        if (annotation.IsSelectItem() || annotation.IsMupSelectItem()) {
                            BasicDataServer.setSelectItem4CommonDB(tempField, "ans.client." + annotation.ClassName(), SetCommonFilter.domainSelectSql(annotation));
                        } else if (annotation.IsFixSelectItem().length > 0) {
                            String[] valueMap = new String[annotation.IsFixSelectItem().length];
                            for (int v = 0; v < annotation.IsFixSelectItem().length; v++) {
                                valueMap[v] = MsgServer.getMsg(annotation.IsFixSelectItem()[v]);
                            }
                            tempField.setValueMap(valueMap);
                        }
                    } else if (annotation.IsfixMapSelectItem()|| annotation.IsFixMupSelectItem()) {
                        tempField.setAttribute("selectUI", "select");
                        ClassType classType = MyClassTypeUtil.getDomainClassType("ans.client.datasource.AppDataMap");
                        tempField.setValueMap((LinkedHashMap) classType.invoke(null, annotation.ResourceKey()));
                    }else if (annotation.isNullSelect()) {
                        tempField.setAttribute("selectUI", "select");
                        tempField = new ListGridField(annotation.ResourceKey(),
                                MsgServer.getMsg(annotation.ResourceKey()));
                        tempField.setRequired(!annotation.IsNullable());
                        tempField.setValueMap(new LinkedHashMap<String, String>());
                    } else {
                        tempField.setType(MyListGridFieldType.getFieldType(annotation.FieldType()));
                    }
                    if (annotation.IsMupSelectItem()||annotation.IsFixMupSelectItem()) {
                        tempField.setMultiple(true);
                    }
                    if(annotation.ResourceKey2()!=null&&annotation.ResourceKey2().length()>0){
                        tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey2()));
                    }
                    if(!annotation.IsNullable()){
                    	tempField.setTitle("<b>"+tempField.getTitle()+"</b>");
                    }
                    if(annotation.hint().length()>0){
                    	tempField.setTitle(tempField.getTitle()+"("+annotation.hint()+")");
                    }
                    outF:
                    for (int n = 0; n < count; n++) {
                        if (annotation.GOrderNum() == indexOrder[n]) {
                            orderField[n] = tempField;
                            break outF;
                        }
                    }
                    tempField.setAlign(Alignment.LEFT);
                }
            }
        }

        return orderField;
    }
    public static int[] indexOrder;

    /**
     * 获取字段的个数
     * @param fields 字段（列）
     * @param hasAnnotation 是否有注释
     * @return 
     */
    public static int getFieldsLenByOrder(Field[] fields, boolean hasAnnotation) {
        int fieldsLen = 0;
        String orderStr = ",";
        EntityAnn annotation;
        for (int i = 0; i < fields.length; i++) {
            hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {
                annotation = fields[i].getAnnotation(EntityAnn.class);

                if (annotation.IsRequired() && annotation.IsShowG() && annotation.GOrderNum() > 0) {
                    if (orderStr.indexOf("," + annotation.GOrderNum() + ",") == -1) {
                        orderStr += annotation.GOrderNum() + ",";
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

    /**
     * 获取按照指定字段（列名）生成的列名ListGridField[]
     * @param domainClass 实体类的类型
     * @param yourFields 指定字段（列名）的字符串数组
     * @return 
     */
    @SuppressWarnings("rawtypes")
	public static ListGridField[] getListGridFieldsByFiledNames(Class domainClass, String[] yourFields) {

        Field[] fields = MyClassTypeUtil.getDomainClassFields(domainClass);
        boolean hasAnnotation = false;
        EntityAnn annotation;

        ListGridField tempField = new ListGridField();
        ListGridField[] myFields = new ListGridField[yourFields.length];
        for (int y = 0; y < yourFields.length; y++) {
            for (int i = 0; i < fields.length; i++) {
                hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
                if (hasAnnotation) {
                    annotation = fields[i].getAnnotation(EntityAnn.class);
                    if (annotation.ResourceKey().equals(yourFields[y])) {
                        tempField = new ListGridField(annotation.ResourceKey(),
                                MsgServer.getMsg(annotation.ResourceKey()));
                        tempField.setWidth(annotation.GWidth());
                        tempField.setRequired(!annotation.IsNullable());

                        if (annotation.IsSelectItem() || annotation.IsMupSelectItem() || annotation.IsFixSelectItem().length > 0) {
                            if (annotation.IsSelectItem() || annotation.IsMupSelectItem()) {
                                tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey2()));
                                BasicDataServer.setSelectItem4CommonDB(tempField, "ans.client." + annotation.ClassName(), null);
                            } else if (annotation.IsFixSelectItem().length > 0) {
                                String[] valueMap = new String[annotation.IsFixSelectItem().length];
                                for (int v = 0; v < annotation.IsFixSelectItem().length; v++) {
                                    valueMap[v] = MsgServer.getMsg(annotation.IsFixSelectItem()[v]);
                                }
                                tempField.setValueMap(valueMap);
                            }
                        } else {
                            tempField.setType(MyListGridFieldType.getFieldType(annotation.FieldType()));
                        }

                        myFields[y] = tempField;
                        break;
                    }
                }
            }
        }

        return myFields;
    }

    /**
     * 获取按照实体类的类型生成的列名ListGridField[]
     * @param domainClass 实体类的类型
     * @return 
     */
    @SuppressWarnings("rawtypes")
	public static ListGridField[] getListGridFields(Class domainClass) {

        Field[] fields = MyClassTypeUtil.getDomainClassFields(domainClass);
        boolean hasAnnotation = false;
        EntityAnn annotation;
//        int count=getFieldsLen(fields,hasAnnotation);
        ListGridField tempField = new ListGridField();
//        ListGridField[] orderField = new ListGridField[count];
        List<ListGridField> fieldList = new ArrayList<ListGridField>();
        for (Field field : fields) {
            hasAnnotation = field.isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {
                annotation = field.getAnnotation(EntityAnn.class);

                if (annotation.IsRequired() && annotation.IsShowG()) {
                    tempField = new ListGridField(annotation.ResourceKey(),
                            MsgServer.getMsg(annotation.ResourceKey()));
                    tempField.setWidth(annotation.GWidth());
                    tempField.setRequired(!annotation.IsNullable());
                    
                    if (annotation.FieldType().equals("color")) {
                        tempField.setType(MyListGridFieldType.getFieldType("text"));
                    } else if (annotation.FieldType().equals("image")) {
                        tempField.setType(MyListGridFieldType.getFieldType("image"));
                        tempField.setImgDir("TAGS/");
                    } else if (annotation.FieldType().equals("file")) {
                        tempField.setType(MyListGridFieldType.getFieldType("text"));
                        tempField.setImgDir("TAGS/");
                    } else if (annotation.FieldType().equals("datetime")) {
                        tempField.setType(MyListGridFieldType.getFieldType("datetime"));
                        tempField.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATETIME);
                    } else if (annotation.FieldType().equals("date")) {
                        tempField.setType(MyListGridFieldType.getFieldType("date"));
                        tempField.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
                    } else if (annotation.FieldType().equals("textarea")) {
                        tempField.setShowHover(true);
                        tempField.setHoverCustomizer(new HoverCustomizer() {

                            @Override
							public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
                                return value.toString();
                            }
                        });
                    }else if (annotation.FieldType().equals("text")) {
                        tempField.setType(MyListGridFieldType.getFieldType("text"));
                    }else if (annotation.FieldType().equals("float")||annotation.FieldType().equals("decimal")) {
                        tempField.setType(MyListGridFieldType.getFieldType("float"));
                        tempField.setCellFormatter(new FloatCellFormatter());
                        tempField.setCellAlign(Alignment.RIGHT);
                    }


                    if (annotation.IsCommonSelect()) {
                        tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey()));
                        ArrayList<PageSql> pageSqls = new ArrayList<PageSql>();
                        pageSqls.add(new PageSql.Builder("COMMON_TYPE_PCODE", "=").content(annotation.ResourceKey()).build());
                        pageSqls.add(new PageSql.Builder("VALD_FLAG", "=").content("1").build());

                        BasicDataServer.setCommonSelectItem(tempField, "ans.client.domain.APP_COMMON_TYPE", pageSqls);

                    } else if (annotation.IsSelectItem() || annotation.IsMupSelectItem() || annotation.IsFixSelectItem().length > 0) {
                        if (annotation.IsSelectItem() || annotation.IsMupSelectItem()) {
                            tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey2()));
                            ArrayList<PageSql> pageSqls = null;
                            if(annotation.onlyMineData()) {
                                pageSqls=new ArrayList<PageSql>();
                                pageSqls.add(new PageSql.Builder("USER_ID", "=").content(AppParsServer.getClientUser().getUSER_ID()).build());
                            }
                            BasicDataServer.setSelectItem4CommonDB(tempField, "ans.client." + annotation.ClassName(), pageSqls);
                            if (annotation.IsMupSelectItem()) {
                                tempField.setMultiple(true);
                            }
                        } else if (annotation.IsFixSelectItem().length > 0) {
                            String[] valueMap = new String[annotation.IsFixSelectItem().length];
                            for (int v = 0; v < annotation.IsFixSelectItem().length; v++) {
                                valueMap[v] = MsgServer.getMsg(annotation.IsFixSelectItem()[v]);
                            }
                            tempField.setValueMap(valueMap);
                        }
                    }else if (annotation.IsfixMapSelectItem()) {
                        tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey()));
                        ClassType classType = MyClassTypeUtil.getDomainClassType("ans.client.datasource.AppDataMap");
                        tempField.setValueMap((LinkedHashMap) classType.invoke(null, annotation.ResourceKey()));
                    } 
//                    else {
//                        tempField.setType(MyListGridFieldType.getFieldType(annotation.FieldType()));
//                    }
                    fieldList.add(tempField);
                }
            }
        }

        return fieldList.toArray(new ListGridField[fieldList.size()]);
    }

    /**
     * 获取字段（列）的个数
     * @param fields 字段（列）
     * @param hasAnnotation 是否有注释
     * @return 
     */
    public static int getFieldsLen(Field[] fields, boolean hasAnnotation) {
        int fieldsLen = 0;
        EntityAnn annotation;
        for (int i = 0; i < fields.length; i++) {
            hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {
                annotation = fields[i].getAnnotation(EntityAnn.class);

                if (annotation.IsRequired() && annotation.IsShowG() && annotation.GOrderNum() > 0) {
                    fieldsLen++;
                }
            }
        }
        return fieldsLen;
    }

    /**
     * 获取按照实体类的类型生成的列名ListGridField[]，没有操作列
     * @param domainClass 实体类的类型
     * @return ListGrid的列
     */
    @SuppressWarnings("rawtypes")
	public static ListGridField[] getLgFieldsNoOpCell(Class domainClass) {

        Field[] fields = MyClassTypeUtil.getDomainClassFields(domainClass);
        boolean hasAnnotation = false;
        EntityAnn annotation;

        int count = getLgFieldsNoOpCellLen(fields, hasAnnotation);
        ListGridField tempField = new ListGridField();
        ListGridField[] orderField = new ListGridField[count];
        for (int i = 0; i < fields.length; i++) {
            hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {
                annotation = fields[i].getAnnotation(EntityAnn.class);

//                if (annotation.IsRequired() && annotation.IsShowG() && !annotation.IsOpCell() && annotation.GOrderNum() > 0) {
                    if (annotation.IsRequired() && annotation.IsShowG() && annotation.GOrderNum() > 0) {
                    tempField = new ListGridField(annotation.ResourceKey(),
                            MsgServer.getMsg(annotation.ResourceKey()));
                    tempField.setWidth(annotation.GWidth());
                    //非空验证
                    if (!annotation.IsNullable()) {
                        tempField.setRequired(true);
                    }
                    if (annotation.IsSelectItem() || annotation.IsMupSelectItem() || annotation.IsFixSelectItem().length > 0) {
                        if (annotation.IsSelectItem() || annotation.IsMupSelectItem()) {
                            tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey2()));
                            BasicDataServer.setSelectItem4CommonDB(tempField, "ans.client." + annotation.ClassName(), null);
                        } else if (annotation.IsFixSelectItem().length > 0) {
                            String[] valueMap = new String[annotation.IsFixSelectItem().length];
                            for (int v = 0; v < annotation.IsFixSelectItem().length; v++) {
                                valueMap[v] = MsgServer.getMsg(annotation.IsFixSelectItem()[v]);
                            }
                            tempField.setValueMap(valueMap);
                        }
                    } else {
                        tempField.setType(MyListGridFieldType.getFieldType(annotation.FieldType()));
                    }
                    outF:
                    for (int n = 0; n < count; n++) {
                        if (annotation.GOrderNum() == indexOrder[n]) {
                            orderField[n] = tempField;
                            break outF;
                        }
                    }
                }
            }
        }

        return orderField;
    }

    /**
     * 获取没有操作列的数量
     * @param fields
     * @param hasAnnotation
     * @return 
     */
    public static int getLgFieldsNoOpCellLen(Field[] fields, boolean hasAnnotation) {
        int fieldsLen = 0;
        String orderStr = ",";
        EntityAnn annotation;
        for (int i = 0; i < fields.length; i++) {
            hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {
                annotation = fields[i].getAnnotation(EntityAnn.class);

//                if (annotation.IsRequired() && annotation.IsShowG() && !annotation.IsOpCell() && annotation.GOrderNum() > 0) {
                    if (annotation.IsRequired() && annotation.IsShowG() && annotation.GOrderNum() > 0) {
                    if (orderStr.indexOf("," + annotation.GOrderNum() + ",") == -1) {
                        orderStr += annotation.GOrderNum() + ",";
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
}