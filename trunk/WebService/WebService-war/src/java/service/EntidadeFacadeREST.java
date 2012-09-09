/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import br.com.ejb.bean.Entidade;
import br.com.ejb.ejb.EntidadeDAORemote;
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
@Path("br.com.ejb.bean.entidade")
public class EntidadeFacadeREST {
    @EJB
    private EntidadeDAORemote dao;
    
    public EntidadeFacadeREST() {
    
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Entidade entity) {
        dao.create(entity);
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public void edit(Entidade entity) {
        dao.update(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        Entidade ent = new Entidade();
        ent.setId(id);
        dao.remove(ent);
    }

//    @GET
//    @Path("{id}")
//    @Produces({"application/xml", "application/json"})
//    public Entidade find(@PathParam("id") Long id) {
//        return super.find(id);
//    }

//    @GET
//    @Override
//    @Produces({"application/xml", "application/json"})
//    public List<Entidade> findAll() {
//        return super.findAll();
//    }
   
}
