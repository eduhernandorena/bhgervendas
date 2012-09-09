package br.com.ejb.ejb;

import br.com.ejb.bean.UF;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface UfDAORemote {

    public void create(UF uf);

    public void remove(UF uf);

    public void update(UF uf);
}
