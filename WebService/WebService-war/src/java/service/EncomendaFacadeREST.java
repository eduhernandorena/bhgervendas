package service;

import br.com.ejb.bean.Encomenda;
import br.com.ejb.ejb.EncomendaDAORemote;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author Eduardo Hernandorena
 */
@Stateless
@Path("br.com.ejb.bean.encomenda")
public class EncomendaFacadeREST {
    @EJB
    private EncomendaDAORemote dao;

    public EncomendaFacadeREST() {
        
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Encomenda entity) {
        dao.create(entity);
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public void edit(Encomenda entity) {
        dao.update(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        Encomenda enc = new Encomenda();
        enc.setCodigo(id);
        dao.remove(enc);
    }

//    @GET
//    @Path("{id}")
//    @Produces({"application/xml", "application/json"})
//    public Encomenda find(@PathParam("id") Long id) {
//        return (id);
//    }
//
//    @GET
//    @Override
//    @Produces({"application/xml", "application/json"})
//    public List<Encomenda> findAll() {
//        return super.findAll();
//    }
    
}