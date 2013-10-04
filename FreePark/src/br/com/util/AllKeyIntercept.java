package br.com.util;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Classe implementada para reconhecimento de atalhos de tecla em um JFrame.<br>
 * Para utilizar a mesma, implemtente a interface <b>KeyListener</b> na classe
 * de uso e insira o comando:<br>
 * <b>KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher
 * (new AllKeyIntercept(this))</b><br> no construtor da classe de uso. Após,
 * implemente os metodos obrigatórios de keylistener.
 *
 * @author Eduardo
 */
public final class AllKeyIntercept implements KeyEventDispatcher {

    private KeyListener listener;

    public AllKeyIntercept(KeyListener kl) {
        setKeyListener(kl);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
//        if (enter(e, KeyEvent.VK_ESCAPE) || enter(e, KeyEvent.VK_F3) || enter(e, KeyEvent.VK_F4) || enter(e, KeyEvent.VK_F5) || enter(e, KeyEvent.VK_F6) || enter(e, KeyEvent.VK_F7) || enter(e, KeyEvent.VK_F8) || enter(e, KeyEvent.VK_ENTER)) {
            switch (e.getID()) {
                case KeyEvent.KEY_TYPED:
                    listener.keyTyped(e);
                    break;
                case KeyEvent.KEY_PRESSED:
                    listener.keyPressed(e);
                    break;
                case KeyEvent.KEY_RELEASED:
                    listener.keyReleased(e);
                    break;
                default:
                    return false;
//            }

        }
        return false;
    }

    public boolean enter(KeyEvent e, int key) {
        if (e.getKeyCode() != key) {
            return false;
        } else {
            return true;
        }
    }

    public void setKeyListener(KeyListener kl) {
        this.listener = kl;
    }

    public KeyListener getKeyListener() {
        return this.listener;
    }
}
