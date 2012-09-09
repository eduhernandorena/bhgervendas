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

    @Override
    public Usuario find(String user) {
        if (user != null && !user.trim().isEmpty()) {
            Query q = em.createQuery("select o from Usuario o where o.nome=:user");
            q.setParameter("user", user);
            return (Usuario) q.getSingleResult();
        } else {
            return null;
        }
    }

    @Override
    public List<Usuario> findAll() {
        return em.createQuery("select o from Usuario o").getResultList();
    }
}
