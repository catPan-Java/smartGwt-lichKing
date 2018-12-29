/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lichKing.server.sysServer.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lichKing.client.entity.BaseDomain;
import lichKing.client.entityAnnotation.EntityAnn;

import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author catPan
 */
@Entity
@Table(name = "SYS_USER")
public class SYS_USER extends BaseDomain {
    
    private static final long serialVersionUID = -5611131450562008954L;
    @EntityAnn(ResourceKey = "USER_ID", IsPrimaryKey = true,IsSelectCode=true)
    private String USER_ID;//	Varchar(32)		NOT NULL	id
    @EntityAnn(ResourceKey = "USER_CODE", GOrderNum = 1, FOrderNum = 1, IsNullable = false)
    private String USER_CODE;//	Varchar(20)		NOT NULL	帐号，登陆用
    @EntityAnn(ResourceKey = "USER_PWD", IsShowG = false, FOrderNum = 2,IsNullable = false, Length = 20,formUI="PasswordItem")
    private String USER_PWD;//	varchar(20)		NOT NULL	登录密码
    @EntityAnn(ResourceKey = "USER_NAME", GOrderNum = 3,FOrderNum = 3,  IsNullable = false, Length = 50,IsSelectText=true)
    private String USER_NAME;//	Varchar(50)		NOT NULL	员工姓名
    @EntityAnn(ResourceKey = "USER_NAME2")
    private String USER_NAME2;//	Varchar(50)		NOT NULL	员工姓名

    @EntityAnn(ResourceKey = "EMPNO", GOrderNum = 5,FOrderNum=5, Length = 32)
    private String EMPNO;//	员工编号	Varchar 32
    @EntityAnn(ResourceKey = "SYS_FLAG", GOrderNum = 7, IsfixMapSelectItem=true,GWidth="70",ResourceKey2="USER_TYPE_FK")
    private String SYS_FLAG;//	用户类型	Varchar 32
    @EntityAnn(ResourceKey = "COMPANY_CODE_FK", Length = 6)
    private String COMPANY_CODE_FK;//	公司代码ID	Varchar 6
    @EntityAnn(ResourceKey = "MANAGER", Length = 32)
    private String MANAGER;//	上级	Varchar 32
    @EntityAnn(ResourceKey = "DEPARTMENT_FK", Length = 32)
    private String DEPARTMENT_FK;//	部门	Varchar 32
    @EntityAnn(ResourceKey = "STATION", GOrderNum = 15,Length = 32)
    private String STATION;//	岗位	Varchar 32
    @EntityAnn(ResourceKey = "SEX", GOrderNum = 17,FOrderNum = 17, Length = 1,IsfixMapSelectItem=true,GWidth="60")
    private String SEX;//	性别	Varchar 1
    @EntityAnn(ResourceKey = "STATE", GOrderNum = 19, Length = 1,IsfixMapSelectItem=true)
    private String STATE;//	状态	Varchar 1
    @EntityAnn(ResourceKey = "PHONE",  GOrderNum = 21,FOrderNum = 21, Length = 13)
    private String PHONE;//	手机号	Varchar 13
    @EntityAnn(ResourceKey = "FAX", FOrderNum = 23, Length = 13)
    private String FAX;//	传真号	Varchar 13
    @EntityAnn(ResourceKey = "TELEPHONE", FOrderNum = 25, Length = 13)
    private String TELEPHONE;//	固定电话	Varchar 13
    @EntityAnn(ResourceKey = "POSTAL_CODE", FOrderNum = 27, Length = 10)
    private String POSTAL_CODE;//	邮编	Varchar 10
    @EntityAnn(ResourceKey = "EMAIL", GOrderNum = 29,FOrderNum = 29, Length = 40,GWidth="140")
    private String EMAIL;//	邮箱	varchar 40
    @EntityAnn(ResourceKey = "QQ", GOrderNum = 31,FOrderNum = 31, Length = 13)
    private String QQ;//	QQ	varchar 13
    @EntityAnn(ResourceKey = "MSN", FOrderNum = 33, Length = 40)
    private String MSN;//	MSN	varchar 40
    @EntityAnn(ResourceKey = "VALD_FLAG",FieldType="boolean")
    private String VALD_FLAG="1";//	是否有效	Varchar 1
    @EntityAnn(ResourceKey = "MEMO",FOrderNum = 37, Length = 200)
    private String MEMO;//	备注	memo Varchar 200
    @EntityAnn(ResourceKey = "BAN_LOGIN",FOrderNum=35,FieldType="boolean")
    private String BAN_LOGIN;// 禁止登陆 1:禁止
    
