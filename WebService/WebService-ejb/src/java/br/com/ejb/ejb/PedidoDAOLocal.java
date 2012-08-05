package br.com.ejb.ejb;

import br.com.ejb.bean.Pedido;
import javax.ejb.Local;

/**
 *
 * @author Eduardo Hernandorena
 */
@Local
public interface PedidoDAOLocal {

    void create(Pedido ped);

    void remove(Pedido ped);

    void update(Pedido ped);
}
