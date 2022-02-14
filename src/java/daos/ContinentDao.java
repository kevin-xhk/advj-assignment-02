
package dao;

import entity.Continent;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class ContinentDao extends BaseDao<Continent> {
    
    @PersistenceContext(unitName = "jpaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContinentDao() {
        super(Continent.class);
    }
    
}
