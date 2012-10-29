package br.com.ejb.ejb;

import br.com.ejb.bean.Usuario;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface UsuarioDAORemote {

    public Usuario create(Usuario user);

    public void remove(Usuario user);

    public Usuario findByNome(String user);
    
    public Boolean isEmpty();
}
