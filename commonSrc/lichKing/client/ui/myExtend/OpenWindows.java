package lichKing.client.ui.myExtend;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lichKing.client.myException.DefaultCallback;
import lichKing.client.pojo.Page;
import lichKing.client.pojo.PageSql;
import lichKing.client.remote.CommonRemote;
import lichKing.client.server.BasicDataServer;
import lichKing.client.ui.msg.MessageUI;
import lichKing.client.utils.LayManager;
import lichKing.client.utils.MyDateFormat;
import lichKing.client.utils.PojoCastRecord;
import lichKing.client.utils.ResultBuild;
import lichKing.client.utils.SetCommonFilter;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.types.EscapeKeyEditAction;
import com.smartgwt.client.types.FooterControls;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.EditorExitEvent;
import com.smartgwt.client.widgets.form.fields.events.EditorExitHandler;
import com.smartgwt.client.widgets.form.fields.events.FocusEvent;
import com.smartgwt.client.widgets.form.fields.events.FocusHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyDownEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyDownHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * 比较通用的弹出窗体
 *
 * @author catPan
 */
public class OpenWindows {
	
	/**
	 * Form表单中,表单元素编辑时,变成按钮选择形式,跳出选择弹窗
	 * @param form 表单
	 * @param pageSqls sql过滤条件
	 * @param fieldName form中表单元素的fieldName,只是显示
	 * @param fieldCode 作为数据保存的fieldName
	 * @param searchClass 提供查询的表单的实体类
	 * @param searchArr 提供查询的表单元素
	 * @param winTitle 窗体的标题
	 */
	public static void formPickerWin(final DynamicForm form,final ArrayList<PageSql> pageSqls,final String fieldName,
			final String fieldCode,final Class searchClass,final String[] searchArr,final String winTitle){

    	FormItemIcon picker = new FormItemIcon();
    	picker.setSrc("[SKIN]/pickers/search_picker.png");
    	picker.addFormItemClickHandler(new FormItemClickHandler() {
			
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				ListGrid winLg = SearchLgWin.show(searchClass, "search_win"+fieldCode, searchArr, "SINGLE",
						pageSqls,winTitle);

				winLg.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
					@Override
					public void onRecordDoubleClick(RecordDoubleClickEvent event) {
                        if(SearchLgWin.getSelectLgrs()!=null&&SearchLgWin.getSelectLgrs().length>0){
                            ListGridRecord lgr = SearchLgWin.getSelectLgrs()[0];
                            form.getItem(fieldName).setValue(lgr.getAttribute(fieldName));
                            form.getItem(fieldCode).setValue(lgr.getAttribute(fieldCode));
                            OpenWindows.closeWin("search_win"+fieldCode);
                        }else{
                        	MessageUI.setMsgInfo("请选择一条数据！");
                        }
                    }
                });
			}
		});

    	final ComboBoxItem item=(ComboBoxItem) form.getItem(fieldName);
		item.setIcons(picker);
    	item.setShowPickerIcon(false);
    	item.setType("ComboBoxItem");
    	item.setLoadingDisplayValue(null);

		item.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				changeContext(pageSqls, fieldName, searchClass, event);
			}
		});
		item.addEditorExitHandler(new EditorExitHandler() {
			
			@Override
			public void onEditorExit(EditorExitEvent event) {
				if(Window.getById("search_win"+fieldCode)==null){
					checkCbiValWin(item,form, fieldName, fieldCode, searchClass, searchArr, winTitle, pageSqls);
				}
			}
		});
	}
	public static void formPickerWin(final DynamicForm form,final ArrayList<PageSql> pageSqls,final String fieldName,
			final String fieldCode,final String fieldSR,final Class searchClass,final String[] searchArr,final String winTitle){

    	FormItemIcon picker = new FormItemIcon();
    	picker.setSrc("[SKIN]/pickers/search_picker.png");
    	picker.addFormItemClickHandler(new FormItemClickHandler() {
			
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				ListGrid winLg = SearchLgWin.show(searchClass, "search_win"+fieldCode, searchArr, "SINGLE",
						pageSqls,winTitle);

				winLg.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
					@Override
					public void onRecordDoubleClick(RecordDoubleClickEvent event) {
                        if(SearchLgWin.getSelectLgrs()!=null&&SearchLgWin.getSelectLgrs().length>0){
                            ListGridRecord lgr = SearchLgWin.getSelectLgrs()[0];
                            form.getItem(fieldName).setValue(lgr.getAttribute(fieldName));
                            form.getItem(fieldCode).setValue(lgr.getAttribute(fieldCode));
                            OpenWindows.closeWin("search_win"+fieldCode);
                        }else{
                        	MessageUI.setMsgInfo("请选择一条数据！");
                        }
                    }
                });
			}
		});

    	final ComboBoxItem item=(ComboBoxItem) form.getItem(fieldName);
		item.setIcons(picker);
    	item.setShowPickerIcon(false);
    	item.setType("ComboBoxItem");
    	item.setLoadingDisplayValue(null);
    	if(fieldSR!=null){
    		item.setFilterFields(fieldSR, fieldName);
		}


		item.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				changeContext(pageSqls, fieldName,fieldSR, searchClass, event);
			}
		});

		item.addEditorExitHandler(new EditorExitHandler() {
			
			@Override
			public void onEditorExit(EditorExitEvent event) {
				if(Window.getById("search_win"+fieldCode)==null){
					checkCbiValWin(item,form, fieldName, fieldCode, searchClass, searchArr, winTitle, pageSqls);
				}
			}
		});
	}

	public static void checkCbiValWin(final ComboBoxItem item,final DynamicForm form,final String fieldName,final String fieldCode,
			final Class searchClass,final String[] searchArr,final String winTitle,final ArrayList<PageSql> pageSqls){

		final TextItem valueItem=(TextItem)form.getItem(fieldCode);
		if(item.getDisplayValue()!=null&&item.getDisplayValue().toString().length()>0){
			String enterVal=item.getDisplayValue().toString().trim();
    		item.setAttribute("checkPassServer", false);
        	valueItem.setValue("");
				if(enterVal.length()>0){
	            	
			        ArrayList<PageSql> pageSql = new ArrayList<PageSql>();
			        if(item.getValue()!=null){
			        	pageSql.add(new PageSql.Builder(fieldCode, "=").content(item.getValue().toString()).build());
			        	valueItem.setValue(item.getValue().toString());
			        }
			        pageSql.add(new PageSql.Builder(fieldName, "=").content(enterVal).build());
			        item.setValue(enterVal);
			        SetCommonFilter.addComValSql(pageSql, searchClass);
			        CommonRemote.Util.getInstance().commonSearch(searchClass.getName(), pageSql, new DefaultCallback<List>() {
		
			            @Override
			            public void onSuccess(List result) {
			            	if(result.size()==1){
			            		item.setAttribute("checkPassServer", true);
			            		ResultBuild.buildForm(result.get(0), form);
			            	}else{
			            		item.setAttribute("checkPassServer", false);
			                	valueItem.setValue("");
			            	}
			            }
			        });
				}
		}
	}

	/**
	 * ListGrid列表中,单元格编辑时,变成按钮选择形式,跳出选择弹窗
	 * @param lg 列表
	 * @param pageSqls sql过滤条件
	 * @param fieldName lg中单元格的fieldName,只是显示
	 * @param fieldCode 作为数据保存的fieldName
	 * @param searchClass 提供查询的表单的实体类
	 * @param searchArr 提供查询的表单元素
	 * @param winTitle 窗体的标题
	 */
	public static void lgCellBtnWin(final ListGrid lg,final ArrayList<PageSql> pageSqls,final String fieldName,final String fieldCode,
																						final Class searchClass,final String[] searchArr,final String winTitle){

		lg.setArrowKeyAction("none");
		lg.setEscapeKeyEditAction(EscapeKeyEditAction.DONE);
		lg.setModalEditing(false);
		
		lg.setEditorCustomizer(new ListGridEditorCustomizer() {
            public FormItem getEditor(final ListGridEditorContext context) {
            	if (context.getEditedRecord()!=null){
	                final ListGridField field = context.getEditField();
	                if (field.getName().equals(fieldName)) {
	        			context.getEditedRecord().setAttribute("showWin"+fieldCode, true);
	                    
	                    PickerIcon searchPicker = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
	                        public void onFormItemClick(FormItemIconClickEvent event) {
	    						ListGrid winLg = SearchLgWin.show(searchClass, "search_win"+fieldCode, searchArr, "SINGLE",
	    								pageSqls,winTitle);
	    						winLg.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
	    							@Override
	    							public void onRecordDoubleClick(RecordDoubleClickEvent event) {
	    	                            if(SearchLgWin.getSelectLgrs()!=null&&SearchLgWin.getSelectLgrs().length>0){
	        	                            ListGridRecord lgr = SearchLgWin.getSelectLgrs()[0];
	        	        					context.getEditedRecord().setAttribute("codeVal"+fieldCode, lgr.getAttribute(fieldCode));
	        	                            context.getEditedRecord().setAttribute(fieldName, lgr.getAttribute(fieldName));
	        	                            context.getEditedRecord().setAttribute(fieldCode, lgr.getAttribute(fieldCode));
	        	        		            
	        	        		            ListGridRecord editLgr=lg.getRecord(lg.getRecordIndex(context.getEditedRecord()));
	        	        		            editLgr.setAttribute(fieldName, lgr.getAttribute(fieldName));
	        	        		            editLgr.setAttribute(fieldCode, lgr.getAttribute(fieldCode));
	        	        		            
	        	                            lg.refreshCell(lg.getRecordIndex(context.getEditedRecord()), lg.getFieldNum(fieldName));
	        	                            OpenWindows.closeWin("search_win"+fieldCode);
	    	                            }else{
	    	                            	MessageUI.setMsgInfo("请选择一条数据！");
	    	                            }
	    	                        }
	    	                    });
	                        }
	                    });
	
	                    ComboBoxItem sItem=new ComboBoxItem();
	                    sItem.setIcons(searchPicker);
	                    sItem.setShowPickerIcon(false);
	                    sItem.addChangeHandler(new ChangeHandler() {
							
							@Override
							public void onChange(ChangeEvent event) {
			        			context.getEditedRecord().setAttribute("checkPassServer"+fieldName, false);
								changeContext(pageSqls, fieldName, searchClass, event);
							}
						});
	                    sItem.addKeyDownHandler(new KeyDownHandler() {
	                    	public void onKeyDown(KeyDownEvent event) {
	 							if("Escape".equals(event.getKeyName())){
									Timer timer=new Timer() {
										
										@Override
										public void run() {
						        			context.getEditedRecord().setAttribute("codeVal"+fieldCode, (Object)null);
									}
									};
									timer.schedule(600);
				        			context.getEditedRecord().setAttribute("codeVal"+fieldCode, "Escape");
	 							}
	 						}
	 					});
	                    sItem.addEditorExitHandler(new EditorExitHandler() {
							
							@Override
							public void onEditorExit(EditorExitEvent event) {
								if(Window.getById("search_win"+fieldCode)==null){
	 								FormItem item=event.getItem();
	 								checkCbiValWin(item,lg, context, fieldName, fieldCode, searchClass, searchArr, winTitle, pageSqls,
	 										item.getSelectedRecord()==null?context.getEditedRecord().getAttribute("codeVal"+fieldCode):item.getSelectedRecord().getAttribute(fieldCode));
	 							}
							}
						});
	                  return sItem;
	                }
	
	                if (field.getType()!=null&&field.getType().equals(ListGridFieldType.DATE)) {
	                	if(context.getEditedRecord().getAttribute(field.getName())==null){
		                	final DateItem d=new DateItem();
		        			context.getEditedRecord().setAttribute(field.getName(), new Date());
		        			d.addFocusHandler(new FocusHandler() {
								
								@Override
								public void onFocus(FocusEvent event) {
									if(event.getForm().validate()){
										int len=MyDateFormat.dateAndTimeFormat((Date)event.getItem().getValue(), 0).length();
										((TextItem)event.getItem()).setSelectionRange(len-2, len);
									}else{
										((TextItem)event.getItem()).setSelectionRange(20, 20);
									}
								}
							});
		                	return d;
	                	}else{
	                		context.getDefaultProperties().addFocusHandler(new FocusHandler() {
								
								@Override
								public void onFocus(FocusEvent event) {
									if(event.getForm().validate()){
										int len=MyDateFormat.dateAndTimeFormat((Date)event.getItem().getValue(), 0).length();
										((TextItem)event.getItem()).setSelectionRange(len-2, len);
									}else{
										((TextItem)event.getItem()).setSelectionRange(20, 20);
									}
								}
							});
	                	}
	                }
            	}
                return context.getDefaultProperties();  
            }  
        }); 
		
	}
	
	public static void changeContext(ArrayList<PageSql> pageSqls,String fieldName,Class searchClass,ChangeEvent event){
		if(!pageSqls.isEmpty()){
			List<PageSql> removeSqls=new ArrayList();
			for(PageSql ps:pageSqls){
				if("T".equals(ps.getSqlType())){
					removeSqls.add(ps);
				}
			}
			pageSqls.removeAll(removeSqls);
		}
		if(event.getValue()!=null&&event.getValue().toString().trim().length()>0){
			pageSqls.add(new PageSql.Builder(fieldName, "iStartsWith").content(event.getValue().toString()).sqlType("T").build());
			
			
            BasicDataServer.setCbI4DB(event.getItem(), searchClass.getName(), pageSqls);
		}
		
	}
	public static void changeContext(ArrayList<PageSql> pageSqls,String fieldName,
			String fieldSR,Class searchClass,ChangeEvent event){
		if(!pageSqls.isEmpty()){
			List<PageSql> removeSqls=new ArrayList();
			for(PageSql ps:pageSqls){
				if("T".equals(ps.getSqlType())){
					removeSqls.add(ps);
				}
			}
			pageSqls.removeAll(removeSqls);
		}
		if(event.getValue()!=null&&event.getValue().toString().trim().length()>0){
			if(fieldSR!=null){
				pageSqls.add(new PageSql.Builder(fieldName, "iStartsWith").
						content(event.getValue().toString()).sqlType("T").leftLevel("(").build());
				pageSqls.add(new PageSql.Builder(fieldSR, "iStartsWith").
						content(event.getValue().toString()).sqlType("T").andOr("or").rightLevel(")").build());
			}else{
				pageSqls.add(new PageSql.Builder(fieldName, "iStartsWith").
						content(event.getValue().toString()).sqlType("T").build());
			}
			
            BasicDataServer.setCbI4DB(event.getItem(), searchClass.getName(), pageSqls);
		}
		
	}
	
	public static void checkCbiValWin(final FormItem item,final ListGrid lg,final ListGridEditorContext context,final String fieldName,final String fieldCode,
			final Class searchClass,final String[] searchArr,final String winTitle,final ArrayList<PageSql> pageSqls,String codeVal){


		if(item.getValue()!=null&&item.getValue().toString().length()>0){
			String enterVal=item.getValue().toString().trim();
			if(codeVal==null){
				

//		        ArrayList<PageSql> pageSql = new ArrayList<PageSql>();

//		        if(PojoCastRecord.isHadDomainField(searchClass, "FARM_FK")){
//		        	pageSql.add(new PageSql.Builder("FARM_FK", "=").content().build());
//		        }

				SetCommonFilter.deleteExistSql(pageSqls, new String[]{fieldName});
		        pageSqls.add(new PageSql.Builder(fieldName, "=").content(enterVal).sqlType("T").build());
//		        SetCommonFilter.addComValSql(pageSql, searchClass);
				Page page=new Page.Builder().pageSql(pageSqls).maxResults(10).build();

//				if(enterVal!=null&&(searchClass.getName().indexOf("PIG_BASE_SEARCH_V_Test")!=-1
//						||searchClass.getName().indexOf("PIG_BASE_STRAIN_SEARCH")!=-1)){
//					String viewParm="";
//					viewParm="set @ac='"+enterVal+"'";
//					viewParm+=",@farmId='"+AppParsServer.getFarm().getFARM_ID()+"'";
//					viewParm+=",@pigType='"+
//					(AddEventPage.get_pigType().equals("g")?"36":"37")+"';";
//					page.setViewParm(viewParm);
//					
//				}
		        CommonRemote.Util.getInstance().commonSearch(page,searchClass.getName(), new DefaultCallback<ArrayList>() {
	
		            @Override
		            public void onSuccess(ArrayList result) {
		            	if(result!=null&&result.size()==1){
		            		PojoCastRecord.toListGridRecord(result.get(0), context.getEditedRecord(), "AutoComplete");
		            		lg.refreshRow(context.getRowNum());
//		        			lg.endEditing();
		        			context.getEditedRecord().setAttribute("checkPassServer"+fieldCode, true);
		            	}else{
		        			context.getEditedRecord().setAttribute("checkPassServer"+fieldCode, false);
		        		}
	        			context.getEditedRecord().setAttribute("codeVal"+fieldCode, (Object)null);
		            }
		        });
		        
				
//					MessageUI.setMsgInfo("请完善数据");// 需要完善拥有自己输入完整且正确的数据时，要正确验证
//				if(!context.getEditedRecord().getAttributeAsBoolean("showWin"+fieldCode)){
//        			context.getEditedRecord().setAttribute(fieldName, (Object)null);
//        			context.getEditedRecord().setAttribute("codeVal"+fieldCode, (Object)null);
//		            
//            		lg.refreshRow(context.getRowNum());
//				}else{
////	        		item.focusInItem();
////					autoShowCbiValWin(lg, context, fieldName, fieldCode, searchClass, searchArr, winTitle, pageSqls);
//				}
			}else{
				if("Escape".equals(codeVal)){
//					lg.removeData(context.getEditedRecord());
				}else if(codeVal.length()>0 && enterVal.length()>0){
					context.getEditedRecord().setAttribute("codeVal"+fieldCode, codeVal);
//			        OpenWindows.getLoadingWin("checkCbiVal_win");
			        ArrayList<PageSql> pageSql = new ArrayList<PageSql>();
			        pageSql.add(new PageSql.Builder(fieldCode, "=").content(codeVal).build());
			        pageSql.add(new PageSql.Builder(fieldName, "=").content(enterVal).build());
			        SetCommonFilter.addComValSql(pageSql, searchClass);
					Page page=new Page.Builder().pageSql(pageSql).maxResults(10).build();
//					if(enterVal!=null&&(searchClass.getName().indexOf("PIG_BASE_SEARCH_V_Test")!=-1
//							||searchClass.getName().indexOf("PIG_BASE_STRAIN_SEARCH")!=-1)){
//						String viewParm="";
//						viewParm="set @ac='"+enterVal+"'";
//						viewParm+=",@farmId='"+AppParsServer.getFarm().getFARM_ID()+"'";
//						viewParm+=",@pigType='"+
//						(AddEventPage.get_pigType().equals("g")?"36":"37")+"';";
//						
//						page.setViewParm(viewParm);
//					}
			        CommonRemote.Util.getInstance().commonSearch(page,searchClass.getName(),new DefaultCallback<ArrayList>() {
		
			            @Override
			            public void onSuccess(ArrayList result) {
//			        		closeWin("checkCbiVal_win");
			            	if(result.size()==1){
			            		PojoCastRecord.toListGridRecord(result.get(0), context.getEditedRecord(), "AutoComplete");
			            		lg.refreshRow(context.getRowNum());
//			        			lg.endEditing();
			        			context.getEditedRecord().setAttribute("checkPassServer"+fieldCode, true);
			            	}else{
			        			context.getEditedRecord().setAttribute("checkPassServer"+fieldCode, false);
//			            		item.focusInItem();
//			        			autoShowCbiValWin(lg, context, fieldName, fieldCode, searchClass, searchArr, winTitle, pageSqls);
			            	}
		        			context.getEditedRecord().setAttribute("codeVal"+fieldCode, (Object)null);
			            }
			        });
				}else{
//	        		item.focusInItem();
//					autoShowCbiValWin(lg, context, fieldName, fieldCode, searchClass, searchArr, winTitle, pageSqls);
				}
			}
		}else{
			if("Escape".equals(codeVal)){
				lg.removeData(context.getEditedRecord());
			}
		}
	}
	

	public static void checkCbiValWin_LG(final FormItem item,final ListGrid lg,final ListGridEditorContext context,final String fieldName,final String fieldCode,
			final Class searchClass,final ArrayList<PageSql> pageSqls,String codeVal){


		if(item.getValue()!=null&&item.getValue().toString().length()>0){
			String enterVal=item.getValue().toString().trim();
			if(codeVal==null){
				SetCommonFilter.deleteExistSql(pageSqls, new String[]{fieldName});
		        pageSqls.add(new PageSql.Builder(fieldName, "=").content(
		        		context.getEditedRecord().getAttribute("UUID")).sqlType("T").build());
//		        SetCommonFilter.addComValSql(pageSql, searchClass);
				Page page=new Page.Builder().pageSql(pageSqls).maxResults(10).build();

		        CommonRemote.Util.getInstance().commonSearch(page,searchClass.getName(), new DefaultCallback<ArrayList>() {
	
		            @Override
		            public void onSuccess(ArrayList result) {
		            	if(result!=null&&result.size()==1){
		            		PojoCastRecord.toListGridRecord(result.get(0), context.getEditedRecord(), "AutoComplete");
		            		lg.refreshRow(context.getRowNum());
//		        			lg.endEditing();
		        			context.getEditedRecord().setAttribute("checkPassServer"+fieldCode, true);
		            	}else{
		        			context.getEditedRecord().setAttribute("checkPassServer"+fieldCode, false);
		        		}
	        			context.getEditedRecord().setAttribute("codeVal"+fieldCode, (Object)null);
		            }
		        });
		        
			}else{
				if(codeVal.length()>0 && enterVal.length()>0){
					context.getEditedRecord().setAttribute("codeVal"+fieldCode, codeVal);
//			        OpenWindows.getLoadingWin("checkCbiVal_win");
			        ArrayList<PageSql> pageSql = new ArrayList<PageSql>();
			        pageSql.add(new PageSql.Builder(fieldCode, "=").content(codeVal).build());
			        pageSql.add(new PageSql.Builder(fieldName, "=").content(enterVal).build());
			        SetCommonFilter.addComValSql(pageSql, searchClass);
					Page page=new Page.Builder().pageSql(pageSql).maxResults(10).build();
			        CommonRemote.Util.getInstance().commonSearch(page,searchClass.getName(),new DefaultCallback<ArrayList>() {
		
			            @Override
			            public void onSuccess(ArrayList result) {
//			        		closeWin("checkCbiVal_win");
			            	if(result.size()==1){
			            		PojoCastRecord.toListGridRecord(result.get(0), context.getEditedRecord(), "AutoComplete");
			            		lg.refreshRow(context.getRowNum());
//			        			lg.endEditing();
			        			context.getEditedRecord().setAttribute("checkPassServer"+fieldCode, true);
			            	}else{
			        			context.getEditedRecord().setAttribute("checkPassServer"+fieldCode, false);
//			            		item.focusInItem();
//			        			autoShowCbiValWin(lg, context, fieldName, fieldCode, searchClass, searchArr, winTitle, pageSqls);
			            	}
		        			context.getEditedRecord().setAttribute("codeVal"+fieldCode, (Object)null);
			            }
			        });
				}
			}
		}
	}
