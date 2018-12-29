/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lichKing.client.entityAnnotation;

import java.util.LinkedHashMap;

import lichKing.client.utils.OnlyClassName;

import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.Constructor;
import com.gwtent.reflection.client.Field;
import com.gwtent.reflection.client.TypeOracle;

/**
 *
 * @author catPan
 */
public class MyClassTypeUtil {
    
    //className和ClassType的map
    @SuppressWarnings("rawtypes")
	private static LinkedHashMap<String, ClassType> domainClassType = new LinkedHashMap<String, ClassType>();
    //className和Class的map
    @SuppressWarnings("rawtypes")
	private static LinkedHashMap<String, Class> domainClass = new LinkedHashMap<String, Class>();
    //className和Field[]的map
    private static LinkedHashMap<String, Field[]> domainClassFields = new LinkedHashMap<String, Field[]>();
    //页面名称
    private static String className = "";
    //ClassType
    @SuppressWarnings("rawtypes")
	private static ClassType classType=null;
    //className和className的map
    private static LinkedHashMap<String, String> domainClassName = new LinkedHashMap<String, String>();
    
    /**
     * 把实体类的类型，字段，classType,放入对应的map，防止重复生成，浪费资源
     * @param domainC 实体类的类型
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void doDomainMap(Class domainC){
        className = OnlyClassName.removePackage(domainC.getName());
        if (domainClass.get(className)==null||!domainClass.get(className).equals(domainC)) {
            domainClass.put(className, domainC);
            classType=TypeOracle.Instance.getClassType(domainC);
            domainClassType.put(className, classType);
//            domainClassFields.put(className, classType.getFields());
            putFields(className);
        }
    }

    /**
     * 获取classType，通过实体类的类型
     * @param domainClass 实体类的类型
     * @return 
     */
    @SuppressWarnings("rawtypes")
	public static ClassType getDomainClassType(Class domainClass) {
        doDomainMap(domainClass);
        return domainClassType.get(className);
    }
    
    /**
     * 获取Field[]，通过实体类的类型
     * @param domainClass 实体类的类型
     * @return 
     */
    @SuppressWarnings("rawtypes")
	public static Field[] getDomainClassFields(Class domainClass){
        doDomainMap(domainClass);
        return domainClassFields.get(className);
    }
    
    /**
     * 获取ClassType，通过实体类类型的字符串（全路径名）
     * @param className
     * @return 
     */
    @SuppressWarnings("rawtypes")
	public static ClassType getDomainClassType(String className) {
        doDomainMap2(className);
        return domainClassType.get(className);
    }
    
    /**
     * 获取Field[]，通过实体类类型的字符串（全路径名）
     * @param className
     * @return 
     */
    public static Field[] getDomainClassFields(String className){
        doDomainMap2(className);
        return domainClassFields.get(className);
    }
    
    @SuppressWarnings({ "rawtypes", "unused" })
	private static Constructor constructor = null;
    /**
     * 把实体类的类型，字段，classType,放入对应的map，防止重复生成，浪费资源
     * @param className 实体类类型的字符串（全路径名）
     */
    public static void doDomainMap2(String className) {
        if (domainClassName.get(className) == null || !domainClassName.get(className).equals(className)) {
            classType = TypeOracle.Instance.getClassType(className);
            constructor = classType.findConstructor(new String[]{});
            domainClassName.put(className, className);
            domainClassType.put(className, classType);
//            domainClassFields.put(className, classType.getFields());
            putFields(className);
        }
    }
    
    /**
     * 将类的字段放入map中，如果父类有反射注释，就把父类的字段也放入反射字段中
     */
    public static void putFields(String className){

//        if(classType.getSuperclass().getDeclaringClass().equals(VIEW_TASK_BASE.class)||
//        		classType.getSuperclass().getDeclaringClass().equals(INPUT_TASK_BASE.class)||
//        		classType.getSuperclass().getDeclaringClass().equals(SAVE_TASK_BASE.class)||
//        		classType.getSuperclass().getDeclaringClass().equals(SAVE_TASK_SIMPLE.class)){
        	Field[] superF=classType.getSuperclass().getFields();
			Field[] subF=classType.getFields();
        	int superFlen=superF.length;
        	int subFlen=subF.length;
        	Field[] Fields=new Field[superFlen+subFlen];

        	for(int i=0;i<superFlen;i++){
        		Fields[i]=superF[i];
        	}
        	for(int i=0;i<subFlen;i++){
        		Fields[superFlen+i]=subF[i];
        	}
        	domainClassFields.put(className, Fields);
//        }else{
//        	domainClassFields.put(className, classType.getFields());
//        }
    }
}
