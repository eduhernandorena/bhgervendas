package br.com.ejb.ejb;

import br.com.ejb.bean.Encomenda;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Eduardo Hernandorena
 */
@Stateless
public class EncomendaDAO implements EncomendaDAORemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Encomenda create(Encomenda encom) {
        if (encom.getCodigo() != null) {
            em.merge(encom);
        } else {
            em.persist(encom);
        }
        return encom;
    }

    @Override
    public void remove(Encomenda encom) {
        em.remove(encom);
    }

    @Override
    public List<Encomenda> findAll() {
        TypedQuery<Encomenda> createNamedQuery = em.createNamedQuery("Encomenda.findAll", Encomenda.class);
        return createNamedQuery.getResultList();
    }

    @Override
    public Encomenda find(Long id) {
        return em.find(Encomenda.class, id);
    }
}
