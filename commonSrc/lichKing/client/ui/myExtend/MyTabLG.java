package lichKing.client.ui.myExtend;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lichKing.client.datasource.DSEditFormJoinSelect;
import lichKing.client.datasource.DSListGridFields;
import lichKing.client.datasource.OpTypeEnum;
import lichKing.client.entity.BaseDomain;
import lichKing.client.entityAnnotation.MyClassTypeUtil;
import lichKing.client.myException.DefaultCallback;
import lichKing.client.pojo.PageSql;
import lichKing.client.pojo.TabLG;
import lichKing.client.pojo.TabsInfo;
import lichKing.client.pojo.TabsInfoData;
import lichKing.client.remote.CommonRemote;
import lichKing.client.server.CommonServer;
import lichKing.client.server.MsgServer;
import lichKing.client.utils.LayManager;
import lichKing.client.utils.MyValidate;
import lichKing.client.utils.PojoCastRecord;
import lichKing.client.utils.RecordCastPojo;
import lichKing.client.utils.ResultBuild;
import lichKing.client.utils.SetCommonFilter;

import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.Constructor;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RowEditorExitEvent;
import com.smartgwt.client.widgets.grid.events.RowEditorExitHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

/**
 * 
 * @author catPan
 *
 */
public class MyTabLG {
	
	public static void initPage(TabSet tabSet){
	      for(Tab tab:tabSet.getTabs()){
	    	  if(tab.getPane()!=null){
				  MyDynamicForm form=getTabForm(((MyTab)tab).getFormClassN());
				  form.focusInItem(0);
				  if(form!=null){
					  form.clearValues();
				  }
				  MyListGrid lg=getTabLg(((MyTab)tab).getLgClassN());
				  if(lg!=null){
			        clearLgData(lg);
				  }
	    	  }
	      }
	      tabSet.selectTab(0);
	      for(Tab tab:tabSet.getTabs()){
			  MyDynamicForm form=getTabForm(((MyTab)tab).getFormClassN());
			  form.focusInItem(0);
			  break;
	      }
	}
	/**
	 * 在列表上面添加 新增按钮的UI
	 * @param lg
	 * @return
	 */
	public static VLayout addHeadBtn(final ListGrid lg,String type){
		return addHeadBtnOrderLgr(lg, "DR",type);
	}

