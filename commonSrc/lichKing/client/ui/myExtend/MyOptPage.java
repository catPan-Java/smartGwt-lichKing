package lichKing.client.ui.myExtend;

import lichKing.client.datasource.DSEditFormJoinSelect;
import lichKing.client.datasource.DSFormFields;
import lichKing.client.datasource.OpTypeEnum;
import lichKing.client.ui.bootstrap.BsButton;
import lichKing.client.ui.msg.MessageUI;

import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 *
 * @author catPan
 */
public abstract class MyOptPage {
    
    protected ListGrid _lg;
    protected String _type;
    protected ListGridRecord _editRecord;
    @SuppressWarnings("rawtypes")
	protected Class _cls;
    protected MyDynamicForm _editForm;
    protected boolean _isWin;

	public MyOptPage(ListGrid lg, String type, ListGridRecord record,Class cls,boolean isWin) {
        this._lg = lg;
        this._type = type;
        this._editRecord=record;
        this._cls=cls;
        this._isWin=isWin;
        initPath(type, record);
    }
    public MyOptPage(ListGrid lg, String type, ListGridRecord record) {
        this._lg = lg;
        this._type = type;
        this._editRecord=record;
        initPath(type, record);
    }

    @SuppressWarnings("rawtypes")
	public MyOptPage(ListGrid lg, String type, ListGridRecord record,Class cls) {
        this._lg = lg;
        this._type = type;
        this._editRecord=record;
        this._cls=cls;
        initPath(type, record);
    }
    
    public void initPath(String opType,ListGridRecord record){
        if(opType.equals(OpTypeEnum.Look.getValue())||opType.equals(OpTypeEnum.Modify.getValue())||opType.equals("Copy")){
        	if(record!=null){
        		getLay();
        	}else{
        		MessageUI.setMsgInfo("请选择一条数据！");
        	}
        }else{
        	getLay();
        }
    }
    
    public abstract void getLay();
    
    public IButton[] autoCheckBtns(IButton[] btns){
        if (_type.equals("Look")) {
        	btns=null;
        }
        if(_editForm!=null){
        	setMEMOWitdh();
        	autoCheckForm();
        }
        return btns;
    }

    public BsButton[] autoCheckBtns(BsButton[] btns){
        if (_type.equals("Look")) {
        	btns=null;
        }
        if(_editForm!=null){
        	setMEMOWitdh();
        	autoCheckForm();
        }
        return btns;
    }
    
    public void autoCheckForm(){
        if (!_type.equals("Add")) {
            _editForm.reset();
            if(_editRecord!=null){
            	_editForm.editRecord(_editRecord);
            }
        }
    }
    
    public void setMEMOWitdh(){
        if(_editForm.getItem("MEMO")!=null){
        	_editForm.getItem("MEMO").setWidth("100%");
        	_editForm.getItem("MEMO").setColSpan(_editForm.getNumCols()-1);
        	_editForm.getItem("MEMO").setHeight(52);
        }
    }
    

    public MyDynamicForm addEditForm(DSEditFormJoinSelect ds) {
        _editForm = new MyDynamicForm();
        _editForm.setWidth(520);
        _editForm.setNumCols(4);
        //绑定表单元素
        _editForm.setDataSource(ds);
        _editForm.setAutoFetchData(false);
        _editForm.addItemChangedHandler(new ItemChangedHandler() {
			
			@Override
			public void onItemChanged(ItemChangedEvent event) {
				_editForm.setEdited(true);
			}
		});
        return _editForm;
    }

    public MyDynamicForm addEditForm() {
        _editForm = new MyDynamicForm();
        _editForm.setWidth(520);
        _editForm.setNumCols(4);
        //绑定表单元素
        _editForm.setDataSource(DSFormFields.getInstance(_cls));
        _editForm.setAutoFetchData(false);
        _editForm.addItemChangedHandler(new ItemChangedHandler() {
			
			@Override
			public void onItemChanged(ItemChangedEvent event) {
				_editForm.setEdited(true);
			}
		});
        return _editForm;
    }
    
    public IButton addAgainSaveBtn(final boolean addAgain) {
        IButton saveBtn = MyButton.getSaveBtn(addAgain);
        return saveBtn;
    }
    
    public void initPage(){
        _editForm.reset();
        _editForm.focusInItem(0);
    }
}
