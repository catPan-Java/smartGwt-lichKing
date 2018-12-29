package lichKing.client.ui.bootstrap;

import lichKing.client.server.MsgServer;
import lichKing.client.utils.LayManager;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;


/**
 * 比较通用的弹出窗体
 *
 * @author catPan
 */
public class BsOpenWindows {
	

    /**
     * 另一种新增和修改展示的方法，平滑进入输入界面，不禁用背景
     * @param type
     * @param mlg
     * @param layout
     * @param btns
     */
	public static void showWinWithHeaderControl(final String type,final ListGrid mlg, Layout layout,BsButton[] btns) {
		String winId=mlg.getID();
		if (Window.getById("win"+winId) == null) {

			final VLayout maimLay = (VLayout) mlg.getParentCanvas();
			final Canvas opCancas = maimLay.getMember(0);
			final Layout tabLay=(Layout) maimLay.getParentCanvas().getParentCanvas();

			String editStyle=BsBox.BoxColor.PRIMARY.getValue();
			if("Add".equals(type)){
				editStyle=BsBox.BoxColor.SUCCESS.getValue();
			}else if("Modify".equals(type)){
				editStyle=BsBox.BoxColor.PRIMARY.getValue();
			}
			
			final BsBox editWin = new BsBox(editStyle);
			editWin.setID("win"+winId);
			editWin.setWidth100();
			editWin.setHeight100();
			editWin.setBackgroundColor("#f7f7f7");
			
			VLayout winLay=new VLayout();
			winLay.setWidth100();
			HLayout headerLay=new HLayout();
			headerLay.setPadding(6);
			headerLay.setMembersMargin(2);
			headerLay.setHeight(40);
			headerLay.setAlign(Alignment.RIGHT);
			headerLay.setStyleName("with-bottomBorder");

			Label title=new Label();
			title.setMargin(4);
			title.setWidth100();
			title.setContents(
					LayManager.changeFontSizeColor(
							MsgServer.getMsg(type), "18px", "balck")
					);
	        headerLay.addMember(title);

			if (btns != null && btns.length > 0) {
				for (BsButton btn : btns) {
					if(btn!=null){
						headerLay.addMember(btn);
					}
				}
			}

	        BsButton closeBtn=new BsButton(BsButton.BtnColor.PRIMARY.getValue());
//	        closeBtn.setText(" 关闭");
	        closeBtn.setWidth("15px");
	        closeBtn.addStyleName("fa fa-times fa-1");
	        headerLay.addMember(closeBtn);
	        
			winLay.addMember(headerLay);
			winLay.addMember(layout);
			
			editWin.addChild(winLay);

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
						LayManager.hideTabLay(opCancas, editWin, tabLay);
					}
				}
			});
		}
	}
	
    public static HLayout getBtnSpace() {
        HLayout btnSpace = new HLayout();
        btnSpace.setHeight(10);
        btnSpace.setWidth(6);
        return btnSpace;
    }

}
