package br.com.ejb.ejb;

import br.com.ejb.bean.Pedido;
import br.com.ejb.bean.enumeration.TipoEntidade;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface PedidoDAORemote {

    Pedido create(Pedido ped);

    void remove(Pedido ped);

    Pedido find(Long id);
    
    List<Pedido> findAll();
    
    List<Pedido> findAllByEntidade(TipoEntidade tp, Long id);
 
    List<Pedido> findByCli(String nome);
}
