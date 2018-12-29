/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lichKing.client.utils;

/**
 *把类的全路径名转成类名
 * @author catPan
 */
public class OnlyClassName {
    /**
     * 类的全路径名，去掉包名
     * @param classPath 类的全路径名的字符串
     * @return 类名的字符串
     */
    public static String removePackage(String classPath){
        int nameStartIdx=classPath.lastIndexOf(".");
        return classPath.substring(nameStartIdx+1);
    }
}
