package service;

import br.com.ejb.bean.Viagem;
import br.com.ejb.ejb.ViagemDAORemote;
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

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Viagem entity) {
        dao.create(entity);
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public void edit(Viagem entity) {
        dao.update(entity);
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

//    @GET
//    @Override
//    @Produces({"application/xml", "application/json"})
//    public List<Viagem> findAll() {
//        return dao.findAll();
//    } 
}
