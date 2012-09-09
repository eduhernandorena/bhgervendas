package br.com.ejb.bean;

import java.io.Serializable;
import java.util.List;
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
public class UF implements Serializable {

    @Id
    @Column(nullable = false)
    private Long codIbge;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private String sigla;
    @JoinColumn(name = "cidades")
    @OneToMany
    private List<Cidade> cidades;

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

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
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
        if (!Objects.equals(this.cidades, other.cidades)) {
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
        hash = 29 * hash + Objects.hashCode(this.cidades);
        return hash;
    }
}
