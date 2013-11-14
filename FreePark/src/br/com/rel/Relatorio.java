package br.com.rel;

import br.com.bean.Ticket;
import br.com.impressora.Impressora;
import br.com.rel.decorator.RelDecorator;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author roger
 */
public class Relatorio {

    /**
     * Metodo que monta e retorna uma string com o ticket de entrada.
     *
     * @param ticket
     * @return
     */
    public String ticketEntrada(Ticket ticket) {
        SimpleDateFormat sdfDt = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfHr = new SimpleDateFormat("HH:mm");
        String text = "==========================================\n"
                + "    ESTACIONAMENTO DE VEICULOS RS - 2\n"
                + "         RUA ANDRADE NEVES, 2012\n"
                + "    FONES: (53) 8141-0932 (53) 9911-7611\n"
                + "==========================================\n\n"
                + "              ---ENTRADA---               \n\n"
                + "    PLACA: " + ticket.getPlaca() + "    Modelo: " + ticket.getTabela().getMod().name() + "\n\n"
                + "     DATA: " + sdfDt.format(ticket.getDataEnt()) + "    HORA: " + sdfHr.format(ticket.getHoraEnt()) + "\n\n"
                + "------------------------------------------\n"
                + "        SEG - SEXTA 08:00 AS 21:00\n"
                + "------------------------------------------";
        new Impressora().imprime(text);
        new Impressora().acionarGuilhotina();
        return text;
    }

    /**
     * Metodo que monta e retorna uma string com o ticket de saida.
     *
     * @param ticket
     * @return
     */
    public String ticketSaida(Ticket ticket) {
        SimpleDateFormat sdfDt = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfHr = new SimpleDateFormat("HH:mm");
        String text = "==========================================\n"
                + "    ESTACIONAMENTO DE VEICULOS RS - 2\n"
                + "         RUA ANDRADE NEVES, 2012\n"
                + "    FONES: (53) 8141-0932 (53) 9911-7611\n"
                + "==========================================\n\n"
                + "                ---SAIDA---               \n\n"
                + "    PLACA: " + ticket.getPlaca() + "    Modelo: " + ticket.getTabela().getMod().name() + "\n\n"
                + "  PERIODO: " + sdfDt.format(ticket.getDataEnt()) + " - " + sdfHr.format(ticket.getHoraEnt())
                + " AS " + sdfHr.format(ticket.getDataSai()) + "\n\n"
                + "   PERMANENCIA: " + ticket.getTempo() + "    VALOR: " + new BigDecimal(ticket.getValor()).setScale(2, RoundingMode.HALF_EVEN) + "\n\n"
                + "------------------------------------------\n"
                + "        SEG - SEXTA 08:00 AS 21:00\n"
                + "------------------------------------------";
        new Impressora().imprime(text);
        new Impressora().acionarGuilhotina();
        return text;
    }

    /**
     * Metodo que monta e retorna uma string com o relatorio detalhado de
     * movimentos do estacionamento.
     *
     * @param ticket
     * @return
     */
    public String relAnalitico(List<Ticket> list, Date dtIni, Date dtFin) {
        SimpleDateFormat sdfPadrao = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        SimpleDateFormat sdfPerson = new SimpleDateFormat("dd HH:mm");
        String text = "==========================================\n"
                + "ESTACIONAMENTO DE VEICULOS RS - 2\n"
                + "Movimento de Saida - Analitico\n"
                + "Emissao: " + sdfPadrao.format(new Date()) + "\n"
                + "==========================================\n"
                + "Periodo: " + sdfPadrao.format(dtIni) + " a\n"
                + "         " + sdfPadrao.format(dtFin) + "\n\n"
                + "Placa   Dia  Entr Dia Saida Tempo Tb  Pago\n";
        double total = 0;
        for (Ticket reg : list) {
            text += reg.getPlaca().replace("-", "") + "  " + sdfPerson.format(reg.getHoraEnt()) + "  "
                    + sdfPerson.format(reg.getHoraSai()) + "  " + reg.getTempo().substring(0, 4) + " "
                    + "0" + (reg.getTabela().getMod().ordinal() + 1)
                    + (reg.getValor() > 9 ? " " : "  ") + new BigDecimal(reg.getValor()).setScale(2, RoundingMode.HALF_EVEN) + "\n";
            total += reg.getValor();
        }
        text += "\nTotal de Veiculos.......: " + list.size() + "\n\n"
                + "Tempo Total.............: \n\n"
                + "Valor Total Pago........: " + total + " /Qtd.: " + list.size() + "\n\n"
                + "Valor Medio p/ Veiculo..: " + new BigDecimal(((total == 0.0 || list.isEmpty()) ? 0.0 : total / list.size())).setScale(2, RoundingMode.HALF_EVEN) + "\n\n"
                + "==========================================";
        new Impressora().imprime(text);
        new Impressora().acionarGuilhotina();
        return text;
    }

    /**
     * Metodo que monta e retorna uma string com o relatorio simplificado de
     * movimentos do estacionamento.
     *
     * @param ticket
     * @return
     */
    public String relSintetico(RelDecorator dec, Date dtIni, Date dtFin) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        String text = "==========================================\n"
                + "ESTACIONAMENTO DE VEICULOS RS - 2\n"
                + "Movimento de Saida - Sintetico\n"
                + "Emissao: " + sdf.format(new Date()) + "\n"
                + "==========================================\n"
                + "Periodo: " + sdf.format(dtIni) + " a\n"
                + "         " + sdf.format(dtFin) + "\n\n"
                + "Total de Veiculos.......: " + dec.getTotalVeic() + "\n\n"
                + "Tempo Total.............: \n\n"
                + "Valor Total Pago........: " + new BigDecimal(dec.getTotalPago()).setScale(2, RoundingMode.HALF_EVEN) + " /Qtd.: " + dec.getTotalVeic() + "\n\n"
                + "Valor Medio p/ Veiculo..: " + new BigDecimal(dec.getValorMedioPorVeic()).setScale(2, RoundingMode.HALF_EVEN) + "\n\n"
                + "Veiculos p/ Hora........: \n"
                + "==========================================";
        new Impressora().imprime(text);
        new Impressora().acionarGuilhotina();
        return text;
    }
}
