package lichKing.client.ui.myExtend;

import java.util.ArrayList;

import lichKing.client.datasource.DSFormFields;
import lichKing.client.datasource.DSListGridFields;
import lichKing.client.pojo.PageSql;
import lichKing.client.server.BasicDataServer;
import lichKing.client.server.MsgServer;

import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 *
 * @author catPan
 */
public class SearchLgWin {

    private static String _pageName;
    @SuppressWarnings("rawtypes")
	private static Class _domainClass;
    private static String[] _fieldNames;
    private static boolean _rodio;
    private static ListGridRecord[] selectLgrs;
    private static ArrayList<PageSql> _pageSqls=new ArrayList<PageSql>();
    private static ListGrid _lg;

    /**
     * 获取选择的数据
     * @return
     */
    public static ListGridRecord[] getSelectLgrs() {
        return selectLgrs;
    }

    /**
     * 在数据量大的情况下，用弹窗的方式带出数据，让用户选择，暂时没有分页（默认15条）
     * @param domainClass 实体类的类型
     * @param pageName 生成实体类表单的ID，包括弹窗的ID
     * @param fieldNames 作为查询字段的字段数组
     * @param selectType 查询类型：MULTIPLE：多选，其他情况单选
     * @param pageSqls 附加查询条件
     * @param title 弹窗的标题
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static ListGrid show(Class domainClass, String pageName, String[] fieldNames, String selectType, ArrayList<PageSql> pageSqls,String title) {
    	selectLgrs=null;
        _fieldNames = fieldNames;
        _pageName = pageName;
        _domainClass = domainClass;
        if(selectType.equals("MULTIPLE")){ //多选
        	 _rodio = false;
        }else{// 单选
        	 _rodio = true;
        }
        _pageSqls = pageSqls;

        _lg = new MyListGrid();
        IButton okBtn = new IButton("确定");
        okBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				_lg.fireEvent(new RecordDoubleClickEvent(_lg.getConfig()));
			}
		});
        OpenWindows.getWinWithFooterControl(title, getLayout(_lg), new IButton[]{okBtn},pageName);
        return _lg; 
    }

    protected static VLayout getLayout(final ListGrid lg) {

        VLayout vlayout = new VLayout(10);
        vlayout.setHeight(400);
        vlayout.setWidth(720);
        vlayout.setPadding(5);

        HLayout hLayout = new HLayout();
        if(_fieldNames!=null&&_fieldNames.length>0){
	        final DynamicForm form = new DynamicForm();
	    	form.setDataSource(DSFormFields.getYourFiedsFormDS(_pageName, _fieldNames, _domainClass));
	        form.setNumCols(4);
	        form.setWidth(580);
	        IButton searchBtn = new IButton();
	        searchBtn.setTitle(MsgServer.getMsg("SEARCH"));
	
	        hLayout.addMember(form);
	        hLayout.addMember(searchBtn);

	        searchBtn.addClickHandler(new ClickHandler() {
	
	            @Override
				public void onClick(ClickEvent event) {
	                final ArrayList<PageSql> pageSql = new ArrayList<PageSql>();
	                String acSearch="";
	                for (int i = 0; i < _fieldNames.length; i++) {
	                    if (form.getField(_fieldNames[i]).getValue() != null && form.getField(_fieldNames[i]).getValue().toString().length() > 0) {
	                        pageSql.add(new PageSql.Builder(_fieldNames[i], "iContains").content(form.getField(_fieldNames[i]).getValue().toString()).build());
	                    }
	                }
	                if(_pageSqls!=null&&!_pageSqls.isEmpty()){
	                	pageSql.addAll(_pageSqls);
	                }
	                BasicDataServer.setLG4CommonDB(lg, _domainClass.getName(), pageSql,"");
	            }
	        });

	        vlayout.addMember(hLayout);
        }

        lg.setMinHeight(300);
        lg.setHeight100();
        lg.setShowRecordComponents(true);
        lg.setShowRecordComponentsByCell(true);
        lg.setCanAddFormulaFields(true);
        lg.setSelectionAppearance(SelectionAppearance.CHECKBOX);
        if (_rodio) {
            lg.setSelectionType(SelectionStyle.SINGLE);
        }else{
            lg.setSelectionType(SelectionStyle.MULTIPLE);
        }
        lg.setCanAddSummaryFields(true);
        lg.setFields(DSListGridFields.getListGridFieldsByOrder(_domainClass));
        if(lg.getField("CZ")!=null){
            lg.getField("CZ").setHidden(true);
        }
        ArrayList<PageSql> pageSqls = new ArrayList<PageSql>();
        if(_pageSqls!=null&&!_pageSqls.isEmpty()){
            pageSqls.addAll(_pageSqls);
        }
        String acSearch="";
        for(PageSql ps:_pageSqls){
        	if("ACTUAL_OVERBIT".equals(ps.getField())){
        		acSearch=ps.getContent();
        	}
        }
        BasicDataServer.setLG4CommonDB(lg, _domainClass.getName(), pageSqls,acSearch);
        
        vlayout.addMember(lg);
        
        lg.addRecordClickHandler(new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
                selectLgrs=new ListGridRecord[]{(ListGridRecord) event.getRecord()};
			}
		});
        
        return vlayout;
    }
}
