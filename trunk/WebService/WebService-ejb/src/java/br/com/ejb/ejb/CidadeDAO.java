package br.com.ejb.ejb;

import br.com.ejb.bean.Cidade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Eduardo Hernandorena
 */
@Stateless
public class CidadeDAO implements CidadeDAOLocal, CidadeDAORemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Cidade city) {
        em.persist(city);
    }

    @Override
    public void update(Cidade city) {
        em.merge(city);
    }

    @Override
    public void remove(Cidade city) {
        em.remove(city);
    }
    
    @Override
    public void findAll() {
        TypedQuery<Cidade> createNamedQuery = em.createNamedQuery("Cidade.findAll", Cidade.class);
        createNamedQuery.getResultList();
    }
}
