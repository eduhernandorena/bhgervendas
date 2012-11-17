package service;

import br.com.ejb.bean.Pedido;
import br.com.ejb.bean.enumeration.TipoEntidade;
import br.com.ejb.ejb.PedidoDAORemote;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

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

    @PUT
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public Pedido create(Pedido entity) {
        return dao.create(entity);
    }

//    @PUT
//    @Consumes({"application/xml", "application/json"})
//    public void edit(Pedido entity) {
//        dao.update(entity);
//    }
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        dao.remove(dao.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Pedido find(@PathParam("id") Long id) {
        return dao.find(id);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Pedido> findAll() {
        return dao.findAll();
    }

    @GET
    @Path("entidade/{tp}/{id}")
    @Produces({"application/xml", "application/json"})
    public List<Pedido> findAllByEntidade(@PathParam("tp") TipoEntidade tp, @PathParam("id") Long id) {
        return dao.findAllByEntidade(tp, id);
    }

    @GET
    @Path("cliente/{nome}")
    @Produces({"application/xml", "application/json"})
    public List<Pedido> findByCli(@PathParam("nome") String nome) {
        return dao.findByCli(nome);
    }
}
