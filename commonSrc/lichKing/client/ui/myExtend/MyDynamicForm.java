package lichKing.client.ui.myExtend;

import java.util.ArrayList;
import java.util.List;

import lichKing.client.pojo.PageSql;

import com.smartgwt.client.widgets.form.DynamicForm;

/**
 * 
 * @author catPan
 *
 */
public class MyDynamicForm extends DynamicForm {
	
	private boolean edited=false;
	private List<PageSql> pageSqlList=new ArrayList();

	/**
	 * 是否被编辑过
	 * @return
	 */
	public boolean isEdited() {
		return edited;
	}

	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	public MyDynamicForm() {
	}

	/**
	 * 通用查询表单的pagesql
	 * @return
	 */
	public List<PageSql> getPageSqlList() {
		return pageSqlList;
	}

	public void setPageSqlList(List<PageSql> pageSqlList) {
		this.pageSqlList = pageSqlList;
	}

	
}
