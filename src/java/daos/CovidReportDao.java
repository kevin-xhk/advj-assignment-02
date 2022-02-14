
package dao;
import entity.CovidReport;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class CovidReportDao extends BaseDao<CovidReport>{
    
    @PersistenceContext(unitName = "jpaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CovidReportDao() {
        super(CovidReport.class);
    }
    
    public List<CovidReport> findLatestCountry() {
        return this.getEntityManager().createNamedQuery("CovidReport.findByIsoCode")
                .getResultList();
    }
    
    public List<CovidReport> findByCompositeKey(String isoCode, Date date) {
        return this.getEntityManager().createNamedQuery("CovidReport.getByCompositeKeyte")
            .setParameter("isoCode", isoCode)
            .setParameter("date", date)
            .getResultList();
    }
    
    public CovidReport findByIso(String isoCode) {
        return (CovidReport) this.getEntityManager().createNamedQuery("CovidReport.getByIsoCode")
            .setParameter("isoCode", isoCode)
            .getSingleResult();
    }
     
    public List<CovidReport> findByContinent(String continent) {
        return this.getEntityManager().createNamedQuery("CovidReport.getByContinent")
            .setParameter("continent", continent)
            .getResultList();
    }
    
}
