package lichKing.client.ui.bootstrap;

import com.google.gwt.user.client.ui.Label;
import com.smartgwt.client.types.ValueEnum;

public class BsLabel extends Label{
	
    public BsLabel(String style) {
		super();
		this.setStyleName("label"+style);
	}

    public static enum LabelColor implements ValueEnum {
    	/**
    	 * （默认样式）Default 深灰
    	 */
    	DEFAULT(" label-default"),
    	/**
    	 * （首选项）Primary 深蓝
    	 */
        PRIMARY(" label-primary"),
    	/**
    	 * （成功）Success 草绿
    	 */
        SUCCESS(" label-success"),
    	/**
    	 * （一般信息）Info 淡蓝
    	 */
        INFO(" label-info"),
    	/**
    	 * （警告）Warning 橙黄
    	 */
        WARNING(" label-warning"),
    	/**
    	 * （危险）Danger 红色
    	 */
        DANGER(" label-danger");
    	
        private String value;

        LabelColor(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}
