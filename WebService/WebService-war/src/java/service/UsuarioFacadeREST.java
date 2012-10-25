package service;

import br.com.ejb.bean.Usuario;
import br.com.ejb.ejb.UsuarioDAORemote;
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
@Path("br.com.ejb.bean.usuario")
public class UsuarioFacadeREST {

    @EJB
    private UsuarioDAORemote dao;

    public UsuarioFacadeREST() {
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public Usuario create(Usuario entity) {
        return dao.create(entity);
    }

//    @PUT
//    @Consumes({"application/xml", "application/json"})
//    public void edit(Usuario entity) {
//        dao.update(entity);
//    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        Usuario usr = new Usuario();
        usr.setId(id);
        dao.remove(usr);
    }

    @GET
    @Path("{nome}")
    @Produces({"application/xml", "application/json"})
    public Usuario findByNome(@PathParam("nome") String nome) {
        return dao.findByNome(nome);
    }
}
