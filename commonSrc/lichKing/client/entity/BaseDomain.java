package lichKing.client.entity;

import java.io.Serializable;

import net.sf.gilead.pojo.gwt.LightEntity;

import com.gwtent.reflection.client.Reflection;

/**
 *
 * @author catPan
 */
//@MappedSuperclass
public class BaseDomain extends LightEntity  implements Reflection,Serializable {
    private static final long serialVersionUID = -8375107125070449431L;

    private String type="";
    
//    private String CREATE_BY;//	Nvarchar(32)	NOT NULL	创建者
//    private Date CREATE_TIME;//	DateTime	NOT NULL	创建时间
//    private String UPDATE_BY;//	Nvarchar(32)	NOT NULL	更新者
//    private Date UPDATE_TIME;//	DateTime	NOT NULL	更新时间
    public static String ORDERBY_COL;//排序字段
    
    private String[] uniqueFields;
    
    /**
     * 实体状态，Add、Modify、Delete……
     * @return
     */
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 唯一验证的字段
	 * @return
	 */
	public String[] getUniqueFields() {
		return uniqueFields;
	}

	public void setUniqueFields(String[] uniqueFields) {
		this.uniqueFields = uniqueFields;
	}
	
	

//	/**
//     * @return 创建者
//     */
//    @Column(name = "CREATE_BY", nullable = false, length = 32)
//    public String getCREATE_BY() {
//        return CREATE_BY;
//    }
//
//    public void setCREATE_BY(String CREATE_BY) {
//        this.CREATE_BY = CREATE_BY;
//    }
//
//    /**
//     * @return 创建时间
//     */
//    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
//    @Column(columnDefinition="DATE DEFAULT CURRENT_DATE")//datetime default getdate()
//    public Date getCREATE_TIME() {
//        return CREATE_TIME;
//    }
//
//    public void setCREATE_TIME(Date CREATE_TIME) {
//        this.CREATE_TIME = CREATE_TIME;
//    }
//
//    /**
//     * @return 更新者
//     */
//    @Column(name = "UPDATE_BY", nullable = false, length = 32)
//    public String getUPDATE_BY() {
//        return UPDATE_BY;
//    }
//
//    public void setUPDATE_BY(String UPDATE_BY) {
//        this.UPDATE_BY = UPDATE_BY;
//    }
//
//    /**
//     * @return 更新时间
//     */
//    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
//    @Column(columnDefinition="DATE DEFAULT CURRENT_DATE")
//    public Date getUPDATE_TIME() {
//        return UPDATE_TIME;
//    }
//
//    public void setUPDATE_TIME(Date UPDATE_TIME) {
//        this.UPDATE_TIME = UPDATE_TIME;
//    }

}
