package lichKing.client.utils;

import java.util.Date;
import java.util.List;

import lichKing.client.datasource.AppDataMap;
import lichKing.client.entityAnnotation.EntityAnn;
import lichKing.client.entityAnnotation.MyClassTypeUtil;
import lichKing.client.server.MsgServer;
import lichKing.client.ui.myExtend.PartsListGrid;

import com.google.gwt.user.client.Timer;
import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.Field;
import com.gwtent.reflection.client.TypeOracle;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;

/**
 * 数据绑定到UI，ListGrid、TreeGird、DynamicForm等
 * @author catPan
 */
public class ResultBuild {

    /**
     * 改变字段显示，通过给定字段的字符串数组
     * @param yourFields 给定字段的字符串数组
     * @param DSFields DataSourceField数组
     */
    public static void changeFieldsShowByFieldNames(String[] yourFields, DataSourceField[] DSFields) {

        for (DataSourceField DSField : DSFields) {
            DSField.setHidden(true);
        }
        for (String myFieldsName : yourFields) {
            for (DataSourceField DSField : DSFields) {
                if (myFieldsName.equals(DSField.getName())) {
                    DSField.setHidden(false);
                    break;
                }
            }
        }
    }

    /**
     * 绑定表单DynamicForm
     * @param domain
     * @param form 
     */
    @SuppressWarnings("rawtypes")
	public static void buildForm(Object domain, DynamicForm form) {

        ClassType classType = TypeOracle.Instance.getClassType(domain.getClass());

        Field[] fields = classType.getFields();
        boolean hasAnnotation = false;
        EntityAnn annotation;
        for (int i = 0; i < fields.length; i++) {
            hasAnnotation = fields[i].isAnnotationPresent(EntityAnn.class);
            if (hasAnnotation) {
                annotation = fields[i].getAnnotation(EntityAnn.class);

//                if (!annotation.IsOpCell()) {
                    if (classType.invoke(domain, "get" + annotation.ResourceKey()) == null) {
                    	form.clearValue(annotation.ResourceKey());
                    }else if (annotation.DomainMap().equals("oto")) {
	                    String[] joinNameKey = annotation.JoinNameKey();
	                    if (joinNameKey.length > 0) {
	                        Object objOto = classType.invoke(domain, "get" + annotation.ResourceKey());
	                        if (objOto != null) {
//	                        	form.setValue(annotation.ResourceKey(),objOto);
	                            ClassType classTypeOto = MyClassTypeUtil.getDomainClassType(objOto.getClass());
	                            if (!joinNameKey[0].equals("onlySelf") && classTypeOto != null) {
	                                for (int j = 0; j < joinNameKey.length; j++) {
	                                	form.setValue(joinNameKey[j],
	                                            (String) classTypeOto.invoke(objOto, "get" + joinNameKey[j]));
	                                }
	                            }
	                        }
	                    }
	                } else {
                        if (annotation.FieldType().equals("boolean")) {
                            if (classType.invoke(domain, "get" + annotation.ResourceKey()).toString().equals("1")) {
                            	form.setValue(annotation.ResourceKey(), true);
                            } else {
                            	form.setValue(annotation.ResourceKey(), false);
                            }
                        } else {
                            if (annotation.FieldType().equals("date")) {
                            	form.setValue(annotation.ResourceKey(), (Date) classType.invoke(domain, "get" + annotation.ResourceKey()));
                            } else if (annotation.FieldType().equals("datetime")) {
                            	form.setValue(annotation.ResourceKey(), (Date) classType.invoke(domain, "get" + annotation.ResourceKey()));
                            } else if (annotation.FieldType().equals("float")) {
                            	form.setValue(annotation.ResourceKey(), (Float) classType.invoke(domain, "get" + annotation.ResourceKey()));
                            }else if (annotation.FieldType().equals("integer")) {
                            	form.setValue(annotation.ResourceKey(), (Integer) classType.invoke(domain, "get" + annotation.ResourceKey()));
                            } else {
                            	form.setValue(annotation.ResourceKey(), classType.invoke(domain, "get" + annotation.ResourceKey()).toString());
                            }
                        }
                    }
//                }
            }
        }
    }

