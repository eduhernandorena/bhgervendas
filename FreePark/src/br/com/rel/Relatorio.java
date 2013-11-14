package br.com.rel;

import br.com.bean.Ticket;
import br.com.rel.decorator.RelDecorator;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author roger
 */
public class Relatorio {

    public String relAnalitico(List<Ticket> list, Date dtIni, Date dtFin) {
        SimpleDateFormat sdfPadrao = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        SimpleDateFormat sdfPerson = new SimpleDateFormat("dd HH:mm");
        String text = "==========================================\n"
                + "ESTACIONAMENTO DE VEICULOS RS\n"
                + "Movimento de Saída - Analítico\n"
                + "Emissão: " + sdfPadrao.format(new Date()) + "\n"
                + "==========================================\n"
                + "Período: " + sdfPadrao.format(dtIni) + " a\n"
                + "         " + sdfPadrao.format(dtFin) + "\n\n"
                + "Placa   Dia  Entr Dia Saida Tempo Tb  Pago\n";
        double total = 0;
        for (Ticket reg : list) {
            text += reg.getPlaca().replace("-", "") + "  " + sdfPerson.format(reg.getDataEnt()) + "  "
                    + sdfPerson.format(reg.getDataSai()) + " " + reg.getTempo().substring(0, 5) + " "
                    + "0" + (reg.getTabela().getMod().ordinal() + 1)
                    + (reg.getValor() > 9 ? " " : "  ") + new BigDecimal(reg.getValor()).setScale(2) + "\n";
            total += reg.getValor();
        }
        text += "\nTotal de Veículos.......: " + list.size() + "\n\n"
                + "Tempo Total.............: \n\n"
                + "Valor Total Pago........: " + total + " /Qtd.: " + list.size() + "\n\n"
                + "Valor Médio p/ Veículo..: " + ((total == 0.0 || list.isEmpty()) ? "0" : total / list.size()) + "\n\n"
                + "==========================================";
        System.out.println(text);
        return text;
    }

    public String relSintetico(RelDecorator dec, Date dtIni, Date dtFin) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        String text = "==========================================\n"
                + "ESTACIONAMENTO DE VEICULOS RS\n"
                + "Movimento de Saída - Sintético\n"
                + "Emissão: " + sdf.format(new Date()) + "\n"
                + "==========================================\n"
                + "Período: " + sdf.format(dtIni) + " a\n"
                + "         " + sdf.format(dtFin) + "\n\n"
                + "Total de Veículos.......: " + dec.getTotalVeic() + "\n\n"
                + "Tempo Total.............: \n\n"
                + "Valor Total Pago........: " + new BigDecimal(dec.getTotalPago()).setScale(2) + " /Qtd.: " + dec.getTotalVeic() + "\n\n"
                + "Valor Médio p/ Veículo..: " + new BigDecimal(dec.getValorMedioPorVeic()).setScale(2) + "\n\n"
                + "Veículos p/ Hora........: \n"
                + "==========================================";
        System.out.println(text);
        return text;
    }
}
