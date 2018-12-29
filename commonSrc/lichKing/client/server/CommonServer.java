/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lichKing.client.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lichKing.client.datasource.AppDataMap;
import lichKing.client.entity.BaseDomain;
import lichKing.client.entityAnnotation.EntityAnn;
import lichKing.client.entityAnnotation.MyClassTypeUtil;
import lichKing.client.myException.DefaultCallback;
import lichKing.client.pojo.Page;
import lichKing.client.pojo.PageSql;
import lichKing.client.pojo.TB;
import lichKing.client.remote.CommonRemote;
import lichKing.client.ui.msg.MessageUI;
import lichKing.client.ui.myExtend.OpenWindows;
import lichKing.client.utils.LayManager;
import lichKing.client.utils.RecordCastPojo;
import lichKing.client.utils.ResultBuild;
import lichKing.client.utils.SetCommonFilter;

import com.gwtent.reflection.client.Field;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * 客户端通用server，基于CommonRemote
 * @author catPan
 */
public class CommonServer {

    @SuppressWarnings("rawtypes")
	public static void mainLgSeach(final Page page, String domainClassName, final ListGrid listGrid) {
        listGrid.setData(new ListGridRecord[0]);
        listGrid.setEmptyMessage(AppDataMap.getLoadingEmptyMessage());
        CommonRemote.Util.getInstance().commonSearch(page, domainClassName, new DefaultCallback<ArrayList>() {

            @Override
            public void onSuccess(ArrayList result) {
                ResultBuild.buildListGrid(result, listGrid);
                listGrid.setPrompt(null);
            }
        });
    }
    
     @SuppressWarnings("rawtypes")
	public static void searchPageAll(ArrayList<PageSql> pageSqls, String domainClassName, final ListGrid listGrid) {
        listGrid.setData(new ListGridRecord[0]);
        listGrid.setEmptyMessage(AppDataMap.getLoadingEmptyMessage());
    	SetCommonFilter.addComValSql(pageSqls,domainClassName);
		Page page=new Page.Builder().pageSql(pageSqls).maxResults(300).build();
        CommonRemote.Util.getInstance().commonSearch(page,domainClassName, new DefaultCallback<ArrayList>() {

            @Override
            public void onSuccess(ArrayList result) {
                ResultBuild.buildListGrid(result, listGrid);
            }
        });
    }
    
     @SuppressWarnings("rawtypes")
	public static void searchPageAllRO(ArrayList<PageSql> pageSqls, String domainClassName, final ListGrid listGrid) {
        listGrid.setData(new ListGridRecord[0]);
        listGrid.setEmptyMessage(AppDataMap.getLoadingEmptyMessage());
    	SetCommonFilter.addComValSql(pageSqls,domainClassName);
		Page page=new Page.Builder().pageSql(pageSqls).maxResults(300).build();
        CommonRemote.Util.getInstance().commonSearch(page,domainClassName, new DefaultCallback<ArrayList>() {

            @Override
            public void onSuccess(ArrayList result) {
                ResultBuild.buildListGridRO(result, listGrid);
            }
        });
    }

    @SuppressWarnings("rawtypes")
	public static void pageSeach(final Page page, String domainClassName, final ListGrid listGrid, final Label pageInfoLb, final String classPage) {
        final long startTime = new Date().getTime();
        listGrid.setData(new ListGridRecord[0]);
        listGrid.setEmptyMessage(AppDataMap.getLoadingEmptyMessage());
        pageInfoLb.setContents(AppDataMap.getLoadingEmptyMessage());
        
        CommonRemote.Util.getInstance().commonSearch(page, domainClassName, new DefaultCallback<ArrayList>() {

            @Override
            public void onSuccess(ArrayList result) {
                ResultBuild.buildListGrid(result, listGrid);
                int pageNum = result.size();
                if (pageNum == 0 || pageNum < page.getMaxResults()) {
                    Canvas.getById(classPage + "nextPage").disable();
                } else {
                    Canvas.getById(classPage + "nextPage").enable();
                }
                pageInfoLb.setContents(getPageInfo(page, startTime, pageNum));
            }
        });
    }

