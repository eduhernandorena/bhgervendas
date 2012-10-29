package br.com.bhgervendas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

public class Principal extends Activity {

    private Spinner spinnerAno = null;
    private Spinner spinnerMes = null;
    private RadioButton btCliente;
    private RadioButton btForn;
    private RadioButton btTodos;
    private Button btMostrar;
    private Integer mes;
    private Integer ano;

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

        List<String> anos = new ArrayList<String>();
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
                ano = position;
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

    private void sincroniza() {
        Intent intent = new Intent(this, Relatorio.class);
        intent.putExtra("mes", mes != null ? spinnerMes.getSelectedItemPosition() : mes);
        intent.putExtra("ano", ano != null ? spinnerAno.getSelectedItemPosition() : ano);
        if (btCliente.isSelected()) {
            intent.putExtra("tpMov", "E");
        } else if (btForn.isSelected()) {
            intent.putExtra("tpMov", "S");
        } else if (btTodos.isSelected()) {
            intent.putExtra("tpMov", "T");
        }
        startActivity(intent);
    }
}