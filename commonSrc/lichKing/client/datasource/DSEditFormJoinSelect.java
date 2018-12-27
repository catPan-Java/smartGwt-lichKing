package lichKing.client.datasource;

import java.util.LinkedHashMap;

import lichKing.client.entityAnnotation.EntityAnn;
import lichKing.client.entityAnnotation.MyClassTypeUtil;
import lichKing.client.server.BasicDataServer;
import lichKing.client.server.MsgServer;
import lichKing.client.utils.ReflectionField;
import lichKing.client.utils.SetCommonFilter;

import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.Field;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.TimeDisplayFormat;


/**
 *
 * @author catPan
 */
public class DSEditFormJoinSelect extends DataSource {
    //id和datasource的map，通常存放页面名称和实体类反射生产的datasource
    private static LinkedHashMap<String, DataSource> DSMap = new LinkedHashMap<String, DataSource>();

    /**
     * 通过id生成或者获取实体类反射生产的datasource
     * @param id 页面名称（如SYSUI010……）
     * @param domianClass 实体类的类型
     * @return DataSource
     */
    @SuppressWarnings("rawtypes")
	public static DSEditFormJoinSelect getEditFormDS(String id, Class domianClass) {
        if (DSMap.isEmpty() || !DSMap.containsKey(id)) {
            DSMap.put(id, new DSEditFormJoinSelect(domianClass));
        }

        return (DSEditFormJoinSelect) DSMap.get(id);
    }

    /**
     * 实体类反射生产datasource
     * @param domainClass 
     */
    @SuppressWarnings("rawtypes")
	public DSEditFormJoinSelect(Class domainClass) {

        Field[] fields = MyClassTypeUtil.getDomainClassFields(domainClass);
        boolean hasAnnotation = false;
        EntityAnn annotation;

        int count = getFieldsLenEditOnForm(fields, hasAnnotation);
        DataSourceField[] orderField = new DataSourceField[count];
        DataSourceField tempField = new DataSourceField();
        for (Field field : fields) {
            hasAnnotation = field.isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {
                annotation = field.getAnnotation(EntityAnn.class);
//                if(annotation.FOrderNum()>0 || annotation.IsPrimaryKey()){
//                if(annotation.FOrderNum()>0&&!hideFields(annotation)){
                    if(annotation.FOrderNum()>0){
                    if (annotation.isNullSelect()) {
                        tempField = new DataSourceField(annotation.ResourceKey(),
                                MyFieldType.getFieldType(annotation.FieldType()));
                        tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey()));
                        tempField.setRequired(!annotation.IsNullable());
                        tempField.setValueMap(new LinkedHashMap<String, String>());
                    }else if (annotation.IsCommonSelect()) {
                        tempField = new DataSourceField(annotation.ResourceKey(),
                                MyFieldType.getFieldType(annotation.FieldType()));
                        tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey()));
                        tempField.setRequired(!annotation.IsNullable());

                        BasicDataServer.setCommonSelectItem(tempField, "lichKing.client.entity.APP_COMMON_TYPE", SetCommonFilter.commonSelectSql(annotation));

                    }else if (annotation.IsSelectItem() || annotation.IsFixSelectItem().length > 0) {
                        tempField = new DataSourceField(annotation.ResourceKey(),
                                MyFieldType.getFieldType(annotation.FieldType()));
                        tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey()));
                        tempField.setRequired(!annotation.IsNullable());
                        if (annotation.IsSelectItem()) {
                            BasicDataServer.setSelectItem4CommonDB(tempField, "lichKing.client." + annotation.ClassName(), SetCommonFilter.domainSelectSql(annotation));
                        } else if (annotation.IsFixSelectItem().length > 0) {
                            String[] valueMap = new String[annotation.IsFixSelectItem().length];
                            for (int v = 0; v < annotation.IsFixSelectItem().length; v++) {
                                valueMap[v] = MsgServer.getMsg(annotation.IsFixSelectItem()[v]);
                            }
                            tempField.setValueMap(valueMap);
                        }
                    }else if (annotation.IsMupSelectItem() || annotation.IsFixSelectItem().length > 0) {
                        tempField = new DataSourceField(annotation.ResourceKey(),
                                MyFieldType.getFieldType(annotation.FieldType()));
                        tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey()));
                        tempField.setRequired(!annotation.IsNullable());
                        if (annotation.IsMupSelectItem()) {
                            BasicDataServer.setSelectItem4CommonDB(tempField, "lichKing.client." + annotation.ClassName(), null);
                        } else if (annotation.IsFixSelectItem().length > 0) {
                            String[] valueMap = new String[annotation.IsFixSelectItem().length];
                            for (int v = 0; v < annotation.IsFixSelectItem().length; v++) {
                                valueMap[v] = MsgServer.getMsg(annotation.IsFixSelectItem()[v]);
                            }
                            tempField.setValueMap(valueMap);
                        }
