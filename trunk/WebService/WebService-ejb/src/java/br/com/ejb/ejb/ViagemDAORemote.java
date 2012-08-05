package br.com.ejb.ejb;

import br.com.ejb.bean.Viagem;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface ViagemDAORemote {

    void create(Viagem viagem);

    void remove(Viagem viagem);

    void update(Viagem viagem);
}
