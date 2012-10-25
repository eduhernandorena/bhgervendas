package br.com.ejb.utils;

/**
 *
 * @author Eduardo Hernandorena
 */
public class Utilidades {

    public static String upperInicial(String frase) {
        frase = frase.toLowerCase();

        StringBuilder frase2 = new StringBuilder(frase);

        for (int i = 0; i < frase2.length(); i++) {
            Character letra = frase2.charAt(i);
            if (i == 0) {
                letra = Character.toUpperCase(letra);
                frase2.setCharAt(i, letra);
            } else if ((i > 0) && (frase2.charAt(i - 1) == ' ')) {
                letra = Character.toUpperCase(letra);
                frase2.setCharAt(i, letra);
            }
        }

        return frase2.toString();
    }
}
