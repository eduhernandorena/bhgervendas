package br.com.view;

import br.com.bean.TabelaPreco;
import br.com.bean.Ticket;
import br.com.bean.enumeration.Modalidade;
import br.com.calc.Calculo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eduardo Hernandorena
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy hhmmss");
        Ticket t = new Ticket();
        try {
            t.setDataEnt(sdf.parse("24102013 000000"));
            t.setDataSai(sdf.parse("24102013 020000"));
            t.setHoraEnt(sdf.parse("24102013 000000"));
            t.setHoraSai(sdf.parse("24102013 000200"));
            TabelaPreco tab = new TabelaPreco();
            tab.setPrHora(3.0);
            tab.setPrFracao(2.0);
            tab.setMod(Modalidade.CARRO);
            t.setTabela(tab);
            Calculo.fechaTicket(t);
        } catch (ParseException ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
