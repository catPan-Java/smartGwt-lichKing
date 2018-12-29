package lichKing.client.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lichKing.client.entityAnnotation.EntityAnn;
/**
 *
 * @author catPan
 */
@Entity
@Table(name="code_item")
public class APP_COMMON_TYPE extends BaseDomain{
    
    private static final long serialVersionUID = -5787153407667477933L;

    public static String ORDERBY_COL="CODE_VALUE";
    
    @EntityAnn(ResourceKey = "COMMON_TYPE_ID")
    private int COMMON_TYPE_ID;
    private String COMMON_TYPE_PCODE;
    @EntityAnn(ResourceKey = "COMMON_TYPE_NAME")
    private String COMMON_TYPE_NAME;
    private String COMPANY_CODE_FK;
    private String CODE_VALUE;


    public String getCODE_VALUE() {
		return CODE_VALUE;
	}
	public void setCODE_VALUE(String cODE_VALUE) {
		CODE_VALUE = cODE_VALUE;
	}
	@Id
    @Column(name = "code_id")
    public int getCOMMON_TYPE_ID() {
        return COMMON_TYPE_ID;
    }
    public void setCOMMON_TYPE_ID(int COMMON_TYPE_ID) {
        this.COMMON_TYPE_ID = COMMON_TYPE_ID;
    }

    @Column(name = "category_fk")
    public String getCOMMON_TYPE_PCODE() {
        return COMMON_TYPE_PCODE;
    }
    public void setCOMMON_TYPE_PCODE(String COMMON_TYPE_PCODE) {
        this.COMMON_TYPE_PCODE = COMMON_TYPE_PCODE;
    }

    @Column(name = "code_name")
    public String getCOMMON_TYPE_NAME() {
        return COMMON_TYPE_NAME;
    }
    public void setCOMMON_TYPE_NAME(String COMMON_TYPE_NAME) {
        this.COMMON_TYPE_NAME = COMMON_TYPE_NAME;
    }
    
    @Column(name = "COMPANY_CODE_FK")
	public String getCOMPANY_CODE_FK() {
		return COMPANY_CODE_FK;
	}
	public void setCOMPANY_CODE_FK(String cOMPANY_CODE_FK) {
		COMPANY_CODE_FK = cOMPANY_CODE_FK;
	}

}
