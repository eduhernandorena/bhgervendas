package br.com.bhgervendas.bean;

import java.io.Serializable;

/**
 *
 * @author Eduardo Hernandorena
 */
public class Sync implements Serializable {

    private long id;
    private String nome;
    private String dataPag;
    private double valor;
    private String tpMov;
    private String parc;
    private boolean sincronizado;

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

    public String getDataPag() {
        return dataPag;
    }

    public void setDataPag(String dataPag) {
        this.dataPag = dataPag;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTpMov() {
        return tpMov;
    }

    public void setTpMov(String tpMov) {
        this.tpMov = tpMov;
    }

    public String getParc() {
        return parc;
    }

    public void setParc(String parc) {
        this.parc = parc;
    }

    public boolean isSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(boolean sincronizado) {
        this.sincronizado = sincronizado;
    }
    
}
