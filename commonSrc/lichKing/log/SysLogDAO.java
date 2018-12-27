package lichKing.log;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lichKing.client.entity.SYS_Log;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author catPan
 *
 */
public class SysLogDAO implements ISysLogDAO {

    private EntityManager _entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        _entityManager = entityManager;
    }

    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void saveSysLog(SYS_Log sysLog) {
        _entityManager.merge(sysLog);
    }

}
