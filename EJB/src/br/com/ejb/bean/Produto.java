package br.com.ejb.bean;

/**
 *
 * @author Eduardo Hernandorena
 */
public class Produto {

    private Long codigo;
    private Long nroNota;
    private String descricao;
    private String referencia;
    private Double precoCusto;
    private Double precoVenda;
    private Double lucro;
    private String tamanho;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produto other = (Produto) obj;
        if (this.codigo != other.codigo && (this.codigo == null || !this.codigo.equals(other.codigo))) {
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
        hash = 97 * hash + (this.codigo != null ? this.codigo.hashCode() : 0);
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