    public static String getPageInfo(Page page, long startTime, int pageNum) {
        return "<font color='green' size='2'><image src='images/btnPNG/32/book.png' style=';margin-right: 2px;width:20px;height:20px;'/>第 <b>"
        		+ (page.getFirstResult() / page.getMaxResults() + 1) + "</b> 页"
                + "<image src='images/btnPNG/32/bookLine.png' style='margin-left: 20px;margin-right: 2px;width:20px;height:20px;'/>第 <b>"
                + (pageNum == 0 ? ("") : (page.getFirstResult() + 1) + "~" + (page.getFirstResult() + pageNum)) + "</b> 条"
                + "<image src='images/btnPNG/32/clock.png' style='margin-left: 20px;;margin-right: 2px;width:20px;height:20px;'/>用时 <b>"
                + (new Date().getTime() - startTime) / 1000.0 + "</b> 秒</font>";
    }

	public static void disableLgData(final Class domainClass, final ListGrid listGrid,final String field,final String val,String msgStr) {
		if(msgStr==null||msgStr.length()==0){
	    	if(val.equals("0")){
	    		if(field.equals("VALD_FLAG")){
	    			msgStr="删除";
	    		}else{
	    			msgStr="禁用";
	    		}
	    	}else if(val.equals("4")){
	    		msgStr="作废";
	    	}else if(val.equals("1")){
	    		msgStr="启用";
	    	}
		}
		final String tempMsg=msgStr;
        if (listGrid.getSelectedRecords().length == 0) {
            MessageUI.setMsgInfo("请选择要操作的数据行！");
        } else {
            SC.confirm("提示", "您确定要 <b>"+msgStr+"</b> 选中的记录吗？", new BooleanCallback() {

                public void execute(Boolean value) {
                    if (value != null && value) {
                    	boolean noField=false;
                    	if(field.equals("VALD_FLAG")){

                            String sql = RecordCastPojo.getPojoDisableSql(domainClass, listGrid,field,val);
                            if (sql.length() > 10) {
                                CommonRemote.Util.getInstance().batchDeleteDomains(sql, new DefaultCallback<Void>() {

                                    @Override
                                    public void onSuccess(Void result) {
                                		ResultBuild.deleteSelectListGrid(listGrid);
                                		MessageUI.setMsgInfo(tempMsg+"成功");
                                    }
                                });
                            }
                    	}else{
	                    	if(listGrid.getFieldNum(field)!=-1){
	                        	ListGridRecord[] lgrs=listGrid.getSelectedRecords();

                        		if(field==null||field.length()==0){
    	                        	for(ListGridRecord lgr:lgrs){// 剔除要修改字段的值，和修改后一样的数据
    	                        		if(lgr.getAttribute(field).equals(val)){
    	                        			listGrid.deselectRecord(lgr);
    	                        		}
    	                        	}
                        		}else{
                        			String toType="doc_disabled.png";
    								if("0".equals(val)){
    									toType="doc_enabled.png";
    								}else if("4".equals(val)){
    									toType="doc_disabled.png";
    								}
                                	for(ListGridRecord lgr:lgrs){// 剔除
        								if(toType.equals(lgr.getAttribute("DATA_SOURCE"))){
    	                        			listGrid.deselectRecord(lgr);
        								}
                                	}
                        		}
	                            String sql = RecordCastPojo.getPojoDisableSql(domainClass, listGrid,field,val);
	                            if (sql.length() > 10) {
	                                CommonRemote.Util.getInstance().batchDeleteDomains(sql, new DefaultCallback<Void>() {
	
	                                    @Override
	                                    public void onSuccess(Void result) {
	                                    	updateCell(listGrid, field, val, tempMsg);
	                                    }
	                                });
	                            }else{
		                    		MessageUI.setMsgInfo("没有可操作的数据字段");
	                            }
	                    		
	                    	}else{
	                    		MessageUI.setMsgInfo("没有"+field+"字段");
	                    	}
                    	}
                    }
                }
            });
        }
	}


