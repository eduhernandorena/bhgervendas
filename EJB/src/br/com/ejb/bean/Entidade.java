package br.com.ejb.bean;

import br.com.ejb.bean.enumeration.EstadoCivil;
import br.com.ejb.bean.enumeration.Genero;
import br.com.ejb.bean.enumeration.TipoEntidade;
import java.util.List;
import org.brazilutils.br.cpfcnpj.CpfCnpj;

/**
 *
 * @author Eduardo Hernandorena
 */
public class Entidade {

    private Long codigo;
    private Long telefone;
    private Long celular;
    private String nome;
    private String email;
    private String obs;
    private Genero genero;
    private EstadoCivil estadoCivil;
    private CpfCnpj cpfCnpj;
    private TipoEntidade tipoEntidade;
    private Endereco endereco;
    private Pedido ultimoPedido;
    private List<Pedido> pedidos;

    public Long getCelular() {
        return celular;
    }

    public void setCelular(Long celular) {
        this.celular = celular;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public CpfCnpj getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(CpfCnpj cpfCnpj) {
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

    public Pedido getUltimoPedido() {
        return ultimoPedido;
    }

    public void setUltimoPedido(Pedido ultimoPedido) {
        this.ultimoPedido = ultimoPedido;
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
        if (this.codigo != other.codigo && (this.codigo == null || !this.codigo.equals(other.codigo))) {
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
        hash = 67 * hash + (this.codigo != null ? this.codigo.hashCode() : 0);
        hash = 67 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        hash = 67 * hash + (this.cpfCnpj != null ? this.cpfCnpj.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Nome=" + nome + ", celular=" + celular + ", telefone=" + telefone;
    }
}