//	public static void autoShowCbiValWin(final ListGrid lg,final ListGridEditorContext context,final String fieldName,final String fieldCode,
//			final Class searchClass,final String[] searchArr,final String winTitle,final ArrayList<PageSql> pageSqls){
//
//		ListGrid winLg = SearchLgWin.show(searchClass, "search_win"+fieldCode, searchArr, "SINGLE",
//		pageSqls,winTitle);
//		winLg.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
//			@Override
//			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
//				lgOkBtnClick(context, lg, fieldCode, fieldName);
//            }
//        });
////		((DynamicForm)Window.getById("search_win").getChildren()[1].getChildren()[0].getChildren()[2]).focusInItem(0);
//		
//	}
	public static void lgOkBtnClick(ListGridEditorContext context,ListGrid lg,String fieldCode,String fieldName){
        if(SearchLgWin.getSelectLgrs()!=null&&SearchLgWin.getSelectLgrs().length>0){
            ListGridRecord lgr = SearchLgWin.getSelectLgrs()[0];
			context.getEditedRecord().setAttribute("codeVal"+fieldCode, lgr.getAttribute(fieldCode));
            context.getEditedRecord().setAttribute(fieldName, lgr.getAttribute(fieldName));
            context.getEditedRecord().setAttribute(fieldCode, lgr.getAttribute(fieldCode));
            
            lg.refreshCell(lg.getRecordIndex(context.getEditedRecord()), lg.getFieldNum(fieldName));
            OpenWindows.closeWin("search_win"+fieldCode);
        }else{
        	MessageUI.setMsgInfo("请选择一条数据！");
        }
	}

    public static void closeWin(String winId) {
        if (Canvas.getById(winId) != null) {
            Canvas.getById(winId).destroy();
        }
    }
    
    public static Window getWinCloseHide(String title, Integer winWidth, Integer winHeight, Layout layout, String winId) {
        final Window editWin = new Window();
        if (winId != null) {
            editWin.setID(winId);
        }
        editWin.setWidth(winWidth);
        editWin.setHeight(winHeight);
        editWin.setTitle(title);
        editWin.setShowMinimizeButton(false);
        editWin.setIsModal(true);
        editWin.setShowModalMask(true);
        editWin.centerInPage();
        editWin.addCloseClickHandler(new CloseClickHandler() {

            @Override
			public void onCloseClick(CloseClickEvent event) {
                editWin.hide();
            }
        });
        editWin.addItem(layout);
        editWin.show();
        return editWin;
    }
    
    public static Window getLoadingWin(String winId){
        final Window editWin = new Window();
        editWin.setIsModal(true);
        editWin.setShowModalMask(true);
        if (winId != null&&Window.getById("winId")==null) {
            editWin.setID(winId);
        }
        editWin.setWidth(100);
        editWin.setHeight(22);
        editWin.setTitle("验证中……");
        editWin.setShowMinimizeButton(false);
        editWin.setShowCloseButton(false);
        editWin.centerInPage();
        editWin.show();
        return editWin;
    	
    }

    /**
     * 弹出窗体，可设置标题以及附加布局，默认长度760、高度500
     *
     * @param title 标题
     * @param layout 布局
     * @return
     */
    public static Window getEditWin(String title, Layout layout) {
        return getEditWin(title, 760, 500, layout);
    }

    /**
     * 弹出窗体，可设置标题、长度、高度，以及附加布局
     *
     * @param title 标题
     * @param winWidth 长度
     * @param winHeight 高度
     * @param layout 布局
     * @return
     */
    public static Window getEditWin(String title, Integer winWidth, Integer winHeight, Layout layout) {
        return getEditWin(title, winWidth, winHeight, layout, null);
    }

    public static Window getEditWin(String title, Integer winWidth, Integer winHeight, Layout layout, String winId) {
        final Window editWin = new Window();
        if (winId != null) {
            editWin.setID(winId);
        }
        editWin.setWidth(winWidth);
        editWin.setHeight(winHeight);
        editWin.setTitle(title);
        editWin.setShowMinimizeButton(false);
        //下层UI禁用
        editWin.setIsModal(true);
        //背景变灰
        editWin.setShowModalMask(true);
        editWin.centerInPage();
        editWin.addCloseClickHandler(new CloseClickHandler() {

            @Override
			public void onCloseClick(CloseClickEvent event) {
                //销毁窗体
                editWin.destroy();
            }
        });
        //在窗体内添加布局
        editWin.addItem(layout);
        editWin.show();
        return editWin;
    }

    /**
     * 弹窗，默认长度760、高度500，附加功能按钮在标题框上
     *
     * @param title 标题
     * @param layout 内容布局
     * @param btns 功能按钮数组
     * @return
     */
    public static Window getWinWithHeaderControl(String title, Layout layout, IButton[] btns) {
        return getWinWithHeaderControl(title, 760, 500, layout, btns);//header
    }

    /**
     * 弹窗，默认长度760、高度500，附加功能按钮在底部状态栏上
     *
     * @param title 标题
     * @param layout 内容布局
     * @param btns 功能按钮数组
     * @return
     */
    public static Window getWinWithFooterControl(String title, Layout layout, IButton[] btns) {
        return getWinWithFooterControl(title, 760, 500, layout, btns);//footer
    }

    public static Window getWinWithFooterControl(String title, Layout layout, IButton[] btns, String winId) {
        return getWinWithFooterControl(title, 760, 500, layout, btns, winId);//footer
    }

    public static Window getWinWithHeaderFooterControl(String title, Integer winWidth, Integer winHeight, Layout layout, IButton[] headerBtns, IButton[] footerBtns) {
        return getWinWithHeaderFooterControl(title, winWidth, winHeight, layout, headerBtns, footerBtns, null);
    }

    public static Window getWinWithHeaderFooterControl(String title, Integer winWidth, Integer winHeight, Layout layout, IButton[] headerBtns, IButton[] footerBtns, String winId) {
        final Window editWin = new Window();
        if (winId != null) {
            editWin.setID(winId);
        }
        editWin.setTitle("");
        editWin.setWidth(winWidth);
        editWin.setHeight(winHeight);
        Label titleLab = new Label("<b>" + title + "</b>");
        titleLab.setWidth(60);
        titleLab.setHeight(15);
        if (headerBtns != null && headerBtns.length > 0) {
            Object[] headerControls = new Object[headerBtns.length * 2 + 3];
            int i = 1;
            headerControls[0] = titleLab;
            for (IButton btn : headerBtns) {
                headerControls[i] = btn;
                i++;
                headerControls[i] = getBtnSpace();
                i++;
            }
            headerControls[headerBtns.length * 2 + 1] = HeaderControls.HEADER_LABEL;
            headerControls[headerBtns.length * 2 + 2] = HeaderControls.CLOSE_BUTTON;
            editWin.setHeaderControls(headerControls);
        }
        if (footerBtns != null && footerBtns.length > 0) {
            editWin.setShowFooter(true);
            Object[] footerControls = new Object[footerBtns.length * 2 + 2];
            int i = 1;
            footerControls[0] = FooterControls.SPACER;
            for (IButton btn : footerBtns) {
                footerControls[i] = btn;
                i++;
                footerControls[i] = getBtnSpace();
                i++;
            }
            editWin.setFooterControls(footerControls);
        }
        //下层UI禁用
        editWin.setIsModal(true);
        //背景变灰
        editWin.setShowModalMask(true);
        editWin.centerInPage();
        editWin.addCloseClickHandler(new CloseClickHandler() {

            @Override
			public void onCloseClick(CloseClickEvent event) {
                //销毁窗体
                editWin.destroy();
            }
        });
        //在窗体内添加布局
        editWin.addItem(layout);
        editWin.show();
        return editWin;
    }

    /**
     * 弹窗，长度、高度自定义，附加功能按钮在标题框上
     *
     * @param title
     * @param winWidth
     * @param winHeight
     * @param layout
     * @param btns
     * @return
     */
    public static Window getWinWithHeaderControl(String title, Integer winWidth, Integer winHeight, Layout layout, IButton[] btns) {
        final Window editWin = new Window();
        editWin.setTitle(title);
        editWin.setWidth(winWidth);
        editWin.setHeight(winHeight);
        if (btns != null && btns.length > 0) {
            Object[] headerControls = new Object[btns.length * 2 + 2];
            int i = 1;
            headerControls[0] = HeaderControls.HEADER_LABEL;
            for (IButton btn : btns) {
                headerControls[i] = btn;
                i++;
                headerControls[i] = getBtnSpace();
                i++;
            }
            headerControls[btns.length * 2 + 1] = HeaderControls.CLOSE_BUTTON;
            editWin.setHeaderControls(headerControls);
        }
        //下层UI禁用
        editWin.setIsModal(true);
        //背景变灰
        editWin.setShowModalMask(true);
        editWin.centerInPage();
        editWin.addCloseClickHandler(new CloseClickHandler() {

            @Override
			public void onCloseClick(CloseClickEvent event) {
                //销毁窗体
                editWin.destroy();
            }
        });
        //在窗体内添加布局
        editWin.addItem(layout);
        editWin.show();
        return editWin;
    }

    public static Window getWinWithFooterControl(String title, Integer winWidth, Integer winHeight, Layout layout, IButton[] btns) {
        return getWinWithFooterControl(title, winWidth, winHeight, layout, btns, null);
    }  
    /**
     * 另一种新增和修改展示的方法，平滑进入输入界面，不禁用背景
     * @param title
     * @param mlg
     * @param layout
     * @param btns
     */
	public static void showWinWithHeaderToProductionLine(final String title,final ListGrid mlg, Layout layout,IButton[] btns) {
		String winId=mlg.getID();
		if (Window.getById("win"+winId) == null) {

			final VLayout maimLay = (VLayout) mlg.getParentElement();
			final Canvas opCancas = maimLay.getMember(0);
			final Layout tabLay=(Layout) maimLay.getParentElement().getParentElement();

			final Window editWin = new Window();
			editWin.setID("win"+winId);
			editWin.setWidth100();
			editWin.setHeight100();
			editWin.setShowCloseButton(false);
	        editWin.addItem(layout);
	        editWin.setTitle(title);
			if (btns != null && btns.length > 0) {
				int nullBtn=0;
				for (IButton btn : btns) {
					if(btn==null){
						nullBtn++;
					}
				}
				Object[] headerControls = new Object[(btns.length-nullBtn) * 2 + 2];
				int i = 1;
				headerControls[0] = HeaderControls.HEADER_LABEL;
				for (IButton btn : btns) {
					if(btn!=null){
						headerControls[i] = btn;
						i++;
						headerControls[i] = getBtnSpace();
						i++;
					}
				}
				headerControls[(btns.length-nullBtn) * 2 + 1] = MyButton.getBackBtn(opCancas, editWin,tabLay);
				editWin.setHeaderControls(headerControls);
			}else{
				editWin.setHeaderControls(HeaderControls.HEADER_LABEL, MyButton.getBackBtn(opCancas, editWin,tabLay));
			}
			editWin.setCanDragReposition(false);

			opCancas.animateHide(AnimationEffect.SLIDE,
					new AnimationCallback() {

						@Override
						public void execute(boolean earlyFinish) {
							tabLay.getMember(0).hide();
							maimLay.addChild(editWin);
							editWin.setHeight(editWin.getHeight()+50);
						}
					});
			editWin.addKeyPressHandler(new KeyPressHandler() {

				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName()!=null&&event.getKeyName().equals("Escape")) {
//						LayManager.hideTabLay(opCancas, editWin, tabLay);
					}
				}
			});
		}
	}
    
    /**
     * 另一种新增和修改展示的方法，平滑进入输入界面，不禁用背景
     * @param title
     * @param mlg
     * @param layout
     * @param btns
     */
	public static void showWinWithHeaderControl(final String title,final ListGrid mlg, Layout layout,IButton[] btns) {
		String winId=mlg.getID();
		if (Window.getById("win"+winId) == null) {

			final VLayout maimLay = (VLayout) mlg.getParentCanvas();
			final Canvas opCancas = maimLay.getMember(0);
			final Layout tabLay=(Layout) maimLay.getParentCanvas().getParentCanvas();

			final Window editWin = new Window();
			editWin.setID("win"+winId);
			editWin.setWidth100();
			editWin.setHeight100();
//			editWin.setHeight(tabLay.getHeight()-50);
			editWin.setShowCloseButton(false);
	        editWin.addItem(layout);
	        editWin.setTitle(title);
			if (btns != null && btns.length > 0) {
				int nullBtn=0;
				for (IButton btn : btns) {
					if(btn==null){
						nullBtn++;
					}
				}
				Object[] headerControls = new Object[(btns.length-nullBtn) * 2 + 2];
				int i = 1;
				headerControls[0] = HeaderControls.HEADER_LABEL;
				for (IButton btn : btns) {
					if(btn!=null){
						headerControls[i] = btn;
						i++;
						headerControls[i] = getBtnSpace();
						i++;
					}
				}
				headerControls[(btns.length-nullBtn) * 2 + 1] = MyButton.getBackBtn(opCancas, editWin,tabLay);
				editWin.setHeaderControls(headerControls);
			}else{
				editWin.setHeaderControls(HeaderControls.HEADER_LABEL, MyButton.getBackBtn(opCancas, editWin,tabLay));
			}
			editWin.setCanDragReposition(false);

			opCancas.animateHide(AnimationEffect.SLIDE,
					new AnimationCallback() {

						@Override
						public void execute(boolean earlyFinish) {
							LayManager.showTabLay(maimLay, editWin, tabLay);
						}
					});
			editWin.addKeyPressHandler(new KeyPressHandler() {

				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName()!=null&&event.getKeyName().equals("Escape")) {
//						LayManager.hideTabLay(opCancas, editWin, tabLay);
					}
				}
			});
		}
	}

	public static void showWinWithHeaderControl2(final String title,final ListGrid mlg, Layout layout,IButton[] btns) {
		String winId=mlg.getID();
		if (Window.getById("win"+winId) == null) {

			final Layout maimLay = (Layout) mlg.getParentCanvas().getParentCanvas();
			final Canvas opCancas = maimLay.getMember(0);
			final Layout tabLay=(Layout) maimLay.getParentCanvas().getParentCanvas();
			final Window editWin = new Window();
			editWin.setID("win"+winId);
			editWin.setWidth100();
			editWin.setHeight100();
//			editWin.setHeight(tabLay.getHeight()-50);
			editWin.setShowCloseButton(false);
	        editWin.addItem(layout);
	        editWin.setTitle(title);
			if (btns != null && btns.length > 0) {
				int nullBtn=0;
				for (IButton btn : btns) {
					if(btn==null){
						nullBtn++;
					}
				}
				Object[] headerControls = new Object[(btns.length-nullBtn) * 2 + 2];
				int i = 1;
				headerControls[0] = HeaderControls.HEADER_LABEL;
				for (IButton btn : btns) {
					if(btn!=null){
						headerControls[i] = btn;
						i++;
						headerControls[i] = getBtnSpace();
						i++;
					}
				}
				headerControls[(btns.length-nullBtn) * 2 + 1] = MyButton.getBackBtn(opCancas, editWin,tabLay);
				editWin.setHeaderControls(headerControls);
			}else{
				editWin.setHeaderControls(HeaderControls.HEADER_LABEL, MyButton.getBackBtn(opCancas, editWin,tabLay));
			}
			editWin.setCanDragReposition(false);

			opCancas.animateHide(AnimationEffect.SLIDE,
					new AnimationCallback() {

						@Override
						public void execute(boolean earlyFinish) {
							LayManager.showTabLay2(maimLay, editWin, tabLay);
						}
					});
			editWin.addKeyPressHandler(new KeyPressHandler() {

				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName()!=null&&event.getKeyName().equals("Escape")) {
//						LayManager.hideTabLay(opCancas, editWin, tabLay);
					}
				}
			});
		}
	}
    /**
     * 弹窗，长度、高度自定义，附加功能按钮在底部状态栏上
     *
     * @param title
     * @param winWidth
     * @param winHeight
     * @param layout
     * @param btns
     * @return
     */
    public static Window getWinWithFooterControl(String title, Integer winWidth, Integer winHeight, Layout layout, IButton[] btns, String winId) {
        final Window editWin = new Window();
        if (winId != null) {
            editWin.setID(winId);
            closeWin(winId);
        }
        editWin.setWidth(winWidth);
        editWin.setHeight(winHeight);
        if (btns != null && btns.length > 0) {
            editWin.setShowFooter(true);
            Object[] footerControls = new Object[btns.length * 2 + 2];
            int i = 1;
            footerControls[0] = FooterControls.SPACER;
            for (IButton btn : btns) {
                footerControls[i] = btn;
                i++;
                footerControls[i] = getBtnSpace();
                i++;
            }
            editWin.setFooterControls(footerControls);
        }
        editWin.setTitle(title);
        editWin.setShowMinimizeButton(false);
        //下层UI禁用
        editWin.setIsModal(true);
        //背景变灰
        editWin.setShowModalMask(true);
        editWin.centerInPage();
        editWin.addCloseClickHandler(new CloseClickHandler() {

            @Override
			public void onCloseClick(CloseClickEvent event) {
                //销毁窗体
                editWin.destroy();
            }
        });
        //为了弹窗获取焦点
        DynamicForm f=new DynamicForm();
        TextItem ti=new TextItem("ti","");
        f.setItems(ti);
        layout.addMember(f);
        //在窗体内添加布局
        editWin.addItem(layout);
        editWin.show();
        ti.focusInItem();
        f.hide();
        return editWin;
    }

    public static HLayout getBtnSpace() {
        HLayout btnSpace = new HLayout();
        btnSpace.setHeight(10);
        btnSpace.setWidth(6);
        return btnSpace;
    }

}
