package br.com.bhgervendas.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.annotations.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * @author Eduardo Hernandorena
 */
public class Sync implements Serializable, Comparable<Sync> {

    @Expose
    @SerializedName("id")
    private Long id;
    @Expose
    @SerializedName("nome")
    private String nome;
    @Expose
    @SerializedName("dataPag")
    private String dataPag;
    @Expose
    @SerializedName("valor")
    private double valor;
    @Expose
    @SerializedName("tpMov")
    private String tpMov;
    @Expose
    @SerializedName("parc")
    private String parc;
    @Expose
    @SerializedName("sincronizado")
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

    public int compareTo(Sync t) {
        return this.getDate().compareTo(t.getDate());
    }

    public Date getDate() {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(dataPag);
        } catch (ParseException ex) {
            Logger.getLogger(Sync.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String toString() {
        Locale local = new Locale("pt", "BR");
        DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(local));
        return "[" + this.getTpMov() + "] - " + this.getDataPag() + "\n"
                + this.getNome()
                + "\nR$" + df.format(this.getValor()) + " (" + this.getParc().replace("/", " de ") + ")";
    }
}
