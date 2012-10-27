package br.com.bhgervendas.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Eduardo Hernandorena
 */
public class Sync implements Serializable {

    private long id;
    private String nome;
    private Date dataPag;
    private double valor;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataPag() {
        return dataPag;
    }

    public void setDataPag(Date dataPag) {
        this.dataPag = dataPag;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
