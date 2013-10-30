package br.com.calc;

import br.com.bean.Ticket;
import br.com.util.Service;

/**
 *
 * @author roger
 */
public class Moto {
    //representa 5:00 min

    private static long toler = 300;

    public static Ticket fechaTicket(Ticket ticket) {
        double total;
        if (ticket.getDataEnt().before(ticket.getDataSai())) {
            long time, init = ticket.getHoraEnt().getTime(),
                    end = ticket.getHoraSai().getTime();
            time = (end - init) / 1000;
            //ficou mais de dois minutos no estacionamento
            if (time > 119) {
                //ficou menos de uma hora com a tolerancia de 4:59 minutos.
                if (hora(time)) {
                    total = ticket.getTabela().getPrHora();
                } else if (time < 18000 + toler) {
                    long hora = time / 3600,
                            minuto = time - (3600 * hora);
                    total = hora * ticket.getTabela().getPrHora();
                    //cobrar hora inteira                       
                    if (minuto >= toler) {
                        if (minuto < 1800 + toler) {
                            total += ticket.getTabela().getPrFracao();
                        } else {
                            total += ticket.getTabela().getPrHora();
                        }
                    }
                } else {
                    total = 5 * ticket.getTabela().getPrHora();
                }
                ticket.setValor(total);
                System.out.println("Total: " + total);
            }
            ticket.setTempo(Service.getTime(time));
            System.out.println("Tempo: " + ticket.getTempo());
        }
        return ticket;
    }

    private static boolean hora(long time) {
        if (time < 3600 + toler) {
            return true;
        } else {
            return false;
        }
    }
}
