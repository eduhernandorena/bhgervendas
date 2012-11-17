package br.com.ws;

import br.com.ejb.bean.Viagem;
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
public class ViagemRest {

    private WebResource webResource;
    private Client client;
    private static final String BASE_URI = ConfInicial.getInstance();

    public ViagemRest() {
        com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI).path("br.com.ejb.bean.viagem");
    }

    public void remove(String id) throws UniformInterfaceException {
        webResource.path(java.text.MessageFormat.format("{0}", new Object[]{id})).delete();
    }

    public String count() throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path("count");
        return resource.accept(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public List<Viagem> findAll() throws UniformInterfaceException {
        WebResource resource = webResource;
        return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(new GenericType<List<Viagem>>() {
        });
    }

//    public void edit(Object requestEntity) throws UniformInterfaceException {
//        webResource.type(javax.ws.rs.core.MediaType.APPLICATION_XML).put(requestEntity);
//    }
    public void create(Object requestEntity) throws UniformInterfaceException {
        webResource.type(javax.ws.rs.core.MediaType.APPLICATION_XML).put(requestEntity);
    }

    public Viagem find(Long id) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        Viagem v;
        try {
            v = resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(Viagem.class);
        } catch (UniformInterfaceException ex) {
            return null;
        }
        return v;
    }

    public List<Viagem> findLocal(String local) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path(java.text.MessageFormat.format("local/{0}", new Object[]{local}));
        List<Viagem> v;
        try {
            v = resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(new GenericType<List<Viagem>>() {
            });
        } catch (UniformInterfaceException ex) {
            return null;
        }
        return v;
    }

    public void close() {
        client.destroy();
    }
}
