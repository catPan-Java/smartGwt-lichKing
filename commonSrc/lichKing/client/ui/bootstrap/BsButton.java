package lichKing.client.ui.bootstrap;

import com.google.gwt.user.client.ui.Label;
import com.smartgwt.client.types.ValueEnum;

public class BsButton extends Label{

	
	
	public BsButton(String style) {
		super();
		this.setStyleName("btn"+style);
	}
    
    public static enum BtnColor implements ValueEnum {
    	/**
    	 * （默认样式）Default 白色
    	 */
    	DEFAULT(" btn-default"),
    	/**
    	 * （首选项）Primary 深蓝
    	 */
        PRIMARY(" btn-primary"),
    	/**
    	 * （成功）Success 草绿
    	 */
        SUCCESS(" btn-success"),
    	/**
    	 * （一般信息）Info 淡蓝
    	 */
        INFO(" btn-info"),
    	/**
    	 * （警告）Warning 橙黄
    	 */
        WARNING(" btn-warning"),
    	/**
    	 * （危险）Danger 红色
    	 */
        DANGER(" btn-danger"),
    	/**
    	 * （链接）Link 超链接
    	 */
        LINK(" btn-link");
    	
        private String value;

        BtnColor(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public static enum BtnSize implements ValueEnum {
    	/**
    	 * 大按钮
    	 */
    	LARGE(" btn-lg"),
    	/**
    	 * 小按钮
    	 */
        SMALL(" btn-sm"),
    	/**
    	 * 超小尺寸
    	 */
        XSMALL(" btn-xs");
    	
        private String value;

        BtnSize(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
    
}
