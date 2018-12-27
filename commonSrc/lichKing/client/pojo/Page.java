package lichKing.client.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author catPan
 */
public class Page implements Serializable{
    
    private static final long serialVersionUID = 2408393311956506541L;

    private int firstResult;
    private int maxResults;
    
    //"set @ac='d2';";
    public String viewParm;
    
    private ArrayList<PageSql> pageSql;
    
    //Builder pattern
    public static class Builder {
        
        private int firstResult=0;
        private int maxResults=15;
        private String viewParm;
        private ArrayList<PageSql> pageSql=new ArrayList<PageSql>();
        
        public Builder firstResult(int val){
            firstResult = val;
            return this;
        }
        public Builder maxResults(int val){
            maxResults = val;
            return this;
        }
        public Builder pageSql(ArrayList<PageSql> val){
            pageSql = val;
            return this;
        }
        public Builder viewParm(String val){
        	viewParm = val;
            return this;
        }
        /**
         * 绑定参数
         * @return 
         */
        public Page build(){
            return new Page(this);
        }
    }
    /**
     * 通过Builder模式绑定页面sql的参数，避免参数过多带来的麻烦
     * @param builder 
     */
    public Page(Builder builder){
        firstResult=builder.firstResult;
        maxResults=builder.maxResults;
        pageSql=builder.pageSql;
        viewParm=builder.viewParm;
    }

    public Page() {
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public ArrayList<PageSql> getPageSql() {
        return pageSql;
    }

    public void setPageSql(ArrayList<PageSql> pageSql) {
        this.pageSql = pageSql;
    }

	public String getViewParm() {
		return viewParm;
	}

	public void setViewParm(String viewParm1) {
		viewParm = viewParm1;
	}
	
    }
