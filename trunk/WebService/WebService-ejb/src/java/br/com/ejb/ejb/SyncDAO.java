package br.com.ejb.ejb;

import br.com.ejb.bean.Sync;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Eduardo Hernandorena
 */
@Stateless
public class SyncDAO implements SyncDAORemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Sync create(Sync sync) {
//        if (user.getId() != null) {
//            em.merge(user);
//        } else {
//            em.persist(user);
//        }
//        return user;
        return null;
    }

    @Override
    public void remove(Sync sync) {
//        em.remove(user);
    }

    @Override
    public Sync findByData(Date data) {
//        if (user != null && !user.trim().isEmpty()) {
//            Query q = em.createQuery("select o from Usuario o where o.nome=:user");
//            q.setParameter("user", user);
//            return (Usuario) q.getSingleResult();
//        } else {
//            return null;
//        }
        return null;
    }
}