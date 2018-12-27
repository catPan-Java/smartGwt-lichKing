package lichKing.client.ui.bootstrap;

import com.smartgwt.client.types.ValueEnum;
import com.smartgwt.client.widgets.HTMLFlow;

public class BsBox extends HTMLFlow{

	
    public BsBox(String style) {
		super();
		this.setStyleName("box"+style);
	}

    public static enum BoxColor implements ValueEnum {
    	/**
    	 * （默认样式）Default 深灰
    	 */
    	DEFAULT(" box-default"),
    	/**
    	 * （首选项）Primary 深蓝
    	 */
        PRIMARY(" box-primary"),
    	/**
    	 * （成功）Success 草绿
    	 */
        SUCCESS(" box-success"),
    	/**
    	 * （一般信息）Info 淡蓝
    	 */
        INFO(" box-info"),
    	/**
    	 * （警告）Warning 橙黄
    	 */
        WARNING(" box-warning"),
    	/**
    	 * （危险）Danger 红色
    	 */
        DANGER(" box-danger");
    	
        private String value;

        BoxColor(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}
