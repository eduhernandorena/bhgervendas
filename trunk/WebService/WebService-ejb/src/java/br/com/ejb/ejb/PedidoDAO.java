package br.com.ejb.ejb;

import br.com.ejb.bean.Pedido;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Eduardo Hernandorena
 */
@Stateless
public class PedidoDAO implements PedidoDAOLocal, PedidoDAORemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Pedido ped) {
        em.persist(ped);
    }

    @Override
    public void update(Pedido ped) {
        em.merge(ped);
    }

    @Override
    public void remove(Pedido ped) {
        em.remove(ped);
    }
}
