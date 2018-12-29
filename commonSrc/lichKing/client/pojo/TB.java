package lichKing.client.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author catPan
 */
public class TB implements Serializable{
    private static final long serialVersionUID = 921359250457398127L;

    private String tableName;
    private String field;
    private String value;
    private List<String> values;
    private String field2;
    private String value2;

    public TB() {
    }    

    public TB(String tableName, String field, String value, String field2, String value2) {
        this.tableName = tableName;
        this.field = field;
        this.value = value;
        List<String> deleteIds = new ArrayList<String>();
        deleteIds.add(value);
        this.values = deleteIds;
        this.field2 = field2;
        this.value2 = value2;
    }
    /**
     * 构造删除sql，delete语句
     * @param tableName 表名
     * @param field 字段名
     * @param value 对应字段的值
     */
    public TB(String tableName, String field, String value) {
        this.tableName = tableName;
        this.field = field;
        this.value = value;
        List<String> deleteIds = new ArrayList<String>();
        deleteIds.add(value);
        this.values = deleteIds;
    }

    /**
     * 构造删除sql，delete语句
     * @param tableName 表名
     * @param field 字段名
     * @param values 对应字段值的集合，List<String>形式多个值
     */
    public TB(String tableName, String field, List<String> values) {
        this.tableName = tableName;
        this.field = field;
        this.values = values;
    }
    

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
    

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }
    
}
