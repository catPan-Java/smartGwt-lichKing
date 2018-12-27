package lichKing.client.ui.myExtend;

import java.util.Date;

import lichKing.client.datasource.OpTypeEnum;
import lichKing.client.server.MsgServer;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.Layout;

/**
 * 自定义的相关按钮，使用频率较高的按钮，保存，关闭等
 * @author catPan
 */
public class MyButton extends IButton {
	

    public static IButton getRefreshBtn(String title) {
    	return getCommBtn(title,"op/"+OpTypeEnum.Refresh.getValue()+".png");
    }
    
    public static IButton getAuthorizeBtn(String title) {
    	return getCommBtn(title,"myPS/userAuthorize.png");
    }
    
    public static IButton getDeleteBtn(String title) {
    	return getCommBtn(title,"op/"+OpTypeEnum.Delete.getValue()+".png");
    }
    
    public static IButton getUploadBtn(String title) {
    	return getCommBtn(MsgServer.getMsg("上传"),"icons/16/export1.png");
    }
    
    public static IButton getWorkStepBtn() {
    	return getCommBtn(MsgServer.getMsg("流程明细"),"myPS/workStep.png");
    }
    /**
     * 请求审批按钮
     * @return 
     */
    public static IButton getReqApprovalBtn() {
    	return getCommBtn(MsgServer.getMsg("ReqApproval"),"silk/comment_edit.png");
    }

    /**
     * 构造保存按钮和新增后继续保持按钮
     * @param addAgain 生成2种保存按钮，false：保存；true：新增后继续保存
     * @return 
     */
    public static MyButton getSaveBtn(boolean addAgain) {
        if (!addAgain) {
        	return getCommBtn(MsgServer.getMsg("save"),OpTypeEnum.Save.getOpImgSrc());
        } else {
        	return getCommBtn(MsgServer.getMsg("saveAddAgain"),"btnPNG/32/saveAddAgain.png");
        }
    }

    /**
     * 构造关闭按钮
     * @param _win 要关闭的窗体
     * @return 
     */
    public static IButton getColseBtn(final Window win) {
        IButton closeBtn = getCommBtn("关闭","btnPNG/32/back.png");
        closeBtn.setIconSize(20);;

        closeBtn.addClickHandler(new ClickHandler() {

            @Override
			public void onClick(ClickEvent event) {
                win.destroy();
            }
        });

        return closeBtn;
    }
    public static IButton getColseBtn(final String winId) {
        IButton closeBtn = getCommBtn("关闭","btnPNG/32/back.png");
        closeBtn.setIconSize(20);;

        closeBtn.addClickHandler(new ClickHandler() {

            @Override
			public void onClick(ClickEvent event) {
                OpenWindows.closeWin(winId);
            }
        });

        return closeBtn;
    }

    public static IButton getBackBtn(final Canvas opCancas,final Window editWin,final Layout tabLay) {
        IButton closeBtn = getCommBtn("关闭","btnPNG/32/back.png");
        closeBtn.setIconSize(20);;
        closeBtn.setTooltip("Esc");

        closeBtn.addClickHandler(new ClickHandler() {

            @Override
			public void onClick(ClickEvent event) {
//				LayManager.hideTabLay(opCancas, editWin, tabLay);
            }
        });

        return closeBtn;
    }
    

    /**
     * 构造编辑按钮
     * @return 
     */
    public static IButton getModifyBtn() {
    	return getCommBtn(MsgServer.getMsg("Modify"),"silk/overlays.png");
    }

    /**
     * 构造禁用按钮
     * @return 
     */
    public static IButton getDisableBtn(){
    	return getCommBtn("禁用",OpTypeEnum.Disabled.getOpImgSrc());
    }
    /**
     * 构造启用按钮
     * @return 
     */
    public static IButton getEnableBtn(){
    	return getCommBtn("启用",OpTypeEnum.Enable.getOpImgSrc());
    }
    
    //按钮点击时的时间点
    private Date clickTime;
    
    public Date getClickTime() {
		return clickTime;
	}

	public void setClickTime(Date clickTime) {
		this.clickTime = clickTime;
	}

	public static MyButton getCommBtn(String title,String iconSrc){
    	final MyButton commBtn = new MyButton();
        commBtn.setTitle(title);
        commBtn.setIcon(iconSrc);
        commBtn.setOverflow(Overflow.VISIBLE);
        commBtn.setAutoWidth();
        commBtn.setAutoFit(true);
        commBtn.setShowDisabledIcon(false);
        commBtn.setIconSize(20);
        commBtn.setAutoHeight();
        commBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				commBtn.setClickTime(new Date());
			}
		});
        return commBtn;
    }

    /**
     * 构造添加按钮
     * @return 
     */
    public static IButton getAddBtn() {
    	return getCommBtn(MsgServer.getMsg("Add"),"attask/icon_add.png");
    }

    /**
     * 构造查看按钮
     * @return 
     */
    public static IButton getLookBtn() {
    	return getCommBtn(MsgServer.getMsg("Look"),"other/magnifier.png");
    }

    /**
     * 构造请求审批按钮
     * @param 
     * @return 
     */
    public static IButton getappPassBtn() {
    	return getCommBtn(MsgServer.getMsg("AppPass"),"silk/server_go.png");
    }
}
