package br.com.ejb.ejb;

import br.com.ejb.bean.Produto;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Eduardo Hernandorena
 */
@Stateless
public class ProdutoDAO implements ProdutoDAORemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Produto create(Produto prod) {
        em.persist(prod);
        return prod;
    }

    @Override
    public void update(Produto prod) {
        em.merge(prod);
    }

    @Override
    public void remove(Produto prod) {
        em.remove(prod);
    }
}
