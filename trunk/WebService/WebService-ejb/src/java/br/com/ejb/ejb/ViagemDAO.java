package br.com.ejb.ejb;

import br.com.ejb.bean.Viagem;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Eduardo Hernandorena
 */
@Stateless
public class ViagemDAO implements ViagemDAORemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Viagem viagem) {
        em.persist(viagem);
    }

    @Override
    public void update(Viagem viagem) {
        em.merge(viagem);
    }

    @Override
    public void remove(Viagem viagem) {
        em.remove(viagem);
    }

    @Override
    public Viagem find(Long id) {
        return em.find(Viagem.class, id);
    }
}
