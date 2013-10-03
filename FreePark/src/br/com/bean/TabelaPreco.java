package br.com.bean;

import br.com.bean.enumeration.Modalidade;

/**
 *
 * @author Eduardo Hernandorena
 */
public class TabelaPreco {
    private Long id;
    private Double prHora;
    private Double prFracao;
    private Double halfHora;
    private Modalidade mod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrHora() {
        return prHora;
    }

    public void setPrHora(Double prHora) {
        this.prHora = prHora;
    }

    public Double getPrFracao() {
        return prFracao;
    }

    public void setPrFracao(Double prFracao) {
        this.prFracao = prFracao;
    }

    public Double getHalfHora() {
        return halfHora;
    }

    public void setHalfHora(Double halfHora) {
        this.halfHora = halfHora;
    }

    public Modalidade getMod() {
        return mod;
    }

    public void setMod(Modalidade mod) {
        this.mod = mod;
    }
}
