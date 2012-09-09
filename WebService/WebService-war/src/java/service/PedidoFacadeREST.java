package service;

import br.com.ejb.bean.Pedido;
import br.com.ejb.ejb.PedidoDAORemote;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

/**
 *
 * @author Eduardo Hernandorena
 */
@Stateless
@Path("br.com.ejb.bean.pedido")
public class PedidoFacadeREST {

    @EJB
    private PedidoDAORemote dao;

    public PedidoFacadeREST() {
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Pedido entity) {
        dao.create(entity);
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public void edit(Pedido entity) {
        dao.update(entity);
    }
//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") Long id) {
//        dao.remove(dao.find(id));
//    }
//    @GET
//    @Path("{id}")
//    @Produces({"application/xml", "application/json"})
//    public Pedido find(@PathParam("id") Long id) {
//        return dao.find(id);
//    }
//    @GET
//    @Produces({"application/xml", "application/json"})
//    public List<Pedido> findAll() {
//        return dao.findAll();
//    } 
}
