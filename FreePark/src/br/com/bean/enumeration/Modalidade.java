package br.com.bean.enumeration;

/**
 *
 * @author Eduardo Hernandorena
 */
public enum Modalidade {

    CARRO, CAMIONETE, MOTO;

    public static Modalidade value(int index) {
        switch (index) {
            case 0:
                return CARRO;
            case 1:
                return CAMIONETE;
            case 2:
                return MOTO;
        }
        return null;
    }
}
