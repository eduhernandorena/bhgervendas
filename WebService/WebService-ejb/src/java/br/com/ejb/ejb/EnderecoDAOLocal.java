package br.com.ejb.ejb;

import br.com.ejb.bean.Endereco;
import javax.ejb.Local;

/**
 *
 * @author Eduardo Hernandorena
 */
@Local
public interface EnderecoDAOLocal {

    void create(Endereco end);

    void remove(Endereco end);

    void update(Endereco end);
}
