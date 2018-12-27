package lichKing.server.sysServer.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lichKing.client.entity.APP_COMMON_TYPE;
import lichKing.client.entity.BaseDomain;
import lichKing.client.entityAnnotation.EntityAnn;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 *
 * @author catPan
 */
@Entity
@Table(name = "company")
public class COMPANY extends BaseDomain {
    
    private static final long serialVersionUID = -5611131462008954L;
    public static String ORDERBY_COL="COMPANY_CODE_ID desc";

    @EntityAnn(ResourceKey = "COMPANY_CODE_ID", GOrderNum = 1, IsPrimaryKey = true,IsSelectCode=true,GWidth="60")
    private String COMPANY_CODE_ID;//	公司ID	uuid
    @EntityAnn(ResourceKey = "COMPANY_NAME", GOrderNum = 2,GWidth="250", FOrderNum = 1, IsNullable = false,IsSelectText=true,Length=20)
    private String COMPANY_NAME;//	公司名字	Varchar
    @EntityAnn(ResourceKey = "FOR_SHORT", GOrderNum = 3, FOrderNum = 2, IsNullable = false, Length = 16)
    private String FOR_SHORT;// 简称
    @EntityAnn(ResourceKey = "COMPANY_TYPE", GOrderNum = 4,  FOrderNum = 3, IsNullable = false,IsCommonSelect = true,GWidth="80")
    private String COMPANY_TYPE;//	公司类型	uuid
    @EntityAnn(ResourceKey = "ADDRESS", GOrderNum = 5,GWidth="100%",  FOrderNum = 5, IsNullable = false,Length=50)
    private String ADDRESS;//	公司地址	Varchar
    @EntityAnn(ResourceKey = "LINKMAN", GOrderNum = 7,  FOrderNum = 7, IsNullable = false,GWidth="70")
    private String LINKMAN;//	联系人	Varchar
    @EntityAnn(ResourceKey = "PHONE", GOrderNum = 9,  FOrderNum = 9, IsNullable = false,Length=13)
    private String PHONE;//	手机号	Varchar
    @EntityAnn(ResourceKey = "FAX", FOrderNum = 11,Length=13)
    private String FAX;//	传真号	Varchar
    @EntityAnn(ResourceKey = "TELEPHONE", FOrderNum = 13,Length=13)
    private String TELEPHONE;//	固定电话	Varchar
    @EntityAnn(ResourceKey = "POSTAL_CODE",  FOrderNum = 15,Length=10)
    private String POSTAL_CODE;//	邮编	Varchar
    @EntityAnn(ResourceKey = "EMAIL",  FOrderNum = 17,Length=40)
    private String EMAIL;//	邮箱	varchar
    @EntityAnn(ResourceKey = "QQ",  FOrderNum = 19,Length=13)
    private String QQ;//	QQ	varchar
    @EntityAnn(ResourceKey = "MSN",  FOrderNum = 21,Length=40)
    private String MSN;//	MSN	varchar
    @EntityAnn(ResourceKey = "VALD_FLAG")
    private String VALD_FLAG="1";//	是否有效	Varchar
    @EntityAnn(ResourceKey = "MEMO",  FOrderNum = 25,Length=200)
    private String MEMO;//	备注	memo
    
    @EntityAnn(ResourceKey = "OPEN_WAY",  GOrderNum = 15,IsfixMapSelectItem=true)
    private String OPEN_WAY;// 开通方式
    @EntityAnn(ResourceKey = "CHARTER")
    private String CHARTER;// 营业执照ID
    @EntityAnn(ResourceKey = "FILEUPLOAD")
    private String FILEUPLOAD;// 上传图片
    @EntityAnn(ResourceKey = "PRIVILEGED_TIME",FieldType="integer")
    private int PRIVILEGED_TIME;// 授权时间
    @EntityAnn(ResourceKey = "REGISTER_DATE", GOrderNum = 22,FieldType="date",GWidth="80")
    private Date REGISTER_DATE;// 注册日期
    @EntityAnn(ResourceKey = "REGISTER_IP")
    private String REGISTER_IP;// 注册IP
    @EntityAnn(ResourceKey = "OPEN_DATE",FieldType="date")
    private Date OPEN_DATE;// 开通日期
    @EntityAnn(ResourceKey = "STATE", GOrderNum = 23,IsfixMapSelectItem=true,GWidth="60")
    private String STATE="1";//是否可用	Varchar				冻结、启用

    @EntityAnn(ResourceKey = "PROVINCE")
    private String PROVINCE;//	省
    @EntityAnn(ResourceKey = "CITY",FOrderNum=10,IsNullable=false)
    private String CITY;//	市

	/**
	 * 省
	 * @return
	 */
	public String getPROVINCE() {
		return PROVINCE;
	}

	public void setPROVINCE(String pROVINCE) {
		PROVINCE = pROVINCE;
	}

	/**
	 * 市
	 * @return
	 */
	public String getCITY() {
		return CITY;
	}

	public void setCITY(String cITY) {
		CITY = cITY;
	}

	/**
	 * 是否可用 冻结、启用
	 * @return
	 */
    @Column(name="COMPANY_STATE")
	public String getSTATE() {
		return STATE;
	}

	public void setSTATE(String cOMPANY_STATE) {
		STATE = cOMPANY_STATE;
	}
	@EntityAnn(ResourceKey = "APP_COMMON_TYPE", DomainMap="oto",JoinNameKey={"COMMON_TYPE_NAME"})
	private APP_COMMON_TYPE APP_COMMON_TYPE;


