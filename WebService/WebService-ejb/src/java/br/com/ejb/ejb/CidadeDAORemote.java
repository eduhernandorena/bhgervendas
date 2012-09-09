package br.com.ejb.ejb;

import br.com.ejb.bean.Cidade;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface CidadeDAORemote {

    public void create(Cidade city);

    public void update(Cidade city);

    public void remove(Cidade city);

    public void findAll();

    public Cidade find(Long id);
}
