package br.com.bhgervendas;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManageFile {

    private static final String TAG = "ManageFile";
    private Context context;

    public ManageFile(Context context) {
        this.context = context;
    }

    /**
     * Escreve no arquivo texto.
     *
     * @param text Texto a ser escrito.
     * @return True se o texto foi escrito com sucesso.
     */
    public boolean WriteFile(String text) {
        try {
            // Abre o arquivo para escrita ou cria se não existir
            FileOutputStream out = context.openFileOutput("ipBHGerVendas.txt",
                    Context.MODE_APPEND);
            out.flush();
            out.write(text.getBytes());
            out.write("\n".getBytes());
            out.flush();
            out.close();
            return true;

        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    /**
     * Faz a leitura do arquivo
     *
     * @return O texto lido.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public String ReadFile() {
        FileInputStream input = null;
        try {
            File file = context.getFilesDir();
            File textfile = new File(file + "/ipBHGerVendas.txt");
            input = context.openFileInput("ipBHGerVendas.txt");
            byte[] buffer = new byte[(int) textfile.length()];
            input.read(buffer);
            return new String(buffer).trim();
        } catch (FileNotFoundException ex) {
            gerarToast("Arquivo de ip não encontrado!");
        } catch (IOException ex) {
            gerarToast("Arquivo de ip não encontrado!");
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(ManageFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    private void gerarToast(CharSequence message) {
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
}