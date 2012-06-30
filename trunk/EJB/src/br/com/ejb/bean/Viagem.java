package br.com.ejb.bean;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Eduardo Hernandorena
 */
public class Viagem {

    private Long codigo;
    private Date dataHora;
    private String local;
    private Encomenda encomenda;
    private Integer qtdeProdutos;
    private Double valor;
    private String guia;
    private List<Pedido> pedidos;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public Encomenda getEncomenda() {
        return encomenda;
    }

    public void setEncomenda(Encomenda encomenda) {
        this.encomenda = encomenda;
    }

    public String getGuia() {
        return guia;
    }

    public void setGuia(String guia) {
        this.guia = guia;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Integer getQtdeProdutos() {
        return qtdeProdutos;
    }

    public void setQtdeProdutos(Integer qtdeProdutos) {
        this.qtdeProdutos = qtdeProdutos;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Viagem other = (Viagem) obj;
        if (this.codigo != other.codigo && (this.codigo == null || !this.codigo.equals(other.codigo))) {
            return false;
        }
        if (this.dataHora != other.dataHora && (this.dataHora == null || !this.dataHora.equals(other.dataHora))) {
            return false;
        }
        if ((this.local == null) ? (other.local != null) : !this.local.equals(other.local)) {
            return false;
        }
        if (this.valor != other.valor && (this.valor == null || !this.valor.equals(other.valor))) {
            return false;
        }
        if ((this.guia == null) ? (other.guia != null) : !this.guia.equals(other.guia)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.codigo != null ? this.codigo.hashCode() : 0);
        hash = 59 * hash + (this.dataHora != null ? this.dataHora.hashCode() : 0);
        hash = 59 * hash + (this.local != null ? this.local.hashCode() : 0);
        hash = 59 * hash + (this.valor != null ? this.valor.hashCode() : 0);
        hash = 59 * hash + (this.guia != null ? this.guia.hashCode() : 0);
        return hash;
    }
}
