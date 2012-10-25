/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import br.com.ejb.bean.Produto;
import br.com.ejb.ejb.ProdutoDAORemote;
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
@Path("br.com.ejb.bean.produto")
public class ProdutoFacadeREST {

    @EJB
    private ProdutoDAORemote dao;

    public ProdutoFacadeREST() {
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public Produto create(Produto entity) {
        return dao.create(entity);
    }

//    @PUT
//    @Consumes({"application/xml", "application/json"})
//    public void edit(Produto entity) {
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
    public Produto find(@PathParam("id") Long id) {
        return dao.find(id);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Produto> findAll() {
        return dao.findAll();
    }
}
