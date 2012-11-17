package br.com.ws;

import br.com.ejb.bean.Entidade;
import br.com.ejb.bean.enumeration.TipoEntidade;
import br.com.principal.ConfInicial;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import java.util.List;

/**
 *
 * @author Eduardo Hernandorena
 */
public class EntidadeRest {

    private WebResource webResource;
    private Client client;
    private static final String BASE_URI = ConfInicial.getInstance();

    public EntidadeRest() {
        com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI).path("br.com.ejb.bean.entidade");
    }

    public void remove(String id) throws UniformInterfaceException {
        webResource.path(java.text.MessageFormat.format("{0}", new Object[]{id})).delete();
    }

//    public String count() throws UniformInterfaceException {
//        WebResource resource = webResource;
//        resource = resource.path("count");
//        return resource.accept(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
//    }
    public List<Entidade> findAll(TipoEntidade tp) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path(java.text.MessageFormat.format("tipoEntidade/{0}", new Object[]{tp}));
        return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(new GenericType<List<Entidade>>() {
        });
    }

//    public void edit(Object requestEntity) throws UniformInterfaceException {
//        webResource.type(javax.ws.rs.core.MediaType.APPLICATION_XML).put(requestEntity);
//    }
    public Entidade create(Object requestEntity) throws UniformInterfaceException {
        return webResource.type(javax.ws.rs.core.MediaType.APPLICATION_XML).put(Entidade.class, requestEntity);
    }

    public Entidade findCli(Long id) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path(java.text.MessageFormat.format("Cliente/{0}", new Object[]{id}));
        Entidade e;
        try {
            e = resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(Entidade.class);
        } catch (UniformInterfaceException ex) {
            return null;
        }
        return e;
    }

    public Entidade findForn(Long id) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path(java.text.MessageFormat.format("Fornecedor/{0}", new Object[]{id}));
        Entidade e;
        try {
            e = resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(Entidade.class);
        } catch (UniformInterfaceException ex) {
            return null;
        }
        return e;
    }

    public List<Entidade> findCliNome(String nome) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path(java.text.MessageFormat.format("ClienteNome/{0}", new Object[]{nome}));
        List<Entidade> e;
        try {
            e = resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(new GenericType<List<Entidade>>() {
            });
        } catch (UniformInterfaceException ex) {
            return null;
        }
        return e;
    }

    public List<Entidade> findFornNome(String nome) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path(java.text.MessageFormat.format("FornecedorNome/{0}", new Object[]{nome}));
        List<Entidade> e;
        try {
            e = resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(new GenericType<List<Entidade>>() {
            });
        } catch (UniformInterfaceException ex) {
            return null;
        }
        return e;
    }

    public void close() {
        client.destroy();
    }
}
