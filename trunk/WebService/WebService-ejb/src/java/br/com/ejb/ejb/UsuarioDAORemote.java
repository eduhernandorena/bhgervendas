package br.com.ejb.ejb;

import br.com.ejb.bean.Usuario;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface UsuarioDAORemote {

    public Usuario create(Usuario user);

//    public Usuario find(String user);

    public List<Usuario> findAll();

    public void update(Usuario user);

    public void remove(Usuario user);

    public Usuario findByNome(String user);
}
