package br.com.ejb.ejb;

import br.com.ejb.bean.Usuario;
import javax.ejb.Remote;

/**
 *
 * @author Eduardo Hernandorena
 */
@Remote
public interface UsuarioDAORemote {

    public void create(Usuario user);
}
