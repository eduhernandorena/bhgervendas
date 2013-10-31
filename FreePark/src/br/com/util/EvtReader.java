package br.com.util;

import br.com.bean.Ticket;
import br.com.view.FormFechaTicket;
import br.com.view.FormTicket;
import br.com.view.TelaPrincipal;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author felipek
 */
public class EvtReader implements KeyListener {

    Window win;
    private FormTicket formTick = null;
    private FormFechaTicket formFecha = null;
    private TelaPrincipal principal = null;

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
                    setWin(principal);
                    formFecha.dispose();
                    break;
                }
            }
        }
    }

    public void actFormPrincipal(KeyEvent e) {
        if (win != null) {
            principal = (TelaPrincipal) win;
            String texto = "";
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
                case KeyEvent.VK_F2:
                    formTick = new FormTicket(principal, true);
                    formTick.setVisible(true);
                    formTick = null;
                    setWin(principal);
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    principal.backSpace(texto, e);
                    break;
                default:
                    principal.defaultAct(texto, e);
            }
            System.out.println(texto);
        }
    }

    public void actFormTicket(KeyEvent e) {
        if (win != null) {
            formTick = (FormTicket) win;
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                formTick.lancaTick();
            }
        }
    }
}
