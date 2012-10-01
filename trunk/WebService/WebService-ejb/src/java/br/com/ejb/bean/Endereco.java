package br.com.ejb.bean;

import br.com.ejb.bean.enumeration.TipoEndereco;
import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eduardo Hernandorena
 */
@Entity
@Table(name = "endereco")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Endereco.findAll", query = "SELECT e FROM Endereco e"),
    @NamedQuery(name = "Endereco.findByRua", query = "SELECT e FROM Endereco e WHERE e.logradouro = :logradouro"),
    @NamedQuery(name = "Endereco.findByCep", query = "SELECT e FROM Endereco e WHERE e.cep = :cep"),
    @NamedQuery(name = "Endereco.findByBairro", query = "SELECT e FROM Endereco e WHERE e.bairro = :bairro"),
    @NamedQuery(name = "Endereco.findByIbge", query = "SELECT e FROM Endereco e WHERE e.cidade.codIbge = :codIbge")
})
@SequenceGenerator(name = "seq_end", sequenceName = "seq_end", allocationSize = 1)
public class Endereco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_end")
    private Long id;
    @Column(nullable = false)
    private String logradouro;
    @Column(nullable = false)
    private Integer numero;
    @Column
    private String complemento;
    @Column(nullable = false)
    private String bairro;
    @Column(nullable = false)
    private String cep;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoEndereco tipoEndereco;
    @ManyToOne
    @JoinColumn(name = "cidade")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return logradouro;
    }

    public void setRua(String rua) {
        this.logradouro = rua;
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
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.logradouro == null) ? (other.logradouro != null) : !this.logradouro.equals(other.logradouro)) {
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
        hash = 29 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 29 * hash + (this.logradouro != null ? this.logradouro.hashCode() : 0);
        hash = 29 * hash + (this.numero != null ? this.numero.hashCode() : 0);
        hash = 29 * hash + (this.cidade != null ? this.cidade.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        String end = "";
        end = end.concat(logradouro);
        end = end.concat(numero != null ? ", " + numero : "S/N");
        end = end.concat(complemento != null ? ", " + complemento : "");
        end = end.concat(cidade != null ? " - " + cidade.getDescricao() : "");
        end = end.concat(cidade.getUf() != null ? "/" + cidade.getUf().getSigla() : "");
        return end;
    }
}
