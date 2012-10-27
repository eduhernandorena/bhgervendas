package br.com.ejb.ejb;

import br.com.ejb.bean.Sync;
import java.util.Date;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface SyncDAORemote {

    public Sync create(Sync sync);

    public void remove(Sync sync);

    public Sync findByData(Date data);
}
