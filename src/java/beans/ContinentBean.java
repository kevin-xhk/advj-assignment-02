
package beans;

import dao.ContinentDao;
import entity.Continent;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean(name = "continentBean")
@SessionScoped
public class ContinentBean implements Serializable {

    private Continent continent;
    private List<Continent> continents;
    @EJB
    private ContinentDao continentDao;
    
    @PostConstruct
    public void init() {
    this.continents=getRead();
    }

    public List<Continent> getContinents() {
        return continents;
    }

    public void setContinents(List<Continent> continents) {
        this.continents = continents;
    }
    
    public void create() {
        this.continentDao.create(continent);
        this.continent = new Continent();
    }

    public List<Continent> getRead() {
        return this.continentDao.findAll();
    }

    public void updateForm(Continent x) {
        this.continent = x;
    }

    public void update() {
        this.continentDao.edit(continent);
        this.continent = new Continent();
    }

    public void delete(Continent x) {
        this.continentDao.remove(x);
    }

    public Continent getContinent() {
        if (this.continent == null) {
            return this.continent = new Continent();
        }
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

}
