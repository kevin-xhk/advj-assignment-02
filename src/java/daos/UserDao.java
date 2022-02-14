
package dao;

import entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class UserDao extends BaseDao<User> {
    
    @PersistenceContext(unitName = "jpaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserDao() {
        super(User.class);
    }
   
    public List<User> login(String email) {
            return this.getEntityManager().createNamedQuery("User.findByEmail")
                    .setParameter("email", email)
                    .getResultList();
    }
    
}
