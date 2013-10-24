package br.com.bean;

import br.com.bean.enumeration.StatusTicket;
import java.util.Date;

/**
 *
 * @author Eduardo Hernandorena
 */
public class Ticket {

    private Long id;
    private String serie;
    private Date dataEnt;
    private Date horaEnt;
    private Date dataSai;
    private Date horaSai;
    private StatusTicket status;
    private String tempo;
    private TabelaPreco tabela;
    private String placa;
    private Double valor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataEnt() {
        return dataEnt;
    }

    public void setDataEnt(Date dataEnt) {
        this.dataEnt = dataEnt;
    }

    public Date getHoraEnt() {
        return horaEnt;
    }

    public void setHoraEnt(Date horaEnt) {
        this.horaEnt = horaEnt;
    }

    public Date getDataSai() {
        return dataSai;
    }

    public void setDataSai(Date dataSai) {
        this.dataSai = dataSai;
    }

    public Date getHoraSai() {
        return horaSai;
    }

    public void setHoraSai(Date horaSai) {
        this.horaSai = horaSai;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public StatusTicket getStatus() {
        return status;
    }

    public void setStatus(StatusTicket status) {
        this.status = status;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public TabelaPreco getTabela() {
        return tabela;
    }

    public void setTabela(TabelaPreco tabela) {
        this.tabela = tabela;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
