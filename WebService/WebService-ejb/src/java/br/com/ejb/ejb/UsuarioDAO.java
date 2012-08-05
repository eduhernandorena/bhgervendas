package br.com.ejb.ejb;

import br.com.ejb.bean.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Eduardo Hernandorena
 */
@Stateless
public class UsuarioDAO implements UsuarioDAOLocal, UsuarioDAORemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Usuario user) {
        em.persist(user);
    }

    @Override
    public void update(Usuario user) {
        em.merge(user);
    }

    @Override
    public void remove(Usuario user) {
        em.remove(user);
    }
}
