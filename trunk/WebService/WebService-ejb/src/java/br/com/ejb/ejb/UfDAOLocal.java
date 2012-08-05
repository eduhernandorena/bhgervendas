package br.com.ejb.ejb;

import br.com.ejb.bean.UF;
import javax.ejb.Local;

/**
 *
 * @author Eduardo Hernandorena
 */
@Local
public interface UfDAOLocal {

    void create(UF uf);

    void update(UF uf);
    
    void remove(UF uf);
}