//                    }else if (annotation.IsRequired() && !annotation.IsOpCell()) {
                    }else if (annotation.IsfixMapSelectItem()|| annotation.IsFixMupSelectItem()) {
                    	tempField = new DataSourceField(annotation.ResourceKey(),
                                 MyFieldType.getFieldType(annotation.FieldType()));
                        tempField.setAttribute("selectUI", "select");
                        tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey()));
                        ClassType classType = MyClassTypeUtil.getDomainClassType("lichKing.client.datasource.AppDataMap");
                        tempField.setValueMap((LinkedHashMap) classType.invoke(null, annotation.ResourceKey()));
                    }else if (annotation.IsRequired()) {
                        tempField = new DataSourceField(annotation.ResourceKey(),
                                MyFieldType.getFieldType(annotation.FieldType()));
                        tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey()));
                        tempField.setRequired(!annotation.IsNullable());
                        if(annotation.FieldType().equals("date")){
                            tempField.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
                            tempField.setAttribute("useTextField", true);
                        }else if(annotation.FieldType().equals("datetime")){
                            tempField.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATETIME);
                            tempField.setAttribute("useTextField", true);
                        }else if(annotation.FieldType().equals("time")){
                            tempField.setTimeFormatter(TimeDisplayFormat.TO24HOURTIME);
                            tempField.setAttribute("useTextField", true);
                        } 
//                        else {
//                            if (annotation.FieldType().equals("textarea")) {
//                                tempField.setEditorType(new TextAreaItem());//被转成TextArea，可写，和只读的设置冲突
//                            }
//                            tempField.setLength(annotation.Length());
//                        }
                        if (annotation.formUI().equals("Slider")) {
                          tempField.setAttribute("editorType", "SliderItem");
                          if(annotation.FieldType().equals("float")){
                              tempField.setAttribute("roundValues", false);
                          }
                          tempField.setAttribute("minValue", annotation.minValue());
                          tempField.setAttribute("maxValue", annotation.maxValue());
                        }else if(annotation.formUI().equals("PasswordItem")){
                            	tempField.setType(FieldType.PASSWORD);
                        }else if(annotation.formUI().equals("Picker")){
                        	tempField.setAttribute("editorType", "StaticTextItem");
                        }else if(annotation.formUI().equals("ComboBoxItem")){
                        	tempField.setAttribute("editorType", "ComboBoxItem");
                        }else if(annotation.formUI().equals("ColorPickerItem")){
                        	tempField.setAttribute("editorType", "ColorPickerItem");
                        }
//                        tempField.setHidden(!annotation.IsShowF());
                        tempField.setAttribute("visible", annotation.IsShowF());
                    	tempField.setLength(annotation.Length());
                        if(annotation.hint().length()>0){
                        	tempField.setAttribute("hint", "<nobr>"+annotation.hint()+"<nobr>");
                        }
                    }else if (annotation.IsPrimaryKey()) {
                        tempField = new DataSourceField(annotation.ResourceKey(),
                                MyFieldType.getFieldType(annotation.FieldType()),
                                MsgServer.getMsg(annotation.ResourceKey()));
                        tempField.setHidden(!annotation.IsShowF());
                    }
                    if(annotation.ResourceKey2()!=null&&annotation.ResourceKey2().length()>0){
                        tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey2()));
                    }
                    if (annotation.IsMupSelectItem()||annotation.IsFixMupSelectItem()) {
                        tempField.setMultiple(true);
                    }
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
        setClientOnly(true);
    }
    
    /**
     * 在表单中，隐藏自动填充的字段
     * @param annotation
     * @return
     */
    public static boolean hideFields(EntityAnn annotation){
    	if(annotation.ResourceKey().equals("CREATE_USER")){
    		return true;
    	}else if(annotation.ResourceKey().equals("CREATE_DATE")){
    		return true;
    	}else if(annotation.ResourceKey().equals("UPDATE_USER")){
    		return true;
    	}else if(annotation.ResourceKey().equals("UPDATE_DATE")){
    		return true;
    	}
    	return false;
    }

    
    public static int[] indexOrder;
    /**
     * 获取字段的数量
     * @param fields 实体类所有的字段（属性）
     * @param hasAnnotation 是否有注释特性
     * @return 字段的数量
     */
    public static int getFieldsLenEditOnForm(Field[] fields,boolean hasAnnotation){
        int fieldsLen=0;
        String orderStr=",";
        EntityAnn annotation;
        for (int i=0;i<fields.length;i++) {
            hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {
                annotation = fields[i].getAnnotation(EntityAnn.class);
//            	if(!hideFields(annotation)){
//                    if (annotation.IsRequired() && !annotation.IsOpCell() && annotation.FOrderNum() > 0 || annotation.IsPrimaryKey()) {
//                    if (annotation.IsRequired() && !annotation.IsOpCell() && annotation.FOrderNum() > 0) {
                        if (annotation.IsRequired() && annotation.FOrderNum() > 0) {
                        if (orderStr.indexOf("," + annotation.FOrderNum() + ",") == -1) {
                            orderStr += annotation.FOrderNum() + ",";
                            fieldsLen++;
                        }
                    }
//            	}
            }
        }
        if(fieldsLen>=1){
            indexOrder=new int[fieldsLen];
            String[] strOrder=orderStr.substring(1).split(",");
            for(int i=0;i<fieldsLen;i++){
                indexOrder[i]=Integer.parseInt(strOrder[i]);
            }
            ReflectionField.insertSort(indexOrder);
        }
        return fieldsLen;
    }
        
}
