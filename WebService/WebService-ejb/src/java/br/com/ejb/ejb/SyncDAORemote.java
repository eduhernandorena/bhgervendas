package br.com.ejb.ejb;

import br.com.ejb.bean.Sync;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface SyncDAORemote {

    public Sync create(Sync sync);

    public void remove(Sync sync);

    public List<Sync> sincroniza();
    
    public void atualiza(List<Sync> syncs);
}
