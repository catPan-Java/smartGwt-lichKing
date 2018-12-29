package lichKing.client.ui.myExtend;

import lichKing.client.datasource.DSEditFormJoinSelect;
import lichKing.client.datasource.DSListGridFields;

import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.widgets.grid.ListGrid;


/**
 *表格编辑的列表
 * @author catPan
 */
public class MyEditOnListGrid extends MyListGrid{

    @SuppressWarnings("rawtypes")
	public MyEditOnListGrid(ListGrid mainLG,Class domianClass,String dsID) {          
        setCanEdit(true);
        setEditEvent(ListGridEditEvent.DOUBLECLICK);
        setHeight100();
        setAutoFetchData(Boolean.FALSE);
        setAutoSaveEdits(Boolean.FALSE);
        if(dsID==null||dsID.length()==0){
            setDataSource(DSListGridFields.getListGridDS(domianClass));
        }else{
            setDataSource(DSEditFormJoinSelect.getEditFormDS(dsID,domianClass));
        }
        setFields(mainLG.getFields());
        hideField("CZ");
        setData(mainLG.getSelectedRecords());
    }
    
}
