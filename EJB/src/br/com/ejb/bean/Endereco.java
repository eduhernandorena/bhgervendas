package br.com.ejb.bean;

import br.com.ejb.bean.enumeration.TipoEndereco;

/**
 *
 * @author Eduardo Hernandorena
 */
public class Endereco {

    private Long codigo;
    private String rua;
    private Integer numero;
    private String complemento;
    private String bairro;
    private String cep;
    private TipoEndereco tipoEndereco;
    private Cidade cidade;

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public TipoEndereco getTipoEndereco() {
        return tipoEndereco;
    }

    public void setTipoEndereco(TipoEndereco tipoEndereco) {
        this.tipoEndereco = tipoEndereco;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Endereco other = (Endereco) obj;
        if (this.codigo != other.codigo && (this.codigo == null || !this.codigo.equals(other.codigo))) {
            return false;
        }
        if ((this.rua == null) ? (other.rua != null) : !this.rua.equals(other.rua)) {
            return false;
        }
        if (this.numero != other.numero && (this.numero == null || !this.numero.equals(other.numero))) {
            return false;
        }
        if (this.cidade != other.cidade && (this.cidade == null || !this.cidade.equals(other.cidade))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.codigo != null ? this.codigo.hashCode() : 0);
        hash = 29 * hash + (this.rua != null ? this.rua.hashCode() : 0);
        hash = 29 * hash + (this.numero != null ? this.numero.hashCode() : 0);
        hash = 29 * hash + (this.cidade != null ? this.cidade.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Endereco{" + "rua=" + rua + ", numero=" + numero + ", cidade=" + cidade + '}';
    }
}
