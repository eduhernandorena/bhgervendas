package br.com.ejb.ejb;

import br.com.ejb.bean.Encomenda;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface EncomendaDAORemote {

    Encomenda create(Encomenda encom);

    void remove(Encomenda encom);

    List<Encomenda> findAll();

    Encomenda find(Long id);
}
