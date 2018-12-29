/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lichKing.client.utils;

import java.util.ArrayList;
import java.util.List;

import lichKing.client.entity.BaseDomain;
import lichKing.client.myException.DefaultCallback;
import lichKing.client.pojo.PageSql;
import lichKing.client.remote.CommonRemote;
import lichKing.client.server.CommonServer;
import lichKing.client.server.MsgServer;
import lichKing.client.ui.msg.MessageUI;
import lichKing.client.ui.myExtend.MyDynamicForm;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tab.TabSet;

/**
 *界面数据的验证
 * @author catPan
 */
public class MyValidate {
	
	public static boolean checkComboBox(DynamicForm f){
		boolean isPass=true;
		for(FormItem fi:f.getFields()){
			if("ComboBoxItem".equals(fi.getAttribute("editorType"))){
				
		    	ComboBoxItem cbi=(ComboBoxItem) f.getItem(fi.getName());
		    	if(cbi.getAttributeAsBoolean("checkPassServer")!=null){
					if(cbi.getAttributeAsBoolean("checkPassServer")){
						continue;
					}else{
						MessageUI.setMsgInfo("请为"+LayManager.changeFontBold(MsgServer.getMsg(cbi.getName()))+"选择一个值。");
						isPass=false;
					}
		    	}
			}
		}
		return isPass;
	}
	
	public static boolean checkSelectedLgr(ListGrid lg){
		if(lg.getSelectedRecords().length>0){
	    	if(lg.getSelectedRecords().length>1){
	    		MessageUI.setMsgInfo("您选择了<b>多条</b>数据，默认第一条数据进行编辑！");
	    	}
			return true;
		}else{
			MessageUI.setMsgInfo("请选择一条数据！");
		}
		return false;
	}
	
//	/**
//	 * 在保持数据前，验证唯一性需求的字段
//	 * @param addAgain 是否再次保存
//	 * @param type 操作类型
//	 * @param tabSet 页签控件
//	 * @param lg 主页面LG
//	 * @param _editForm 主数据的form
//	 * @param dm 实体类,包含唯一性字段
//	 */
//	public static void uniqueDataBeSave(final boolean addAgain,final String _type,final TabSet tabSet,final ListGrid _lg,final MyDynamicForm _editForm,final BaseDomain dm){
//    	ArrayList<PageSql> pageSqls=new ArrayList<PageSql>();
//        SetCommonFilter.addComValSql(pageSqls, dm.getClass());
//        
//    	for(String field:dm.getUniqueFields()){
//    		pageSqls.add(new PageSql.Builder(field, "=").content(_editForm.getValueAsString(field)).build());
//    	}
//		
//		CommonRemote.Util.getInstance().commonSearch(dm.getClass().getName(), pageSqls, new DefaultCallback<List>() {
//
//			@Override
//			public void onSuccess(List result) {
//
//				if(result.isEmpty()){
//
//                    if(addAgain){
//                        CommonServer.addToLgAgain(MyTabLG.getTabDatas(_type, tabSet,dm,_editForm),_lg,tabSet,_editForm);
//                    }else{
//                    	CommonServer.addToLG(MyTabLG.getTabDatas(_type, tabSet,dm,_editForm),_lg,tabSet);
//                    }
//				}else{
//					uniqueFail(_editForm,dm,tabSet);
//				}
//			}
//			
//		});
//		
//	}
	
	public static void uniqueFail(DynamicForm _editForm,BaseDomain dm,TabSet tabSet){
		String sayStr="";
    	for(String field:dm.getUniqueFields()){
    		sayStr+=MsgServer.getMsg(field)+"："+_editForm.getValueAsString(field)+"<br />";
    	}
		
		MessageUI.setMsgInfo(sayStr+"已经定义，请更换！");
		tabSet.selectTab(0);
		_editForm.focusInItem(dm.getUniqueFields()[0]);
	}

