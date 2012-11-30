package br.com.principal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Eduardo Hernandorena
 */
public class ConfInicial {

    private static String URL = "";
    private static String IP = "";

    public static String getInstance() {
        if (URL.isEmpty()) {
            new ConfInicial().leitor();
        }
        return URL;
    }

    public String getIp() {
        if (IP.isEmpty()) {
            leitor();
        }
        return IP;
    }

    private void leitor() {
        try (FileReader arq = new FileReader("conf.txt")) {
            BufferedReader lerArq = new BufferedReader(arq);
            String linha;
            if ((linha = lerArq.readLine()) != null) {
//                URL = "https://" + linha + ":8181/WebService-war/resources";
                URL = "http://" + linha + ":8080/WebService-war/resources";
                IP = linha;
            }
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }
    }

    public void gravar(String ip) {
        try (FileWriter arq = new FileWriter("conf.txt")) {
            BufferedWriter escreve = new BufferedWriter(arq);
            if (escreve != null) {
                escreve.append(ip);
                escreve.close();
                URL = "http://" + ip + ":8080/WebService-war/resources";
            }
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }
    }
}
