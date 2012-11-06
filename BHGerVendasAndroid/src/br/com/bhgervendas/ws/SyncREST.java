package br.com.bhgervendas.ws;

import br.com.bhgervendas.bd.SyncDAO;
import br.com.bhgervendas.bean.Sync;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import java.util.List;

/**
 *
 * @author Eduardo Hernandorena
 */
public class SyncREST {

    private WebResource webResource;
    private Client client;
    private static SyncDAO dao = null;
    private static String BASE_URI = "http://localhost:8080/WebService-war/resources";

    public SyncREST(SyncDAO syncDAO) {
        dao = syncDAO;
        com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI).path("br.com.ejb.bean.sync");
    }

    public Sync create(Sync user) throws UniformInterfaceException {
        return webResource.type(javax.ws.rs.core.MediaType.APPLICATION_XML).put(Sync.class, user);
    }

    public boolean sincroniza() throws UniformInterfaceException {
        WebResource resource = webResource;
        List<Sync> lista = resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(new GenericType<List<Sync>>() {
        });
        if (lista != null && !lista.isEmpty()) {
            for (Sync sync : lista) {
                dao.create(sync);
            }
            return true;
        }
        return false;
    }

    public void close() {
        client.destroy();
    }

    public static void setIP(String ip) {
        BASE_URI = "http://" + ip + ":8080/WebService-war/resources";
    }
}
