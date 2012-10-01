package service;

import br.com.ejb.bean.Entidade;
import br.com.ejb.bean.enumeration.TipoEntidade;
import br.com.ejb.ejb.EntidadeDAORemote;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Eduardo Hernandorena
 */
@Stateless
@Path("br.com.ejb.bean.entidade")
public class EntidadeFacadeREST {

    @EJB
    private EntidadeDAORemote dao;

    public EntidadeFacadeREST() {
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public Entidade create(Entidade entity) {
        return dao.create(entity);

    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public void edit(Entidade entity) {
        dao.update(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        Entidade ent = new Entidade();
        ent.setId(id);
        dao.remove(ent);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Entidade find(@PathParam("id") Long id) {
        return dao.find(id);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Entidade> findAll(TipoEntidade tp) {
        if (tp.isCliente()) {
            return dao.findAllCliente();
        } else {
            return dao.findAllFornecedor();
        }
    }
}
