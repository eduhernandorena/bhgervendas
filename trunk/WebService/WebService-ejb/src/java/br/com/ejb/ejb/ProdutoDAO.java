package br.com.ejb.ejb;

import br.com.ejb.bean.Produto;
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
public class ProdutoDAO implements ProdutoDAORemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Produto create(Produto prod) {
        if (prod.getId() != null) {
            em.merge(prod);
        } else {
            em.persist(prod);
        }
        return prod;
    }

    @Override
    public void remove(Produto prod) {
        em.remove(prod);
    }

    @Override
    public Produto find(Long id) {
        return em.find(Produto.class, id);
    }

    @Override
    public List<Produto> findAll() {
        TypedQuery<Produto> createNamedQuery = em.createNamedQuery("Produto.findAll", Produto.class);
        return createNamedQuery.getResultList();
    }

    @Override
    public List<Produto> findDesc(String desc) {
        TypedQuery<Produto> createQuery = em.createQuery("Select o from Produto o where upper(o.descricao) like :desc", Produto.class);
        createQuery.setParameter("desc", desc.toUpperCase());
        return createQuery.getResultList();
    }
}
