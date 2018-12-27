/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lichKing.client.datasource;

import java.util.ArrayList;
import java.util.List;

import lichKing.client.entityAnnotation.EntityAnn;
import lichKing.client.entityAnnotation.MyClassTypeUtil;
import lichKing.client.server.MsgServer;

import com.gwtent.reflection.client.Field;
import com.smartgwt.client.data.DataSourceField;

/**
 * 生成树的DataSourceField[]
 * @author catPan
 */
public class DSTreeDSF {

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static DataSourceField[] DSTreeJoinSelect(String ForeignKey, Class domainClass) {

        List<DataSourceField> DSFields = new ArrayList<DataSourceField>();

        Field[] fields = MyClassTypeUtil.getDomainClassFields(domainClass);
        boolean hasAnnotation = false;

        DataSourceField tempField = new DataSourceField();
        EntityAnn annotation;
        List fieldsList=new ArrayList();
        for (Field field : fields) {
            hasAnnotation = field.isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {
                annotation = field.getAnnotation(EntityAnn.class);
//                if(!annotation.IsOpCell()&&annotation.DomainMap().equals("noMap")){
                    if(annotation.DomainMap().equals("noMap")){
                    fieldsList.add(annotation.ResourceKey());
                    tempField = new DataSourceField(annotation.ResourceKey(),
                            MyFieldType.getFieldType(annotation.FieldType()));
                    tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey()));
                    tempField.setLength(annotation.Length());
                    tempField.setRequired(!annotation.IsNullable());
    //                if (annotation.IsSelectItem()) {
    //                    tempField.setTitle(MsgServer.getMsg(annotation.ResourceKey2()));
    //                    BasicDataServer.setSelectItem4CommonDB(tempField, "ans.client.domain." + annotation.ClassName(), null);
    //                } else if (annotation.IsFixSelectItem().length > 0) {
    //                    String[] valueMap = new String[annotation.IsFixSelectItem().length];
    //                    for (int v = 0; v < annotation.IsFixSelectItem().length; v++) {
    //                        valueMap[v] = MsgServer.getMsg(annotation.IsFixSelectItem()[v]);
    //                    }
    //                    tempField.setValueMap(valueMap);
    //                } else
                        if (annotation.IsTreeId()) {
                        tempField.setPrimaryKey(true);
                        tempField.setHidden(true);
                    } else if (annotation.IsTreePId()) {
                        tempField.setForeignKey(ForeignKey);
                        tempField.setRootValue("0");
                    }
                    DSFields.add(tempField);
                }
            }
        }
        
        return DSFields.toArray(new DataSourceField[DSFields.size()]);
    }
}
