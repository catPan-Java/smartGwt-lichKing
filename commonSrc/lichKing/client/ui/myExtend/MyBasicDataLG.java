/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lichKing.client.ui.myExtend;

import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 *基础数据中的列表
 * @author catPan
 */
public class MyBasicDataLG extends MyListGrid{

    public MyBasicDataLG() {  
    }
    
    /**
     * 获取单元格的样式
     * @param record 数据行
     * @param rowNum 第几行
     * @param colNum 第几列
     * @return 单元格的样式
     */
    @Override
    protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
        if (getFieldName(colNum).equals("COLOR")) {
            if (record.getAttribute("COLOR")!=null&&record.getAttribute("COLOR").length()>0) {
                return "background-color:"+record.getAttribute("COLOR")+";";
            } else {
                return super.getCellCSSText(record, rowNum, colNum);
            }
        } else {
            return super.getCellCSSText(record, rowNum, colNum);
        }
        
    }
            
            
//    @Override
//    protected String getBaseStyle(ListGridRecord record, int rowNum, int colNum) {
//        if (getFieldName(colNum).equals("COLOR")) {
//            if (record.getAttribute("COLOR")!=null&&record.getAttribute("COLOR").length()>0) {
//                return "myHighGridCell";
//            } else {
//                return super.getBaseStyle(record, rowNum, colNum);
//            }
//        } else {
//            return super.getBaseStyle(record, rowNum, colNum);
//        }
//    }
}
