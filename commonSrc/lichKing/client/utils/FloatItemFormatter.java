package lichKing.client.utils;

import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemValueFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;

/**
 * 
 * @author catPan
 *
 */
public class FloatItemFormatter implements FormItemValueFormatter {

    @Override
	public String formatValue(Object value, Record record, DynamicForm form,FormItem item) {
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