package lichKing.client.entityAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
/**
 * 实体类的注释接口，各种概念的模板
 * 如：表单、列表等多种UI元素的生成，数据的绑定，特殊用法概念的定义……
 *
 * @author catPan
 */
public @interface EntityAnn {

	public float maxValue() default 10;

	public float minValue() default 0;
	/**
	 * Text,Slider,Picker
	 * @return
	 */
	public String formUI() default "Text";
	/**
	 * 表单元素后面附加的信息
	 * @return
	 */
	public String hint() default "";
	
	/**
	 * 用于生产空的下来框，多个下拉框级联操作，暂时无法通用
	 * @return
	 */
	public boolean isNullSelect() default false;
    
	/**
	 *  通用查询时候会过滤出自己的数据，--> Builder("USER_ID", "=")
	 * @return
	 */
    public boolean onlyMineData() default false;

    /**
     * form表单元素排列的顺序
     *
     * @return
     */
    public int FOrderNum() default 0;

    /**
     * G -- 》(list\tree)grid表头排列的顺序
     *
     * @return
     */
    public int GOrderNum() default 0;

    /**
     * 是否需要生成，界面上是否要构造
     *
     * @return
     */
    public boolean IsRequired() default true;

    /**
     * 是否要在form中显示该字段
     *
     * @return
     */
    public boolean IsShowF() default true;

    /**
     * 是否要在gird中显示该字段
     *
     * @return
     */
    public boolean IsShowG() default true;

    /**
     * 是否可以为空（必填字段）
     *
     * @return
     */
    public boolean IsNullable() default true;

    /**
     * 字段的类型（text"，"integer"，"boolean"，"date"，"datetime"……）可自动映射组件
     *
     * @return
     */
    public String FieldType() default "text";

    /**
     * 字段长度
     *
     * @return
     */
    public int Length() default 32;

    /**
     * G -- 》(list\tree)grid表头的宽度
     *
     * @return
     */
    public String GWidth() default "100";

    /**
     * 获取多语言的key值
     *
     * @return
     */
    public String ResourceKey() default "ResourceKey";

    /**
     * 是否主键
     *
     * @return
     */
    public boolean IsPrimaryKey() default false;

    /**
     * 是否为Tree的id
     *
     * @return
     */
    public boolean IsTreeId() default false;

    /**
     * 是否为Tree的父id
     *
     * @return
     */
    public boolean IsTreePId() default false;

    /**
     * 是否为Tree的显示列名
     *
     * @return
     */
    public boolean IsTreeName() default false;

    /**
     * 实体类的映射类型，oto,otm,mto,mtm
     *
     * @return
     */
    public String DomainMap() default "noMap";

    /**
     * 映射关联字段的ResourceKey
     *
     * @return
     */
    public String[] JoinNameKey() default {"onlySelf"};

    /**
     * 第二层嵌套关联映射，A.aaid -- B.aaid，B.bbid -- C.bbid，用于获取C的bbName
     *
     * @return
     */
    public boolean Is2ndJoin() default false;

    /**
     * 实体类的名称，也用于区分是从otm/oto对象中获取的字段，不然会被作为主类的对象覆盖掉，参考下面
     * @BaseDomainAnnotation(ResourceKey = "APP_ITEM", IsShowG = false,
     * DomainMap = "oto", JoinNameKey = {"ITEM_NAME", "ITEM_TYPE", "SPEC",
     * "PURCHASE_UNIT"}) @BaseDomainAnnotation(ResourceKey = "ITEM_NAME",
     * GOrderNum = 3, Length = 50,ClassName="APP_ITEM")
     * @BaseDomainAnnotation(ResourceKey = "ITEM_TYPE",IsCommonSelect=true,
     * GOrderNum = 5,ClassName="APP_ITEM")
     *
     * @return
     */
    public String ClassName() default "";
    
    /**
     * 如果ClassName有值时，就不会对该字段赋值，使用实体级联查询，通过实体赋值，所以导致用sql查询，返回pojo中对应的值也不会被赋值
     * 设置成true，来取消ClassName的影响
     * @return
     */
    public boolean isSqlPojoField() default false;

    /**
     * 是否为下拉框 表格编辑，表单中
     *
     * @return
     */
    public boolean IsSelectItem() default false;

    /**
     * 是否为下拉框编号，通常保存的值
     *
     * @return
     */
    public boolean IsSelectCode() default false;

    /**
     * 是否为下拉框文本，显示的内容
     *
     * @return
     */
    public boolean IsSelectText() default false;

    /**
     * 是否为多选下拉框，显示的内容
     *
     * @return
     */
    public boolean IsMupSelectItem() default false;

    /**
     * 是否为固定下拉框
     *
     * @return
     */
    public String[] IsFixSelectItem() default {};
    
    /**
     * 是否为固定、多选下拉框，显示的内容
     *
     * @return
     */
    public boolean IsFixMupSelectItem() default false;

    /**
     * 备用多语言的key值，如下拉框，或关联字段
     *
     * @return
     */
    public String ResourceKey2() default "";

    /**
     *  多个字段共用一个common code，指定的多语言key值<br />
     *  例如：TURN_OUT_TYPE、TURN_IN_TYPE都指向HOUSE_TYPE，获取下拉框的值
     *
     * @return
     */
    public String ResourceKeySame() default "";

    /**
     * 是否为通用数据表中读出的下拉数据字段
     *
     * @return
     */
    public boolean IsCommonSelect() default false;

    /**
     * 是否为固定Map下拉框，调用AppDataMap中的对应resourKey的方法名称
     *
     * @return
     */
    public boolean IsfixMapSelectItem() default false;

    /**
     * 通用表单查询中，字符串查询的类型，like(看注释，用key填充)或= <br /> ("iContains", "LIKE");包含<br />
     * ("iNotContains", "NOT LIKE");不包含<br /> ("iStartsWith", "LIKE");开始于<br />
     * ("iNotStartsWith", "NOT LIKE");非开始于<br /> ("iEndsWith", "LIKE");结束于<br />
     * ("iNotEndsWith", "NOT LIKE");非结束于
     *
     * @return
     */
    public String SearchStringType() default "iContains";
    
    /**
     * 通用查询(IsSelectItem，下拉框的值为某个表的数据)或类似功能中，<br />
     * 需要加入某些字段的过滤，此处为字符串数组，暂用2个维度，第一个为field，第二个为value<br />
     * 例如：带出仓库下来框是，需要区分仓库类别，{"STORAGE_TYPE","97"}
     * @return
     */
    public String[] searchFilterKV() default {};
    
    /**
     * 如果是打印状态，单元格如果为数字类型的会出现0……，查看时变为空字符
     * @return
     */
    public boolean IsPrintCol() default false;
}
