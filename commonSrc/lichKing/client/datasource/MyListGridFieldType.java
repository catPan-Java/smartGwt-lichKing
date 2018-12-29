/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lichKing.client.datasource;

import com.smartgwt.client.types.ListGridFieldType;
import java.util.LinkedHashMap;

/**
 *ListGrid的字段类型
 * @author catPan
 */
public class MyListGridFieldType {

    //字段类型的key-value
    private static final LinkedHashMap<String, ListGridFieldType> fieldTypeMap = new LinkedHashMap<String, ListGridFieldType>();

    static {
        fieldTypeMap.put("text", ListGridFieldType.TEXT);
        fieldTypeMap.put("textarea", ListGridFieldType.TEXT);
        fieldTypeMap.put("boolean", ListGridFieldType.BOOLEAN);
        fieldTypeMap.put("integer", ListGridFieldType.INTEGER);
        fieldTypeMap.put("float", ListGridFieldType.FLOAT);
        fieldTypeMap.put("date", ListGridFieldType.DATE);
        fieldTypeMap.put("time", ListGridFieldType.TIME);
        fieldTypeMap.put("datetime", ListGridFieldType.DATETIME);
        fieldTypeMap.put("image", ListGridFieldType.IMAGE);
        fieldTypeMap.put("file", ListGridFieldType.TEXT);
        fieldTypeMap.put("decimal", ListGridFieldType.FLOAT);
    }

    /**
     * 获取ListGrid的字段类型
     * @param type 字段类型的key
     * @return 
     */
    public static ListGridFieldType getFieldType(String type) {
        return fieldTypeMap.get(type);
    }
}
