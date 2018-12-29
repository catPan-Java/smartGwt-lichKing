package lichKing.client.ui.myExtend;

import lichKing.client.datasource.DSFormFields;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.NamedFrame;
import com.smartgwt.client.types.Encoding;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

/**
 *基础数据中的编辑表单
 * @author catPan
 */
public class MyBasicDataForm {

    public static final String TARGET = "uploadTarget";
    public static DynamicForm editForm = new DynamicForm();
    public static NamedFrame frame = new NamedFrame(TARGET);

    /**
     * 获取基础数据的表单面板
     * @param editrecord 被编辑的列表的数据行
     * @param className 类的类型
     * @return 
     */
    @SuppressWarnings("rawtypes")
	public static HLayout getBasicDataLay(ListGridRecord editrecord, Class className) {
        HLayout hlay = new HLayout();
        hlay.setHeight(340);

        editForm.setEncoding(Encoding.MULTIPART);
        editForm.setTarget(TARGET);
        editForm.setColWidths("100", "*");
        editForm.setWidth(320);
        editForm.setCellPadding(6);

        //绑定编辑的表单
        editForm.setFields(DSFormFields.getFormItems(className));
        editForm.reset();
        editForm.editRecord(editrecord);

        frame.setWidth("1");
        frame.setHeight("1");
        frame.setVisible(false);

        hlay.addMember(editForm);
        hlay.addMember(frame);

        return hlay;
    }

    /**
     * 提交基础数据，延迟销毁弹出框
     * @param _win 编辑基础数据的弹出框
     * @param fileName 上传文件名
     */
    public static void submitBasicData(final Window _win, String fileName) {
        if (fileName != null && fileName.length() > 0) {
            editForm.setAction(GWT.getModuleBaseURL() + "upload?fileName=" + URL.encode(fileName));
            editForm.submitForm();
        }
        _win.hide();
        Timer timer = new Timer() {

            @Override
            public void run() {
                _win.destroy();
            }
        };
        timer.schedule(2000);
    }

    public static void submitFormFile(final Window _win, String fileName, final DynamicForm form) {
        if (fileName != null && fileName.length() > 0) {
            form.setAction(GWT.getModuleBaseURL() + "upload?fileName=" + URL.encode(fileName));
            form.submitForm();

        }
    }

    public static void delFormFile(final Window _win, String fileName, final DynamicForm form) {
        if (fileName != null && fileName.length() > 0) {
            form.setAction(GWT.getModuleBaseURL() + "delete?fileName=" + URL.encode(fileName));
            form.submitForm();

        }
    }

    public static void Exmport(final DynamicForm df, String SELL_QUOTE_ID, String CUSTOMER_ID, String BJDname,String type, final Window _win) {

        if (SELL_QUOTE_ID != null && SELL_QUOTE_ID.length() > 0) {
            df.setAction(GWT.getModuleBaseURL() + "export?SELL_QUOTE_ID=" + URL.encode(SELL_QUOTE_ID) + "&CUSTOMER_ID=" + URL.encode(CUSTOMER_ID) + "&BJDname=" + URL.encode(BJDname)+"&type="+URL.encode(type));
            df.submitForm();
        }
        Timer timer = new Timer() {

            @Override
            public void run() {
                _win.destroy();
            }
        };
        timer.schedule(2000);
    }
    

    public static void Import(final DynamicForm df, String tyepName, final Window _win,String page) {

        if (tyepName != null && tyepName.length() > 0) {
            df.setAction(GWT.getModuleBaseURL() + "import?tyepName=" + URL.encode(tyepName)+"&page="+page);
            df.submitForm();
        }
        Timer timer = new Timer() {

            @Override
            public void run() {
                _win.destroy();
            }
        };
        timer.schedule(2000);
    }

    public static void CGxmport(final DynamicForm df, String SUPPLIER_ID, String PURCHASE_ID, String USER_ID, String CGname) {

//        if (tyepName != null && tyepName.length() > 0) {
        df.setAction(GWT.getModuleBaseURL() + "CGexport?SUPPLIER_ID=" + URL.encode(SUPPLIER_ID) + "&PURCHASE_ID=" + URL.encode(PURCHASE_ID) + "&USER_ID=" + URL.encode(USER_ID) + "&CGname=" + URL.encode(CGname));
        df.submitForm();
//        }
    }

    public static void HTxmport(final DynamicForm df, String ORDER_CODE, String CONTRACT_CODE, String SELL_CONTRACT_ID, String HTname,String type) {

//        if (tyepName != null && tyepName.length() > 0) {
        df.setAction(GWT.getModuleBaseURL() + "HTexport?ORDER_CODE=" + URL.encode(ORDER_CODE) + "&CONTRACT_CODE=" + URL.encode(CONTRACT_CODE) + "&SELL_CONTRACT_ID" + URL.encode(SELL_CONTRACT_ID) + "&HTname=" + URL.encode(HTname)+"&type="+URL.encode(type));
        df.submitForm();
//        }
    }
}
