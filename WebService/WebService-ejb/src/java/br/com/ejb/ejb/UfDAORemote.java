package br.com.ejb.ejb;

import br.com.ejb.bean.UF;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface UfDAORemote {
    void create(UF uf);
}
