package lichKing.client.pojo;

import java.io.Serializable;
/**
 * 
 * @author catPan
 *
 */
public class TabsInfo implements Serializable{
	
	private static final long serialVersionUID = -6598401415753752973L;
	
//	private String tabId;
	private String tabName;
	@SuppressWarnings("rawtypes")
	private Class formClass;
	private String formFK;
	@SuppressWarnings("rawtypes")
	private Class LgClass;
	private String lgFK;
	@SuppressWarnings("rawtypes")
	private Class subLgClass;
	private String subLgFK;

	public TabsInfo(){
		
	}
	/**
	 * 用于构造tab页签中的元素，表单（上）和列表（下），以及获取数据时对应的外键
	 * @param tabId 页签ID
	 * @param tabName 页签名称
	 * @param formClass 表单实体的Class
	 * @param formFK 表单实体的外键
	 * @param lgClass ListGrid实体的Class
	 * @param lgFK ListGrid实体的外键
	 */
	@SuppressWarnings("rawtypes")
	public TabsInfo(String tabName, Class formClass,String formFK, Class lgClass,String lgFK) {
//	public TabsInfo(String tabId,String tabName, Class formClass,String formFK, Class lgClass,String lgFK) {
		super();
//		this.tabId = tabId;
		this.tabName = tabName;
		this.formClass = formClass;
		this.formFK = formFK;
		this.LgClass = lgClass;
		this.lgFK = lgFK;
	}

	/**
	 * 用于构造tab页签中的元素，表单（上）和列表（下），以及获取数据时对应的外键,subLgClass用于在猪群查看时，列表获取子列表（历史记录）
	 * @param tabId 页签ID
	 * @param tabName 页签名称
	 * @param formClass 表单实体的Class
	 * @param formFK 表单实体的外键
	 * @param lgClass ListGrid实体的Class
	 * @param lgFK ListGrid实体的外键
	 * @param subLgClass ListGrid实体的Class
	 */
	@SuppressWarnings("rawtypes")
	public TabsInfo(String tabName, Class formClass,String formFK, Class lgClass,String lgFK,Class subLgClass,String subLgFK) {
//	public TabsInfo(String tabId,String tabName, Class formClass,String formFK, Class lgClass,String lgFK,Class subLgClass,String subLgFK) {
		super();
//		this.tabId = tabId;
		this.tabName = tabName;
		this.formClass = formClass;
		this.formFK = formFK;
		this.LgClass = lgClass;
		this.lgFK = lgFK;
		this.subLgClass=subLgClass;
		this.subLgFK=subLgFK;
	}

//	/**
//	 * ID，尽量以主表类名为ID
//	 * @return
//	 */
//	public String getTabId() {
//		return tabId;
//	}
//
//	public void setTabId(String tabId) {
//		this.tabId = tabId;
//	}

	/**
	 * 页签名称
	 * @return
	 */
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	/**
	 * 表单实体Class
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Class getFormClass() {
		return formClass;
	}
	@SuppressWarnings("rawtypes")
	public void setFormClass(Class formClass) {
		this.formClass = formClass;
	}

	/**
	 * 表单实体的外键
	 * @return
	 */
	public String getFormFK() {
		return formFK;
	}

	public void setFormFK(String formFK) {
		this.formFK = formFK;
	}

	/**
	 * ListGrid实体的外键
	 * @return
	 */
	public String getLgFK() {
		return lgFK;
	}

	public void setLgFK(String lgFK) {
		this.lgFK = lgFK;
	}

	/**
	 * ListGrid实体Class
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Class getLgClass() {
		return LgClass;
	}
	@SuppressWarnings("rawtypes")
	public void setLgClass(Class lgClass) {
		LgClass = lgClass;
	}

	/**
	 * 历史或明细中ListGrid实体Class
	 * @return
	 */
	public Class getSubLgClass() {
		return subLgClass;
	}
	public void setSubLgClass(Class subLgClass) {
		this.subLgClass = subLgClass;
	}

	/**
	 * 子ListGrid实体的外键
	 * @return
	 */
	public String getSubLgFK() {
		return subLgFK;
	}
	public void setSubLgFK(String subLgFK) {
		this.subLgFK = subLgFK;
	}

}
