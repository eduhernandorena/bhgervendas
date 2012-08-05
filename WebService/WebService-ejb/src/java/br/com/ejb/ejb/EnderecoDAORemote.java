package br.com.ejb.ejb;

import br.com.ejb.bean.Endereco;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface EnderecoDAORemote {

    void create(Endereco end);

    void remove(Endereco end);

    void update(Endereco end);
}
