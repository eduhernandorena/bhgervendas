package br.com.ejb.ejb;

import br.com.ejb.bean.Encomenda;
import javax.ejb.Local;

/**
 *
 * @author Eduardo Hernandorena
 */
@Local
public interface EncomendaDAOLocal {

    void create(Encomenda encom);

    void update(Encomenda encom);

    void remove(Encomenda encom);
}
