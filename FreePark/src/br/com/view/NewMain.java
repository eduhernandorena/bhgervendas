package br.com.view;

import br.com.bean.TabelaPreco;
import br.com.bean.Ticket;
import br.com.bean.enumeration.Modalidade;
import br.com.impressora.Impressora;
import java.util.Date;

/**
 *
 * @author Eduardo Hernandorena
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Ticket t = new Ticket();
//        TabelaPreco tab = new TabelaPreco();
//        tab.setMod(Modalidade.CARRO);
//        t.setDataEnt(new Date());
//        t.setDataSai(new Date());
//        t.setHoraEnt(new Date());
//        t.setHoraSai(new Date());
//        t.setPlaca("HWS9877");
//        t.setTempo("250");
//        t.setValor(5.5);
//        t.setTabela(tab);
//        FormFechaTicket form = new FormFechaTicket(t);
//        form.setVisible(true);
//        new TelaPrincipal().setVisible(true);
        new FormRel(new TelaPrincipal()).setVisible(true);

    }
}