	@OneToOne
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="COMPANY_TYPE",referencedColumnName="code_id",insertable=false,updatable=false)
	public APP_COMMON_TYPE getAPP_COMMON_TYPE() {
		return APP_COMMON_TYPE;
	}

	public void setAPP_COMMON_TYPE(APP_COMMON_TYPE aPP_COMMON_TYPE) {
		APP_COMMON_TYPE = aPP_COMMON_TYPE;
	}
	
	@Id
//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "ans.core.utils.UUIDGenerator32")
    @Column(name = "COMPANY_CODE_ID", nullable = false, length = 6)
	public String getCOMPANY_CODE_ID() {
		return COMPANY_CODE_ID;
	}

	public void setCOMPANY_CODE_ID(String cOMPANY_CODE_ID) {
		COMPANY_CODE_ID = cOMPANY_CODE_ID;
	}

	/**
	 * 公司名字
	 * @return
	 */
	public String getCOMPANY_NAME() {
		return COMPANY_NAME;
	}

	public void setCOMPANY_NAME(String cOMPANY_NAME) {
		COMPANY_NAME = cOMPANY_NAME;
	}
	/**
	* 简称
	* @return
	*/
	@Column(length = 16, nullable = false)
	public String getFOR_SHORT() {
		 return FOR_SHORT;
	}
	public void  setFOR_SHORT(String FOR_SHORT1) {
		FOR_SHORT = FOR_SHORT1;
	}
	/**
	 * 公司类型
	 * @return
	 */
	public String getCOMPANY_TYPE() {
		return COMPANY_TYPE;
	}

	public void setCOMPANY_TYPE(String cOMPANY_TYPE) {
		COMPANY_TYPE = cOMPANY_TYPE;
	}

	/**
	 * 公司地址
	 * @return
	 */
	public String getADDRESS() {
		return ADDRESS;
	}

	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}

	/**
	 * 联系人
	 * @return
	 */
	public String getLINKMAN() {
		return LINKMAN;
	}

	public void setLINKMAN(String lINKMAN) {
		LINKMAN = lINKMAN;
	}

	/**
	 * 手机号
	 * @return
	 */
	public String getPHONE() {
		return PHONE;
	}

	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}

	/**
	 * 传真号
	 * @return
	 */
	public String getFAX() {
		return FAX;
	}

	public void setFAX(String fAX) {
		FAX = fAX;
	}

	/**
	 * 固定电话
	 * @return
	 */
	public String getTELEPHONE() {
		return TELEPHONE;
	}

	public void setTELEPHONE(String tELEPHONE) {
		TELEPHONE = tELEPHONE;
	}

	/**
	 * 邮编
	 * @return
	 */
	public String getPOSTAL_CODE() {
		return POSTAL_CODE;
	}

	public void setPOSTAL_CODE(String pOSTAL_CODE) {
		POSTAL_CODE = pOSTAL_CODE;
	}

	/**
	 * EMAIL
	 * @return
	 */
	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	/**
	 * QQ
	 * @return
	 */
	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
	}

	/**
	 * MSN
	 * @return
	 */
	public String getMSN() {
		return MSN;
	}

	public void setMSN(String mSN) {
		MSN = mSN;
	}

	/**
	 * 是否有效
	 * @return
	 */
	public String getVALD_FLAG() {
		return VALD_FLAG;
	}

	public void setVALD_FLAG(String vALD_FLG) {
		VALD_FLAG = vALD_FLG;
	}

	/**
	 * 备注
	 * @return
	 */
	public String getMEMO() {
		return MEMO;
	}

	public void setMEMO(String mEMO) {
		MEMO = mEMO;
	}

	/**
	* 开通方式
	* @return
	*/
	@Column(length = 32, nullable = false)
	public String getOPEN_WAY() {
		 return OPEN_WAY;
	}
	public void  setOPEN_WAY(String OPEN_WAY1) {
		OPEN_WAY = OPEN_WAY1;
	}
	
	/**
	* 营业执照ID
	* @return
	*/
	@Column(length = 32)
	public String getCHARTER() {
		 return CHARTER;
	}
	public void  setCHARTER(String CHARTER1) {
		CHARTER = CHARTER1;
	}
	
	/**
	* 上传图片
	* @return
	*/
	@Column(length = 32)
	public String getFILEUPLOAD() {
		 return FILEUPLOAD;
	}
	public void  setFILEUPLOAD(String FILEUPLOAD1) {
		FILEUPLOAD = FILEUPLOAD1;
	}
	
	/**
	* 授权时间
	* @return
	*/
	@Column(nullable = false)
	public int getPRIVILEGED_TIME() {
		 return PRIVILEGED_TIME;
	}
	public void  setPRIVILEGED_TIME(int PRIVILEGED_TIME1) {
		PRIVILEGED_TIME = PRIVILEGED_TIME1;
	}
	
	/**
	* 注册日期
	* @return
	*/
	@Column(nullable = false)
	public Date getREGISTER_DATE() {
		 return REGISTER_DATE;
	}
	public void  setREGISTER_DATE(Date REGISTER_DATE1) {
		REGISTER_DATE = REGISTER_DATE1;
	}
	
	/**
	* 注册IP
	* @return
	*/
	@Column(length = 15, nullable = false)
	public String getREGISTER_IP() {
		 return REGISTER_IP;
	}
	public void  setREGISTER_IP(String REGISTER_IP1) {
		REGISTER_IP = REGISTER_IP1;
	}
	
	/**
	* 开通日期
	* @return
	*/
	@Column()
	public Date getOPEN_DATE() {
		 return OPEN_DATE;
	}
	public void  setOPEN_DATE(Date OPEN_DATE1) {
		OPEN_DATE = OPEN_DATE1;
	}

}
