package br.com.ejb.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Eduardo Hernandorena
 */
@Entity
@Table(name = "viagem")
@NamedQueries({
    @NamedQuery(name = "Viagem.findAll", query = "SELECT v FROM Viagem v"),
    @NamedQuery(name = "Viagem.findByGuia", query = "SELECT v FROM Viagem v WHERE v.guia = :guia")
})
public class Viagem implements Serializable {

    @Id
    @Column
    private Long id;
    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataHora;
    @Column(nullable = false)
    private String localizacao;
    @JoinColumn(name = "encomenda")
    @OneToMany
    private List<Encomenda> encomenda;
    @Column
    private Integer qtdeProdutos;
    @Column
    private Double valor;
    @Column(nullable = false)
    private String guia;
    @JoinColumn(name = "pedidos")
    @OneToMany
    private List<Pedido> pedidos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public List<Encomenda> getEncomenda() {
        return encomenda;
    }

    public void setEncomenda(List<Encomenda> encomenda) {
        this.encomenda = encomenda;
    }

    public String getGuia() {
        return guia;
    }

    public void setGuia(String guia) {
        this.guia = guia;
    }

    public String getLocal() {
        return localizacao;
    }

    public void setLocal(String local) {
        this.localizacao = local;
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
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.dataHora != other.dataHora && (this.dataHora == null || !this.dataHora.equals(other.dataHora))) {
            return false;
        }
        if ((this.localizacao == null) ? (other.localizacao != null) : !this.localizacao.equals(other.localizacao)) {
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
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 59 * hash + (this.dataHora != null ? this.dataHora.hashCode() : 0);
        hash = 59 * hash + (this.localizacao != null ? this.localizacao.hashCode() : 0);
        hash = 59 * hash + (this.valor != null ? this.valor.hashCode() : 0);
        hash = 59 * hash + (this.guia != null ? this.guia.hashCode() : 0);
        return hash;
    }
}