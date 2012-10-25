package br.com.ejb.ejb;

import br.com.ejb.bean.Viagem;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface ViagemDAORemote {

    Viagem create(Viagem viagem);

    void remove(Viagem viagem);

    Viagem find(Long id);
    
    List<Viagem> findAll();
}
