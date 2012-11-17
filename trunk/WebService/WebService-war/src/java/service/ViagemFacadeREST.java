package service;

import br.com.ejb.bean.Viagem;
import br.com.ejb.ejb.ViagemDAORemote;
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
@Path("br.com.ejb.bean.viagem")
public class ViagemFacadeREST {

    @EJB
    private ViagemDAORemote dao;

    public ViagemFacadeREST() {
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public Viagem create(Viagem entity) {
        return dao.create(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        dao.remove(dao.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Viagem find(@PathParam("id") Long id) {
        return dao.find(id);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Viagem> findAll() {
        return dao.findAll();
    }

    @GET
    @Path("local/{local}")
    @Produces({"application/xml", "application/json"})
    public List<Viagem> findLocal(@PathParam("local") String local) {
        return dao.findLocal(local);
    }
}
