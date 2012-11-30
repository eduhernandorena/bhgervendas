package br.com.bhgervendas.ws;

import android.content.Context;
import android.widget.Toast;
import br.com.bhgervendas.ManageFile;
import br.com.bhgervendas.bd.SyncDAO;
import br.com.bhgervendas.bean.ListaSync;
import br.com.bhgervendas.bean.Sync;
import com.google.gson.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Eduardo Hernandorena
 */
public class SyncREST {

    private static SyncDAO dao = null;
    private static String BASE_URI = "";
    private static HttpClient httpclient = new DefaultHttpClient();
    private static Context context = null;

    public SyncREST(SyncDAO syncDAO, Context cnt) {
        dao = syncDAO;
        context = cnt;
        ManageFile fileread = new ManageFile(cnt);
        String ip = fileread.ReadFile();
        if (ip != null && !ip.isEmpty()) {
            BASE_URI = "http://" + ip + ":8080/WebService-war/resources/br.com.ejb.bean.sync";
            System.out.println(BASE_URI);
        } else {
            BASE_URI = null;
        }
    }

    public String atualiza(List<?> lista) {
        try {
            Gson gson = new Gson();
            String jsonLista = gson.toJson(lista);
            System.out.println(jsonLista);
            StringEntity sEntity = new StringEntity(jsonLista, "UTF-8");
            sEntity.setContentType("application/json");
            HttpPost httpPost = new HttpPost(BASE_URI + "/atualiza/");
            httpPost.setEntity(sEntity);
            System.out.println(sEntity.toString());
            System.out.println(httpPost.getRequestLine().getUri());
            HttpResponse response = httpclient.execute(httpPost);
            int httpStatusCode = response.getStatusLine().getStatusCode();
            String responsePhrase = response.getStatusLine().getReasonPhrase();
            System.out.println(httpStatusCode + " - " + responsePhrase);
        } catch (UnsupportedEncodingException uee) {
            Logger.getLogger(SyncREST.class.getName()).log(Level.SEVERE, null, uee);
        } catch (IOException e) {
            Logger.getLogger(SyncREST.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

//    public boolean atualiza(List<Sync> syncs) {
//        try {
//            HttpPost post = new HttpPost(BASE_URI + "/br.com.ejb.bean.sync/atualiza");
//            HttpParams param = new BasicHttpParams();
//            param.setParameter("syncs", syncs);
//            post.setParams(param);
//            ResponseHandler<String> handler = new BasicResponseHandler();
//            httpclient.execute(post, handler);
//        System.out.println("Atualiza!");
//            JSONObject array = new JSONObject(resposta);
//            Gson gson = new Gson();
//            ListaSync list = gson.fromJson(array.toString(), ListaSync.class);
//            List<Sync> lista = list.getList();
//            if (lista != null && !lista.isEmpty()) {
//                for (Sync sync : lista) {
//                    dao.create(sync);
//                }
//                return true;
//            }
//        return true;
//        } catch (IOException ex) {
//            Logger.getLogger(SyncREST.class.getName()).log(Level.SEVERE, null, ex);
//            return false;
//        }
//    }
    public boolean sincroniza() {
        if (BASE_URI == null) {
            gerarToast("IP nulo ou inválido!");
            return false;
        } else {
            try {
                HttpGet get = new HttpGet(BASE_URI);
                ResponseHandler<String> handler = new BasicResponseHandler();
                HttpResponse response = httpclient.execute(get);
                int httpStatusCode = response.getStatusLine().getStatusCode();
                String responsePhrase = response.getStatusLine().getReasonPhrase();
                System.out.println(httpStatusCode + " - " + responsePhrase);
                String resposta = httpclient.execute(get, handler);
                JSONObject array = new JSONObject(resposta);
                Gson gson = new Gson();
                ListaSync list = gson.fromJson(array.toString(), ListaSync.class);
                List<Sync> lista = list.getList();
                System.out.println(lista.size());
                if (lista != null && !lista.isEmpty()) {
                    List<Sync> l = dao.getAll();
                    for (Sync sync : lista) {
                        if (!l.contains(sync)) {
                            dao.create(sync);
                        }
                    }
                    return true;
                }
                return false;
            } catch (Exception ex) {
                Logger.getLogger(SyncREST.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
    }

    private void gerarToast(CharSequence message) {
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
}
