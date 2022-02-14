/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="worldentities")
@NamedQueries({
        @NamedQuery(name="WorldEntity.findByIsoCode",
                query="Select t1 FROM WorldEntity t1 WHERE t1.worldEntityIsoCode = :isoCode"),
})
public class WorldEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "countryIsoCode")
    private String worldEntityIsoCode;

    private String location;
    @Basic(optional = false)
    @Column(name = "continentId")
    private Integer continentId;
    private Long population;
    private Double medianAge;

    //define relationship
    @PrimaryKeyJoinColumn(name = "continentId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Continent continent;

    @Transient
    private boolean editable = false;

    public WorldEntity(){}

    public WorldEntity(String WorldEntityIsoCode, String location,
                   Integer continentId, Long population, Double medianAge) {
        this();
        this.worldEntityIsoCode = worldEntityIsoCode;
        this.location = location;
        this.continentId = continentId;
        this.population = population;
        this.medianAge = medianAge;
    }

    public boolean isEditable() {
        return editable;
    }
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    // getters - setters
    public Continent getContinent() {
        return continent;
    }
    public void setContinent(Continent continent) {
        this.continent = continent;
    }
    public String getWorldEntityIsoCode() {
        return worldEntityIsoCode;
    }
    public void setWorldEntityIsoCode(String worldEntityIsoCode) {
        this.worldEntityIsoCode = countryIsoCode;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public Integer getContinentId() {
        return continentId;
    }
    public void setContinentId(Integer continentId) {
        this.continentId = continentId;
    }
    public Long getPopulation() {
        return population;
    }
    public void setPopulation(Long population) {
        this.population = population;
    }
    public Double getMedianAge() {
        return medianAge;
    }
    public void setMedianAge(Double medianAge) {
        this.medianAge = medianAge;
    }


    // Object methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorldEntity that = (WorldEntity) o;
        return Objects.equals(worldEntityIsoCode, that.worldEntityIsoCode);
    }
    public int hashCode() {
        return Objects.hash(countryIsoCode);
    }
    @Override
    public String toString() {
        return "WorldEntity{" +
                "iso_code='" + countryIsoCode + '\'' +
                ", location='" + location + '\'' +
                ", continent_id='" + continentId + '\'' +
                ", population=" + population +
                ", median_age=" + medianAge +
                '}';
    }
}
