package br.com.ejb.ejb;

import br.com.ejb.bean.UF;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface UfDAORemote {

    void create(UF uf);

//    void remove(UF uf);
//
//    void update(UF uf);
    
    UF find(Long id);
    
    List<UF> findAll();
}
