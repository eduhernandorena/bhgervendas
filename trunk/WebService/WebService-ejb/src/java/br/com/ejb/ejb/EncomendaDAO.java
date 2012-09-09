package br.com.ejb.ejb;

import br.com.ejb.bean.Encomenda;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Eduardo Hernandorena
 */
@Stateless
public class EncomendaDAO implements EncomendaDAORemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Encomenda encom) {
        em.persist(encom);
    }

    @Override
    public void update(Encomenda encom) {
        em.merge(encom);
    }

    @Override
    public void remove(Encomenda encom) {
        em.remove(encom);
    }
}
