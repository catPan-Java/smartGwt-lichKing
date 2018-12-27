package lichKing.client.ui.msg;

import lichKing.client.ui.bootstrap.BsAlert;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.layout.HLayout;

/**
 *
 * @author catPan
 */
public class MessageUI {
	
	//临时传递页面之间的参数
	private static String p2pStr;
    
    public static String getP2pStr() {
		return p2pStr;
	}

	public static void setP2pStr(String p2pStr) {
		MessageUI.p2pStr = p2pStr;
	}

	public static void clearP2pStr() {
		MessageUI.p2pStr = "";
	}

	private static int winH=60;
    private static int winW=560;
    private static HLayout messWin;
    public static void createMsgWin() {
        if (messWin == null) {
            messWin = new HLayout();
            messWin.setWidth(winW);
            messWin.setHeight(winH);
            messWin.addMember(getMsgLay());
        }
        hideMsgWin();
    }
    
    
    private static BsAlert label;
    public static Label getMsgLay(){
        
        label = new BsAlert(BsAlert.AlertColor.INFO.getValue());
        label.setHeight(winH+"px");
        
        return label;
    }

    private static Timer timer;
    public static void showMsgWin() {
        if (messWin != null) {
            messWin.moveTo(Page.getWidth() - winW-10, Page.getHeight() - winH-5);
            messWin.show();
            timer=new Timer() {
				
				@Override
				public void run() {
					hideMsgWin();
				}
			};
			timer.schedule(10000);
        }
    }

    public static void hideMsgWin() {
        if (messWin != null) {
        	messWin.hide();
        }
    }

    public static void setMsgInfo(String msg){
    	label.setText(msg);
        showMsgWin();
    }

    public static void setMsgError(String msg){
    	label.setStyleName("alert"+BsAlert.AlertColor.DANGER.getValue());
    	label.setText(msg);
        showMsgWin();
    }

    public static void setMsgSuccess(String msg){
    	label.setStyleName("alert"+BsAlert.AlertColor.SUCCESS.getValue());
    	label.setText(msg);
        showMsgWin();
    }
}
