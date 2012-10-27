package br.com.bhgervendas.ws;

import br.com.bhgervendas.bean.Sync;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;

public class SyncREST {

    private static final String URL_WS = "http://localhost:8080/WebService-war/resources";

    public Sync getSync(int id) throws Exception {

        String[] resposta = new WebService().get(URL_WS + id);

        if (resposta[0].equals("200")) {
            Gson gson = new Gson();
            Sync Sync = gson.fromJson(resposta[1], Sync.class);
            return Sync;
        } else {
            throw new Exception(resposta[1]);
        }
    }

    public List<Sync> getListaSync() throws Exception {

        String[] resposta = new WebService().get(URL_WS + "GSON");
//       String[] resposta = new WebServiceSync().get(URL_WS + "buscarTodos");

        if (resposta[0].equals("200")) {
            Gson gson = new Gson();
            ArrayList<Sync> listaSync = new ArrayList<Sync>();
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(resposta[1]).getAsJsonArray();

            for (int i = 0; i < array.size(); i++) {
                listaSync.add(gson.fromJson(array.get(i), Sync.class));
            }
            return listaSync;
        } else {
            throw new Exception(resposta[1]);
        }
    }

//    public String inserirSync(Sync Sync) throws Exception {
//
//        Gson gson = new Gson();
//        String SyncJSON = gson.toJson(Sync);
//        String[] resposta = new WebService().post(URL_WS + "inserir", SyncJSON);
//        if (resposta[0].equals("200")) {
//            return resposta[1];
//        } else {
//            throw new Exception(resposta[1]);
//        }
//    }
//
//    public String deletarSync(int id) {
//        String[] resposta = new WebService().get(URL_WS + "delete/" + id);
//        return resposta[1];
//    }
}