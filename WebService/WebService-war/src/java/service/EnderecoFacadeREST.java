/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import br.com.ejb.bean.Endereco;
import br.com.ejb.ejb.EnderecoDAORemote;
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
@Path("br.com.ejb.bean.endereco")
public class EnderecoFacadeREST {
    
    @EJB
    private EnderecoDAORemote dao;
    
    public EnderecoFacadeREST() {
        
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Endereco entity) {
        dao.create(entity);
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public void edit(Endereco entity) {
        dao.update(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        Endereco end = new Endereco();
        end.setId(id);
        dao.remove(end);
    }

//    @GET
//    @Path("{id}")
//    @Produces({"application/xml", "application/json"})
//    public Endereco find(@PathParam("id") Long id) {
//        return dao.find(id);
//    }

//    @GET
//    
//    @Produces({"application/xml", "application/json"})
//    public List<Endereco> findAll() {
//        return super.findAll();
//    }
    
}
