package br.com.ejb.ejb;

import br.com.ejb.bean.Cidade;
import javax.ejb.Local;

/**
 *
 * @author Eduardo Hernandorena
 */
@Local
public interface CidadeDAOLocal {

    void create(Cidade city);

    void update(Cidade city);

    void remove(Cidade city);
}
