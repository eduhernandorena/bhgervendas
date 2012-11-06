package br.com.ws;

import br.com.ejb.bean.Usuario;
import br.com.principal.ConfInicial;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

/**
 *
 * @author Eduardo Hernandorena
 */
public class UsuarioRest {

    private WebResource webResource;
    private Client client;
    private static final String BASE_URI = ConfInicial.getInstance();

    public UsuarioRest() {
        com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI).path("br.com.ejb.bean.usuario");
    }

    public void remove(String id) throws UniformInterfaceException {
        webResource.path(java.text.MessageFormat.format("{0}", new Object[]{id})).delete();
    }

//    public String count() throws UniformInterfaceException {
//        WebResource resource = webResource;
//        resource = resource.path("count");
//        return resource.accept(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
//    }
    public Boolean isEmpty() throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path("/empty");
        return Boolean.valueOf(resource.accept(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class));
    }

//    public void edit(Object requestEntity) throws UniformInterfaceException {
//        webResource.type(javax.ws.rs.core.MediaType.APPLICATION_XML).put(requestEntity);
//    }
    public Usuario create(Usuario user) throws UniformInterfaceException {
        return webResource.type(javax.ws.rs.core.MediaType.APPLICATION_XML).put(Usuario.class, user);
    }

    public Usuario find(Long id) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(Usuario.class);
    }

    public void close() {
        client.destroy();
    }

    public Usuario findByNome(String user) {
        WebResource resource = webResource;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{user}));
        return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(Usuario.class);
    }
}
