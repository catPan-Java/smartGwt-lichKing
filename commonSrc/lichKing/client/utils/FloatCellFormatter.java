package lichKing.client.utils;

import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * 
 * @author catPan
 *
 */
public class FloatCellFormatter implements CellFormatter {

    @Override
    public String format(Object value, ListGridRecord record, int rowNum,int colNum) {
        if (value == null) {
            return null;
        }
//        return value.toString();
        NumberFormat nf = NumberFormat.getFormat("#,##0.00");
        try {
            return nf.format(((Number) value).floatValue());
        } catch (Exception e) {
            return value.toString();
        }
    }
}