	/**
	 * 在列表上面添加 新增按钮的UI，并且根据给定的排序字段自动累加
	 * @param lg
	 * @param orderField
	 * @return
	 */
	public static VLayout addHeadBtnOrderLgr(final ListGrid lg,final String orderField,final String type){
		VLayout vlay = new VLayout();
		vlay.setHeight100();

        ToolStrip btnToolStrip = new ToolStrip();

        ToolStripButton addBtn = new ToolStripButton(MsgServer.getMsg("Add"));
        addBtn.setAutoFit(true);
        addBtn.setIcon("attask/icon_add.png");
        addBtn.setShowDisabledIcon(false);
		btnToolStrip.addButton(addBtn);
        ToolStripButton delBtn = new ToolStripButton(MsgServer.getMsg("Delete"));
        delBtn.setAutoFit(true);
        delBtn.setIcon("attask/icon_delete.png");
        delBtn.setShowDisabledIcon(false);
		btnToolStrip.addButton(delBtn);
		
		addBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord lgr=new ListGridRecord();
				lgr.setAttribute("edited", true);
				if(lg.getField(orderField)!=null){
					if(lg.getRecords().length>0){
						lgr.setAttribute(orderField, Integer.parseInt(lg.getRecord(lg.getRecords().length-1).getAttribute(orderField))+1);
					}else{
						lgr.setAttribute(orderField, 1);
					}
				}
				String className=lg.getID();
				if(className.indexOf("MATERIAL_REPRODUCTIVE_PERFORMANCE")!=-1){
					lgr.setAttribute("PARITY", lg.getRecords().length+1);
				}
				lg.addData(lgr);
			}
		});

		delBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord[] lgrs=lg.getSelectedRecords();
				if(lgrs.length>0){

					for(ListGridRecord lgr:lgrs){
	                	if(type.equals("Modify")) {
		                	String className;
		                	if(oneIdValue==null){
		                		className=lg.getID()+"_ITEM";
		                	}else{
		                		className=lg.getID();
		                	}
		                  ClassType classType=MyClassTypeUtil.getDomainClassType(className.replace("0", "."));
		                  Constructor constructor=classType.findConstructor();
		                  BaseDomain d=(BaseDomain) RecordCastPojo.toPojo(constructor.newInstance(), lgr);
		                  if(classType.invoke(d, "get"+PojoCastRecord.getDomainPrimaryKey(d.getClass()))!=null){
		                	deleteDomains.add(d);
		                  }
	                	}
					}
					lg.removeSelectedData();
				}
			}
		});
		
		vlay.addMember(btnToolStrip);
        vlay.addMember(lg);
        
        return vlay;
	}

	public static void addTabs(TabSet tabSets,final TabsInfo[] tabsInfos,final String type){
		deleteDomains.clear();
		clearSubTabs(tabSets);

    	if(tabsInfos!=null&&tabsInfos.length>0){
    		
	        TabsInfo tabsInfo=null;
	        for(int i=0;i<tabsInfos.length;i++){
	        	tabsInfo=tabsInfos[i];
		        final MyTab tab = new MyTab(tabsInfo.getTabName(), "pieces/16/piece_blue.png");
//		        tab.setID(tabsInfo.getTabId().replace(".", "0"));
		        if(tabsInfo.getFormClass()!=null){
		        	tab.setFormClassN(tabsInfo.getFormClass().getName());
		        }
		        if(tabsInfo.getLgClass()!=null){
		        	tab.setLgClassN(tabsInfo.getLgClass().getName());
		        }
		        tab.setAttribute("idx", i);
		        tab.addTabSelectedHandler(new TabSelectedHandler() {
					
					@Override
					public void onTabSelected(TabSelectedEvent event) {
						if(tab.getPane()==null){
							createTabByOneAdd(tabsInfos, tab, type);
						}
					}
				});
		        
		        tabSets.addTab(tab);
	        }
	        if(type.equals("Copy")){
	        	for(int i=1;i<tabSets.getTabs().length;i++){
	        		createTabByOneAdd(tabsInfos, tabSets.getTabs()[i], type);
	        	}
	        }
    	}

    }
	//
	public static void createTabUI(final MyTab tab,final String type,final TabsInfo tabsInfo){
		VLayout vlay=new VLayout(10);
		DynamicForm form=null;
		if(tab.getFormClassN()!=null&&tab.getFormClassN().length()>0){
			form=getEditForm(tabsInfo,type);
			vlay.addMember(form);
			LayManager.resetFormItemsWidth(form, 250,type);
	        setMEMOWitdh(form);
	        
		}
		ListGrid lg=null;
		if(tab.getLgClassN()!=null&&tab.getLgClassN().length()>0){
			lg=getLG(type,tabsInfo);
			if(!type.equals(OpTypeEnum.Look.getValue())){
				vlay.addMember(addHeadBtn(lg,type));
			}else{
				vlay.addMember(lg);
			}
		}
		tab.setPane(vlay);
		if("Copy".equals(type)){
			if(tab.getPane()!=null&&!tab.isDataLoad()){
				tab.setDataLoad(true);
				setDatas(type, tabsInfo, 
						(DynamicForm)DynamicForm.getById(tab.getFormClassN()), 
						(ListGrid)ListGrid.getById(tab.getLgClassN()));
			}
		}

        tab.addTabSelectedHandler(new TabSelectedHandler() {
			
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				if(tab.getPane()!=null&&!tab.isDataLoad()){
					tab.setDataLoad(true);
					setDatas(type, tabsInfo, 
							(DynamicForm)DynamicForm.getById(tab.getFormClassN()), 
							(ListGrid)ListGrid.getById(tab.getLgClassN()));
				}
			}
		});
	}
	
	public static void setDatas(String type,TabsInfo tabsInfo,DynamicForm form,ListGrid lg){
        if(oneIdValue!=null){
	        autoSetDataRO(type,form,oneIdValue,lg,tabsInfo);
        }else{
        	autoSetDataRO(type,form,idValue,lg,tabsInfo);
        }
	}
	
	public static void createTabByOneAdd(TabsInfo[] tabsInfos,Tab tab,String type){
		
		int idx=tab.getAttributeAsInt("idx");
		TabsInfo tabsInfoIn=tabsInfos[idx];
		VLayout vlay=new VLayout(10);
		ListGrid lg=null;
		if(tabsInfoIn.getLgClass()!=null){
			lg=getLG(type,tabsInfoIn);
		}
		DynamicForm form=null;
		if(tabsInfoIn.getFormClass()!=null){
			form=getEditForm(tabsInfoIn,type);
			vlay.addMember(form);
			LayManager.resetFormItemsWidth(form, 250,type);
	        setMEMOWitdh(form);
	        
		}
        if(oneIdValue!=null){
	        autoSetDataRO(type,form,oneIdValue,lg,tabsInfoIn);
        }else{
        	autoSetDataRO(type,form,idValue,lg,tabsInfoIn);
        }
		if(tabsInfoIn.getLgClass()!=null){
			if(!type.equals(OpTypeEnum.Look.getValue())){
				vlay.addMember(addHeadBtn(lg,type));
			}else{
				vlay.addMember(lg);
			}
		}
		tab.setPane(vlay);
		
	}
	
	public static void clearSubTabs(TabSet tabSets){
		Tab[] tabs=tabSets.getTabs();
		for(int i=1;i<tabs.length;i++){
			tabs[i].getPane().destroy();
			tabSets.removeTab(tabs[i]);
		}
	}
	
	public static void clearSubTabs(TabSet tabSets,int startIdx){
		Tab[] tabs=tabSets.getTabs();
		for(int i=startIdx;i<tabs.length;i++){
			tabs[i].getPane().destroy();
			tabSets.removeTab(tabs[i]);
		}
	}
	

	protected static String idValue="";
	protected static String oneIdValue=null;
	public static boolean checkValH(TabLG tabLG){
		if(tabLG.getTabsInfo().length>0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * eg：物料主数据、计划
	 * 获取通用的弹窗内容tab布局，第一个页签为主form，右边为多个页签，每个页签包含一个ListGrid，通过给定的tabLG参数，构造对应的组件，并自动处理数据和持久化
	 * @param tabLG
	 * @param idVal 主页签的id值，用来替换每个页签的id值
	 * @return
	 */
	public static TabSet getUIH(final TabLG tabLG,final String idVal){
		deleteDomains.clear();
		oneIdValue=null;
		TabSet tabSets = new TabSet();
    	tabSets.setTabBarPosition(Side.TOP);
    	tabSets.setWidth100();
    	tabSets.setHeight100();
    	if(checkValH(tabLG)){
    		
    		idValue="";
			VLayout vlay=new VLayout();
	        MyTab tab1 = new MyTab(tabLG.getTabsInfo()[0].getTabName(), "pieces/16/piece_blue.png");
	        DynamicForm form=null;
    		if(tabLG.getTabsInfo()[0].getFormClass()!=null){
	    		if(!tabLG.getType().equals("Add")){
	    			idValue=tabLG.getEditRecord().getAttribute(PojoCastRecord.getDomainIdField(tabLG.getTabsInfo()[0].getFormClass()));
	    		}
//		        tab1.setID(tabLG.getTabsInfo()[0].getTabId().replace(".", "0"));
		        if(tabLG.getTabsInfo()[0].getFormClass()!=null){
		        	tab1.setFormClassN(tabLG.getTabsInfo()[0].getFormClass().getName());
		        }
				form=getEditForm(tabLG.getTabsInfo()[0],tabLG.getType());
				vlay.addMember(form);
		        LayManager.resetFormItemsWidth(form, 250,tabLG.getType());
		        setMEMOWitdh(form);
//		        autoCheckForm(tabLG.getType(),form,tabLG.getEditRecord());
    		}
	        if(idVal!=null){
	        	oneIdValue=idVal;
	        }
    		ListGrid lg=null;
    		if(tabLG.getTabsInfo()[0].getLgClass()!=null){

		        if(tabLG.getTabsInfo()[0].getLgClass()!=null){
		        	tab1.setLgClassN(tabLG.getTabsInfo()[0].getLgClass().getName());
		        }
    			lg=getLG(tabLG.getType(),tabLG.getTabsInfo()[0]);
    			if(!tabLG.getType().equals(OpTypeEnum.Look.getValue())){
    				vlay.addMember(addHeadBtn(lg,tabLG.getType()));
    			}else{
    				vlay.addMember(lg);
    			}
    		}
	        tab1.setPane(vlay);
	        tabSets.addTab(tab1);
	        if(idVal!=null){
		        autoSetData(tabLG.getType(),form,oneIdValue,lg,tabLG.getTabsInfo()[0]);
	        }else{
	        	autoSetData(tabLG.getType(),form,idValue,lg,tabLG.getTabsInfo()[0]);
	        }
	        
	        TabsInfo tabsInfo=null;
	        for(int i=1;i<tabLG.getTabsInfo().length;i++){
	        	tabsInfo=tabLG.getTabsInfo()[i];
		        final MyTab tab = new MyTab(tabsInfo.getTabName(), "pieces/16/piece_blue.png");
//		        tab.setID(tabsInfo.getTabId().replace(".", "0"));
		        if(tabsInfo.getFormClass()!=null){
		        	tab.setFormClassN(tabsInfo.getFormClass().getName());
		        }

		        if(tabsInfo.getLgClass()!=null){
		        	tab.setLgClassN(tabsInfo.getLgClass().getName());
		        }
		        tab.setAttribute("idx", i);
//		        tab.addTabSelectedHandler(new TabSelectedHandler() {
//					
//					@Override
//					public void onTabSelected(TabSelectedEvent event) {
//						if(tab.getPane()==null){
//							createTabBySelected(tab, tabLG, idVal);
//						}
//					}
//				});

		        createTabUI(tab, tabLG.getType(), tabsInfo);
		        tabSets.addTab(tab);
	        }
//	        if(tabLG.getType().equals("Copy")){
//	        	for(int i=1;i<tabSets.getTabs().length;i++){
//	        		createTabByOneAdd(tabLG.getTabsInfo(), tabSets.getTabs()[i], tabLG.getType());
//	        	}
//	        }
    	}

    	return tabSets;
    }
	
	public static void createTabBySelected(Tab tab,TabLG tabLG,String idVal){

		
		int idx=tab.getAttributeAsInt("idx");
		TabsInfo tabsInfoIn=tabLG.getTabsInfo()[idx];
		VLayout vlay=new VLayout(10);
		ListGrid lg=null;
		if(tabsInfoIn.getLgClass()!=null){
			lg=getLG(tabLG.getType(),tabsInfoIn);
		}
		DynamicForm form=null;
		if(tabsInfoIn.getFormClass()!=null){
			form=getEditForm(tabsInfoIn,tabLG.getType());
			vlay.addMember(form);
	        LayManager.resetFormItemsWidth(form, 250,tabLG.getType());
	        setMEMOWitdh(form);
	        
		}
        if(idVal!=null){
	        autoSetData(tabLG.getType(),form,oneIdValue,lg,tabsInfoIn);
        }else{
        	autoSetData(tabLG.getType(),form,idValue,lg,tabsInfoIn);
        }
		if(tabsInfoIn.getLgClass()!=null){
			if(!tabLG.getType().equals(OpTypeEnum.Look.getValue())){
				vlay.addMember(addHeadBtn(lg,tabLG.getType()));
			}else{
				vlay.addMember(lg);
			}
		}
		tab.setPane(vlay);
	}
    
    public static void autoSetData(String type,final DynamicForm _editForm,String id,final ListGrid lg,final TabsInfo tabsInfoIn){
        if (!type.equals("Add")&&id.length()>0) {
        	if(_editForm!=null){
        		_editForm.reset();

		        if(tabsInfoIn.getFormClass()!=null){
	            	ArrayList<PageSql> pageSqls=new ArrayList<PageSql>();
	            	pageSqls.add(new PageSql.Builder(tabsInfoIn.getFormFK(), "=").content(id).build());
	            	SetCommonFilter.addComValSql(pageSqls,tabsInfoIn.getFormClass().getName());
	
	                CommonRemote.Util.getInstance().commonSearch(tabsInfoIn.getFormClass().getName(), pageSqls,new DefaultCallback<List>() {
	
	                    @Override
	                    public void onSuccess(List result) {
	                    	if(!result.isEmpty()){
	                    		BaseDomain d=(BaseDomain) result.get(0);
	                        	ResultBuild.buildForm(d, _editForm);
	                    		if(lg!=null)
	                    			setLgData(lg,PojoCastRecord.getDomainIdValue(d),tabsInfoIn);
	                    	}else{
	                    		if(lg!=null)
	                    			clearLgData(lg);
	                    	}
	                    }
	                });
		        }
        	}else{
        		if(lg!=null)
        			setLgData(lg,id,tabsInfoIn);
        	}
        }else{
    		if(lg!=null)
    			clearLgData(lg);
        }
    }
    
    /**
     * by data_source 0/1
     * @param type
     * @param _editForm
     * @param id
     * @param lg
     * @param tabsInfoIn
     */
    public static void autoSetDataRO(String type,final DynamicForm _editForm,String id,final ListGrid lg,final TabsInfo tabsInfoIn){
        if (!"Add".equals(type)&&id.length()>0) {
        	if(_editForm!=null){
        		_editForm.reset();
		        if(tabsInfoIn.getFormClass()!=null){
	            	ArrayList<PageSql> pageSqls=new ArrayList<PageSql>();
	            	pageSqls.add(new PageSql.Builder(tabsInfoIn.getFormFK(), "=").content(id).build());
	            	SetCommonFilter.addComValSql(pageSqls,tabsInfoIn.getFormClass().getName());
	
	                CommonRemote.Util.getInstance().commonSearch(tabsInfoIn.getFormClass().getName(), pageSqls,new DefaultCallback<List>() {
	
	                    @Override
	                    public void onSuccess(List result) {
	                    	if(!result.isEmpty()){
	                    		BaseDomain d=(BaseDomain) result.get(0);
	                        	ResultBuild.buildForm(d, _editForm);
	                    		if(lg!=null)
	                    			setLgDataRO(lg,PojoCastRecord.getDomainIdValue(d),tabsInfoIn);
	                    	}else{
	                    		if(lg!=null)
	                    			clearLgData(lg);
	                    	}
	                    }
	                });
		        }
        	}else{
        		if(lg!=null)
        			setLgDataRO(lg,id,tabsInfoIn);
        	}
        }else{
    		if(lg!=null)
    			clearLgData(lg);
        }
    }
    
    public static void clearLgData(ListGrid lg){
    	lg.setData(new ListGridRecord[0]);
    	lg.setEmptyMessage("请新增数据");
    }
    
    public static void setLgData(ListGrid lg,String subId,TabsInfo tabsInfoIn){
        if(tabsInfoIn.getLgClass()!=null){
        	ArrayList<PageSql> pageSqls=new ArrayList<PageSql>();
        	pageSqls.add(new PageSql.Builder(tabsInfoIn.getLgFK(), "=").content(subId).build());
        	CommonServer.searchPageAll(pageSqls, tabsInfoIn.getLgClass().getName(), lg);
        }
    }
    public static void setLgDataRO(ListGrid lg,String subId,TabsInfo tabsInfoIn){
        if(tabsInfoIn.getLgClass()!=null){
        	ArrayList<PageSql> pageSqls=new ArrayList<PageSql>();
        	pageSqls.add(new PageSql.Builder(tabsInfoIn.getLgFK(), "=").content(subId).build());
        	CommonServer.searchPageAllRO(pageSqls, tabsInfoIn.getLgClass().getName(), lg);
        }
    }

    public static void autoCheckForm(String type,DynamicForm _editForm,ListGridRecord _editRecord){
        if (!type.equals("Add")) {
            _editForm.reset();
            if(_editRecord!=null)
            _editForm.editRecord(_editRecord);
        }
    }
    
    public static void setMEMOWitdh(DynamicForm _editForm){
        if(_editForm.getNumCols()==4&&_editForm.getItem("MEMO")!=null){
        	_editForm.getItem("MEMO").setWidth("*");
        	_editForm.getItem("MEMO").setColSpan(3);
        	_editForm.getItem("MEMO").setHeight(52);
        }
    }

    public static MyDynamicForm getEditForm(TabsInfo tabsInfoIn,String type) {
    	final MyDynamicForm _editForm = new MyDynamicForm();
    	if(MyDynamicForm.getById(tabsInfoIn.getFormClass().getName().replace(".", "0"))!=null){
    		MyDynamicForm.getById(tabsInfoIn.getFormClass().getName().replace(".", "0")).destroy();
    	}
    	_editForm.setID(tabsInfoIn.getFormClass().getName().replace(".", "0"));
    	_editForm.setTitle(tabsInfoIn.getFormFK());
        _editForm.setWidth(860);
        _editForm.setNumCols(4);
        _editForm.setDataSource(DSEditFormJoinSelect.getEditFormDS(tabsInfoIn.getFormClass().getName(), tabsInfoIn.getFormClass()));
        _editForm.setAutoFetchData(false);
        _editForm.addItemChangedHandler(new ItemChangedHandler() {
			
			@Override
			public void onItemChanged(ItemChangedEvent event) {
				_editForm.setEdited(true);
			}
		});
        return _editForm;
    }

    //
    protected static List<BaseDomain> deleteDomains=new ArrayList<BaseDomain>();;//删除的实体类
	public static ListGrid getLG(final String type,TabsInfo tabsInfoIn){

		final MyListGrid lg=new MyListGrid();
		if(MyListGrid.getById(tabsInfoIn.getLgClass().getName().replace(".", "0"))!=null){
			MyListGrid.getById(tabsInfoIn.getLgClass().getName().replace(".", "0")).destroy();
		}
		lg.setHeight100();
        lg.setID(tabsInfoIn.getLgClass().getName().replace(".", "0"));
        lg.setDomainFK(tabsInfoIn.getLgFK());
        lg.setFields(DSListGridFields.getListGridFieldsByOrder(tabsInfoIn.getLgClass()));
        if(!type.equals(OpTypeEnum.Look.getValue())){
	        lg.setCanEdit(true);
	        lg.setEditEvent(ListGridEditEvent.CLICK);
        }
        if(lg.getField("CREATE_DATE")!=null){
        	lg.getField("CREATE_DATE").setCanEdit(false);
        }
        if(lg.getField("UPDATE_DATE")!=null){
        	lg.getField("UPDATE_DATE").setCanEdit(false);
        }
        lg.addRowEditorExitHandler(new RowEditorExitHandler() {
			
			@Override
			public void onRowEditorExit(RowEditorExitEvent event) {
				event.getRecord().setAttribute("type", "Modify");
			}
		});

        return lg;
	}
	
	
	public static TabsInfoData getTabDatas(String type,TabSet tabSet,BaseDomain d,MyDynamicForm mForm){

		List<MyDynamicForm> formlist=new ArrayList();
		List<MyListGrid> lglist=new ArrayList();
		TabsInfoData dataMaps=null;
		boolean canSave=true;
			outLab2:
			      for(Tab tab:tabSet.getTabs()){
			    	  if(tab.getPane()!=null){
			          	MyTab myTab=(MyTab) tab;
						  if(!validateFormById(myTab.getFormClassN())||!validateLgById(myTab.getLgClassN())){
							  tabSet.selectTab(myTab);
							  canSave=false;
							  break outLab2;
						  }else{
							  MyDynamicForm form=getTabForm(myTab.getFormClassN());
							  if(form!=null){
						        	if(form.isEdited()){
						        		formlist.add(form);
						        	}
							  }
							  MyListGrid lg=getTabLg(myTab.getLgClassN());
							  if(lg!=null){
								  lglist.add(lg);
							  }
						  }
				      }
			      }
      if(canSave&&(!formlist.isEmpty()||!lglist.isEmpty())){


	      	  dataMaps=new TabsInfoData();
	      	  //className		FK ，		ID Field, 表单转的对象
	      	  Map<String, Map<String, Map<String, BaseDomain>>> fDomains=new  LinkedHashMap();// 次页签中的表单的数据
	      	  //className		FK ，ListGridRecord数据转的对象
	      	  Map<String, Map<String, List<BaseDomain>>> lDomains=new  LinkedHashMap();// 页签中的ListGrid的数据
	      	  BaseDomain domain=null;
	      	  for(MyDynamicForm form:formlist){
      		  	  String className=form.getID();
	      		  if(!mForm.getID().equals(className)){
	//                    ClassType classType=MyClassTypeUtil.getDomainClassType("ans.client.info.plan."+className);
	                    ClassType classType=MyClassTypeUtil.getDomainClassType(className.replace("0", "."));
	                    domain=(BaseDomain)RecordCastPojo.toPojo(classType.findConstructor().newInstance(), form,type);
	
		  				  if(form.isEdited()||PojoCastRecord.getDomainIdValue(domain)==null){
		  					domain.setType("Modify");
		  				  }
		  				  
//	                  if(classType.getClass().getName().replace("___Reflection", "").equals(d.getClass().getName())){
//		                  d=domain;
//	                  }else{
	                    	  Map<String, Map<String, BaseDomain>> subMap=new  LinkedHashMap<String, Map<String, BaseDomain>>();
	                    	  Map<String, BaseDomain> kv=new LinkedHashMap<String, BaseDomain>();
	                    	  kv.put(PojoCastRecord.getDomainPrimaryKey(domain.getClass()),domain);
	                    	  subMap.put(form.getTitle(), kv);
	                  	  fDomains.put(className, subMap);
//	                    }
	      		  }
	      	  }

              	if(oneIdValue==null){
	      	      	  for(MyListGrid lg:lglist){
	  	              	String className;
	  	              	className=lg.getID()+"_ITEM";
	  	                ClassType classType=MyClassTypeUtil.getDomainClassType(className.replace("0", "."));
	  	
	  	                Map<String, List<BaseDomain>> subMap=new  LinkedHashMap<String, List<BaseDomain>>();
	  	                if(type.equals("Add")||type.equals("Copy")){
	  	                	subMap.put(lg.getDomainFK(), RecordCastPojo.toPojos(classType, lg,type));
	  	                }else if(type.equals("Modify")){
	  	                	subMap.put(lg.getDomainFK(), RecordCastPojo.toPojosModifyType(classType, lg));
	  	                }
  		                lDomains.put(className, subMap);
	      	      	  }

              	}else{
                	List<BaseDomain> oneList=new ArrayList<BaseDomain>();
                    Map<String, List<BaseDomain>> oneMap=new  LinkedHashMap<String, List<BaseDomain>>();
                    String domainFK="";
        	      	  for(MyListGrid lg:lglist){
    	              	String className;
	              		className=lg.getID();
    	                ClassType classType=MyClassTypeUtil.getDomainClassType(className.replace("0", "."));
    	
    	                if(type.equals("Add")||type.equals("Copy")){
    	                	oneList.addAll(RecordCastPojo.toPojos(classType, lg,type));
    	                }else if(type.equals("Modify")){
    	                	oneList.addAll(RecordCastPojo.toPojosModifyType(classType, lg));
    	                }
    	                domainFK=lg.getDomainFK();
        	      	  }
        	      	  if(domainFK.length()>0&&!oneList.isEmpty()){
	        	      	oneMap.put(domainFK, oneList);
		                lDomains.put("oneId", oneMap);
        	      	  }
              	}

            	if(oneIdValue==null){
            		dataMaps.setOneId(false);
            	}else{
            		dataMaps.setOneId(true);
            	}
	      	  dataMaps.setType(type);
	      	  dataMaps.setmIdField(PojoCastRecord.getDomainPrimaryKey(d.getClass()));
	      	  dataMaps.setmDomain(d);
	      	  if(mForm.isEdited()){
	      		  dataMaps.getmDomain().setType("Modify");
	      	  }
	      	  dataMaps.setfDomains(fDomains);
	      	  dataMaps.setlDomains(lDomains);
	      	  dataMaps.setDeleteDomains(deleteDomains);
		  
	  }

  	  
  	  return dataMaps;
	}

    public static MyDynamicForm getTabForm(String id){
    	MyDynamicForm form=(MyDynamicForm)DynamicForm.getById(id);
    	if(form!=null){
    		return form;
    	}
    	return null;
    }
    
    public static boolean validateFormById(String id){
    	MyDynamicForm form=(MyDynamicForm)DynamicForm.getById(id);
    	if(form!=null){
        	if(form.isEdited()){
        		return form.validate();
        	}
    	}
    	return true;
    }

    
    public static boolean validateLgById(String id){
    	ListGrid lg=(ListGrid)ListGrid.getById(id);
    	if(lg!=null){
    		return MyValidate.validateLG(lg);
    	}
    	return true;
    }

    
    public static MyListGrid getTabLg(String id){
    	MyListGrid lg=(MyListGrid)ListGrid.getById(id);
    	if(lg!=null){
    		return lg;
    	}
    	return null;
    }


	public static TabSet getUIBottom(final TabLG tabLG,final String idValue){

		TabSet tabSets = new TabSet();
    	tabSets.setTabBarPosition(Side.TOP);
    	tabSets.setWidth100();
    	tabSets.setHeight100();
	        
        TabsInfo tabsInfo=null;
        for(int i=0;i<tabLG.getTabsInfo().length;i++){
        	tabsInfo=tabLG.getTabsInfo()[i];
	        final MyTab tab = new MyTab(tabsInfo.getTabName(), "pieces/16/piece_blue.png");
//	        tab.setID(tabsInfo.getTabId().replace(".", "0"));
	        if(tabsInfo.getFormClass()!=null){
	        	tab.setFormClassN(tabsInfo.getFormClass().getName());
	        }
	        if(tabsInfo.getLgClass()!=null){
	        	tab.setLgClassN(tabsInfo.getLgClass().getName());
	        }
	        tab.setAttribute("idx", i);
	        tab.addTabSelectedHandler(new TabSelectedHandler() {
				
				@Override
				public void onTabSelected(TabSelectedEvent event) {
					if(tab.getPane()==null){

						int idx=tab.getAttributeAsInt("idx");
						TabsInfo tabsInfoIn=tabLG.getTabsInfo()[idx];
						VLayout vlay=new VLayout(10);
						ListGrid lg=null;
						if(tabsInfoIn.getLgClass()!=null){
							lg=getLG(tabLG.getType(),tabsInfoIn);
							vlay.addMember(lg);
							autoSetDataButtom(tabLG.getType(),idValue,lg,tabsInfoIn);
						}
						tab.setPane(vlay);
	
					}
				}
			});
	        
	        tabSets.addTab(tab);
        }
	        

    	return tabSets;
    }

    public static void autoSetDataButtom(String type,String id,final ListGrid lg,final TabsInfo tabsInfoIn){
        if (!type.equals("Add")&&id.length()>0) {
    		if(lg!=null)
    			setLgData(lg,id,tabsInfoIn);
        }
    }
}
