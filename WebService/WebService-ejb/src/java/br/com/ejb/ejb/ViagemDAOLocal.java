package br.com.ejb.ejb;

import br.com.ejb.bean.Viagem;
import javax.ejb.Local;

/**
 *
 * @author Eduardo Hernandorena
 */
@Local
public interface ViagemDAOLocal {

    void create(Viagem viagem);

    void remove(Viagem viagem);

    void update(Viagem viagem);
}
