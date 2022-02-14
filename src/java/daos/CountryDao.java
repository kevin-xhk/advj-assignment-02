
package dao;

import entity.Continent;
import entity.Country;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class CountryDao extends BaseDao<Country> {
    
    @PersistenceContext(unitName = "jpaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CountryDao() {
        super(Country.class);
    }
    
    @Override
    public void remove(Country entity) {
        this.getEntityManager().createNamedQuery("CovidReport.removeByIsoCode")
                .setParameter("isoCode", entity.getCountryIsoCode())
                .executeUpdate();
        getEntityManager()
                .remove(getEntityManager().merge(entity));
    }
    
    public List<Country> findByCountryIsoCode(String isoCode) {
    return this.getEntityManager().createNamedQuery("Country.findByIsoCode")
            .setParameter("isoCode", isoCode)
            .getResultList();
    }
    
}
