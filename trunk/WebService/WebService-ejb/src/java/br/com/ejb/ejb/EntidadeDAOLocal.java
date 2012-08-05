package br.com.ejb.ejb;

import br.com.ejb.bean.Entidade;
import javax.ejb.Local;

/**
 *
 * @author Eduardo Hernandorena
 */
@Local
public interface EntidadeDAOLocal {

    void create(Entidade entit);

    Entidade update(Entidade entit);

    void remove(Entidade entit);
}
