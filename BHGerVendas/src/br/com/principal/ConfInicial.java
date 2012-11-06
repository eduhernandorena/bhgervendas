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

    private String ip;
    private Integer porta;
    private String user;
    private String senha;
    private static String URL = "";

    public static String getInstance() {
        if (URL.isEmpty()) {
            new ConfInicial().leitor();
        }
        return URL;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPorta() {
        return porta;
    }

    public void setPorta(Integer porta) {
        this.porta = porta;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    private void leitor() {
        try (FileReader arq = new FileReader("conf.txt")) {
            BufferedReader lerArq = new BufferedReader(arq);
            String linha;
            if ((linha = lerArq.readLine()) != null) {
                setIp(linha);
                setPorta(Integer.valueOf(lerArq.readLine()));
                setUser(lerArq.readLine());
                setSenha(lerArq.readLine());
                URL = "http://" + getIp() + ":8080/WebService-war/resources";
            }
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }
    }

    private void gravar() {
        try (FileWriter arq = new FileWriter("conf.txt")) {
            BufferedWriter escreve = new BufferedWriter(arq);
            if (escreve != null) {
                escreve.append(getIp());
                escreve.newLine();
                escreve.append(getPorta().toString());
                escreve.newLine();
                escreve.append(getUser());
                escreve.newLine();
                escreve.append(getSenha());
                escreve.close();
                URL = "http://" + getIp() + ":8080/WebService-war/resources";
            }
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }
    }
}
