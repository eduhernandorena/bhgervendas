package br.com.ejb.ejb;

import br.com.ejb.bean.Encomenda;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface EncomendaDAORemote {

    Encomenda create(Encomenda encom);

    void update(Encomenda encom);

    void remove(Encomenda encom);
}
