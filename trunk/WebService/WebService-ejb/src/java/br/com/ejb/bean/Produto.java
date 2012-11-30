package br.com.ejb.bean;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eduardo Hernandorena
 */
@Entity
@Table(name = "produto")
@NamedQueries({
    @NamedQuery(name = "Produto.findAll", query = "SELECT p FROM Produto p where p.esgotado=false"),
    @NamedQuery(name = "Produto.findByDesc", query = "SELECT p FROM Produto p WHERE p.descricao = :descricao"),
    @NamedQuery(name = "Produto.findByNota", query = "SELECT p FROM Produto p WHERE p.nroNota = :nroNota")
})
@SequenceGenerator(name = "seq_prod", sequenceName = "seq_prod", allocationSize = 1)
@XmlRootElement
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_prod")
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
    @Column(nullable = false)
    private Boolean esgotado = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNroNota() {
        return nroNota;
    }

    public void setNroNota(Long nroNota) {
        this.nroNota = nroNota;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
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

    public Double getLucro() {
        return lucro;
    }

    public void setLucro(Double lucro) {
        this.lucro = lucro;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public Boolean getEsgotado() {
        return esgotado;
    }

    public void setEsgotado(Boolean esgotado) {
        this.esgotado = esgotado;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.nroNota);
        hash = 53 * hash + Objects.hashCode(this.referencia);
        hash = 53 * hash + Objects.hashCode(this.tamanho);
        return hash;
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.nroNota, other.nroNota)) {
            return false;
        }
        if (!Objects.equals(this.referencia, other.referencia)) {
            return false;
        }
        if (!Objects.equals(this.tamanho, other.tamanho)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Produto{" + "descricao=" + descricao + ", precoVenda=" + precoVenda + ", tamanho=" + tamanho + ", esgotado=" + esgotado + '}';
    }
}
