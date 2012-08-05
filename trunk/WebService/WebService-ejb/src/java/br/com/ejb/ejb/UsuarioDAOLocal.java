package br.com.ejb.ejb;

import br.com.ejb.bean.Usuario;
import javax.ejb.Local;

/**
 *
 * @author Eduardo Hernandorena
 */
@Local
public interface UsuarioDAOLocal {

    void create(Usuario user);

    void remove(Usuario user);

    void update(Usuario user);
}