    /**
     * 勾选中TreeGrid的复选框
     * @param result ID的List
     * @param treeGrid 
     */
    public static void selectTreeNodes(List<String> result, TreeGrid treeGrid) {
        int currentSize = result.size();

        if (currentSize > 0) {
            TreeNode[] lgrs = treeGrid.getTree().getAllNodes();
            for (int i = 0; i < currentSize; i++) {
                for (int j = 0; j < lgrs.length; j++) {
                    if (result.get(i).equals(lgrs[j].getAttribute("ID"))) {
                        treeGrid.selectRecord(lgrs[j]);
                        break;
                    }
                }
            }
        }

    }

    /**
     * 勾选中TreeGrid的复选框
     * @param result ID的拼接字符串，“,”间隔
     * @param treeGrid
     */
    public static void selectTreeNodes(String result, TreeGrid treeGrid) {
        String[] ids = result.split(",");
        int currentSize = ids.length;

        if (currentSize > 0) {
            TreeNode[] lgrs = treeGrid.getTree().getAllNodes();
            for (int i = 0; i < currentSize; i++) {
                for (int j = 0; j < lgrs.length; j++) {
                    if (ids[i].equals(lgrs[j].getAttribute("ID"))) {
                        treeGrid.selectRecord(lgrs[j]);
                        break;
                    }
                }
            }
        }

    }

    /**
     * 绑定Tree，到指定节点
     * @param resultList 数据集合List
     * @param tree
     * @param treeNode 要添加的位置节点
     * @param level 不同性质、等级、表的节点的区分
     */
    @SuppressWarnings("rawtypes")
	public static void buildTree(List resultList, Tree tree, TreeNode treeNode, String level) {
        int currentSize = resultList.size();

        if (currentSize > 0) {
            TreeNode record = null;
            TreeNode[] list = new TreeNode[currentSize];
            for (int i = 0; i < currentSize; i++) {
                record = new TreeNode();
                PojoCastRecord.toTreeRecord(resultList.get(i), record, level);
                list[i] = record;
            }
            tree.addList(list, treeNode);
            tree.openAll(treeNode);
//            tree.setData(list);
        } else {
//            tree.setData(new TreeNode[0]);
        }

    }

    /**
     * 绑定Tree，到指定节点
     * @param resultList 数据集合List
     * @param tree
     * @param treeNode 要添加的位置节点
     */
    @SuppressWarnings("rawtypes")
	public static void buildTree(List resultList, Tree tree, TreeNode treeNode) {
        int currentSize = resultList.size();

        if (currentSize > 0) {
            TreeNode record = null;
            TreeNode[] list = new TreeNode[currentSize];
            for (int i = 0; i < currentSize; i++) {
                record = new TreeNode();
                PojoCastRecord.toTreeRecord(resultList.get(i), record);
                list[i] = record;
            }
            tree.addList(list, treeNode);
            tree.openAll(treeNode);
//            tree.setData(list);
        } else {
//            tree.setData(new TreeNode[0]);
        }

    }

    /**
     * 绑定Tree，到Root节点
     * @param resultList 数据集合List
     * @param tree 
     */
    @SuppressWarnings("rawtypes")
	public static void buildTree(List resultList, Tree tree) {
        int currentSize = resultList.size();

        if (currentSize > 0) {
            TreeNode record = null;
            TreeNode[] list = new TreeNode[currentSize];
            for (int i = 0; i < currentSize; i++) {
                record = new TreeNode();
                PojoCastRecord.toTreeRecord(resultList.get(i), record);
                list[i] = record;
            }
            tree.addList(list, tree.getRoot());
//            tree.setData(list);
        } else {
//            tree.setData(new TreeNode[0]);
        }

    }

    /**
     * 绑定Tree，到Root节点
     * @param resultList 数据集合List
     * @param tree
     * @param level 不同性质、等级、表的节点的区分
     */
    @SuppressWarnings("rawtypes")
	public static void buildTree(List resultList, Tree tree, String level) {
        int currentSize = resultList.size();

        if (currentSize > 0) {
            TreeNode record = null;
            TreeNode[] list = new TreeNode[currentSize];
            for (int i = 0; i < currentSize; i++) {
                record = new TreeNode();
                PojoCastRecord.toTreeRecord(resultList.get(i), record, level);
                list[i] = record;
            }
            tree.addList(list, tree.getRoot());
//            tree.setData(list);
        } else {
//            tree.setData(new TreeNode[0]);
        }

    }

