package br.com.bhgervendas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import br.com.bhgervendas.bean.Sync;
import br.com.bhgervendas.ws.SyncREST;
import java.util.ArrayList;

public class Principal extends Activity {

    private String id;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//     final EditText idEdt = (EditText) findViewById(R.id.editTextIdCliente);
        final ListView txtView = (ListView) findViewById(R.id.lista);
//     Button buscarIdBtn = (Button) findViewById(R.id.buttonBuscarPorId);
        Button buscarTodosBtn = (Button) findViewById(R.id.sincroniza);
//     Button deletarBtn = (Button) findViewById(R.id.buttonDeletarCliente);
//     Button inserirBtn = (Button) findViewById(R.id.buttonInserirCliente);

//     buscarIdBtn.setOnClickListener(new View.OnClickListener() {
//
//         @Override
//         public void onClick(View v) {
//             id = idEdt.getText().toString();
//             ClienteREST cliREST = new ClienteREST();
//             try {
//                 Cliente cliente = cliREST.getCliente(Integer.parseInt(id));
//                 txtView.setText(cliente.toString());
//             } catch (NumberFormatException e) {
//                 e.printStackTrace();
//                 gerarToast("Digite um ID válido!");
//             } catch (Exception e) {
//                 e.printStackTrace();
//                 gerarToast(e.getMessage());
//             }
//         }
//     });

        buscarTodosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SyncREST cliREST = new SyncREST();
                try {
                    ArrayList<Sync> listaSync = (ArrayList<Sync>) cliREST.getListaSync();
                    Intent i = new Intent(getApplicationContext(), ListaSync.class);
                    i.putExtra("lista", listaSync);
                    startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                    gerarToast(e.getMessage());
                }

            }
        });

//     deletarBtn.setOnClickListener(new View.OnClickListener() {
//
//         @Override
//         public void onClick(View v) {
//             id = idEdt.getText().toString();
//             ClienteREST cliREST = new ClienteREST();
//             try {
//                 String resposta = cliREST.deletarCliente(Integer.parseInt(id));
//                 txtView.setText(resposta);
//             } catch (NumberFormatException e) {
//                 e.printStackTrace();
//                 gerarToast("Digite um ID válido!");
//             } catch (Exception e) {
//                 e.printStackTrace();
//                 gerarToast(e.getMessage());
//             }
//
//         }
//     });;

//     inserirBtn.setOnClickListener(new View.OnClickListener() {
//
//         @Override
//         public void onClick(View v) {
//             Cliente cliente = new Cliente();
//             cliente.setNome("João da Silva");
//             cliente.setCpf("000333444-01");
//             cliente.setEndereco("Rua do lado, 33");
//             //não coloquei o ID, pq isso é papel do banco, com um auto increment.
//             ClienteREST cliREST = new ClienteREST();
//             try {
//                 String resposta = cliREST.inserirCliente(cliente);
//                 txtView.setText(resposta);
//             } catch (Exception e) {
//                 e.printStackTrace();
//                 gerarToast(e.getMessage());
//             }
//         }
//     });
//    }
    }

    private void gerarToast(CharSequence message) {
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast
                .makeText(getApplicationContext(), message, duration);
        toast.show();
    }
}
