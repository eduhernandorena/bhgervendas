package br.com.ejb.ejb;

import br.com.ejb.bean.Entidade;
import br.com.ejb.bean.enumeration.TipoEntidade;
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
public class EntidadeDAO implements EntidadeDAORemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Entidade entit) {
        em.persist(entit);
    }

    @Override
    public Entidade update(Entidade entit) {
        return em.merge(entit);
    }

    @Override
    public void remove(Entidade entit) {
        em.remove(entit);
    }

    @Override
    public List<Entidade> findAllCliente() {
        TypedQuery<Entidade> createNamedQuery = em.createNamedQuery("Cidade.findByTpEntidade", Entidade.class);
        createNamedQuery.setParameter("tpEntidade", TipoEntidade.CLIENTE);
        return createNamedQuery.getResultList();
    }

    @Override
    public List<Entidade> findAllFornecedor() {
        TypedQuery<Entidade> createNamedQuery = em.createNamedQuery("Cidade.findByTpEntidade", Entidade.class);
        createNamedQuery.setParameter("tpEntidade", TipoEntidade.FORNECEDOR);
        return createNamedQuery.getResultList();
    }
}
