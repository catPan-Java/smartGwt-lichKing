package lichKing.client.ui.myExtend;

import java.util.Date;

import lichKing.client.datasource.AppDataMap;
import lichKing.client.datasource.DSListGridFields;
import lichKing.client.pojo.Page;
import lichKing.client.server.CommonServer;

import com.smartgwt.client.types.AutoFitEvent;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.StringUtil;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RowEditorEnterEvent;
import com.smartgwt.client.widgets.grid.events.RowEditorEnterHandler;

/**
 *大多数页面通用的列表
 * @author catPan
 */
public class MyListGrid extends ListGrid{
    
    @SuppressWarnings("rawtypes")
	public static ListGrid getMainPageLG(Class domainClass,Page page){
        ListGrid _MyListGrid = new ListGrid();
        _MyListGrid.setWidth100();
        _MyListGrid.setHeight(220);
        _MyListGrid.setHoverWidth(300);  
        _MyListGrid.setEmptyMessage(AppDataMap.getLoadingEmptyMessage());
        _MyListGrid.setFields(DSListGridFields.getListGridFieldsByOrder(domainClass));
        CommonServer.mainLgSeach(page, domainClass.getName(), _MyListGrid);
        return _MyListGrid;
    }
    
    public static MyListGrid getPrintLg(){
    	MyListGrid lg=new MyListGrid();
		lg.setEditOnFocus(false);
		lg.setSelectOnEdit(false);
    	lg.setShowHeaderMenuButton(false);
//    	lg.setCanResizeFields(false);
//    	lg.setCanSort(false);
    	lg.setCanReorderFields(false);
//		lg.setAutoFitData(Autofit.VERTICAL);
		lg.setBaseStyle("myBoxedGridCell");
//		lg.setPrintAutoFit(false);
		lg.setEditEvent(ListGridEditEvent.CLICK);
		lg.setHeaderAutoFitEvent(AutoFitEvent.NONE);
		lg.setDataPageSize(14);
		lg.setShowRowNumbers(true);
		return lg;
    }
    public static MyListGrid getModifyLg(){
    	MyListGrid lg=new MyListGrid();
		lg.setEditOnFocus(false);
		lg.setSelectOnEdit(false);
    	lg.setShowHeaderMenuButton(false);
    	lg.setCanReorderFields(false);
		lg.setBaseStyle("myBoxedGridCell");
		lg.setEditEvent(ListGridEditEvent.CLICK);
		lg.setHeaderAutoFitEvent(AutoFitEvent.NONE);
		lg.setDataPageSize(14);
		lg.setShowRowNumbers(true);
		
		lg.addRowEditorEnterHandler(new RowEditorEnterHandler() {
			
			@Override
			public void onRowEditorEnter(RowEditorEnterEvent event) {
				event.getRecord().setAttribute("edited", true);
			}
		});
		return lg;
    }

    private String functionName="null";
    /**
     * 功能名称，保存Resource Key,如为NULL表示模块
     * @return
     */
    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
    
    private String domainFK;
    /**
     * 实体类的外键
     * @return
     */
    public String getDomainFK() {
		return domainFK;
	}

	public void setDomainFK(String domainFK) {
		this.domainFK = domainFK;
	}

	public MyListGrid() {
        setShowHeaderContextMenu(false);
        //加载数据时显示加载的动态效果
//        setEmptyMessage(AppDataMap.getLoadingEmptyMessage());
        setHeight(365);
        
//        myCellFormat(true);
        setShowHover(false);
        
//        //是否要显示附加的组件
//        setShowRecordComponents(true);
//        setShowRecordComponentsByCell(true);
        setWidth100();
        setHeight100();
        //选中的类型为复选
        setSelectionAppearance(SelectionAppearance.CHECKBOX);
    }

	public final void myCellFormat(final boolean cellabbreviate){
        setCellFormatter(new CellFormatter() {
            @Override
            public String format(final Object value, final ListGridRecord record, final int rowNum, final int colNum) {
                if(value!=null&&value.toString().length()>0){
                    if(value instanceof Date){
                        if(getField(colNum).getType().equals(ListGridFieldType.DATE)){
                            return DateUtil.formatAsShortDate((Date)value);
                        }else{
                            return DateUtil.formatAsShortDatetime((Date)value);
                        }
                    }else if(cellabbreviate&&value.toString().length()>20){
                        if(getField(colNum).getWidth().equals("100%")){
                            return value.toString();
                        }else{
                            int width=Integer.parseInt(getField(colNum).getWidth())/100*20;
                            if(width>0){
                                return StringUtil.abbreviate(value.toString(), width);
                            }
                            return value.toString();
                        }
                    }else{
                        return value.toString();
                    }
                }else{
                    return "";
                }
            }
        });
        
    }
    
    public MyListGrid(boolean cellabbreviate) {
        setShowHeaderContextMenu(false);
        setEmptyMessage(AppDataMap.getLoadingEmptyMessage());
        
//        myCellFormat(cellabbreviate);
        setShowHover(false);
    }
        
    
}
