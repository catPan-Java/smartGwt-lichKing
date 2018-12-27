/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lichKing.client.utils;

import lichKing.client.datasource.AppDataMap;
import lichKing.client.datasource.OpTypeEnum;
import lichKing.client.ui.bootstrap.BsBox;
import lichKing.client.ui.myExtend.MyTabSet;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.events.SectionHeaderClickEvent;
import com.smartgwt.client.widgets.layout.events.SectionHeaderClickHandler;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

/**
 * 布局处理类
 * @author catPan
 */
public class LayManager {
	

	/**
	 * 是Layout显示正在加载数据的状态loading……
	 * @param lay
	 */
	public static void setLayLoading(Layout lay){
		lay.setContents(AppDataMap.getLoadingEmptyMessage());
	}
	
	/**
	 * 是ListGrid显示正在加载数据的状态loading……
	 * @param lg
	 */
	public static void setLgLoading(ListGrid lg){
		lg.setData(new ListGridRecord[0]);
		lg.setEmptyMessage(AppDataMap.getLoadingEmptyMessage());
	}

	/**
	 * 指定ListGrid的列，可用
	 * @param lg
	 * @param fieldsName
	 */
	public static void lgCanEditFields(ListGrid lg,String[] fieldsName){
		for(ListGridField lgf:lg.getFields()){
			lg.getField(lgf.getName()).setCanEdit(false);
		}
		for(String fieldName:fieldsName){
			lg.getField(fieldName).setCanEdit(true);
		}
	}
	/**
	 * 指定ListGrid的列，可见，其它列不可见
	 * @param lg
	 * @param fieldsName
	 */
	public static void lgShowFields(ListGrid lg,String[] fieldsName){
		for(ListGridField lgf:lg.getFields()){
			if(!lgf.getName().equals("_checkboxField")&&!lgf.getName().equals("$74y")){
				lg.hideFields(lgf);
			}
		}
		lg.showFields(fieldsName);
	}
	/**
	 * 指定ListGrid的列，不可用
	 * @param lg
	 * @param fieldsName
	 */
	public static void lgDisEditFields(ListGrid lg,String[] fieldsName){
		lg.setCanEdit(true);
		for(String fieldName:fieldsName){
			if(lg.getField(fieldName)!=null){
				lg.getField(fieldName).setCanEdit(false);
			}
		}
	}
	
	public static void hideTabLay(ListGrid lg){
		final VLayout maimLay = (VLayout) lg.getParentCanvas();
		final Canvas opCancas = maimLay.getMember(0);
		final Layout tabLay=(Layout) maimLay.getParentCanvas().getParentCanvas();
		hideTabLay(opCancas, Canvas.getById("win"+lg.getID()), tabLay);
	}

	
	public static void hideTabLay2(ListGrid lg){
		final Layout maimLay = (Layout) lg.getParentCanvas().getParentCanvas();
		final Canvas opCancas = maimLay.getMember(0);
		final Layout tabLay=(Layout) maimLay.getParentCanvas().getParentCanvas();
		hideTabLay(opCancas, Canvas.getById("win"+lg.getID()), tabLay);
	}

	public static void hideTabLay(Canvas opCancas,Canvas editWin,Layout tabLay){
		opCancas.animateShow(AnimationEffect.SLIDE);
		editWin.destroy();
		tabLay.getMember(0).show();
		tabLay.getMember(tabLay.getMembers().length-1).show();
	}
	
	public static void showTabLay(VLayout maimLay,Window editWin,Layout tabLay){
			tabLay.getMember(0).hide();
			tabLay.getMember(tabLay.getMembers().length-1).hide();
			maimLay.addChild(editWin);
			editWin.setHeight(editWin.getHeight()+50);
	}

	public static void showTabLay(VLayout maimLay,BsBox editWin,Layout tabLay){
			tabLay.getMember(0).hide();
			tabLay.getMember(tabLay.getMembers().length-1).hide();
			maimLay.addChild(editWin);
			editWin.setHeight(editWin.getHeight()+50);
	}
	
	public static void hideTabLay(Canvas opCancas,BsBox editWin,Layout tabLay){
		opCancas.animateShow(AnimationEffect.SLIDE);
		editWin.destroy();
		tabLay.getMember(0).show();
		tabLay.getMember(tabLay.getMembers().length-1).show();
	}

