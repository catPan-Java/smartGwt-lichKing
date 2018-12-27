package lichKing.client.ui.bootstrap;

import com.smartgwt.client.types.ValueEnum;
import com.smartgwt.client.widgets.HTMLFlow;

public class BsPanel extends HTMLFlow{
	
    public BsPanel(String style) {
		super();
    	String contents="<div class='panel "+style+"'>"
    			+ "<div class='panel-heading'>"
    			+ "<h3 class='panel-title'>Panel title</h3></div>"
    			+ "<div class='panel-body'>Panel content</div>"
    			+ "</div>";
		this.setContents(contents);
		this.setWidth(300);
	}

    public static enum PanelColor implements ValueEnum {

    	/**
    	 * （默认样式）Default 浅灰
    	 */
    	DEFAULT(" panel-default"),
    	/**
    	 * （首选项）Primary 深蓝
    	 */
        PRIMARY(" panel-primary"),
    	/**
    	 * （成功）Success 浅绿
    	 */
        SUCCESS(" panel-success"),
    	/**
    	 * （一般信息）Info 淡蓝
    	 */
        INFO(" panel-info"),
    	/**
    	 * （警告）Warning 浅黄
    	 */
        WARNING(" panel-warning"),
    	/**
    	 * （危险）Danger 浅色
    	 */
        DANGER(" panel-danger");
    	
        private String value;

        PanelColor(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}
