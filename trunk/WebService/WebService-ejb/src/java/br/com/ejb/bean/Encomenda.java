package br.com.ejb.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eduardo Hernandorena
 */
@Entity
@Table(name = "encomenda")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Encomenda.findAll", query = "SELECT e FROM Encomenda e"),
    @NamedQuery(name = "Encomenda.findByViagem", query = "SELECT e FROM Encomenda e WHERE e.viagem.id = :viagemId")
})
@SequenceGenerator(name = "seq_encom", sequenceName = "seq_encom", allocationSize = 1)
public class Encomenda implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_encom")
    private Long codigo;
    @JoinColumn(name = "viagem")
    @OneToOne
    private Viagem viagem;
    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataCadastro;
    @JoinColumn(name = "pedido")
    @OneToOne
    private Pedido pedido;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDataCadastro() {
        return new SimpleDateFormat("dd/MM/yyyy").format(dataCadastro);
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
