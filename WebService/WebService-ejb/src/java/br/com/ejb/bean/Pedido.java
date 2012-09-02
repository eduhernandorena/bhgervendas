package br.com.ejb.bean;

import br.com.ejb.bean.enumeration.FormaPagamento;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Eduardo Hernandorena
 */
@Entity
@Table(name = "pedido")
@NamedQueries({
    @NamedQuery(name = "Pedido.findAll", query = "SELECT p FROM Pedido p"),
    @NamedQuery(name = "Pedido.findByPrazo", query = "SELECT p FROM Pedido p WHERE p.formaPagamento = :formaPag"),
    @NamedQuery(name = "Pedido.findByFornecedor", query = "SELECT p FROM Pedido p WHERE p.fornecedor.id = :entidadeId")
})
public class Pedido implements Serializable {

    @Id
    @Column
    private Long codigo;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column
    private Date dataCompra;
    @Column
    private Double valor;
    @Column(nullable = false)
    private Integer nroParcelas;
    @Column(nullable = false)
    private FormaPagamento formaPagamento;
    @JoinColumn(name = "cliente")
    @ManyToOne
    private Entidade cliente;
    @ManyToOne
    @JoinColumn(name = "fornecedor")
    private Entidade fornecedor;
    @JoinColumn(name = "produtos")
    @ManyToMany
    private List<Produto> produtos;

    public Entidade getCliente() {
        return cliente;
    }

    public void setCliente(Entidade cliente) {
        this.cliente = cliente;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Entidade getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Entidade fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Integer getNroParcelas() {
        return nroParcelas;
    }

    public void setNroParcelas(Integer nroParcelas) {
        this.nroParcelas = nroParcelas;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
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
        final Pedido other = (Pedido) obj;
        if (this.codigo != other.codigo && (this.codigo == null || !this.codigo.equals(other.codigo))) {
            return false;
        }
        if (this.dataCompra != other.dataCompra && (this.dataCompra == null || !this.dataCompra.equals(other.dataCompra))) {
            return false;
        }
        if (this.valor != other.valor && (this.valor == null || !this.valor.equals(other.valor))) {
            return false;
        }
        if (this.cliente != other.cliente && (this.cliente == null || !this.cliente.equals(other.cliente))) {
            return false;
        }
        if (this.fornecedor != other.fornecedor && (this.fornecedor == null || !this.fornecedor.equals(other.fornecedor))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.codigo != null ? this.codigo.hashCode() : 0);
        hash = 47 * hash + (this.dataCompra != null ? this.dataCompra.hashCode() : 0);
        hash = 47 * hash + (this.valor != null ? this.valor.hashCode() : 0);
        hash = 47 * hash + (this.cliente != null ? this.cliente.hashCode() : 0);
        hash = 47 * hash + (this.fornecedor != null ? this.fornecedor.hashCode() : 0);
        return hash;
    }
}