	public static void disableLgDataByDm(final Class domainClass, final ListGrid lg,
			final String field,final String val,final String msgStr) {
        SC.confirm("提示", "您确定要 <b>"+msgStr+"</b> 选中的记录吗？", new BooleanCallback() {

            public void execute(Boolean value) {
                if (value != null && value) {
                    Field[] fields = MyClassTypeUtil.getDomainClassFields(domainClass);
                    boolean hasAnnotation = false;
                    EntityAnn annotation;

                    boolean hadField=false;
                    for (int i = 0; i < fields.length; i++) {
                        hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
                        if (hasAnnotation) {
                            annotation = fields[i].getAnnotation(EntityAnn.class);
                            if(field.equals(annotation.ResourceKey())){
                            	hadField=true;
                            	break;	
                            }
                        }
                    }
                	
					if(hadField){
						ListGridRecord[] lgrs=lg.getSelectedRecords();
						for(ListGridRecord r:lgrs){
							r.setAttribute(field, val);
						}
						List<BaseDomain> dms=RecordCastPojo.toPojos(domainClass, lgrs);
					    CommonRemote.Util.getInstance().
					    batchAddOrModifyNoReturn(dms, 
					    		new DefaultCallback<Void>() {

					        @Override
					        public void onSuccess(Void result) {
					        	lg.removeSelectedData();
						        MessageUI.setMsgInfo(msgStr+" 成功");
					        }
					    });
					}else{
						MessageUI.setMsgInfo("没有 "+field+" 字段");
					}
                }
            }
        });
	}
	public static void updateCell(ListGrid listGrid,String field,String val,String tempMsg){

    	int fN=listGrid.getFieldNum(field);
//                                    	if(val.equals("0")){
//                                    		ResultBuild.deleteSelectListGrid(listGrid);
//                                    		MessageUI.setMsgInfo("删除成功");
//                                    	}else{
    		ListGridRecord[] lgrs=listGrid.getSelectedRecords();

    		if(listGrid.getField(fN).getType()==null||!listGrid.getField(fN).getType().equals(ListGridFieldType.IMAGE)){
            	for(ListGridRecord lgr:lgrs){// 更新Lg UI数据
            		lgr.setAttribute(field, val);
            		listGrid.refreshCell(listGrid.getRecordIndex(lgr),fN);
            	}
    		}else{
            	for(ListGridRecord lgr:lgrs){// 更新Lg UI数据，icon
					if("0".equals(val)){
                		lgr.setAttribute(field, "doc_enabled.png");
                		if(listGrid.getField("PLAN_DATE")!=null){
                			listGrid.getField("PLAN_DATE").setCanEdit(true);
                		}
						listGrid.refreshFields();
					}else if("4".equals(val)){
                		lgr.setAttribute(field, "doc_disabled.png");
                		if(listGrid.getField("PLAN_DATE")!=null){
                			listGrid.getField("PLAN_DATE").setCanEdit(false);
                		}
                		listGrid.endEditing();
					}
            		listGrid.refreshCell(listGrid.getRecordIndex(lgr),fN);
            	}
    		}
    		MessageUI.setMsgInfo(tempMsg+"成功");
//                                    	}
	}
	
	public static void disableLgData(final Class domainClass, final ListGrid listGrid,final String field,final String val) {
		disableLgData(domainClass, listGrid, field, val,"");
	}
    /**
     * ListGrid数据不可用的通用方法，VALD_FLAG=0
     * @param domainClass 用于获取类的主键
     * @param listGrid 列表
     */
    @SuppressWarnings("rawtypes")
	public static void disableListGridData(final Class domainClass, final ListGrid listGrid) {
    	disableListGridData(domainClass, listGrid, "0");
    }