	public static void uniqueDataBeSave(final boolean addAgain,final String _type,final ListGrid _lg,final MyDynamicForm _editForm,final BaseDomain dm){
    	ArrayList<PageSql> pageSqls=new ArrayList<PageSql>();
        SetCommonFilter.addComValSql(pageSqls, dm.getClass());
        
    	for(String field:dm.getUniqueFields()){
    		pageSqls.add(new PageSql.Builder(field, "=").content(_editForm.getValueAsString(field)).build());
    	}
		
		CommonRemote.Util.getInstance().commonSearch(dm.getClass().getName(), pageSqls, new DefaultCallback<List>() {

			@Override
			public void onSuccess(List result) {

				if(result.isEmpty()){

                    if(addAgain){
                        CommonServer.addToLgAgain(dm, _lg,_editForm);
                    }else{
                        CommonServer.addToLG(dm, _lg);
                    }
				}else{
					uniqueFail(_editForm,dm);
				}
			}
			
		});
		
	}
	public static void uniqueFail(DynamicForm _editForm,BaseDomain dm){
		String sayStr="";
    	for(String field:dm.getUniqueFields()){
    		sayStr+=MsgServer.getMsg(field)+"："+_editForm.getValueAsString(field)+"<br />";
    	}
		
		MessageUI.setMsgInfo(sayStr+"已经定义，请更换！");
		_editForm.focusInItem(dm.getUniqueFields()[0]);
	}

	public static void uniqueDataBeSaveList(final boolean addAgain,final String _type,
			final ListGrid _lg,final MyDynamicForm _editForm,final BaseDomain dm,
			final List<BaseDomain> dms){
    	ArrayList<PageSql> pageSqls=new ArrayList<PageSql>();
        SetCommonFilter.addComValSql(pageSqls, dm.getClass());
        
    	for(String field:dm.getUniqueFields()){
    		pageSqls.add(new PageSql.Builder(field, "=").content(_editForm.getValueAsString(field)).build());
    	}
		
		CommonRemote.Util.getInstance().commonSearch(dm.getClass().getName(), pageSqls, new DefaultCallback<List>() {

			@Override
			public void onSuccess(List result) {

				if(result.isEmpty()){
					dms.add(dm);
                    if(addAgain){
                        CommonServer.addToLgAgain(dms,dm, _lg,_editForm);
                    }else{
                        CommonServer.addToLG(dms,dm, _lg);
                    }
				}else{
					uniqueFail(_editForm,dm);
				}
			}
			
		});
		
	}
    /**
     * 验证ListGrid每行单元格，是否符合指定的类型
     * @param lg ListGrid
     * @return boolean
     */
    public static boolean validateLG(ListGrid lg) {
        boolean validate = true;
        for (int i = 0; i < lg.getRecords().length; i++) {
        	ListGridRecord record=lg.getRecords()[i];
//        	if(!validateNull(record,lg)){
//                validate = false;
//                lg.deselectAllRecords();
//                lg.selectRecord(i);
//                break;
//        	}
            if (!lg.validateRow(i)) {
                validate = false;
                lg.deselectAllRecords();
                lg.selectRecord(i);
                MessageUI.setMsgInfo("请完善列表中的数据!");
                break;
            }
        }
        return validate;
    }

    public static boolean validateLG(ListGrid lg,String errMsg) {
        boolean validate = true;
        for (int i = 0; i < lg.getRecords().length; i++) {
//        	ListGridRecord record=lg.getRecords()[i];
//        	if(!validateNull(record,lg)){
//                validate = false;
//                lg.deselectAllRecords();
//                lg.selectRecord(i);
//                break;
//        	}
            if (!lg.validateRow(i)) {
                validate = false;
                lg.deselectAllRecords();
                lg.selectRecord(i);
				if(errMsg!=null&&errMsg.length()>0){
				    MessageUI.setMsgInfo(errMsg);
				}
                break;
            }
        }
        return validate;
    }

    public static boolean validateLG(ListGrid lg,String autoComField,String msg) {
        boolean validate = true;
		lg.endEditing();
        for (int i = 0; i < lg.getRecords().length; i++) {
            if (!lg.validateRow(i)) {
                validate = false;
        		lg.deselectAllRecords();
                lg.selectRecord(i);
                MessageUI.setMsgInfo("请完善列表中的数据!");
                break;
            }

    		lg.deselectAllRecords();
        	ListGridRecord lgr=lg.getRecords()[i];
        	boolean checkPassServer=true;
    		for(String s:lgr.getAttributes()){
				if(s.equals("checkPassServer"+autoComField)){//没有通过后台验证的数据
					if(!lgr.getAttributeAsBoolean(s)){
	            		checkPassServer=false;
					}
				}
				
				if(!checkPassServer){
	                validate = false;
        			MessageUI.setMsgInfo("自动提示框<b>"+msg+
        					"</b>的值，没有通过后台验证，请从新<b>输入或选择</b>");
            		lg.selectRecord(lgr);
//            		lg.startEditing(lg.getRecordIndex(lgr));
					break;
				}
    		}
        }
        return validate;
    }

