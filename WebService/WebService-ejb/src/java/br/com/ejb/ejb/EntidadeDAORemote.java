package br.com.ejb.ejb;

import br.com.ejb.bean.Entidade;
import br.com.ejb.bean.enumeration.TipoEntidade;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface EntidadeDAORemote {

    public Entidade create(Entidade entit);

    public void remove(Entidade entit);

    public List<Entidade> findAllCliente();

    public List<Entidade> findAllFornecedor();
    
    public Entidade find(TipoEntidade tp, long id);
    
    public List<Entidade> findNome(TipoEntidade tp, String nome);
}
