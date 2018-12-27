/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lichKing.client.ui.myExtend;

import lichKing.client.server.MsgServer;

import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

/**
 *自己扩展的工具按钮
 * @author catPan
 */
public class MyToolStripButton extends ToolStripButton{
	
	public static ToolStripButton getMoreBtn(){
		ToolStripButton moreBtn = new ToolStripButton("更 多 信 息");
        moreBtn.setShowDisabledIcon(false);
        moreBtn.setIcon("op/Recovery.png");
        moreBtn.setAutoFit(true);
		return moreBtn;
	}

	public MyToolStripButton(String title,SelectionType type) {
        setTitle(title);
        setShowDisabledIcon(false);
        setActionType(type);
        setIconSize(20);
    }

	/*
	 * 工具条上的按钮，和权限关联，默认不显示
	 */
	public MyToolStripButton(String title) {
        setTitle(title);
        setShowDisabledIcon(false);
        setVisible(false);
        setIconSize(20);
    }

	/*
	 * 指定icon图标的大小
	 */
	public MyToolStripButton(String title,int iconSize) {
        setTitle(title);
        setShowDisabledIcon(false);
        setIconSize(iconSize);
		setAutoFit(true);
    }

    public MyToolStripButton(String title,String classPage) {
        setTitle(MsgServer.getMsg(title));
        setShowDisabledIcon(false);
        setID(title+"_OP_"+classPage);
    }


    //功能名称，保存Resource Key,如为NULL表示模块
    private String functionName;
    
    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

//    //功能代号10目录，20模块，30以后表功能
//    private Integer functionCode;
//    //权限代号，被授权的功能代号
//    private Integer permCode;
//    public Integer getFunctionCode() {
//        return functionCode;
//    }
//
//    public void setFunctionCode(Integer functionCode) {
//        this.functionCode = functionCode;
//    }
//
//    public Integer getPermCode() {
//        return permCode;
//    }
//
//    public void setPermCode(Integer permCode) {
//        this.permCode = permCode;
//    }

}
