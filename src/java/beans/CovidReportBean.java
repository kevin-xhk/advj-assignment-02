
package beans;

import dao.CovidReportDao;
import entity.CovidReport;
import java.io.Serializable;
import java.text.ParseException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@ManagedBean(name = "covidReportBean")
@SessionScoped
public class CovidReportBean implements Serializable {

    private CovidReport covidReport;
    private List<CovidReport> covidReports;

    @EJB
    private CovidReportDao covidReportDao;
    
    @PostConstruct
    public void init() {
    this.covidReports=readByIsoCode();
    }


    public void create() {
        if(!isValid()){
            setMessage("Entry exists, try updating!");
        }else{
            this.covidReportDao.create(covidReport);
            this.init();
            setMessage("Entry created!");
        }
    }
    
    public String editCovidReport(CovidReport c){
        for(CovidReport co : covidReports){
            if(co.isEditable()==true){
            return null;
            }
        }
            this.covidReport=c;
	    this.covidReport.setEditable(true);
	    return null;
   }

    public List<CovidReport> getCovidReportList() {
        
        return covidReports;
    }
    
    public List<CovidReport> getRead() {
        this.covidReports = this.covidReportDao.findAll();
        return this.covidReports; 
    }
    
    public List<CovidReport> readByIsoCode() {
        return this.covidReportDao.findLatestCountry();
    }

    public void updateForm(CovidReport x) {
        this.covidReport = x;
    }

    public void update() {
        if(!isValid()){
             setMessage("Invalid entry, please try again!");
        }
        else{
            this.covidReportDao.edit(this.covidReport);
            this.init();
            setMessage("Entry updated!");
            
        }
    }
    public void findByCompositeKey(){
            this.covidReport = this.covidReportDao.findByCompositeKey(
                    this.covidReport.getIsoCode(),
                    this.covidReport.getDate()).get(0);
    }
     public void findByIso(){
            this.covidReport = this.covidReportDao.findByIso(this.covidReport.getIsoCode());
    }
    
    public void delete(){
        if(!isValid()){
             setMessage("Invalid entry, please try again!");
        }else{
            this.covidReport = covidReports.get(0);
            this.covidReportDao.remove(this.covidReport);
            setMessage("Entry removed from database!");
            this.init();
        }

    }
    
    public void setMessage(String message){
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("", new FacesMessage(message));
    }
    
    public boolean isValid(){
        List<CovidReport> covidReports = this.covidReportDao.findByCompositeKey(covidReport.getIsoCode(), covidReport.getDate());
        if(covidReports.isEmpty()){
            return false;
        }else
            return true;
        
        
    }

    public CovidReport getCovidReport() {
        if (this.covidReport == null) {
            return this.covidReport = new CovidReport();
        }
        return covidReport;
    }

    public void setCovidReport(CovidReport covidReport) {
        this.covidReport = covidReport;
    }

}
