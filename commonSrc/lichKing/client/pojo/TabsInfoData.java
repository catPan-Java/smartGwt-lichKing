package lichKing.client.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lichKing.client.entity.BaseDomain;
/**
 * 
 * @author catPan
 *
 */
public class TabsInfoData implements Serializable{
	
	private static final long serialVersionUID = -65986853752973L;

	private boolean isOneId=false;
	private String type="";
	private String mIdField;
	private BaseDomain mDomain;// 主类的数据
	
	private Map<String, Map<String, Map<String, BaseDomain>>> fDomains;// 次页签中的表单的数据
	
	private Map<String, Map<String, List<BaseDomain>>> lDomains;// 页签中的ListGrid的数据
	
	private List<BaseDomain> deleteDomains;//删除的实体类
	
	
	
	public boolean isOneId() {
		return isOneId;
	}

	public void setOneId(boolean isOneId) {
		this.isOneId = isOneId;
	}

	/**
	 * 记录删除的实体类
	 * @return
	 */
	public List<BaseDomain> getDeleteDomains() {
		return deleteDomains;
	}

	public void setDeleteDomains(List<BaseDomain> deleteDomains) {
		this.deleteDomains = deleteDomains;
	}

	/**
	 * 状态，Add、Modify……
	 * @return
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 实体的ID字段
	 * @return
	 */
	public String getmIdField() {
		return mIdField;
	}

	public void setmIdField(String mIdField) {
		this.mIdField = mIdField;
	}

	/**
	 * 主类的数据
	 * @return
	 */
	public BaseDomain getmDomain() {
		return mDomain;
	}

	public void setmDomain(BaseDomain mDomain) {
		this.mDomain = mDomain;
	}
	


	/**
	 * 次页签中的表单的数据Map<String（表单的className）, Map<String（和主表的外键）, Map<String（实体的ID）, BaseDomain（实体类）>>>
	 * @return
	 */
	public Map<String, Map<String, Map<String, BaseDomain>>> getfDomains() {
		return fDomains;
	}

	public void setfDomains(Map<String, Map<String, Map<String, BaseDomain>>> fDomains) {
		this.fDomains = fDomains;
	}

	/**
	 * 页签中的ListGrid的数据Map<String（列表的className）, Map<String（和主表的外键）, List<BaseDomain（实体类）>>>
	 * @return
	 */
	public Map<String, Map<String, List<BaseDomain>>> getlDomains() {
		return lDomains;
	}

	public void setlDomains(Map<String, Map<String, List<BaseDomain>>> lDomains) {
		this.lDomains = lDomains;
	}
	
	
}
