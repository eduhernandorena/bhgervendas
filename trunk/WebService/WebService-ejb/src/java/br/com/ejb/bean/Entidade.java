package br.com.ejb.bean;

import br.com.ejb.bean.enumeration.EstadoCivil;
import br.com.ejb.bean.enumeration.Genero;
import br.com.ejb.bean.enumeration.TipoEntidade;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Eduardo Hernandorena
 */
@Entity
@Table(name = "entidade")
@NamedQueries({
    @NamedQuery(name = "Entidade.findAll", query = "SELECT e FROM Entidade e"),
    @NamedQuery(name = "Entidade.findByEstadoCivil", query = "SELECT e FROM Entidade e WHERE e.estadoCivil = :estadoCivil"),
    @NamedQuery(name = "Entidade.findByTpEntidade", query = "SELECT e FROM Entidade e WHERE e.tipoEntidade = :tpEntidade"),
    @NamedQuery(name = "Entidade.findByCpfCnpj", query = "SELECT e FROM Entidade e WHERE e.cpfCnpj = :cpfCnpj"),
    @NamedQuery(name = "Entidade.findByGenero", query = "SELECT e FROM Entidade e WHERE e.genero = :genero")
})
@SequenceGenerator(name = "seq_ent", sequenceName = "seq_ent", allocationSize = 1)
@XmlRootElement
public class Entidade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ent")
    private Long id;
    @Column
    private Long telefone;
    @Column
    private Long celular;
    @Column(nullable = false)
    private String nome;
    @Column
    private String email;
    @Column
    private String obs;
    @Column(nullable = false)
    private Genero genero;
    @Column(nullable = false)
    private EstadoCivil estadoCivil;
    @Column(nullable = false)
    private String cpfCnpj;
    @Column(nullable = false)
    private TipoEntidade tipoEntidade;
    @JoinColumn(name = "endereco")
    @OneToOne
    private Endereco endereco;
    @Column
    private Long codUltPedido;
    @Column
    private Boolean ativo;
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;

    public Long getCelular() {
        return celular;
    }

    public void setCelular(Long celular) {
        this.celular = celular;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    @XmlTransient
    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    public TipoEntidade getTipoEntidade() {
        return tipoEntidade;
    }

    public void setTipoEntidade(TipoEntidade tipoEntidade) {
        this.tipoEntidade = tipoEntidade;
    }

    public Long getUltimoPedido() {
        return codUltPedido;
    }

    public void setUltimoPedido(Long codUltPedido) {
        this.codUltPedido = codUltPedido;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Entidade other = (Entidade) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if (this.cpfCnpj != other.cpfCnpj && (this.cpfCnpj == null || !this.cpfCnpj.equals(other.cpfCnpj))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 67 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        hash = 67 * hash + (this.cpfCnpj != null ? this.cpfCnpj.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return nome;
    }
}
