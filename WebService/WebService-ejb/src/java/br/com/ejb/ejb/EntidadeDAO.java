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
    public Entidade create(Entidade entit) {
        if (entit.getId() != null) {
            em.merge(entit);
        } else {
            em.persist(entit);
        }
        return entit;
    }

    @Override
    public void remove(Entidade entit) {
        em.remove(entit);
    }

    @Override
    public List<Entidade> findAllCliente() {
        TypedQuery<Entidade> createNamedQuery = em.createNamedQuery("Entidade.findByTpEntidade", Entidade.class);
        createNamedQuery.setParameter("tpEntidade", TipoEntidade.Cliente);
        return createNamedQuery.getResultList();
    }

    @Override
    public List<Entidade> findAllFornecedor() {
        TypedQuery<Entidade> createNamedQuery = em.createNamedQuery("Entidade.findByTpEntidade", Entidade.class);
        createNamedQuery.setParameter("tpEntidade", TipoEntidade.Fornecedor);
        return createNamedQuery.getResultList();
    }

    @Override
    public Entidade find(long id) {
        return em.find(Entidade.class, id);
    }
}