    public static boolean validateLGandRm(ListGrid lg,String autoComField,String msg) {
        boolean validate = true;
		lg.endEditing();
		List<ListGridRecord> rmLgrs=new ArrayList();
        for (int i = 0; i < lg.getRecords().length; i++) {
            if (!lg.validateRow(i)) {
            	rmLgrs.add(lg.getRecord(i));
            }
        }
        for(ListGridRecord r:rmLgrs){
        	lg.removeData(r);
        }
        for (int i = 0; i < lg.getRecords().length; i++) {
        	ListGridRecord lgr=lg.getRecords()[i];
        	boolean checkPassServer=true;
    		for(String s:lgr.getAttributes()){
				if(s.equals("checkPassServer"+autoComField)){//没有通过后台验证的数据
					if(!lgr.getAttributeAsBoolean(s)){
	            		checkPassServer=false;
					}
				}
				
				if(!checkPassServer){
	                validate = false;
        			MessageUI.setMsgInfo("自动提示框<b>"+msg+
        					"</b>的值，没有通过后台验证，请从新<b>输入或选择</b>");
            		lg.selectRecord(lgr);
					break;
				}
    		}
        }
        return validate;
    }
    public static boolean validateForm(DynamicForm form,String autoComField,String msg) {
        boolean validate = true;
//        if (form.validate()) {
//            validate = false;
//            MessageUI.setMsgInfo("请完善表单中的数据!");
//            break;
//        }

    	boolean checkPassServer=true;
    	ComboBoxItem item=(ComboBoxItem) form.getItem(autoComField);
    	if(!item.getAttributeAsBoolean("checkPassServer")){
    		checkPassServer=false;//没有通过后台验证的数据
		}
			
		if(!checkPassServer){
            validate = false;
			MessageUI.setMsgInfo("自动提示框<b>"+msg+
					"</b>的值，没有通过后台验证，请从新<b>输入或选择</b>");
			item.focusInItem();
		}
        return validate;
    }

    public static boolean validateLG(ListGridRecord lgr,ListGrid lg) {
        return lg.validateRow(lg.getRecordIndex(lgr));
    }
    
    /**
     * 验证ListGrid选中行单元格，是否符合指定的类型
     * @param lg ListGrid
     * @return boolean
     */
    public static boolean validateLGselected(ListGrid lg) {
        boolean validate = true;
        for (ListGridRecord record:lg.getSelectedRecords()) {
//        	if(!validateNull(record,lg)){
//                validate = false;
//                break;
//        	}
            if (!lg.validateRow(lg.getRecordIndex(record))) {
                validate = false;
                MessageUI.setMsgInfo("请完善列表中选中的数据!");
                break;
            }
        }
        return validate;
    }
//    /**
//     * 验证ListGrid选中行单元格，是否符合指定的类型，errMsg为提示的信息，如果errMsg为空或长度为0，则不提示
//     * @param lg
//     * @param errMsg
//     * @return
//     */
//    public static boolean validateLGselected(ListGrid lg,String errMsg) {
//        boolean validate = true;
//        for (ListGridRecord record:lg.getSelectedRecords()) {
////        	if(!validateNull(record,lg)){
////                validate = false;
////                break;
////        	}
//            if (!lg.validateRow(lg.getRecordIndex(record))) {
//                validate = false;
//                if(errMsg!=null&&errMsg.length()>0){
//                	MessageUI.setMsgInfo(errMsg);
//                }
//                break;
//            }
//        }
//        return validate;
//    }

//	private static boolean validateNull(ListGridRecord lgr,ListGrid _lg)
//	{
//		String fieldName="";
//		for(ListGridField lgf:_lg.getFields()){
//			fieldName=lgf.getName();
//			Integer recordIdx = _lg.getRecordIndex(lgr);
//			if (lgf.getAttributeAsBoolean("required")&&_lg.getEditValue(recordIdx, fieldName)==null)
//			{
//				_lg.setEditValue(recordIdx, fieldName, "");
//				_lg.validateCell(recordIdx, fieldName);
//                MessageUI.setMsgInfo("请完善数据!");
//				return false;
//			}
//		}
//
//		return true;
//	}
    
}
