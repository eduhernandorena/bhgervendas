package service;

import br.com.ejb.bean.Cidade;
import br.com.ejb.ejb.CidadeDAORemote;
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
@Path("br.com.ejb.bean.cidade")
public class CidadeFacadeREST {

    @EJB
    private CidadeDAORemote dao;

    public CidadeFacadeREST() {
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Cidade entity) {
        dao.create(entity);
    }

//    @PUT
//    @Consumes({"application/xml", "application/json"})
//    public void edit(Cidade entity) {
//        dao.update(entity);
//    }
//
//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") Long id) {
//        Cidade cidade = new Cidade();
//        cidade.setCodIbge(id);
//        dao.remove(cidade);
//    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Cidade find(@PathParam("id") Long id) {
        return dao.find(id);
    }
    
    @GET
    @Produces({"application/xml", "application/json"})
    public List<Cidade> findAll() {
        return dao.findAll();
    }
    
    @GET
    @Path("uf/{uf}")
    @Produces({"application/xml", "application/json"})
    public List<Cidade> findAll(@PathParam("uf") Long uf) {
        return dao.findByUF(uf);
    }
}
