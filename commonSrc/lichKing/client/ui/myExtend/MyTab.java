package lichKing.client.ui.myExtend;

import com.smartgwt.client.widgets.tab.Tab;

/**
 * 
 * @author catPan
 *
 */
public class MyTab extends Tab {
	
	
	
	public MyTab() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MyTab(String title, String icon) {
		super(title, icon);
		// TODO Auto-generated constructor stub
	}
	public MyTab(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}
	private String formClassN;
	private String lgClassN;
	private boolean dataLoad=false;
	
	public String getFormClassN() {
		return formClassN!=null?formClassN.replace(".", "0"):"";
	}
	public void setFormClassN(String formClassN) {
		this.formClassN = formClassN;
	}
	public String getLgClassN() {
		return lgClassN!=null?lgClassN.replace(".", "0"):"";
	}
	public void setLgClassN(String lgClassN) {
		this.lgClassN = lgClassN;
	}
	public boolean isDataLoad() {
		return dataLoad;
	}
	public void setDataLoad(boolean dataLoad) {
		this.dataLoad = dataLoad;
	}

}
