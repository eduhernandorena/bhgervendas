package br.com.bhgervendas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

public class Principal extends Activity {

    private Spinner spinnerAno = null;
    private Spinner spinnerMes = null;
    private RadioButton btCliente;
    private RadioButton btForn;
    private RadioButton btTodos;
    private RadioGroup rg;
    private Button btMostrar;
    private Integer mes;
    private Integer ano;
    private List<String> anos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Habilita novos recursos na janela  
        requestWindowFeature(Window.FEATURE_LEFT_ICON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.setTitle("BHGerVendas");

        // Adiciona um ícone a esquerda 
        setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.icon);

        spinnerAno = (Spinner) findViewById(R.id.comboAnoTP);
        spinnerMes = (Spinner) findViewById(R.id.comboMesTP);
        btCliente = (RadioButton) findViewById(R.id.rdCliente);
        btForn = (RadioButton) findViewById(R.id.rdForn);
        btTodos = (RadioButton) findViewById(R.id.rdTodos);
        rg = (RadioGroup) findViewById(R.id.btGroup);
        btMostrar = (Button) findViewById(R.id.btMostrar);

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
        spinnerMes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v,
                    int position, long id) {
                mes = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        anos = new ArrayList<String>();
        anos.add("2012");
        anos.add("2013");
        anos.add("2014");
        anos.add("2015");
        anos.add("2016");
        ArrayAdapter<String> adapterAno = new ArrayAdapter<String>(this, R.layout.spinner_textview_ano, anos);
        adapterAno.setDropDownViewResource(R.layout.simple_ano);
        spinnerAno.setAdapter(adapterAno);
        spinnerAno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v,
                    int position, long id) {
                ano = Integer.valueOf(anos.get(position).substring(2));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        btMostrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sincroniza();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this, IpPorta.class));
        return true;
    }

    private void sincroniza() {
        Intent intent = new Intent(this, Relatorio.class);
        intent.putExtra("mes", mes != null ? spinnerMes.getSelectedItemPosition() : mes);
        intent.putExtra("ano", ano != null ? spinnerAno.getSelectedItemPosition() : ano);
        int op = rg.getCheckedRadioButtonId();
        if (op == btCliente.getId()) {
            System.out.println("E");
            intent.putExtra("tpMov", "E");
        } else if (op == btForn.getId()) {
            System.out.println("S");
            intent.putExtra("tpMov", "S");
        } else {
            System.out.println("T");
            intent.putExtra("tpMov", "T");
        }
        startActivity(intent);
    }
}