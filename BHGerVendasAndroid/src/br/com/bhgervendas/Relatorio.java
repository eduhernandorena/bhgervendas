package br.com.bhgervendas;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import br.com.bhgervendas.bd.SyncDAO;
import br.com.bhgervendas.bean.Sync;
import br.com.bhgervendas.ws.SyncREST;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Relatorio extends Activity {

    private List<Sync> lista;
    private Spinner spinnerAno = null;
    private Spinner spinnerMes = null;
    private ListView txtView = null;
    private int ano;
    private int mes;
    private String anoString;
    private String mesString;
    private String tpMov;
    SyncDAO dao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relatorio);
        Bundle extras = getIntent().getExtras();
        mes = extras != null ? extras.getInt("mes") : 1;
        ano = extras != null ? extras.getInt("ano") : 1;
        tpMov = extras != null ? extras.getString("tpMov") : "T";
        this.setTitle("BHGerVendas -- Relatorio");

        dao = new SyncDAO(this);

        spinnerAno = (Spinner) findViewById(R.id.comboAno);
        spinnerMes = (Spinner) findViewById(R.id.comboMes);
        txtView = (ListView) findViewById(R.id.list);

        List<String> meses = new ArrayList<String>();
        meses.add("Janeiro");
        meses.add("Fevereiro");
        meses.add("Marco");
        meses.add("Abril");
        meses.add("Maio");
        meses.add("Junho");
        meses.add("Julho");
        meses.add("Agosto");
        meses.add("Setembro");
        meses.add("Outubro");
        meses.add("Novembro");
        meses.add("Dezembro");
        ArrayAdapter<String> adapterMes = new ArrayAdapter<String>(this, R.layout.spinner_textview_mes, meses);
        adapterMes.setDropDownViewResource(R.layout.simple_mes);
        spinnerMes.setAdapter(adapterMes);
        spinnerMes.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v,
                    int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        List<String> anos = new ArrayList<String>();
        anos.add("2012");
        anos.add("2013");
        anos.add("2014");
        anos.add("2015");
        anos.add("2016");
        anos.add("2017");

        ArrayAdapter<String> adapterAno = new ArrayAdapter<String>(this, R.layout.spinner_textview_ano, anos);
        adapterAno.setDropDownViewResource(R.layout.simple_ano);
        spinnerAno.setAdapter(adapterAno);
        spinnerAno.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v,
                    int position, long id) {
                sincroniza();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                sincroniza();
            }
        });
        spinnerMes.setSelection(mes);
        spinnerAno.setSelection(ano);
        anoString = spinnerAno.getItemAtPosition(ano).toString();
        mesString = spinnerMes.getItemAtPosition(mes).toString();
    }

    private void sincroniza() {
        try {
            dao.deleteAll();
            new SyncREST(dao, this).sincroniza();
            lista = new ArrayList<Sync>();
//                List<Sync> sincronizar = new ArrayList<Sync>();
            List<Sync> list = dao.getAll();
            for (Sync sync : list) {
                if (!sync.isSincronizado()) {
                    sync.setSincronizado(true);
                    dao.update(sync);
//                        sincronizar.add(sync);
                }
                String anoSync, mesSync;
                String[] ss = sync.getDataPag().split("/");
                mesSync = getMes(ss[1]);
                anoSync = ss[2];
                System.out.println("MesSync: " + mesSync);
                System.out.println("AnoSync: " + anoSync);
                System.out.println("ANO: " + anoString);
                System.out.println("MES: " + mesString);
                if (anoSync.equalsIgnoreCase(anoString) && mesSync.equalsIgnoreCase(mesString)) {
                    System.out.println("ano/mes");
                    if (!tpMov.equalsIgnoreCase("T")) {
                        System.out.println("!T");
                        if (sync.getTpMov().equalsIgnoreCase(tpMov)) {
                            System.out.println(tpMov);
                            lista.add(sync);
                        }
                    } else {
                        lista.add(sync);
                    }
                }
            }
//                rest.atualiza(sincronizar);
            Collections.sort(lista);
            ArrayAdapter<Sync> adapterSync = new ArrayAdapter<Sync>(this, R.layout.list_item, lista);
            txtView.setAdapter(adapterSync);

            txtView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                }
            });
        } catch (Exception e) {
            Logger.getLogger(Relatorio.class.getName()).log(Level.INFO, "ERRO", e);
            gerarToast("Erro: " + e.getMessage());
            Log.e("NGVL", "Erro Diverso!", e);
        }
    }

    @Override
    protected void onDestroy() {
        dao.close();
        super.onDestroy();
    }

    private void gerarToast(CharSequence message) {
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(getApplicationContext(), message, duration);
        toast.show();
    }

    private String getMes(String mes) {
        if (mes != null && !mes.isEmpty()) {
            int m = Integer.valueOf(mes);
            switch (m) {
                case 1:
                    return "Janeiro";
                case 2:
                    return "Fevereiro";
                case 3:
                    return "Marco";
                case 4:
                    return "Abril";
                case 5:
                    return "Maio";
                case 6:
                    return "Junho";
                case 7:
                    return "Julho";
                case 8:
                    return "Agosto";
                case 9:
                    return "Setembro";
                case 10:
                    return "Outubro";
                case 11:
                    return "Novembro";
                case 12:
                    return "Dezembro";
            }
        }
        return null;
    }
//     Pessoa pessoa = new Pessoa(1, "fulano de tal");
//        
//        Gson gson = new Gson();
//        String jsonPessoa = gson.toJson(pessoa, Pessoa.class); //aqui vc cria o seu JSON
//
//        StringEntity sEntity;
//        
//        
//        try {
//            sEntity = new StringEntity(jsonPessoa, "UTF-8");
//            sEntity.setContentType("application/json");
//        } catch (UnsupportedEncodingException e) {
//            sEntity = null;
//            Log.i("NGVL", e.toString()+"no sEntity");
//        } 
//        
//        //aqui vc cria a sua StringEntity pra ser colocada no seu httpPost
//        DefaultHttpClient httpclient = new DefaultHttpClient();
//        HttpPost httpPost = new HttpPost("url do webservice");
//
//        httpPost.setEntity(sEntity); //aqui vc coloca o seu objeto no httpPost
//
//        try {
//            HttpResponse response = httpclient.execute(httpPost);
//        } catch (ClientProtocolException e) {
//            Log.d("NGVL", e.toString());
//        } catch (IOException e) {
//            Log.w("NGVL", e.toString());
//        }
//    }
}