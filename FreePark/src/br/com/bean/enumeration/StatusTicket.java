package br.com.bean.enumeration;

/**
 *
 * @author Eduardo Hernandorena
 */
public enum StatusTicket {

    ATIVO, FINALIZADO, CANCELADO;

    public static StatusTicket value(int index) {
        switch (index) {
            case 0:
                return ATIVO;
            case 1:
                return FINALIZADO;
            case 2:
                return CANCELADO;
        }
        return null;
    }
}
