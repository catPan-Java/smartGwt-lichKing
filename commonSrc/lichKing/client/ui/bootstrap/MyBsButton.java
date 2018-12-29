package lichKing.client.ui.bootstrap;

public class MyBsButton extends BsButton {
	

    public static BsButton getSaveBtn() {
    	return getCommBtn("保存",BsButton.BtnColor.INFO.getValue(),"floppy-o");
    }

    public static BsButton getSaveAddAgainBtn() {
    	return getCommBtn("保存并新增","floppy-o");
    }

    public static BsButton getCloseBtn() {
    	return getCommBtn("关闭","times");
    }

	public MyBsButton(String style) {
		super(style);
	}

	public static MyBsButton getCommBtn(String title,String fontName){
    	final MyBsButton commBtn = new MyBsButton(BsButton.BtnColor.PRIMARY.getValue());
        commBtn.setText(title);
        commBtn.addStyleName("fa fa-"+fontName+" fa-1");
        return commBtn;
    }

	public static MyBsButton getCommBtn(String title,String style,String fontName){
    	final MyBsButton commBtn = new MyBsButton(style);
        commBtn.setText(title);
        commBtn.addStyleName("fa fa-"+fontName+" fa-1");
        commBtn.setWidth("50px");
        return commBtn;
    }
	
}
