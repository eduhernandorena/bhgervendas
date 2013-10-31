package br.com.calc;

import br.com.bean.Ticket;
import br.com.util.Service;

/**
 *
 * @author roger
 */
public class Carro {

    //representa 5:00 min
    private static long toler = 300;

    public static Ticket fechaTicket(Ticket ticket) {
        double total;
        if (ticket.getHoraEnt().before(ticket.getHoraSai())) {
            long time, init = ticket.getHoraEnt().getTime(),
                    end = ticket.getHoraSai().getTime();
            time = (end - init) / 1000;
            //ficou mais de dois minutos no estacionamento
            if (time > 119) {
                //ficou menos de meia hora com a tolerancia de 4:59 minutos.
                if (fracao(time)) {
                    total = ticket.getTabela().getPrFracao();
                }//ficou menos de uma hora com a tolerancia 
                else if (time < 3600 + toler) {
                    total = ticket.getTabela().getPrHora();
                } else if (time < 18000 + toler) {
                    long hora = time / 3600,
                            minuto = time - (3600 * hora);
                    total = hora * ticket.getTabela().getPrHora();
                    if (minuto >= toler) {
                        //cobrar preço da fração
                        if (fracao(minuto)) {
                            total += ticket.getTabela().getPrFracao();
                        }//cobrar hora inteira
                        else {
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

    private static boolean fracao(long time) {
        if (time < 1800 + toler) {
            return true;
        } else {
            return false;
        }
    }
}
