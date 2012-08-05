package br.com.ejb.ejb;

import br.com.ejb.bean.Produto;
import javax.ejb.Local;

/**
 *
 * @author Eduardo Hernandorena
 */
@Local
public interface ProdutoDAOLocal {

    void create(Produto prod);

    void remove(Produto prod);

    void update(Produto prod);
}
