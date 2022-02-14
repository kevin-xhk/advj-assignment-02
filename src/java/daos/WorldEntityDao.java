
package dao;

import entity.Continent;
import entity.Country;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class WorldEntityDao extends BaseDao<WorldEntity> {

    @PersistenceContext(unitName = "jpaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WorldEntityDao() {
        super(WorldEntity.class);
    }

    @Override
    public void remove(Country entity) {
        this.getEntityManager().createNamedQuery("CovidReport.removeByIsoCode")
                .setParameter("isoCode", entity.getWorldEntityIsoCode())
                .executeUpdate();
        getEntityManager()
                .remove(getEntityManager().merge(entity));
    }

    public List<WorldEntity> findByWorldEntityIsoCode(String isoCode) {
        return this.getEntityManager().createNamedQuery("WorldEntity.findByIsoCode")
                .setParameter("isoCode", isoCode)
                .getResultList();
    }

}
