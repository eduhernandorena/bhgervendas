package br.com.ejb.ejb;

import br.com.ejb.bean.Produto;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface ProdutoDAORemote {

    Produto create(Produto prod);

    void remove(Produto prod);

    void update(Produto prod);
}
