package br.com.calc;

import br.com.bean.Ticket;
import br.com.bean.enumeration.Modalidade;

/**
 *
 * @author roger
 */
public class Calculo {

    public static Ticket fechaTicket(Ticket ticket) {
        if (ticket != null && ticket.getTabela().getMod() != null) {
            if (ticket.getTabela().getMod().equals(Modalidade.CAMIONETE)) {
                return Camionete.fechaTicket(ticket);
            } else if (ticket.getTabela().getMod().equals(Modalidade.CARRO)) {
                return Carro.fechaTicket(ticket);
            } else if (ticket.getTabela().getMod().equals(Modalidade.MOTO)) {
                return Moto.fechaTicket(ticket);
            }
        }
        return null;
    }
}