	public static void showTabLay2(Layout maimLay,Window editWin,Layout tabLay){
			tabLay.getMember(0).hide();
			tabLay.getMember(tabLay.getMembers().length-1).hide();
			maimLay.addChild(editWin);
			editWin.setHeight(editWin.getHeight()+50);
	}
	
	/**
	 * 改变选中的Label的颜色，还原上一次的Label
	 * @param lab
	 * @param lastLabId
	 */
	public static void changeSelectedColor(Label lab,String labId){
		if(labId!=null&&labId.length()>1){
			if(Label.getById(labId)!=null){
				Label.getById(labId).setBackgroundColor("lightgreen");
			}
		}
		lab.setBackgroundColor("pink");
	}
	
	/**
	 * 抽屉是UI的点击事件重写，选择一个抽屉后展开，其它抽屉收缩
	 * @param sectionStack
	 */
	public static void mySectionStackClick(final SectionStack sectionStack){

        sectionStack.addSectionHeaderClickHandler(new SectionHeaderClickHandler() {
			
			@Override
			public void onSectionHeaderClick(SectionHeaderClickEvent event) {
				for(SectionStackSection section:sectionStack.getSections()){
					if(!event.getSection().equals(section)){
						sectionStack.collapseSection(section.getID());
					}
				}
			}
		});
	}

	/**
	 * 猪舍等上下结构，下边作为更多页签信息布局，可以隐藏、展开
	 * @param title 页签的标题
	 * @param vlay 纵向布局
	 * @param lay 放在Tab页签中的布局
	 */
	public static void hideTabLayV(String title,VLayout vlay,Layout lay){
		hideTabLayV(title, vlay, lay, 440);
	}

