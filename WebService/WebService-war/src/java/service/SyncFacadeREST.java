package service;

import br.com.ejb.bean.Sync;
import br.com.ejb.ejb.SyncDAORemote;
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
@Path("br.com.ejb.bean.sync")
public class SyncFacadeREST {

    @EJB
    private SyncDAORemote dao;

    public SyncFacadeREST() {
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public Sync create(Sync entity) {
        return dao.create(entity);
    }

    @POST
    @Path("atualiza/")
    @Consumes({"application/json"})
    public void atualiza(List<Sync> entity) {
        if (entity != null && !entity.isEmpty()) {
            dao.atualiza(entity);
        }
    }

    @DELETE
    @Path("{sync}")
    public void remove(@PathParam("sync") Sync sync) {
        dao.remove(sync);
    }

    @GET
    @Produces({"application/json"})
    public List<Sync> sincroniza() {
        return dao.sincroniza();
    }
}