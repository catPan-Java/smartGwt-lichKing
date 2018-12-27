package lichKing.client.pojo;

import java.io.Serializable;

import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 *
 * @author catPan
 */
public class TabLG implements Serializable{
    
    private static final long serialVersionUID = 24083923956506541L;

	
	private String type;
	private ListGridRecord editRecord;
	private String[] tabNames;
	@SuppressWarnings("rawtypes")
	private Class[] clsArr;
	private TabsInfo[] tabsInfo;

    public static class Builder {
        
        private String type;
    	private ListGridRecord editRecord;
    	private String[] tabNames;
    	@SuppressWarnings("rawtypes")
		private Class[] clsArr;
    	private TabsInfo[] tabsInfo;

        public Builder(String type, ListGridRecord editRecord) {
            this.type = type;
            this.editRecord = editRecord;
        }

    	/**
    	 * tab页签子元素
    	 * @param val
    	 * @return
    	 */
        public Builder tabsInfo(TabsInfo[] val){
        	tabsInfo = val;
            return this;
        }
    	/**
    	 * 页面操作状态，Add、Modify、Look……
    	 * @param val
    	 * @return
    	 */
        public Builder type(String val){
        	type = val;
            return this;
        }
        /**
         * 主页面数据ListGridRecord
         * @param val
         * @return
         */
        public Builder editRecord(ListGridRecord val){
        	editRecord = val;
            return this;
        }
        /**
         * tab页签的名称
         * @param val
         * @return
         */
        public Builder tabNames(String[] val){
        	tabNames = val;
            return this;
        }
        /**
         * tab页签中ListGrid对应的实体Class类型
         * @param val
         * @return
         */
        @SuppressWarnings("rawtypes")
		public Builder clsArr(Class[] val){
        	clsArr = val;
            return this;
        }
        /**
         * 绑定参数
         * @return 
         */
        public TabLG build(){
            return new TabLG(this);
        }
    }
    /**
     * 通过Builder模式弹出页面form和tab页签中多个ListGrid页面的构造
     * @param builder 
     */
    public TabLG(Builder builder){
    	type=builder.type;
    	editRecord=builder.editRecord;
    	tabNames=builder.tabNames;
    	clsArr=builder.clsArr;
    	tabsInfo=builder.tabsInfo;
    }
    
    public static class Builder2 {
        
    	private String[] tabNames;
    	@SuppressWarnings("rawtypes")
		private Class[] clsArr;
    	private TabsInfo[] tabsInfo;


    	/**
    	 * tab页签子元素
    	 * @param val
    	 * @return
    	 */
        public Builder2 tabsInfo(TabsInfo[] val){
        	tabsInfo = val;
            return this;
        }
        /**
         * tab页签的名称
         * @param val
         * @return
         */
        public Builder2 tabNames(String[] val){
        	tabNames = val;
            return this;
        }
        /**
         * tab页签中ListGrid对应的实体Class类型
         * @param val
         * @return
         */
        @SuppressWarnings("rawtypes")
		public Builder2 clsArr(Class[] val){
        	clsArr = val;
            return this;
        }
        /**
         * 绑定参数
         * @return 
         */
        public TabLG build2(){
            return new TabLG(this);
        }
    }
    /**
     * 通过Builder模式，绑定首页面form和tab页签中多个ListGrid页面的构造
     * @param builder 
     */
    public TabLG(Builder2 builder){
    	tabNames=builder.tabNames;
    	clsArr=builder.clsArr;
    	tabsInfo=builder.tabsInfo;
    }

    public TabLG() {
    }
    

	/**
	 * tab页签子元素
	 * @param val
	 * @return
	 */
    public TabsInfo[] getTabsInfo() {
		return tabsInfo;
	}

	public void setTabsInfo(TabsInfo[] tabsInfo) {
		this.tabsInfo = tabsInfo;
	}

	/**
     * 页面操作状态，Add、Modify、Look……
     * @return
     */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    /**
     * 主页面数据ListGridRecord
     * @return
     */
	public ListGridRecord getEditRecord() {
		return editRecord;
	}

	public void setEditRecord(ListGridRecord editRecord) {
		this.editRecord = editRecord;
	}

    /**
     * tab页签的名称
     * @return
     */
	public String[] getTabNames() {
		return tabNames;
	}

	public void setTabNames(String[] tabNames) {
		this.tabNames = tabNames;
	}

    /**
     * tab页签中ListGrid对应的实体Class类型
     * @return
     */
	@SuppressWarnings("rawtypes")
	public Class[] getClsArr() {
		return clsArr;
	}

	@SuppressWarnings("rawtypes")
	public void setClsArr(Class[] clsArr) {
		this.clsArr = clsArr;
	}

    }
