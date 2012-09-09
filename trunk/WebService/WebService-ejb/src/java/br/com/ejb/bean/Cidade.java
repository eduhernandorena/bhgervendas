package br.com.ejb.bean;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eduardo Hernandorena
 */
@Entity
@Table(name = "cidade")
@NamedQueries({
    @NamedQuery(name = "Cidade.findByDesc", query = "SELECT c FROM Cidade c WHERE c.descricao = :descricao"),
    @NamedQuery(name = "Cidade.findByIbge", query = "SELECT c FROM Cidade c WHERE c.codIbge = :ibge"),
    @NamedQuery(name = "Cidade.findAll", query = "SELECT c FROM Cidade c")})
@XmlRootElement
public class Cidade implements Serializable {

    @Id
    @Column(nullable = false)
    private Long codIbge;
    @Column(nullable = false)
    private String descricao;
    @JoinColumn(name = "UF")
    @ManyToOne
    private UF uf;

    public Long getCodIbge() {
        return codIbge;
    }

    public void setCodIbge(Long codIbge) {
        this.codIbge = codIbge;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public UF getUf() {
        return uf;
    }

    public void setUf(UF uf) {
        this.uf = uf;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cidade other = (Cidade) obj;
        if (this.codIbge != other.codIbge && (this.codIbge == null || !this.codIbge.equals(other.codIbge))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.codIbge != null ? this.codIbge.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Cidade{" + "descricao=" + descricao + ", uf=" + uf + '}';
    }
}
