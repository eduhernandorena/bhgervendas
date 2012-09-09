package br.com.ejb.ejb;

import br.com.ejb.bean.Viagem;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface ViagemDAORemote {

    public void create(Viagem viagem);

    public void remove(Viagem viagem);

    public void update(Viagem viagem);

    public Viagem find(Long id);
}
