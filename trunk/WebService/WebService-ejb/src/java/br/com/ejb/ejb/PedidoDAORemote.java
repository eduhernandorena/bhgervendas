package br.com.ejb.ejb;

import br.com.ejb.bean.Pedido;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface PedidoDAORemote {

    void create(Pedido ped);

    void remove(Pedido ped);

    void update(Pedido ped);
}
