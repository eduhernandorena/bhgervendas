package br.com.ejb.bean;

import br.com.ejb.bean.enumeration.FormaPagamento;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eduardo Hernandorena
 */
@Entity
@Table(name = "pedido")
@NamedQueries({
    @NamedQuery(name = "Pedido.findAll", query = "SELECT p FROM Pedido p"),
    @NamedQuery(name = "Pedido.findByPrazo", query = "SELECT p FROM Pedido p WHERE p.formaPagamento = :formaPag"),
    @NamedQuery(name = "Pedido.findByFornecedor", query = "SELECT p FROM Pedido p WHERE p.fornecedor.id = :entidadeId")
})
@SequenceGenerator(name = "seq_ped", sequenceName = "seq_ped", allocationSize = 1)
@XmlRootElement
public class Pedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_ped")
    private Long codigo;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column
    private Date dataCompra;
    @Column
    private Double valor;
    @Column(nullable = false)
    private Integer nroParcelas;
    @Column(nullable = false)
    private FormaPagamento formaPagamento;
    @JoinColumn(name = "cliente")
    @ManyToOne
    private Entidade cliente;
    @ManyToOne
    @JoinColumn(name = "fornecedor")
    private Entidade fornecedor;
    @JoinColumn(name = "produtos")
    @ManyToMany
    private List<Produto> produtos;
    @Column
    private String ObsPagamento;
    @OneToOne
    private Viagem viagem;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDataCompra() {
        return new SimpleDateFormat("dd/MM/yyyy").format(dataCompra);
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getNroParcelas() {
        return nroParcelas;
    }

    public void setNroParcelas(Integer nroParcelas) {
        this.nroParcelas = nroParcelas;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Entidade getCliente() {
        return cliente;
    }

    public void setCliente(Entidade cliente) {
        this.cliente = cliente;
    }

    public Entidade getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Entidade fornecedor) {
        this.fornecedor = fornecedor;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public String getObsPagamento() {
        return ObsPagamento;
    }

    public void setObsPagamento(String ObsPagamento) {
        this.ObsPagamento = ObsPagamento;
    }

    public Viagem getViagem() {
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.codigo);
        hash = 59 * hash + Objects.hashCode(this.cliente);
        hash = 59 * hash + Objects.hashCode(this.fornecedor);
        hash = 59 * hash + Objects.hashCode(this.viagem);
        return hash;
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
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (!Objects.equals(this.fornecedor, other.fornecedor)) {
            return false;
        }
        if (!Objects.equals(this.viagem, other.viagem)) {
            return false;
        }
        return true;
    }
}
