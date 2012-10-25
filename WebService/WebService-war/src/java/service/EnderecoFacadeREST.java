package service;

import br.com.ejb.bean.Endereco;
import br.com.ejb.ejb.EnderecoDAORemote;
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
@Path("br.com.ejb.bean.endereco")
public class EnderecoFacadeREST {
    
    @EJB
    private EnderecoDAORemote dao;
    
    public EnderecoFacadeREST() {
        
    }

    @PUT
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
    public Endereco create(Endereco entity) {
        return dao.create(entity);
    }
//
//    @PUT
//    @Consumes({"application/xml", "application/json"})
//    public void edit(Endereco entity) {
//        dao.update(entity);
//    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        Endereco end = new Endereco();
        end.setId(id);
        dao.remove(end);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Endereco find(@PathParam("id") Long id) {
        return dao.find(id);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Endereco> findAll() {
        return dao.findAll();
    }
    
}
