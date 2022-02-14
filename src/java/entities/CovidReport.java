
package entities;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="covids")
@IdClass(CovidReportKey.class)
@NamedNativeQueries({
         @NamedNativeQuery(name = "CovidReport.findByIsoCode", query = "SELECT t1.* FROM covids t1 JOIN (SELECT isocode, max(date) maxdate FROM Covids GROUP BY isocode) t2 ON t1.isocode = t2.isocode AND t1.date = t2.maxdate",
                    resultClass=CovidReport.class
         )
})
@NamedQueries({
    @NamedQuery(name="CovidReport.removeByIsoCode",
                query="DELETE FROM CovidReport t1 WHERE t1.isoCode = :isoCode"),
    @NamedQuery(name="CovidReport.getByCompositeKeyte",
                query="Select t1 FROM CovidReport t1 WHERE t1.isoCode = :isoCode AND t1.date = :date"),
    @NamedQuery(name="CovidReport.getByIsoCode",
                query="Select t1 FROM CovidReport t1 WHERE t1.isoCode = :isoCode"),
    @NamedQuery(name="CovidReport.getByContinent",
                query="Select distinct t1.isoCode FROM CovidReport t1 WHERE t1.country.continent.continentName = :continent"),
}) 
public class CovidReport implements Serializable {

    private static final long serialVersionUID = 1L;

    //composite key
    @Id
    private String isoCode;
    @Id
    private Date date;

    //rest of the columns
    private Long totalCases;
    private Long newCases;
    private Double newCasesSmoothed;
    private Long totalDeaths;
    private Long newDeaths;
    private Double newDeathsSmoothed;
    private Double reproductionRate;
    private Long newTests;
    private Long totalTests;
    private Double stringencyIndex;
    private Double newDeathsPerCase;
    
    @PrimaryKeyJoinColumn(name = "isoCode", referencedColumnName = "countryIsoCode")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Country country;

    public CovidReport(){}

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    // getters - setters
    public Country getCountry() {
        return country;
    }
    public void setCountry(Country country) {
        this.country = country;
    }
    public String getIsoCode() {
        return isoCode;
    }
    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }
    public Date getDate(){
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Long getTotalCases() {
        return totalCases;
    }
    public void setTotalCases(Long totalCases) {
        this.totalCases = totalCases;
    }
    public Long getNewCases() {
        return newCases;
    }
    public void setNewCases(Long newCases) {
        this.newCases = newCases;
    }
    public Double getNewCasesSmoothed() {
        return newCasesSmoothed;
    }
    public void setNewCasesSmoothed(Double newCasesSmoothed) {
        this.newCasesSmoothed = newCasesSmoothed;
    }
    public Long getTotalDeaths() {
        return totalDeaths;
    }
    public void setTotalDeaths(Long totalDeaths) {
        this.totalDeaths = totalDeaths;
    }
    public Long getNewDeaths() {
        return newDeaths;
    }
    public void setNewDeaths(Long newDeaths) {
        this.newDeaths = newDeaths;
    }
    public Double getNewDeathsSmoothed() {
        return newDeathsSmoothed;
    }
    public void setNewDeathsSmoothed(Double newDeathsSmoothed) {
        this.newDeathsSmoothed = newDeathsSmoothed;
    }
    public Double getReproductionRate() {
        return reproductionRate;
    }
    public void setReproductionRate(Double reproductionRate) {
        this.reproductionRate = reproductionRate;
    }
    public Long getNewTests() {
        return newTests;
    }
    public void setNewTests(Long newTests) {
        this.newTests = newTests;
    }
    public Long getTotalTests() {
        return totalTests;
    }
    public void setTotalTests(Long totalTests) {
        this.totalTests = totalTests;
    }
    public Double getStringencyIndex() {
        return stringencyIndex;
    }
    public void setStringencyIndex(Double stringencyIndex) {
        this.stringencyIndex = stringencyIndex;
    }
    public Double getNewDeathsPerCase() {
        return newDeathsPerCase;
    }
    public void setNewDeathsPerCase(Double newDeathsPerCase) {
        this.newDeathsPerCase = newDeathsPerCase;
    }

    // Object methods
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.isoCode);
        hash = 83 * hash + Objects.hashCode(this.date);
        return hash;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CovidReport other = (CovidReport) obj;
        if (!Objects.equals(this.isoCode, other.isoCode)) {
            return false;
        }
        return Objects.equals(this.date, other.date);
    }
    @Override
    public String toString() {
        return "CovidReport{" + "isoCode=" + isoCode + ", date=" + date + ", totalCases=" + totalCases + ", newCases=" + newCases + ", newCasesSmoothed=" + newCasesSmoothed + ", totalDeaths=" + totalDeaths + ", newDeaths=" + newDeaths + ", newDeathsSmoothed=" + newDeathsSmoothed + ", reproductionRate=" + reproductionRate + ", newTests=" + newTests + ", totalTests=" + totalTests + ", stringencyIndex=" + stringencyIndex + ", newDeathsPerCase=" + newDeathsPerCase + '}';
    }
    
    
}
