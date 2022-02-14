
package soap;

import dao.CovidReportDao;
import dao.UserDao;
import entities.CovidReport;
import entity.User;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;


@WebService(serviceName = "covidSOAP")
public class CovidSOAP {

    @EJB
    private CovidReportDao crDao;
    @EJB
    private UserDao uDao;

    @WebMethod(operationName = "getCovidData")
    public List<CovidData> getCovidData(@WebParam(name = "email") String email,
                                        @WebParam(name = "password") String password,
                                        @WebParam(name = "countries") String countries,
                                        @WebParam(name = "continent") String continent) {
        // handle authorization
        if(!doLogin(email, password))
           return null;
       
       if(!(continent==null || "".equals(continent))){
           return getCovidDataByContinent(continent);
       }else if(!countries.isEmpty()){
           if("all".equals(countries)){
               return getAllCovidReports();
           }else{
               return getCovidDataByCountries(countries);
           }
       }else{
           return null;
       }
    }

    private List<CovidData> getAllCovidReports() {
        List<CovidReport> covidReports= this.crDao.findLatestCountry();
        List<CovidData> cd=covidReports.stream()
                             .map(x-> newCovidReport(x))
                             .collect(Collectors.toList());
         return cd;
    }
    
    private List<CovidData> getCovidDataByContinent(String continent) {
        List<CovidReport> covidReports= this.crDao.findLatestCountry();
        List<CovidData> cd=covidReports.stream()
                .filter(x->  x.getCountry().getContinent() != null)
                .filter(x->  x.getCountry().getContinent().getContinentName().equalsIgnoreCase(continent))
                .map(x-> newCovidReport(x))
                .collect(Collectors.toList());
         return cd;
    }
    
    private List<CovidData> getCovidDataByCountries(String countries) {
        List<String> countriesList = Stream.of(countries.split(","))
                                                .collect(Collectors.toList());

        List<CovidReport> covidReports= this.crDao.findLatestCountry();
        List<CovidData> cd=covidReports.stream()
                             .filter(covidReport-> countriesList.stream()
                                           .anyMatch(country-> covidReport.getCountry().getLocation().equals(country))
                                           )
                             .map(covidReport-> newCovidReport(covidReport))
                             .collect(Collectors.toList());
         return cd;
    }
    
    private boolean doLogin(String email, String password){
        List<User> results;
        results = this.uDao.login(email);
        if (results.isEmpty()) {
            return false;
        }
        User user = results.get(0);
        if ( ! password.equals(user.getPassword()) ) {    
            return false;
        }
        return true;
    
    }

    //creates new CovidReport needed
    public static CovidData newCovidReport(CovidReport covidInput){
        CovidData covid = new CovidData();
        covidReport.setIsoCode(covidInput.getIsoCode());
        covidReport.setDate(covidInput.getDate());
        covidReport.setTotalCases(covidInput.getTotalCases());
        covidReport.setNewCases(covidInput.getNewCases());
        covidReport.setNewCasesSmoothed(covidInput.getNewCasesSmoothed());
        covidReport.setTotalDeaths(covidInput.getTotalDeaths());
        covidReport.setNewDeaths(covidInput.getNewDeaths());
        covidReport.setNewDeathsSmoothed(covidInput.getNewDeathsSmoothed());
        covidReport.setReproductionRate(covidInput.getReproductionRate());
        covidReport.setNewTests(covidInput.getNewTests());
        covidReport.setTotalTests(covidInput.getTotalTests());
        covidReport.setStringencyIndex(covidInput.getStringencyIndex());
        covidReport.setNewDeathsPerCase(covidInput.getNewDeathsPerCase());
        return covidReport;
    }
    
}
