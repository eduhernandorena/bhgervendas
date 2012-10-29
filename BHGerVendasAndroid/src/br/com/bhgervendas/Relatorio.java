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
import java.util.List;
import org.apache.http.conn.HttpHostConnectException;

public class Relatorio extends Activity {

    private List<Sync> lista;
    private Spinner spinnerAno = null;
    private Spinner spinnerMes = null;
    private ListView txtView = null;
    private int ano;
    private int mes;
    private String tpMov;
    private SyncDAO dao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relatorio);
        Bundle extras = getIntent().getExtras();
        mes = extras != null ? extras.getInt("mes") : 1;
        ano = extras != null ? extras.getInt("ano") : 1;
        tpMov = extras != null ? extras.getString("tpMov") : "T";
        this.setTitle("BHGerVendas -- Relatorio");

        spinnerAno = (Spinner) findViewById(R.id.comboAno);
        spinnerMes = (Spinner) findViewById(R.id.comboMes);
        txtView = (ListView) findViewById(R.id.list);

        dao = new SyncDAO(this);
        dao.open();

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
            }
        });
        spinnerMes.setSelection(mes);
        spinnerAno.setSelection(ano);
    }

    private void sincroniza() {
        List<Sync> list = null;
        lista = new ArrayList<Sync>();
        try {
//            list = new SyncREST().getListaSync();
            if (list == null) {
//                list = dao.getAll();
            }
            for (Sync sync : list) {
                if (sync.getTpMov().equalsIgnoreCase(tpMov)) {
                    lista.add(sync);
                }
                if (!sync.isSincronizado()) {
                    dao.create(sync);
                    lista.add(sync);
                }
            }
            List<String> l = new ArrayList<String>();
            for (Sync sync : lista) {
                String temp = "[" + sync.getTpMov() + "] "
                        + sync.getDataPag() + " - "
                        + sync.getNome()
                        + " R$" + sync.getValor()
                        + " (" + sync.getParc() + ")";
                l.add(temp);
            }
//            Collections.sort(l); Aplicar comparação
            ArrayAdapter<String> adapterSync = new ArrayAdapter<String>(this, R.layout.list_item, l);
            txtView.setAdapter(adapterSync);
            txtView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                }
            });
            finish();
//        } catch (HttpHostConnectException e) {
//            gerarToast("Erro:\nNão foi possível conectar!");
//            Log.e("NGVL", "Erro de conexão!", e);
        } catch (Exception e) {
            gerarToast("Erro: " + e.getMessage());
            Log.e("NGVL", "Erro Diverso!", e);
        }
    }

    @Override
    protected void onResume() {
        dao.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dao.close();
        super.onPause();
    }

    private void gerarToast(CharSequence message) {
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(getApplicationContext(), message, duration);
        toast.show();
    }
}