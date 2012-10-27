package service;

import br.com.ejb.bean.Sync;
import br.com.ejb.ejb.SyncDAORemote;
import com.google.gson.Gson;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
@Path("br.com.ejb.bean.sync")
public class SyncFacadeREST {

    @EJB
    private SyncDAORemote dao;

    public SyncFacadeREST() {
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public Sync create(Sync entity) {
        return dao.create(entity);
    }

    @DELETE
    @Path("{sync}")
    public void remove(@PathParam("sync") Sync sync) {
        dao.remove(sync);
    }

    @GET
    @Path("{data}")
    @Produces({"application/xml", "application/json"})
    public Sync findByData(@PathParam("data") Date data) {
        return dao.findByData(data);
    }

    @GET
    @Path("/GSON/{data}")
    @Produces("application/json")
    public String findByDataGSON(@PathParam("data") Date data) {
        return new Gson().toJson(dao.findByData(data));
    }
}