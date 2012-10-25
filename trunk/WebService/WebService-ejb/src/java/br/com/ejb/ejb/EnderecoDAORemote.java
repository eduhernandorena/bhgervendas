package br.com.ejb.ejb;

import br.com.ejb.bean.Endereco;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface EnderecoDAORemote {

    Endereco create(Endereco end);

    void remove(Endereco end);
    
    Endereco find(Long id);
    
    List<Endereco> findAll();
}
