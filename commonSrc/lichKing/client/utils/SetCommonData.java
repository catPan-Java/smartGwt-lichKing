package lichKing.client.utils;

import java.util.Date;

import lichKing.client.entity.BaseDomain;
import lichKing.client.entityAnnotation.MyClassTypeUtil;
import lichKing.client.server.AppParsServer;

import com.gwtent.reflection.client.ClassType;

/**
 * java对象中的共有字段，进行通用的赋值
 * @author catPan
 */
public class SetCommonData {

	
	public static BaseDomain setData(BaseDomain d){
		ClassType classType=MyClassTypeUtil.getDomainClassType(d.getClass());
        //注入新增用户ID，新增时间数据库CURRENT_TIMESTAMP自动保存
        if(PojoCastRecord.isHadDomainField(d.getClass(), "CREATE_USER")){
  		  if(classType.invoke(d, "getCREATE_USER")==null){
  			  classType.invoke(d, "setCREATE_USER", AppParsServer.getClientUser().getUSER_ID());
  		  }else{//Modify
                //注入修改用户ID
                if(PojoCastRecord.isHadDomainField(d.getClass(), "UPDATE_USER")){
              	  classType.invoke(d, "setUPDATE_USER", AppParsServer.getClientUser().getUSER_ID());
                }
                //注入修改时间
                if(PojoCastRecord.isHadDomainField(d.getClass(), "UPDATE_DATE")){
              	  classType.invoke(d, "setUPDATE_DATE", new Date());
                }
  		  }
        }
        //注入公司ID
//        if(classType.invoke(d, "get"+PojoCastRecord.getDomainPrimaryKey(d.getClass()))==null){
//            if(PojoCastRecord.isHadDomainField(d.getClass(), "COMPANY_CODE_FK")
//          		  && classType.invoke(d, "getCOMPANY_CODE_FK")==null){
//                if(AppParsServer.getClientUser()!=null&&AppParsServer.getCompany()!=null){
//                		classType.invoke(d, "setCOMPANY_CODE_FK",AppParsServer.getCompany().getCOMPANY_CODE_ID());
//                }
//            }
//        }
        
        return d;
	}
}
