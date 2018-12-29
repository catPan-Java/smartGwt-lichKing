package lichKing.client.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 *
 * @author catPan
 */
public class PageSql implements Serializable{
    
    private static final long serialVersionUID = -8094668050508618953L;

    private String andOr;
    private String field;
    private String fieldType;
    private String condition;
    private String content;
    private String inList;
    private String leftLevel;
    private String rightLevel;
    private LinkedHashMap<Date,Date> dateBetweenAnd;
    private Date dateCont;
//    private String startD;
//    private String endD;
    private String sqlType;//用于区分，临时添加的（T），还是始终存在的sql参数
    
    //Builder pattern
    public static class Builder {
        
        private final String field;
        private final String condition;

        private String fieldType="text";
        private String andOr = null;
        private String content = null;
        private String inList = null;
        private String leftLevel = null;
        private String rightLevel = null;
        private LinkedHashMap<Date,Date> dateBetweenAnd = new LinkedHashMap<Date, Date>();
        private Date dateCont = null;
//        private String startD="";
//        private String endD="";
        private String sqlType;
        
        /**
         * 页面sql，字段和逻辑关系的绑定
         * @param field
         * @param condition 
         */
        public Builder(String field, String condition) {
            this.field = field;
            this.condition = condition;
        }
        
        /**
         * 字段类型，text、integer、date、datetime
         * @param val
         * @return 
         */
        public Builder fieldType(String val){
            fieldType = val;
            return this;
        }
        /**
         * 逻辑关系，and/or
         * @param val
         * @return 
         */
        public Builder andOr(String val){
            andOr = val;
            return this;
        }
        /**
         * 内容
         * @param val
         * @return 
         */
        public Builder content(String val){
            content = val;
            return this;
        }
        /**
         * 实体关联语句，IN (userRole.SYS_ROLE) role
         * new PageSql.Builder("order.IS_END_COLLECTION", "isNull").inList("IN (domain.APP_SELL_ORDER) order").build()
         * @param val
         * @return 
         */
        public Builder inList(String val){
            inList = val;
            return this;
        }
        /**
         * 条件中的层次，(sys_organ0_.ORGAN_ID=? or P_ID=?)左括号
         * @param val
         * @return 
         */
        public Builder leftLevel(String val){
            leftLevel = val;
            return this;
        }
        /**
         * 条件中的层次，(sys_organ0_.ORGAN_ID=? or P_ID=?)右括号
         * @param val
         * @return 
         */
        public Builder rightLevel(String val){
            rightLevel = val;
            return this;
        }
        
        /**
         * 
         * @param from 开始时间
         * @param to 结束时间
         * @return 
         */
        public Builder dateBetweenAnd(Date from,Date to){
            dateBetweenAnd.put(from, to);
//            if(from!=null){
//                startD=MyDateFormat.dateAndTimeFormat(from, 0);
//            }
//            if(to!=null){
//                endD=MyDateFormat.dateAndTimeFormat(to, 0);
//            }
            return this;
        }
        
        public Builder dateCont(Date val){
            dateCont = val;
            return this;
        }
        /**
         * 用于区分，临时添加的（T），还是始终存在的sql参数
         * @param val
         * @return 
         */
        public Builder sqlType(String val){
        	sqlType = val;
            return this;
        }
        /**
         * 绑定参数
         * @return 
         */
        public PageSql build(){
            return new PageSql(this);
        }
    }
    /**
     * 通过Builder模式绑定页面sql的参数，避免参数过多带来的麻烦
     * @param builder 
     */
    public PageSql(Builder builder){
        andOr = builder.andOr;
        field = builder.field;
        fieldType=builder.fieldType;
        condition = builder.condition;
        content = builder.content;
        inList = builder.inList;
        leftLevel=builder.leftLevel;
        rightLevel=builder.rightLevel;
        dateBetweenAnd=builder.dateBetweenAnd;
        dateCont=builder.dateCont;
//        startD=builder.startD;
//        endD=builder.endD;
        sqlType=builder.sqlType;
    }
    
    
    

    public PageSql() {
    }
//    
//    // from SYS_ORGAN sys_organ0_ where sys_organ0_.ORGAN_TYPE=? and (sys_organ0_.ORGAN_ID=? or P_ID=?)括号
//    /**
//     * 
//     * @param andOr
//     * @param field
//     * @param condition
//     * @param content
//     * @param inList
//     * @param leftLevel
//     * @param rightLevel
//     * @deprecated
//     */
//    @Deprecated
//    public PageSql(String andOr, String field, String condition, String content, String inList,String leftLevel,String rightLevel) {
//        this.andOr = andOr;
//        this.field = field;
//        this.condition = condition;
//        this.content = content;
//        this.inList = inList;
//        this.leftLevel=leftLevel;
//        this.rightLevel=rightLevel;
//    }
//
//    //select userRole from SYS_USER_ROLE userRole,IN (userRole.SYS_ROLE) role where USER_ID=:USER_ID and role.IS_ENABLED='Y'
//    /**
//     * 
//     * @param andOr
//     * @param field
//     * @param condition
//     * @param content
//     * @param inList
//     * @deprecated
//     */
//    @Deprecated
//    public PageSql(String andOr, String field, String condition, String content, String inList) {
//        this.andOr = andOr;
//        this.field = field;
//        this.condition = condition;
//        this.content = content;
//        this.inList = inList;
//    }

    public String getLeftLevel() {
        return leftLevel;
    }

    public void setLeftLevel(String leftLevel) {
        this.leftLevel = leftLevel;
    }

    public String getRightLevel() {
        return rightLevel;
    }

    public void setRightLevel(String rightLevel) {
        this.rightLevel = rightLevel;
    }
    

    public String getAndOr() {
        return andOr;
    }

    public void setAndOr(String andOr) {
        this.andOr = andOr;
    }
    
    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getInList() {
        return inList;
    }

    public void setInList(String inList) {
        this.inList = inList;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public LinkedHashMap<Date, Date> getDateBetweenAnd() {
        return dateBetweenAnd;
    }

    public void setDateBetweenAnd(LinkedHashMap<Date, Date> dateBetweenAnd) {
        this.dateBetweenAnd = dateBetweenAnd;
    }

//    public String getEndD() {
//        return endD;
//    }
//
//    public void setEndD(String endD) {
//        this.endD = endD;
//    }
//
//    public String getStartD() {
//        return startD;
//    }
//
//    public void setStartD(String startD) {
//        this.startD = startD;
//    }

    public Date getDateCont() {
        return dateCont;
    }

    public void setDateCont(Date dateCont) {
        this.dateCont = dateCont;
    }


	public String getSqlType() {
		return sqlType;
	}

	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}

}
