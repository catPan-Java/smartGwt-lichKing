/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lichKing.client.datasource;

import java.util.LinkedHashMap;

import com.smartgwt.client.types.FieldType;

/**
 *smartgwt的字段类型，ListGrid、TreeGrid、DynamicForm等
 * @author catPan
 */
public class MyFieldType {

    //字段类型的key-value
    private static final LinkedHashMap<String, FieldType> fieldTypeMap = new LinkedHashMap<String, FieldType>();

    static {
        fieldTypeMap.put("text", FieldType.TEXT);
        fieldTypeMap.put("textarea", FieldType.TEXT);
        fieldTypeMap.put("boolean", FieldType.BOOLEAN);
        fieldTypeMap.put("integer", FieldType.INTEGER);
        fieldTypeMap.put("float", FieldType.FLOAT);
        fieldTypeMap.put("date", FieldType.DATE);
        fieldTypeMap.put("time", FieldType.TIME);
        fieldTypeMap.put("datetime", FieldType.DATETIME);
        fieldTypeMap.put("decimal", FieldType.FLOAT);
    }

    /**
     * 获取smartgwt的字段类型
     * @param type 字段类型的key
     * @return 
     */
    public static FieldType getFieldType(String type) {
        return fieldTypeMap.get(type);
    }
}
