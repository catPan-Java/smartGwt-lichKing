/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lichKing.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

/**
 *
 * @author catPan
 */
@Aspect
public class SysLogAspect {

    private ISysLogDAO sysLogDAO;

    public ISysLogDAO getSysLogDAO() {
        return sysLogDAO;
    }

    public void setSysLogDAO(ISysLogDAO sysLogDAO) {
        this.sysLogDAO = sysLogDAO;
    }
//    private XT_Log clientLog;
//    @Before("execution(* ans.server.service.impl.XTYHService.*(..))")

//    @Before("execution(* ans.server.service.impl.*.*(..))")
//    public void doBeforeFunction(JoinPoint jp) {
//
//        System.out.println("--------------- 前置通知-------------------");
//        System.out.println("获取类名：" + jp.getTarget().getClass().getName());
//        if (jp.getArgs().length > 0) {
//            System.out.println("");
//            System.out.println("开始获取XT_Log参数：");
//            if (jp.getArgs()[0] != null && jp.getArgs()[0].getClass() == XT_Log.class) {
//                System.out.println("正在获取XT_Log参数：");
//                clientLog = (XT_Log) jp.getArgs()[0];
//                clientLog.setFunction("方法:"+jp.getSignature().getName());
//                sysLogDAO.saveXTLog(clientLog);
//            }else{
//                System.out.println("XT_Log参数为空，或不是XT_Log类型！");
//            }
//        } else {
//            System.out.println("没有参数");
//        }
//        System.out.println("--------------- end-------------------");
//    }


//    @AfterThrowing(pointcut="execution(* ans.server.dao.jpa.XTYHDAO.*(..))",throwing="ex")
    @AfterThrowing(pointcut = "execution(* ans.server.service.impl.*.*(..))", throwing = "ex")
    public void catchException(JoinPoint jp, Exception ex) {
        System.out.println("获取service抛出的异常：" + ex);

//        if (jp.getArgs().length > 0) {
//            System.out.println("开始获取XT_Log参数：");
//            if (jp.getArgs()[0] != null && jp.getArgs()[0].getClass() == XT_Log.class) {
//                System.out.println("正在获取XT_Log参数：");
//                clientLog = (XT_Log) jp.getArgs()[0];
//                clientLog.setFunction("方法:" + jp.getSignature().getName());
//                clientLog.setLogLevel(3);//"INFO","WARN","ERROR","EXCEPTION";
//                clientLog.setErrorInfo(ex.toString());
//                sysLogDAO.saveXTLog(clientLog);
//            }else{
//                System.out.println("XT_Log参数为空，或不是XT_Log类型！");
//            }
//        } else {
//            System.out.println("没有参数");
//        }
    }
}
