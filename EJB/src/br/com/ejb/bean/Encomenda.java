package br.com.ejb.bean;

import java.util.Date;

/**
 *
 * @author Eduardo Hernandorena
 */
public class Encomenda {
    private Long codigo;
    private Viagem viagem;
    private Date dataCadastro;
    private Pedido pedido;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Viagem getViagem() {
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Encomenda other = (Encomenda) obj;
        if (this.codigo != other.codigo && (this.codigo == null || !this.codigo.equals(other.codigo))) {
            return false;
        }
        if (this.dataCadastro != other.dataCadastro && (this.dataCadastro == null || !this.dataCadastro.equals(other.dataCadastro))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.codigo != null ? this.codigo.hashCode() : 0);
        hash = 79 * hash + (this.dataCadastro != null ? this.dataCadastro.hashCode() : 0);
        return hash;
    }
}
