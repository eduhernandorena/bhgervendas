package br.com.ejb.ejb;

import br.com.ejb.bean.Entidade;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface EntidadeDAORemote {

    public void create(Entidade entit);

    public Entidade update(Entidade entit);

    public void remove(Entidade entit);

    public List<Entidade> findAllCliente();

    public List<Entidade> findAllFornecedor();
}