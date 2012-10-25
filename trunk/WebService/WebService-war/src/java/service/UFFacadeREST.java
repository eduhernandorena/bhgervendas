package service;

import br.com.ejb.bean.UF;
import br.com.ejb.ejb.UfDAORemote;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Eduardo Hernandorena
 */
@Stateless
@Path("br.com.ejb.bean.uf")
public class UFFacadeREST {
    @EJB
    UfDAORemote dao;

    public UFFacadeREST() {
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(UF entity) {
        dao.create(entity);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public UF find(@PathParam("id") Long id) {
        return dao.find(id);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<UF> findAll() {
        return dao.findAll();
    }
}
