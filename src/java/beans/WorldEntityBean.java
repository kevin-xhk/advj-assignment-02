
package beans;

import dao.CountryDao;
import dao.WorldEntityDao;
import entity.Country;
import entity.CovidReport;
import static entity.CovidReport_.country;
import entity.WorldEntity;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;


@ManagedBean(name = "wordlEntityBean")
@SessionScoped
public class WorldEntityBean implements Serializable {

    private WorldEntity worldEntity;
    private List<WorldEntity> worldEntities;
    @EJB
    private WorldEntityDao worldEntityDao;

    @PostConstruct
    public void init() {
        this.worldEntities=getRead();
    }

    public String editWorldEntity(WorldEntity we){
        for(WorldEntity w : worldEntities)
            if(w.isEditable()==true){ return null; }

        this.worldEntity=we;
        this.worldEntity.setEditable(true);
        return null;
    }

    public List<WorldEntity> getworldEntities() {
        return worldEntities;
    }
    public void setworldEntities(List<WorldEntity> worldEntities) {
        this.worldEntities = worldEntities;
    }
    public void create() {
        if(isValid()){
            setMessage("world entity is already present");
        }else{
            this.countryDao.create(country);
            this.init();
            setMessage("creation succesful");

        }
    }
    public List<WorldEntity> getRead() {
        this.worldEntities = this.worldEntityDao.findAll();
        return this.worldEntities;
    }
    public void updateForm(WorldEntity we) {
        this.worldEntity = we;
    }
    public void setMessage(String message){
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("", new FacesMessage(message));
    }
    public boolean isValid() {
        List<WorldEntity> worldEntities =
                this.worldEntityDao.findByWorldEntityIsoCode(
                        worldEntity.getWorldEntityIsoCode());
        return !worldEntities.isEmpty();
    }
    public void update() {
        if(!isValid()){
            setMessage("world entity invalid");
        }else{
            this.worldEntity.setEditable(false);
            this.worldEntityDao.edit(worldEntity);
            setMessage("update successful");
            this.worldEntity = new WorldEntity();
        }
    }
    public void delete(WorldEntity we) {
        this.worldEntityDao.remove(we);
        this.init();

    }
    public WorldEntity getWorldEntity() {
        if (this.worldEntity == null) {
            return this.worldEntity = new WorldEntity();
        }
        return worldEntity;
    }
    public void setWorldEntity(WorldEntity worldEntity) {
        this.worldEntity = worldEntity;
    }
}