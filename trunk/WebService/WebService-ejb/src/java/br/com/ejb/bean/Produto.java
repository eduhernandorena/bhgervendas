package br.com.ejb.bean;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eduardo Hernandorena
 */
@Entity
@Table(name = "produto")
@NamedQueries({
    @NamedQuery(name = "Produto.findAll", query = "SELECT p FROM Produto p"),
    @NamedQuery(name = "Produto.findByDesc", query = "SELECT p FROM Produto p WHERE p.descricao = :descricao"),
    @NamedQuery(name = "Produto.findByNota", query = "SELECT p FROM Produto p WHERE p.nroNota = :nroNota")
})
@XmlRootElement
public class Produto implements Serializable {
    
    @Id
    @Column
    private Long id;
    @Column(nullable = false)
    private Long nroNota;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private String referencia;
    @Column(nullable = false)
    private Double precoCusto;
    @Column(nullable = false)
    private Double precoVenda;
    @Column(nullable = false)
    private Double lucro;
    @Column(nullable = false)
    private String tamanho;
    @ManyToMany(mappedBy = "produtos")
    @JoinColumn(name = "pedidos")
    private List<Pedido> pedidos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getLucro() {
        return lucro;
    }

    public void setLucro(Double lucro) {
        this.lucro = lucro;
    }

    public Long getNroNota() {
        return nroNota;
    }

    public void setNroNota(Long nroNota) {
        this.nroNota = nroNota;
    }

    public Double getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(Double precoCusto) {
        this.precoCusto = precoCusto;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produto other = (Produto) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.nroNota != other.nroNota && (this.nroNota == null || !this.nroNota.equals(other.nroNota))) {
            return false;
        }
        if ((this.referencia == null) ? (other.referencia != null) : !this.referencia.equals(other.referencia)) {
            return false;
        }
        if ((this.tamanho == null) ? (other.tamanho != null) : !this.tamanho.equals(other.tamanho)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 97 * hash + (this.nroNota != null ? this.nroNota.hashCode() : 0);
        hash = 97 * hash + (this.referencia != null ? this.referencia.hashCode() : 0);
        hash = 97 * hash + (this.tamanho != null ? this.tamanho.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Produto{" + "descricao=" + descricao + ", referencia=" + referencia + ", tamanho=" + tamanho + '}';
    }
}
