package br.com.ejb.ejb;

import br.com.ejb.bean.Sync;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
        if (sync.getId() != null) {
            sync = em.merge(sync);
        } else {
            em.persist(sync);
        }
        return sync;
    }

    @Override
    public void remove(Sync sync) {
        em.remove(sync);
    }

    @Override
    public List<Sync> sincroniza() {
        Query q = em.createQuery("select o from Sync o where o.sincronizado=false");
        return q.getResultList();
    }

    @Override
    public void atualiza(List<Sync> lista) {
        for (Sync sync : lista) {
            Query q = em.createQuery("update Sync o set o.sincronizado=TRUE where o.id =" + sync.getId());
            q.executeUpdate();
        }
    }
}