    /**
     * ListGrid数据状态VALD_FLAG，修改的通用方法，禁用为4
     * @param domainClass 用于获取类的主键
     * @param listGrid 列表
     * @param val VALD_FLAG的值
     */
	public static void disableListGridData(final Class domainClass, final ListGrid listGrid,final String val) {
		disableLgData(domainClass, listGrid, "VALD_FLAG", val);
	}
    /**
     * ListGrid删除数据的通用方法
     * @param domainClass 用于获取类的主键
     * @param listGrid 列表
     */
    @SuppressWarnings("rawtypes")
	public static void deleteListGridData(final Class domainClass, final ListGrid listGrid) {
        if (listGrid.getSelectedRecords().length == 0) {
        	MessageUI.setMsgInfo("请选择要删除的行.");
        } else {
            SC.confirm("提示", "您确定要删除选中的记录吗？", new BooleanCallback() {

                public void execute(Boolean value) {
                    if (value != null && value) {
                        String sql = RecordCastPojo.getPojoDeleteSql(domainClass, listGrid);
                        if (sql.length() > 10) {
                            CommonRemote.Util.getInstance().batchDeleteDomains(sql, new DefaultCallback<Void>() {

                                @Override
                                public void onSuccess(Void result) {
                                    ResultBuild.deleteSelectListGrid(listGrid);
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    /**
     * 审批业务ListGrid删除数据的通用方法，会剔除审批中的数据，也就是审批中的或者以后的数据都不能删除
     * @param domainClass 用于获取类的主键
     * @param listGrid 列表
     */
    @SuppressWarnings("rawtypes")
	public static void deleteLGDataCheckApprovalState(final Class domainClass, final ListGrid listGrid) {
        if (listGrid.getSelectedRecords().length == 0) {
        	MessageUI.setMsgInfo("请选择要删除的行.");
        } else {
            SC.confirm("提示", "您确定要删除选中的记录吗？", new BooleanCallback() {

                public void execute(Boolean value) {
                    if (value != null && value) {
                        final int beforeDelete = listGrid.getSelectedRecords().length;
                        String sql = RecordCastPojo.getPojoDeleteSqlNoApproval(domainClass, listGrid);
                        if (sql.length() > 10) {
                            CommonRemote.Util.getInstance().batchDeleteDomains(sql, new DefaultCallback<Void>() {

                                @Override
                                public void onSuccess(Void result) {
                                    if (beforeDelete > listGrid.getSelectedRecords().length) {
                                    	MessageUI.setMsgInfo("有部分审批中的数据无法删除！");
                                    }
                                    ResultBuild.deleteSelectListGrid(listGrid);
                                    listGrid.setEmptyMessage(MsgServer.getMsg("No_data"));
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    /**
     * 删除ListGrid中所有数据的通用方法
     * @param domainClass 用于获取类的主键
     * @param listGrid 列表
     */
    @SuppressWarnings("rawtypes")
	public static void deleteListGridAllData(final Class domainClass, final ListGrid listGrid) {
        if (listGrid.getRecords().length > 0) {
            String sql = RecordCastPojo.getPojoDeleteSql(domainClass, listGrid);
            if (sql.length() > 10) {
                CommonRemote.Util.getInstance().batchDeleteDomains(sql, new DefaultCallback<Void>() {

                    @Override
                    public void onSuccess(Void result) {
                        //
                    }
                });
            }
        }
    }

    /**
     * 保存实体类，无返回值，没有提示语句
     * @param domain 
     */
    public static void saveDomainNoReturn(BaseDomain domain) {
        saveDomainNoReturn(domain, "");
    }

    /**
     * 保存实体类，无返回值，可自定义提示语句
     * @param domain 要保存的实体类
     * @param message 提示语句
     */
    public static void saveDomainNoReturn(BaseDomain domain, final String message) {
        CommonRemote.Util.getInstance().addOrModifyNoReturn(domain, new DefaultCallback<Void>() {

            @Override
            public void onSuccess(Void result) {
                if (message.length() > 0) {
                	MessageUI.setMsgInfo(message);
                }
            }
        });
    }

    /**
     * 批量删除后批量新增（如角色权限，删掉旧的权限后，生成新的权限）
     * @param tb 要删除的数据，tb对象包括表名、列和列的值，构成sql的删除语句
     * @param domains 要保存的对List
     */
    public static void batchDeleteThenBatchAdd(TB tb, List<BaseDomain> domains) {
        CommonRemote.Util.getInstance().batchDeleteThenBatchAdd(tb, domains, new DefaultCallback<Void>() {

            @Override
            public void onSuccess(Void result) {
                super.onSuccess(result);
            }
        });
    }
    
    public static void addToLGThenCloseWin(BaseDomain domain, final ListGrid lg,final String winId) {
        CommonRemote.Util.getInstance().addOrModify(domain, new DefaultCallback<BaseDomain>() {

            @Override
            public void onSuccess(BaseDomain result) {
                super.onSuccess(result);
                ResultBuild.addToListGrid(result, lg);
                if(winId!=null&&winId.length()>0){
                    OpenWindows.closeWin(winId);
                }
            }
        });
    }

//    /**
//     * 多页签，新增操作时，返回主数据到主页列表，或者重置表单和列表
//     * @param dataMaps 要持久化的数据
//     * @param lg 主页列表
//     * @param tabSet 获取tab页签中的表单和列表
//     */
//    public static void addToLG(TabsInfoData dataMaps,final ListGrid lg,final TabSet tabSet) {
//    	if(dataMaps!=null){
//	        SysUserRemote.Util.getInstance().batchAddOrModify(dataMaps, new DefaultCallback<BaseDomain>() {
//	
//	            @Override
//	            public void onSuccess(BaseDomain result) {
//	                super.onSuccess(result);
//	                ResultBuild.addToListGrid(result, lg);
//					LayManager.hideTabLay(lg);
//	            }
//	        });
//    	}else{
//    		MessageUI.setMsgInfo("请完善数据");
//    	}
//    }
//
//    public static void addToLgAgain(TabsInfoData dataMaps,final ListGrid lg,final TabSet tabSet,final DynamicForm _editForm) {
//    	if(dataMaps!=null){
//	        SysUserRemote.Util.getInstance().batchAddOrModify(dataMaps, new DefaultCallback<BaseDomain>() {
//	
//	            @Override
//	            public void onSuccess(BaseDomain result) {
//	                super.onSuccess(result);
//	                ResultBuild.addToListGrid(result, lg);
//	                clearForm(_editForm);
//	            }
//	        });
//    	}else{
//    		MessageUI.setMsgInfo("请完善数据");
//    	}
//    }
//    /**
//     * 多页签，修改操作时，返回主数据到主页列表，加载数据到表单和列表，或者重置表单和列表
//     * @param dataMaps 要持久化的数据
//     * @param lg 主页列表
//     * @param lgr 编辑的列表数据
//     * @param tabSet 获取tab页签中的表单和列表
//     */
//    public static void updateToLG(TabsInfoData dataMaps, final ListGrid lg, final ListGridRecord lgr,final TabSet tabSet) {
//    	if(dataMaps!=null){
//    		SysUserRemote.Util.getInstance().batchAddOrModify(dataMaps, new DefaultCallback<BaseDomain>() {
//	
//	            @Override
//	            public void onSuccess(BaseDomain result) {
//	                super.onSuccess(result);
//	                ResultBuild.updateToListGrid(result, lg, lgr);
//					LayManager.hideTabLay(lg);
//	            }
//	        });
//    	}else{
//    		MessageUI.setMsgInfo("请完善数据");
//    	}
//    }

    /**
     * 新增实体成功后，在ListGrid上新增一条记录
     * @param domain 要新增的实体
     * @param lg 列表
     */
    public static void addToLG(BaseDomain domain, final ListGrid lg) {
        CommonRemote.Util.getInstance().addOrModify(domain, new DefaultCallback<BaseDomain>() {

            @Override
            public void onSuccess(BaseDomain result) {
                super.onSuccess(result);
                ResultBuild.addToListGrid(result, lg);
				LayManager.hideTabLay(lg);
            }
        });
    }
    public static void addToLgAgain(BaseDomain domain, final ListGrid lg,final DynamicForm _editForm) {
        CommonRemote.Util.getInstance().addOrModify(domain, new DefaultCallback<BaseDomain>() {

            @Override
            public void onSuccess(BaseDomain result) {
                super.onSuccess(result);
                ResultBuild.addToListGrid(result, lg);
                clearForm(_editForm);
            }
        });
    }

    public static void clearForm(DynamicForm _editForm){
        _editForm.reset();
        _editForm.focusInItem(0);
    }
    
	/**
	 * 批量新增，新增页面为ListGrid，例如，猪只新增
	 * @param domains
	 * @param lg
	 */
    public static void addToLG(List<BaseDomain> domains, final ListGrid lg) {
        CommonRemote.Util.getInstance().batchAddOrModifyNoReturn(domains, new DefaultCallback<Void>() {

            @Override
            public void onSuccess(Void result) {
                super.onSuccess(result);
				LayManager.hideTabLay(lg);
            }
        });
    }
    public static void addToLgAgain(List<BaseDomain> domains, final ListGrid lg,final DynamicForm _editForm) {
        CommonRemote.Util.getInstance().batchAddOrModifyNoReturn(domains, new DefaultCallback<Void>() {

            @Override
            public void onSuccess(Void result) {
                super.onSuccess(result);
                clearForm(_editForm);
            }
        });
    }
    public static void addToLG(List<BaseDomain> domains,final BaseDomain dm, final ListGrid lg) {
        CommonRemote.Util.getInstance().batchAddOrModifyNoReturn(domains, new DefaultCallback<Void>() {

            @Override
            public void onSuccess(Void result) {
                super.onSuccess(result);
				LayManager.hideTabLay(lg);
                ResultBuild.addToListGrid(dm, lg);
            }
        });
    }
    public static void addToLgAgain(List<BaseDomain> domains,final BaseDomain dm,
    		final ListGrid lg,final DynamicForm _editForm) {
        CommonRemote.Util.getInstance().batchAddOrModifyNoReturn(domains, new DefaultCallback<Void>() {

            @Override
            public void onSuccess(Void result) {
                super.onSuccess(result);
                clearForm(_editForm);
                ResultBuild.addToListGrid(dm, lg);
            }
        });
    }
    /**
     * 修改实体成功后，在ListGrid上更新被修改的记录
     * @param domain 要修改的实体
     * @param lg 列表
     * @param lgr 被选中修改的行
     */
    public static void updateToLG(BaseDomain domain, final ListGrid lg, final ListGridRecord lgr) {
        CommonRemote.Util.getInstance().addOrModify(domain, new DefaultCallback<BaseDomain>() {

            @Override
            public void onSuccess(BaseDomain result) {
                super.onSuccess(result);
                ResultBuild.updateToListGrid(result, lg, lgr);
				LayManager.hideTabLay(lg);
            }
        });
    }
    public static void updateToLG(TB tb, BaseDomain domain, final ListGrid lg, final ListGridRecord lgr) {
        CommonRemote.Util.getInstance().cascadeUpdateItems(tb, domain, new DefaultCallback<BaseDomain>() {

            @Override
            public void onSuccess(BaseDomain result) {
                super.onSuccess(result);
                ResultBuild.updateToListGrid(result, lg, lgr);
            }
        });
    }

    public static void updateToLG(List<BaseDomain> domains, final ListGrid lg) {
        CommonRemote.Util.getInstance().batchAddOrModify(domains, new DefaultCallback<List<BaseDomain>>() {

            @Override
            public void onSuccess(List<BaseDomain> result) {
                super.onSuccess(result);
                for(ListGridRecord lgr:lg.getSelectedRecords()){
                	lg.removeData(lgr);
                }
                for(BaseDomain d:result){
                	ResultBuild.addToListGrid(d,lg);
                }
				LayManager.hideTabLay(lg);
            }
        });
    }
    public static void updateToLG(List<BaseDomain> delDms,List<BaseDomain> domains,
    		final BaseDomain dm, final ListGrid lg) {
    	domains.add(dm);
        CommonRemote.Util.getInstance().batchAddOrModifyNoReturn(delDms,domains, 
        		new DefaultCallback<Void>() {

            @Override
            public void onSuccess(Void result) {
                super.onSuccess(result);
                ResultBuild.updateToListGrid(dm, lg, lg.getSelectedRecord());

				LayManager.hideTabLay(lg);
            }
        });
    }
    /**
     * 一对多时，更新many端时，删除原有的，再保存新的
     * @param tb 要删除many端的数据格式
     * @param domain one端的实体
     * @param lg
     * @param lgr 
     */
    public static void updateToLGThenCloseWin(TB tb, BaseDomain domain, final ListGrid lg, final ListGridRecord lgr,final String winId) {
        CommonRemote.Util.getInstance().cascadeUpdateItems(tb, domain, new DefaultCallback<BaseDomain>() {

            @Override
            public void onSuccess(BaseDomain result) {
                super.onSuccess(result);
                ResultBuild.updateToListGrid(result, lg, lgr);
                if(winId!=null&&winId.length()>0){
                    OpenWindows.closeWin(winId);
                }
            }
        });
    }

    @SuppressWarnings("rawtypes")
	public static void deleteListGridType(final Class domainClass, final ListGrid listGrid) {
        for (int i = 0; i < listGrid.getSelectedRecords().length; i++) {
            if (listGrid.getSelectedRecords()[i].getAttribute("STATE").equals("审批中") || listGrid.getSelectedRecords()[i].getAttribute("STATE").equals("审批通过")) {
            	MessageUI.setMsgInfo("确认数据状态！");
                return;
            }
        }
        if (listGrid.getSelectedRecords().length == 0) {
        	MessageUI.setMsgInfo("请选择要删除的行.");
        } else {
            SC.confirm("提示", "您确定要删除选中的记录吗？", new BooleanCallback() {

                public void execute(Boolean value) {
                    if (value != null && value) {
                        String sql = RecordCastPojo.getPojoDeleteSql(domainClass, listGrid);
                        if (sql.length() > 10) {
                            CommonRemote.Util.getInstance().batchDeleteDomains(sql, new DefaultCallback<Void>() {

                                @Override
                                public void onSuccess(Void result) {
                                    ResultBuild.deleteSelectListGrid(listGrid);
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    /**
     * 新增实体成功后，在ListGrid上新增一条记录(有附加字段的无法显示时)
     * @param domain 要新增的实体
     * @param lg 列表
     * @param tb 需要更新的表名
     */
    public static void addListByCascade(TB tb, BaseDomain domain, final ListGrid lg) {
        CommonRemote.Util.getInstance().cascadeUpdateItems(tb, domain, new DefaultCallback<BaseDomain>() {

            @Override
            public void onSuccess(BaseDomain result) {
                super.onSuccess(result);
                ResultBuild.addToListGrid(result, lg);
            }
        });
    }

    /**
     * 新增实体
     * @param domain 新增的实体
     */
    public static void addPojo(BaseDomain domain) {
        CommonRemote.Util.getInstance().addOrModify(domain, new DefaultCallback<BaseDomain>() {

            @Override
            public void onSuccess(BaseDomain result) {
                super.onSuccess(result);
            }
        });
    }

}
