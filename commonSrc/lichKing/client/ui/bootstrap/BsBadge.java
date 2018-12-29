package lichKing.client.ui.bootstrap;

import com.smartgwt.client.types.ValueEnum;
import com.smartgwt.client.widgets.HTMLFlow;

public class BsBadge extends HTMLFlow{

	public BsBadge(int number){
		super();
		
	}
	
	
	public BsBadge(int number,String contents) {
		super();
		this.setContents(contents+" <span class='badge'>"+number+"</span>");
	}
	

	public BsBadge(int number,String style,String contents) {
		super();
		this.setHeight(50);
        this.setWidth(140);
		this.setStyleName("btn"+style);
		this.setContents(contents+" <span class='badge'>"+number+"</span>");
	}

	public static enum BadgeColor implements ValueEnum {
    	/**
    	 * （首选项）Primary 深蓝
    	 */
        PRIMARY(" btn-primary"),
    	/**
    	 * （成功）Success 浅绿
    	 */
        SUCCESS(" btn-success"),
    	/**
    	 * （一般信息）Info 淡蓝
    	 */
        INFO(" btn-info"),
    	/**
    	 * （警告）Warning 浅黄
    	 */
        WARNING(" btn-warning"),
    	/**
    	 * （危险）Danger 浅色
    	 */
        DANGER(" btn-danger");
    	
        private String value;

        BadgeColor(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}
