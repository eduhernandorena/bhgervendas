package br.com.ejb.bean;

import br.com.ejb.utils.Utilidades;
import java.io.Serializable;
import java.text.Collator;
import java.util.Objects;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eduardo Hernandorena
 */
@Entity
@Table(name = "UF")
@NamedQueries({
    @NamedQuery(name = "UF.findAll", query = "SELECT u FROM UF u"),
    @NamedQuery(name = "UF.findByDesc", query = "SELECT u FROM UF u WHERE u.descricao = :descricao"),
    @NamedQuery(name = "UF.findByIbge", query = "SELECT u FROM UF u WHERE u.codIbge = :ibge")
})
@XmlRootElement
public class UF implements Serializable, Comparable<UF> {

    @Id
    @Column(nullable = false)
    private Long codIbge;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private String sigla;

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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UF other = (UF) obj;
        if (!Objects.equals(this.codIbge, other.codIbge)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.sigla, other.sigla)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.codIbge);
        hash = 29 * hash + Objects.hashCode(this.descricao);
        hash = 29 * hash + Objects.hashCode(this.sigla);
        return hash;
    }

    @Override
    public String toString() {
        return Utilidades.upperInicial(descricao);
    }

    @Override
    public int compareTo(UF o) {
        return Collator.getInstance().compare(descricao, o.getDescricao());
    }
}
