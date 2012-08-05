package br.com.ejb.ejb;

import br.com.ejb.bean.Encomenda;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface EncomendaDAORemote {

    void create(Encomenda encom);

    void update(Encomenda encom);

    void remove(Encomenda encom);
}