    /**
     * 绑定TreeGrid
     * @param resultList
     * @param treeGrid 
     */
    @SuppressWarnings("rawtypes")
	public static void buildTree(List resultList, final TreeGrid treeGrid) {
        int currentSize = resultList.size();

        if (currentSize > 0) {
            TreeNode node = null;
            final TreeNode[] list = new TreeNode[currentSize];
            for (int i = 0; i < currentSize; i++) {
                node = new TreeNode();
                PojoCastRecord.toTreeRecord(resultList.get(i), node);
                list[i] = node;
            }
//                 Timer timer = new Timer() {
//
//                @Override
//                public void run() {
            treeGrid.setData(list);
//                    treeGrid.setEmptyMessage("");
//                }
//            };
//            timer.schedule(1000);
//                tree.addList(list, tree.getRoot());
        } else {
            treeGrid.setData(new TreeNode[0]);
            treeGrid.setEmptyMessage("no records ...");
        }
    }

//    private static TreeNode staticTn=new TreeNode();
//    private static ListGridRecord staticLgr=new ListGridRecord();
    /**
     * 绑定ListGrid数据，延时1秒
     * @param result 结果集
     * @param listGrid
     */
    @SuppressWarnings("rawtypes")
	public static void buildListGrid(List resultList, final ListGrid listGrid) {
        if (resultList != null) {
            int currentSize = resultList.size();

            if (currentSize > 0) {
                final ListGridRecord[] list = new ListGridRecord[currentSize];
                ListGridRecord record = null;
                for (int i = 0; i < currentSize; i++) {
                    record = new ListGridRecord();
                    PojoCastRecord.toListGridRecord(resultList.get(i), record, "Fetch");
                    list[i] = record;
                }
                listGrid.setEmptyMessage(AppDataMap.getLoadingEmptyMessage());
                Timer timer = new Timer() {

                    @Override
                    public void run() {
                        listGrid.setData(list);
                        listGrid.setEmptyMessage("");
                    }
                };
                timer.schedule(400);
                //            listGrid.setData(list);//字段太多，绑定时，影响速度
            } else {
                listGrid.setData(new ListGridRecord[0]);
                listGrid.setEmptyMessage(MsgServer.getMsg("No_data"));
            }
//            listGrid.setPrompt("loadFinish");
        } else {
            listGrid.setData(new ListGridRecord[0]);
            listGrid.setEmptyMessage(MsgServer.getMsg("No_data"));
        }

    }

	public static void buildListGrid(List resultList, final PartsListGrid listGrid) {
        if (resultList != null) {
            int currentSize = resultList.size();

            if (currentSize > 0) {
                final ListGridRecord[] list = new ListGridRecord[currentSize];
                ListGridRecord record = null;
                for (int i = 0; i < currentSize; i++) {
                    record = new ListGridRecord();
                    PojoCastRecord.toListGridRecord(resultList.get(i), record, "Fetch");
                    list[i] = record;
                }
                listGrid.setEmptyMessage(AppDataMap.getLoadingEmptyMessage());
                Timer timer = new Timer() {

                    @Override
                    public void run() {
                        listGrid.setData(list);
                        listGrid.setEmptyMessage("");
                    }
                };
                timer.schedule(400);
                //            listGrid.setData(list);//字段太多，绑定时，影响速度
            } else {
                listGrid.setData(new ListGridRecord[0]);
                listGrid.setEmptyMessage(MsgServer.getMsg("No_data"));
            }
//            listGrid.setPrompt("loadFinish");
        } else {
            listGrid.setData(new ListGridRecord[0]);
            listGrid.setEmptyMessage(MsgServer.getMsg("No_data"));
        }

    }
    
