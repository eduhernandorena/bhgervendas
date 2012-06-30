package br.com.ejb.bean;

import br.com.ejb.bean.enumeration.FormaPagamento;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Eduardo Hernandorena
 */
public class Pedido {
    private Long codigo;
    private Date dataCompra;
    private Double valor;
    private Integer nroParcelas;
    private FormaPagamento formaPagamento;
    private Entidade cliente;
    private Entidade fornecedor;
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
