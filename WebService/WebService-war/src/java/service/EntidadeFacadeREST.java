package service;

import br.com.ejb.bean.Entidade;
import br.com.ejb.bean.enumeration.TipoEntidade;
import br.com.ejb.ejb.EntidadeDAORemote;
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
@Path("br.com.ejb.bean.entidade")
public class EntidadeFacadeREST {

    @EJB
    private EntidadeDAORemote dao;

    public EntidadeFacadeREST() {
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public Entidade create(Entidade entity) {
        return dao.create(entity);

    }

//    @PUT
//    @Consumes({"application/xml", "application/json"})
//    public void edit(Entidade entity) {
//        dao.update(entity);
//    }
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        Entidade ent = new Entidade();
        ent.setId(id);
        dao.remove(ent);
    }

    @GET
    @Path("Cliente/{id}")
    @Produces({"application/xml", "application/json"})
    public Entidade findCli(@PathParam("id") Long id) {
        return dao.find(TipoEntidade.Cliente, id);
    }

    @GET
    @Path("Fornecedor/{id}")
    @Produces({"application/xml", "application/json"})
    public Entidade findForn(@PathParam("id") Long id) {
        return dao.find(TipoEntidade.Fornecedor, id);
    }
    
    @GET
    @Path("ClienteNome/{nome}")
    @Produces({"application/xml", "application/json"})
    public List<Entidade> findCliNome(@PathParam("nome") String nome) {
        return dao.findNome(TipoEntidade.Cliente, nome);
    }

    @GET
    @Path("FornecedorNome/{nome}")
    @Produces({"application/xml", "application/json"})
    public List<Entidade> findFornNome(@PathParam("nome") String nome) {
        return dao.findNome(TipoEntidade.Fornecedor, nome);
    }

    @GET
    @Path("tipoEntidade/{tp}")
    @Produces({"application/xml", "application/json"})
    public List<Entidade> findAll(@PathParam("tp") TipoEntidade tp) {
        if (tp.isCliente()) {
            return dao.findAllCliente();
        } else {
            return dao.findAllFornecedor();
        }
    }
}
