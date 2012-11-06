package br.com.ejb.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eduardo Hernandorena
 */
@Entity
@Table(name = "sync")
@XmlRootElement
@SequenceGenerator(name = "seq_sync", sequenceName = "seq_sync", allocationSize = 1)
public class Sync implements Serializable {

    @Id
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String dataPag;
    @Column(nullable = false)
    private double valor;
    @Column(nullable = false)
    private String tpMov;
    @Column(nullable = false)
    private String parc;
    @Column(nullable = false)
    private boolean sincronizado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public void setDataPag(Date dataPag) {
        this.dataPag = new SimpleDateFormat("dd/MM/yyyy").format(dataPag);
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
