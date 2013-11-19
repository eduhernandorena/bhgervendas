package br.com.util;

import br.com.bean.Ticket;
import br.com.bean.enumeration.StatusTicket;
import br.com.calc.Calculo;
import br.com.dao.RelatorioDAO;
import br.com.dao.TicketDAO;
import br.com.rel.Relatorio;
import br.com.view.FormEstorno;
import br.com.view.FormFechaTicket;
import br.com.view.FormRel;
import br.com.view.FormTicket;
import br.com.view.TelaPrincipal;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;

/**
 *
 * @author felipek
 */
public class EvtReader implements KeyListener {

    Window win;
    private FormTicket formTick = null;
    private FormFechaTicket formFecha = null;
    private TelaPrincipal principal = null;
    private FormRel formRel = null;
    private FormEstorno formEst = null;
    private String texto = "";

    public EvtReader(Window w) {
        win = w;
    }

    public Window getWin() {
        return win;
    }

    public void setWin(Window win) {
        this.win = win;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (win instanceof FormFechaTicket) {
            actFormFechaTicket(e);
        } else if (win instanceof FormTicket) {
            actFormTicket(e);
        } else if (win instanceof TelaPrincipal) {
            actFormPrincipal(e);
        } else if (win instanceof FormRel) {
            actFormRel(e);
        } else if (win instanceof FormEstorno){
            actFormEst(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void actFormFechaTicket(KeyEvent e) {
        if (win != null) {
            formFecha = (FormFechaTicket) win;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ENTER: {
                    Calculo.fechaTicket(formFecha.tick);
                    formFecha.tick.setDataSai(new Date());
                    formFecha.tick.setHoraSai(new Date());
                    formFecha.tick.setStatus(StatusTicket.FINALIZADO);
                    new TicketDAO().upDate(formFecha.tick);
                    new Relatorio().ticketSaida(formFecha.tick);
                    principal.initTable(null);
                    setWin(principal);
                    formFecha.dispose();
                    break;
                }
                case KeyEvent.VK_ESCAPE: {
                    formFecha.dispose();
                }
            }
        }
    }

    public void actFormPrincipal(KeyEvent e) {
        if (win != null) {
            principal = (TelaPrincipal) win;
            System.out.println(e.getKeyChar());

            switch (e.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    Ticket t = principal.fechaTick();
                    formFecha = new FormFechaTicket(principal, t);
                    formFecha.setVisible(true);
                    formFecha = null;
                    setWin(principal);
                    break;
                case KeyEvent.VK_ESCAPE:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                    principal.tbTicket.requestFocus();
                    break;
                case KeyEvent.VK_INSERT:
                    formTick = new FormTicket(principal, true);
                    formTick.setVisible(true);
                    formTick = null;
                    setWin(principal);
                    break;
                case KeyEvent.VK_F3:
                    formRel = new FormRel(principal);
                    formRel.setVisible(true);
                    formRel = null;
                    setWin(principal);
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    texto = principal.backSpace(texto, e);
                    break;
                default:
                    texto = principal.defaultAct(texto, e);
            }
            System.out.println(texto);
        }
    }

    public void actFormTicket(KeyEvent e) {
        if (win != null) {
            formTick = (FormTicket) win;
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                formTick.lancaTick();
            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                formTick.dispose();
            }
        }
    }

    public void actFormRel(KeyEvent e) {
        if (win != null) {
            formRel = (FormRel) win;
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                formRel.lancaRel();
            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                formRel.dispose();
            }
        }
    }
    
    public void actFormEst(KeyEvent e) {
        if (win != null) {
            formEst = (FormEstorno) win;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    formEst.estornaTicket();
                    break;
                case KeyEvent.VK_ESCAPE:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                    formEst.tbTicket.requestFocus();
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    texto = formEst.backSpace(texto, e);
                    break;
                default:
                    texto = formEst.defaultAct(texto, e);
            }
        }
    }
}