	/**
	 * 
	 * attribute的DATA_SOURCE，等于指定val值时，数据行不可用
	 * @param resultList
	 * @param listGrid
	 * @param val
	 */
	public static void buildListGridRO(List resultList, final ListGrid listGrid,String val) {
        if (resultList != null) {
            int currentSize = resultList.size();

            if (currentSize > 0) {
                final ListGridRecord[] list = new ListGridRecord[currentSize];
                ListGridRecord record = null;
                for (int i = 0; i < currentSize; i++) {
                    record = new ListGridRecord();
                    PojoCastRecord.toListGridRecord(resultList.get(i), record, "Fetch");
                    if(record.getAttribute("DATA_SOURCE")!=null&&record.getAttribute("DATA_SOURCE").equals(val)){// -------
                    	record.setEnabled(false);
                    	
                    }
                    list[i] = record;
                }
                listGrid.setEmptyMessage(AppDataMap.getLoadingEmptyMessage());
                Timer timer = new Timer() {

                    @Override
                    public void run() {
                        listGrid.setData(list);
                        listGrid.setEmptyMessage("");
                    }
                };
                timer.schedule(1000);
                //            listGrid.setData(list);//字段太多，绑定时，影响速度
            } else {
                listGrid.setData(new ListGridRecord[0]);
                listGrid.setEmptyMessage(MsgServer.getMsg("No_data"));
            }
//            listGrid.setPrompt("loadFinish");
        } else {
            listGrid.setData(new ListGridRecord[0]);
            listGrid.setEmptyMessage(MsgServer.getMsg("No_data"));
        }

		
	}
    /**
     * attribute的DATA_SOURCE=0时，数据行不可用
     * @param resultList
     * @param listGrid
     */
	public static void buildListGridRO(List resultList, final ListGrid listGrid) {
		buildListGridRO(resultList, listGrid, "0");
    }

    /**
     * ListGrid绑定数据，浏览器客户端上的数据
     * @param result 结果集
     * @param listGrid
     */
    @SuppressWarnings("rawtypes")
	public static void buildListGridClient(List resultList, final ListGrid listGrid) {
        int currentSize = resultList.size();

        if (currentSize > 0) {
            final ListGridRecord[] list = new ListGridRecord[currentSize];
            ListGridRecord record = null;
            for (int i = 0; i < currentSize; i++) {
                record = new ListGridRecord();
                PojoCastRecord.toListGridRecord(resultList.get(i), record, "Fetch");
                list[i] = record;
            }
            listGrid.setEmptyMessage("");
            listGrid.setData(list);
        } else {
            listGrid.setData(new ListGridRecord[0]);
            listGrid.setEmptyMessage(MsgServer.getMsg("No_data"));
        }

    }

    /**
     * 绑定ListGrid数据，数据来自数据库和其他ListGrid的数据的混合
     * @param resultList 数据库的数据
     * @param listGrid
     * @param copyLgrs 其他ListGrid的数据
     */
    @SuppressWarnings("rawtypes")
	public static void buildListGrid(List resultList, ListGrid listGrid, ListGridRecord[] copyLgrs) {
        int currentSize = resultList.size();

        if (currentSize > 0) {
            ListGridRecord[] list = new ListGridRecord[currentSize + copyLgrs.length];
            System.arraycopy(copyLgrs, 0, list, 0, copyLgrs.length);
            ListGridRecord record = null;
            for (int i = 0; i < currentSize; i++) {
                record = new ListGridRecord();
                PojoCastRecord.toListGridRecord(resultList.get(i), record, "Fetch");
                list[i + copyLgrs.length] = record;
            }
            listGrid.setData(list);
        } else {
            if (copyLgrs.length == 0) {
                listGrid.setData(new ListGridRecord[0]);
            }
        }

    }

    /**
     * 添加ListGrid数据，原来基础上添加
     * @param result 数据对象
     * @param listGrid 
     */
    public static void addToListGrid(Object result, ListGrid listGrid) {

        if (result != null) {
            ListGridRecord record = new ListGridRecord();
            PojoCastRecord.toListGridRecord(result, record, "Fetch");
            listGrid.addData(record);
//            listGrid.refreshFields();
        } else {
            SC.say(MsgServer.getMsg("CanNotHandleANullObject"));
        }

    }

    /**
     * 更新ListGrid的数据行ListGridRecord
     * @param result 单个数据对象
     * @param listGrid
     * @param record 被更新的record
     */
    public static void updateToListGrid(Object result, ListGrid listGrid, ListGridRecord record) {

        if (result != null) {
            PojoCastRecord.updateToListGrid(result, record);
            listGrid.refreshRow(listGrid.getRecordIndex(record));
            listGrid.refreshFields();
        } else {
            SC.say(MsgServer.getMsg("CanNotHandleANullObject"));
        }

    }

    /**
     * 在ListGrid中删除选中的数据行，客户端UI的操作（通常数据库的数据删除成功后调用）
     * @param listGrid 
     */
    public static void deleteSelectListGrid(ListGrid listGrid) {
        listGrid.removeSelectedData();
    }

}