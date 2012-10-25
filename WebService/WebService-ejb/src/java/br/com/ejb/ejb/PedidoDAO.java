package br.com.ejb.ejb;

import br.com.ejb.bean.Pedido;
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
public class PedidoDAO implements PedidoDAORemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Pedido create(Pedido ped) {
        if (ped.getCodigo() != null) {
            em.merge(ped);
        } else {
            em.persist(ped);
        }
        return ped;
    }
    
    @Override
    public void remove(Pedido ped) {
        em.remove(ped);
    }

    @Override
    public Pedido find(Long id) {
        return em.find(Pedido.class, id);
    }

    @Override
    public List<Pedido> findAll() {
        TypedQuery<Pedido> createNamedQuery = em.createNamedQuery("Pedido.findAll", Pedido.class);
        return createNamedQuery.getResultList();
    }

    @Override
    public List<Pedido> findAllByEntidade(TipoEntidade tp, Long id) {
        TypedQuery<Pedido> createNamedQuery;
        if (tp.isCliente()) {
            createNamedQuery = em.createNamedQuery("Pedido.findByCliente", Pedido.class);
            createNamedQuery.setParameter("clienteId", id);
        } else {
            createNamedQuery = em.createNamedQuery("Pedido.findByFornecedor", Pedido.class);
            createNamedQuery.setParameter("fornecedorId", id);
        }
        return createNamedQuery.getResultList();
    }
}
