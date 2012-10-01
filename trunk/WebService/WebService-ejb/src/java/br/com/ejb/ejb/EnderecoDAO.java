package br.com.ejb.ejb;

import br.com.ejb.bean.Endereco;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Eduardo Hernandorena
 */
@Stateless
public class EnderecoDAO implements EnderecoDAORemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Endereco create(Endereco end) {
        em.persist(end);
        return end;
    }

    @Override
    public void update(Endereco end) {
        em.merge(end);
    }

    @Override
    public void remove(Endereco end) {
        em.remove(end);
    }
    
    @Override
    public Endereco find(Long id){
        return em.find(Endereco.class, id);
    }
}
