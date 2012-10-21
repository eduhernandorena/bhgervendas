package br.com.ws;

import br.com.ejb.bean.Usuario;
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
    private static final String BASE_URI = "http://localhost:8080/WebService-war/resources";

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

//    public <T> T findAll(Class<T> responseType) throws UniformInterfaceException {
//        WebResource resource = webResource;
//        return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
//    }

//    public void edit(Object requestEntity) throws UniformInterfaceException {
//        webResource.type(javax.ws.rs.core.MediaType.APPLICATION_XML).put(requestEntity);
//    }

    public Usuario create(Object requestEntity) throws UniformInterfaceException {
        return webResource.type(javax.ws.rs.core.MediaType.APPLICATION_XML).put(Usuario.class, requestEntity);
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