    private Date LoginDate;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "ans.core.utils.UUIDGenerator32")
    @Column(name = "USER_ID", nullable = false, length = 32)
	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}

	/**
	 * 帐号，登陆用
	 * @return
	 */
	@Column(length=20)
	public String getUSER_CODE() {
		return USER_CODE;
	}

	public void setUSER_CODE(String uSER_CODE) {
		USER_CODE = uSER_CODE;
	}

	/**
	 * 登录密码
	 * @return
	 */
	@Column(length=20)
	public String getUSER_PWD() {
		return USER_PWD;
	}

	public void setUSER_PWD(String uSER_PWD) {
		USER_PWD = uSER_PWD;
	}

	/**
	 * 员工姓名
	 * @return
	 */
	@Column(length=50)
	public String getUSER_NAME() {
		return USER_NAME;
	}

	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}

	/**
	 * 员工姓名2
	 * @return
	 */
	@Transient
	public String getUSER_NAME2() {
		return USER_NAME2;
	}

	public void setUSER_NAME2(String uSER_NAME2) {
		USER_NAME2 = uSER_NAME2;
	}

	/**
	 * 员工编号
	 * @return
	 */
	public String getEMPNO() {
		return EMPNO;
	}

	public void setEMPNO(String eMPNO) {
		EMPNO = eMPNO;
	}

	/**
	 * 用户类型
	 * @return
	 */
	@Column(name="USER_TYPE_FK")
	public String getSYS_FLAG() {
		return SYS_FLAG;
	}

	public void setSYS_FLAG(String SYS_FLAG1) {
		SYS_FLAG = SYS_FLAG1;
	}

	/**
	 * 公司代码ID
	 * @return
	 */
	public String getCOMPANY_CODE_FK() {
		return COMPANY_CODE_FK;
	}

	public void setCOMPANY_CODE_FK(String cOMPANY_CODE_FK) {
		COMPANY_CODE_FK = cOMPANY_CODE_FK;
	}

	/**
	 * 上级
	 * @return
	 */
	public String getMANAGER() {
		return MANAGER;
	}

	public void setMANAGER(String mANAGER) {
		MANAGER = mANAGER;
	}

	/**
	 * 部门
	 * @return
	 */
	public String getDEPARTMENT_FK() {
		return DEPARTMENT_FK;
	}

	public void setDEPARTMENT_FK(String dEPARTMENT_FK) {
		DEPARTMENT_FK = dEPARTMENT_FK;
	}

	/**
	 * 岗位
	 * @return
	 */
	public String getSTATION() {
		return STATION;
	}

	public void setSTATION(String sTATION) {
		STATION = sTATION;
	}

	/**
	 * 性别
	 * @return
	 */
	public String getSEX() {
		return SEX;
	}

	public void setSEX(String sEX) {
		SEX = sEX;
	}

	/**
	 * 状态
	 * @return
	 */
	public String getSTATE() {
		return STATE;
	}

	public void setSTATE(String sTATE) {
		STATE = sTATE;
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

	public void setVALD_FLAG(String vALD_FLAG) {
		VALD_FLAG = vALD_FLAG;
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
	 * 禁止登陆
	 * @return
	 */
	@Transient
	public String getBAN_LOGIN() {
		return BAN_LOGIN;
	}
	public void setBAN_LOGIN(String bAN_LOGIN) {
		BAN_LOGIN = bAN_LOGIN;
	}

	@Transient
	public Date getLoginDate() {
		return LoginDate;
	}
	public void setLoginDate(Date loginDate) {
		LoginDate = loginDate;
	}

}
