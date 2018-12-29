package lichKing.client.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

import lichKing.client.entityAnnotation.EntityAnn;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author catPan
 *
 */
@Entity
@Table(name = "sys_log")
public class SYS_Log extends BaseDomain{

    private static final long serialVersionUID = 1072709516246015092L;

    @EntityAnn(ResourceKey = "LogXH", IsPrimaryKey = true, IsShowF = false)
    private String LogXH;//   序号
    //client
    @EntityAnn(ResourceKey = "OPpage", FOrderNum = 1, GOrderNum = 1, IsNullable = false, Length = 10)
    private String OPpage;
    @EntityAnn(ResourceKey = "OPtype", FOrderNum = 3, GOrderNum = 4, Length = 10)
    private String OPtype;
    @EntityAnn(ResourceKey = "OPcontent", FOrderNum = 5, GOrderNum =8,  Length = 10)
    private String OPcontent;
    @EntityAnn(ResourceKey = "OPtime", FOrderNum = 9, GOrderNum = 10, Length = 10,FieldType="datetime")
    private Date OPtime;
    @EntityAnn(ResourceKey = "OPuser", FOrderNum = 11,GOrderNum = 12, Length = 10)
    private String OPuser;
    
    //server
    private Integer OPwithTime;
    private String function;
    private Integer LogLevel;
    private String errorInfo;

    public SYS_Log() {
    }

    /**
     * @return 序号
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "ans.core.utils.UUIDGenerator32")
    @Column(name = "LogXH", nullable = false, length = 32)
    public String getLogXH() {
        return LogXH;
    }

    public void setLogXH(String LogXH) {
        this.LogXH = LogXH;
    }


    /**
     * @return 操作内容
     */
    @Column(name = "OPcontent", nullable = true, length = 500)
    public String getOPcontent() {
        return OPcontent;
    }

    public void setOPcontent(String OPcontent) {
        this.OPcontent = OPcontent;
    }

    /**
     * @return 操作页面
     */
    @Column(name = "OPpage", nullable = true, length = 10)
    public String getOPpage() {
        return OPpage;
    }

    public void setOPpage(String OPpage) {
        this.OPpage = OPpage;
    }

    /**
     * @return 操作时间
     */
    @Column(name = "OPtime", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getOPtime() {
        return OPtime;
    }

    public void setOPtime(Date OPtime) {
        this.OPtime = OPtime;
    }

    /**
     * @return 操作类型
     */
    @Column(name = "OPtype", nullable = true, length = 10)
    public String getOPtype() {
        return OPtype;
    }

    public void setOPtype(String OPtype) {
        this.OPtype = OPtype;
    }

    /**
     * @return 操作用户
     */
    @Column(name = "OPuser", nullable = true, length = 32)
    public String getOPuser() {
        return OPuser;
    }

    public void setOPuser(String OPuser) {
        this.OPuser = OPuser;
    }


    /**
     * @return 操作用时
     */
    @Column(name = "OPwithTime", nullable = true, length = 10)
    public Integer getOPwithTime() {
        return OPwithTime;
    }

    public void setOPwithTime(Integer OPwithTime) {
        this.OPwithTime = OPwithTime;
    }

    /**
     * @return 操作函数
     */
    @Column(name = "function", nullable = true, length = 32)
    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    /**
     * @return 日志等级
     */
    @Column(name = "LogLevel", nullable = true, length = 5)
    public Integer getLogLevel() {
        return LogLevel;
    }


    public void setLogLevel(Integer LogLevel) {
        this.LogLevel = LogLevel;
    }

    /**
     * @return 错误异常信息
     */
    @Column(name = "errorInfo", nullable = true, length = 500)
    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

}