	public static void hideTabLayV(String title,final VLayout vlay,Layout lay,final int height){
		final MyTabSet tabSet=new MyTabSet();
		tabSet.setWidth100();
		tabSet.setHeight(25);
		tabSet.setTabBarAlign(Side.RIGHT);
		Tab tab1=new Tab(title);
		tab1.setPane(lay);
		tabSet.addTab(tab1);
		tabSet.setOpenType("展开");
		
		VLayout space=new VLayout();
		space.setWidth("50%");
		tabSet.setTabBarControls(space,HeaderControls.HEADER_LABEL);

		tabSet.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(tabSet.getOpenType().equals("展开")){
					if(event.getY()<Page.getHeight()-25){
						tabSet.setOpenType("收起");
						tabSet.animateResize(tabSet.getWidth(), height);
						vlay.getMember(0).animateHide(AnimationEffect.SLIDE);
					}
				}else{
					if(event.getY()<150+25){
						tabSet.setOpenType("展开");
						tabSet.animateResize(tabSet.getWidth(), 25);
						vlay.getMember(0).animateShow(AnimationEffect.SLIDE);
					}
				}
			}
		});
		
		vlay.addMember(tabSet);
	}
	/**
	 * 猪场、猪舍等左右结构，右边作为信息布局，类似vb、C#可视化窗体的属性页签，可以隐藏、展开
	 * @param title 页签的标题
	 * @param hlay 横向布局
	 * @param lay 放在Tab页签中的布局
	 * @param id tabset和箭头img的id，在直接调用展开和收缩时要用到
	 */
	public static void hideTabLayH(String title,HLayout hlay,Layout lay,String id){
		hideTabLayH(title, hlay, lay, id, 355, 440);
	}

	public static void hideTabLayH(String title,HLayout hlay,Layout lay,String id,final int width,final int height){
		final MyTabSet tabSet=new MyTabSet();
		tabSet.setID("tab"+id);
		tabSet.setWidth(width);
		tabSet.setHeight(height);
		tabSet.setTabBarPosition(Side.LEFT);
		tabSet.setTabBarAlign(Side.BOTTOM);
		Tab tab1=new Tab(title);
		tab1.setPane(lay);
		tabSet.addTab(tab1);
		tabSet.setOpenType("收起");

		VLayout space=new VLayout();
		space.setHeight(200);
		tabSet.setTabBarControls(space,HeaderControls.HEADER_LABEL);

		tabSet.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(tabSet.getOpenType().equals("收起")){
					if(event.getX()<Page.getWidth()-width){
						tabSet.setOpenType("展开");
						tabSet.animateResize(25, height);
					}
				}else{
					tabSet.setOpenType("收起");
					tabSet.animateResize(width, height);
				}
			}
		});
		
		hlay.addMember(tabSet);
	}

	public static void hideTabLayMain(HLayout hlay,Layout lay){
		final MyTabSet tabSet=new MyTabSet();
		tabSet.setWidth(205);
		tabSet.setHeight100();
		tabSet.setTabBarPosition(Side.RIGHT);
		tabSet.setTabBarAlign(Side.BOTTOM);
		Tab tab1=new Tab("导<br />航<br />信<br />息");
		tab1.setPane(lay);
		tabSet.addTab(tab1);
		tabSet.setOpenType("收起");

		VLayout space=new VLayout();
		space.setHeight(200);
		tabSet.setTabBarControls(space,HeaderControls.HEADER_LABEL);

		tabSet.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(tabSet.getOpenType().equals("收起")){
					if(event.getX()>190){
						tabSet.setOpenType("展开");
						tabSet.animateResize(25, tabSet.getHeight());
					}
				}else{
					tabSet.setOpenType("收起");
					tabSet.animateResize(205, tabSet.getHeight());
				}
			}
		});
		
		hlay.addMember(tabSet);
	}
	
	/**
	 * 直接调用展开、收缩功能，需要指定对应UI的id
	 * @param openOrClose true：展开；false：收缩
	 * @param id tabset的id
	 */
	public static void callArrowLR(boolean openOrClose,String id){
		MyTabSet tabSet=(MyTabSet) TabSet.getById("tab"+id);
		if(!openOrClose&&!tabSet.getOpenType().equals("展开")){
			tabSet.setOpenType("展开");
			tabSet.animateResize(25, tabSet.getHeight());
		}else if(!tabSet.getOpenType().equals("收起")){
			tabSet.setOpenType("收起");
			tabSet.animateResize(335, tabSet.getHeight());
		}
	}
	/**
	 * 改变字体为粗体
	 * @param txt
	 * @return
	 */
	public static String changeFontBold(String txt){
		return "<b>"+txt+"</b>";
	}
	/**
	 * 改变字体为粗体,并设置字体大小
	 * @param txt
	 * @return
	 */
	public static String changeFontBoldSize(String txt,String fontSize){
		return " <font style='font-size:"+fontSize+"'><b>"+txt+"</b></font>";
	}
	/**
	 * 改变数字为粗体
	 * @param txt
	 * @return
	 */
	public static String changeFontBold(int num){
		return "<b>"+num+"</b>";
	}
	
	/**
	 * 改变字体颜色为白色
	 * @param txt
	 * @return
	 */
	public static String changeFontWhite(String txt){
		return "<font style='color:white'>"+txt+"</font>";
	}

	/**
	 * 改变字体颜色为color
	 * @param txt
	 * @param color
	 * @return
	 */
	public static String changeFontColor(String txt,String color){
		return "<b><font style='color:"+color+"'>"+txt+"</font></b>";
	}
	/**
	 * 改变字体颜色，并设置字体大小
	 * @param txt
	 * @param fontSize
	 * @return
	 */
	public static String changeFontSizeColor(String txt,String fontSize,String color){
		return "<font style='color:"+color+";font-size:"+fontSize+"'>"+txt+"</font>";
	}
	
	/**
	 *  给定字符串加左右括号
	 * @param txt
	 * @return
	 */
	public static String addBrackets(String txt){
		return " <font size='3'>("+txt+")</font>";
	}
	
	/**
	 * 改变字体颜色为白色，并设置字体大小
	 * @param txt
	 * @param fontSize
	 * @return
	 */
	public static String changeFontWhite(String txt,String fontSize){
		return "<font style='color:white;font-size:"+fontSize+"'>"+txt+"</font>";
	}
    
    /**
     * 设置表单元素不可编辑（只读状态）
     */
    public static void setFormItemsReadOnly(DynamicForm form){
        form.setCanEdit(false);
        for(FormItem item:form.getFields()){
            item.setAttribute("readOnly", true);
        }
    }

    /**
     * 设置表单元素不可编辑（只读状态）,noReadonlyFields剔除的字段
     */
    public static void setFormItemsReadOnly(DynamicForm form,String[] noReadonlyFields){
////        form.setCanEdit(false);
//        for(FormItem item:form.getFields()){
//        	for(String str:noReadonlyFields){
//        		if(!item.getName().equals(str)){
//                    item.setAttribute("readOnly", true);
//        		}else{
//        			 item.setAttribute("readOnly", false);
//        		}
//        	}
//        }
        for(FormItem item:form.getFields()){
            item.setCanEdit(false);
        }
    	for(String str:noReadonlyFields){
    		form.getItem(str).setCanEdit(true);
    	}
    }

    /**
     * 重新设置表单元素的宽度，并制定表单的宽度<br />
     * <b>注意：</b>如果<b>字段太多的类</b>，调用此方法，尽量在addmember()后面点，可能绑定表单的时候，反射处理循环判断太多，字段还没有构建就调用了设置宽度，就导致此方法无效！
     * @param form 要重置的表单
     * @param width 新的宽度
     * @param fromWidth 表单的宽度
     */
    public static void resetFormItemsWidth(final DynamicForm form,int width,int fromWidth){
		form.setWidth(fromWidth);
        for(FormItem item:form.getFields()){
        	item.setTitle("<nobr>"+item.getTitle()+"</nobr>");
            item.setWidth(width);
        }
        Timer timer=new Timer() {

            @Override
            public void run() {
//                form.focusInItem(0);
            	for(FormItem fi:form.getFields()){
            		if(fi.isDisabled()){
            			continue;
            		}else{
            			fi.focusInItem();
            			break;
            		}
            	}
            }
        };
        timer.schedule(600);
    }
    /**
     * 重新设置表单元素的宽度<br />
     * <b>注意：</b>如果<b>字段太多的类</b>，调用此方法，尽量在addmember()后面点，可能绑定表单的时候，反射处理循环判断太多，字段还没有构建就调用了设置宽度，就导致此方法无效！
     * @param form 要重置的表单
     * @param width 新的宽度
     */
    public static void resetFormItemsWidth(final DynamicForm form,int width){
    	if(form.getNumCols()==4){
    		form.setWidth(760);
    	}
        for(FormItem item:form.getFields()){
        	item.setTitle("<nobr>"+item.getTitle()+"</nobr>");
            item.setWidth(width);
            if(item.getType().equals("float")){
            	item.setEditorValueFormatter(new FloatItemFormatter());
            }
        }
        Timer timer=new Timer() {

            @Override
            public void run() {
//                form.focusInItem(0);
            	for(FormItem fi:form.getFields()){
            		if(fi.isDisabled()){
            			continue;
            		}else{
            			fi.focusInItem();
            			break;
            		}
            	}
            }
        };
        timer.schedule(600);
    }

    /**
     * 重新设置表单元素的宽度<br />
     * <b>注意：</b>如果<b>字段太多的类</b>，调用此方法，尽量在addmember()后面点，可能绑定表单的时候，反射处理循环判断太多，字段还没有构建就调用了设置宽度，就导致此方法无效！
     * @param form 要重置的表单
     * @param width 新的宽度
     * @param type 表单的操作类型，如果为Look时，表单为只读状态
     */
    public static void resetFormItemsWidth(final DynamicForm form,int width,String type){
    	if(form.getNumCols()==4){
    		form.setWidth(760);
    	}
        for(FormItem item:form.getFields()){
        	if(type.equals(OpTypeEnum.Look.getValue())){
        		item.setCanEdit(false);
        	}
        	item.setTitle("<nobr>"+item.getTitle()+"</nobr>");
            item.setWidth(width);
        }
        if(!"Look".equals(type)){
	        Timer timer=new Timer() {
	
	            @Override
	            public void run() {
	//                form.focusInItem(0);
	            	for(FormItem fi:form.getFields()){
	            		if(fi.isDisabled()){
	            			continue;
	            		}else{
	            			fi.focusInItem();
	            			break;
	            		}
	            	}
	            }
	        };
	        timer.schedule(1000);
        }
    }
    /**
     * 设置组件在父容器中，距右下角的距离（默认值）
     * @param parent 父容器
     * @param canvas 需要靠右下角的组件
     */
    public static void setCanvasRightBottom(Canvas parent,Canvas canvas){
        setCanvasRightBottom(parent, canvas, 100, 30);
    }
    
    /**
     * 设置组件在父容器中，距右下角的距离
     * @param parent 父容器
     * @param canvas 需要靠右下角的组件
     * @param right 靠右多少
     * @param bottom 靠下多少
     */
    public static void setCanvasRightBottom(Canvas parent,Canvas canvas,int right,int bottom){
        parent.addChild(canvas);
        int width=parent.getWidth();
        int height=parent.getHeight();
        canvas.setLeft(width-right-canvas.getWidth()-10);
        canvas.setTop(height-bottom);
    }
    
    
}
