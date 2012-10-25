package br.com.ejb.ejb;

import br.com.ejb.bean.Endereco;
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
public class EnderecoDAO implements EnderecoDAORemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Endereco create(Endereco end) {
        if (end.getId() != null) {
            em.merge(end);
        } else {
            em.persist(end);
        }
        return end;
    }

    @Override
    public void remove(Endereco end) {
        em.remove(end);
    }

    @Override
    public Endereco find(Long id) {
        return em.find(Endereco.class, id);
    }

    @Override
    public List<Endereco> findAll() {
        TypedQuery<Endereco> createNamedQuery = em.createNamedQuery("Endereco.findAll", Endereco.class);
        return createNamedQuery.getResultList();
    }
}
