
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Embeddable;


@Embeddable
public class CovidReportKey implements Serializable{

    //properties that will function as a composite key
    private String isoCode;
    private Date date;

    //getters - setters
    public String getIsoCode() {
        return isoCode;
    }
    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    //overridden Object methods
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.isoCode);
        hash = 79 * hash + Objects.hashCode(this.date);
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
        final CovidReportKey other = (CovidReportKey) obj;
        if (!Objects.equals(this.isoCode, other.isoCode)) {
            return false;
        }
        return Objects.equals(this.date, other.date);
    }
    
    
    
}
