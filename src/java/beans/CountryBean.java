
package beans;

import dao.CountryDao;
import entity.Country;
import entity.CovidReport;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;



@ManagedBean(name = "countryBean")
@SessionScoped
public class CountryBean implements Serializable {

    private Country country;
    private List<Country> countries;
    @EJB
    private CountryDao countryDao;
    
    
    @PostConstruct
    public void init() {
    this.countries=getRead();
    }
    
    public String editCountry(Country c){
        for(Country co : countries){
            if(co.isEditable()==true){
            return null;
            }
        }
            this.country=c;
	    this.country.setEditable(true);
	    return null;
   }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public void create() {
        if(isValid()){
             setMessage("Country exists, try updating!");
        }else{
            this.countryDao.create(country);
            this.init();
             setMessage("Country created!");
             
        }
    }

public List<Country> getRead() {
        this.countries = this.countryDao.findAll();
        return this.countries; 
    }

    public void updateForm(Country x) {
        this.country = x;
    }
    
    public void setMessage(String message){
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("", new FacesMessage(message));
    }
    
     public boolean isValid() {
        List<Country> countries = this.countryDao.findByCountryIsoCode(country.getCountryIsoCode());
        return !countries.isEmpty();
    }
    

    public void update() {
        if(!isValid()){
             setMessage("Country invalid, try creating!");
        }else{
            this.country.setEditable(false);
            this.countryDao.edit(country);
            setMessage("Country updated!");
            this.country = new Country();
    }
    }

    public void delete(Country x) {
              this.countryDao.remove(x);
              this.init();

    }

    public Country getCountry() {
        if (this.country == null) {
            return this.country = new Country();
        }
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

}
