package br.com.ejb.ejb;

import br.com.ejb.bean.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Eduardo Hernandorena
 */
@Stateless
public class UsuarioDAO implements UsuarioDAORemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Usuario create(Usuario user) {
        if (user.getId() != null) {
            em.merge(user);
        } else {
            em.persist(user);
        }
        return user;
    }

    @Override
    public void remove(Usuario user) {
        em.remove(user);
    }

    @Override
    public Usuario findByNome(String user) {
        try {
            if (user != null && !user.trim().isEmpty()) {
                Query q = em.createQuery("select o from Usuario o where o.nome=:user");
                q.setParameter("user", user);
                List<Usuario> listUser = q.getResultList();
                if (!listUser.isEmpty()) {
                    return listUser.get(0);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public String isEmpty() {
        Query q = em.createQuery("select o from Usuario o");
        return String.valueOf(q.getResultList().isEmpty());
    }
}
