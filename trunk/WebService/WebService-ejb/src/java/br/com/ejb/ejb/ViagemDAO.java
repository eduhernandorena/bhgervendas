package br.com.ejb.ejb;

import br.com.ejb.bean.Viagem;
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
public class ViagemDAO implements ViagemDAORemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Viagem create(Viagem viagem) {
        if (viagem.getId() != null) {
            em.merge(viagem);
        } else {
            em.persist(viagem);
        }
        return viagem;
    }

    @Override
    public void remove(Viagem viagem) {
        em.remove(viagem);
    }

    @Override
    public Viagem find(Long id) {
        return em.find(Viagem.class, id);
    }

    @Override
    public List<Viagem> findAll() {
        TypedQuery<Viagem> createNamedQuery = em.createNamedQuery("Viagem.findAll", Viagem.class);
        return createNamedQuery.getResultList();
    }
}
