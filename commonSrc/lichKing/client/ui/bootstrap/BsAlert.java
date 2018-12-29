package lichKing.client.ui.bootstrap;

import com.google.gwt.user.client.ui.Label;
import com.smartgwt.client.types.ValueEnum;

public class BsAlert extends Label{


	public BsAlert(String style) {
		super();
    	this.setWidth("100%");
    	this.setStyleName("alert"+style);
	}


	public static enum AlertColor implements ValueEnum {
    	/**
    	 * （成功）Success 草绿
    	 */
        SUCCESS(" alert-success"),
    	/**
    	 * （一般信息）Info 淡蓝
    	 */
        INFO(" alert-info"),
    	/**
    	 * （警告）Warning 橙黄
    	 */
        WARNING(" alert-warning"),
    	/**
    	 * （危险）Danger 红色
    	 */
        DANGER(" alert-danger");
    	
        private String value;

        AlertColor(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}
