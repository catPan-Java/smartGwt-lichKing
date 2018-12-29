package lichKing.log;

import lichKing.client.entity.SYS_Log;

/**
 * 
 * @author catPan
 *
 */
public interface ISysLogDAO {

    public static final String NAME = "sysLogDAO";

    public void saveSysLog(SYS_Log sysLog);
}
