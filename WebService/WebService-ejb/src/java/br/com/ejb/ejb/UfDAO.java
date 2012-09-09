package br.com.ejb.ejb;

import br.com.ejb.bean.UF;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Eduardo Hernandorena
 */
@Stateless
public class UfDAO implements UfDAORemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(UF uf) {
        em.persist(uf);
    }

    @Override
    public void remove(UF uf) {
        em.remove(uf);
    }

    @Override
    public void update(UF uf) {
        em.merge(uf);
    }
}
