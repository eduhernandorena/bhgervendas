package br.com.rel.decorator;

/**
 *
 * @author roger
 */
public class RelDecorator {

    private Integer totalVeic;
    private Double totalPago;
    private Double valorMedioPorVeic;
    private Double veicPorHora;

    public Integer getTotalVeic() {
        return totalVeic;
    }

    public void setTotalVeic(Integer totalVeic) {
        this.totalVeic = totalVeic;
    }

    public Double getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(Double totalPago) {
        this.totalPago = totalPago;
    }

    public Double getValorMedioPorVeic() {
        return valorMedioPorVeic;
    }

    public void setValorMedioPorVeic(Double valorMedioPorVeic) {
        this.valorMedioPorVeic = valorMedioPorVeic;
    }

    public Double getVeicPorHora() {
        return veicPorHora;
    }

    public void setVeicPorHora(Double veicPorHora) {
        this.veicPorHora = veicPorHora;
    }
}
