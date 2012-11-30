package br.com.bhgervendas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 *
 * @author Eduardo Hernandorena
 */
public class IpPorta extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ipporta);
        Button btSave = (Button) findViewById(R.id.btSalvar);
        final EditText txtIP = (EditText) findViewById(R.id.txtIP);
        final ManageFile filewrite = new ManageFile(getApplicationContext());
        String file = filewrite.ReadFile();
        txtIP.setText(file != null ? file : "");
        btSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // Avisa o usuário se a gravação foi bem sucedida
                if (txtIP != null && txtIP.getText() != null) {
                    if (filewrite.WriteFile(txtIP.getText().toString()) == true) {
                        Toast.makeText(getApplicationContext(), "Texto gravado com sucesso.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Não foi possível escrever o texto.", Toast.LENGTH_LONG).show();
                    }
                }
                
            }
        });
        
    }
}
