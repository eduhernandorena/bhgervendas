package br.com.ejb.ejb;

import br.com.ejb.bean.Cidade;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface CidadeDAORemote {

    public void create(Cidade city);

//    public void update(Cidade city);
//
//    public void remove(Cidade city);

    public List<Cidade> findAll();

    public Cidade find(Long id);

    public List<Cidade> findByUF(Long uf);
}
