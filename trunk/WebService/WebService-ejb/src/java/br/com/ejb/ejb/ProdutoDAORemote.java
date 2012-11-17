package br.com.ejb.ejb;

import br.com.ejb.bean.Produto;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface ProdutoDAORemote {

    Produto create(Produto prod);

    void remove(Produto prod);

    Produto find(Long id);
    
    List<Produto> findAll();
    
    List<Produto> findDesc(String desc);
}
