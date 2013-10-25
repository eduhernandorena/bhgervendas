package br.com.calc;

import br.com.bean.Ticket;
import javax.swing.JOptionPane;

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
                        if (hora(minuto)) {
                            total += ticket.getTabela().getPrHora();
                        }
                    }
                } else {
                    total = 5 * ticket.getTabela().getPrHora();
                }
                long hora = time / 3600,
                        minutos = (time - (3600 * hora)) / 60,
                        seg = time - ((minutos * 60) + (3600 * hora));
                String tempo = hora + ":" + minutos + ":" + seg;
                JOptionPane.showMessageDialog(null, "Total: " + total);
                JOptionPane.showMessageDialog(null, "Tempo: " + tempo);
                ticket.setValor(total);
                ticket.setTempo(tempo);
            }
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
