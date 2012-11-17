package br.com.principal;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 *
 * @author Eduardo Hernandorena
 */
public class Cripto {

    final BasicTextEncryptor bte = new BasicTextEncryptor();
    private final String alg = "fInAlPaSsWoRd";
    private static Cripto cript;

    private static Cripto getInstance() {
        if (cript != null) {
            return cript;
        } else {
            cript = new Cripto();
            return cript;
        }
    }

    private Cripto() {
        bte.setPassword(alg);
    }

    public static String encode(String str) {
        return getInstance().enc(str);
    }

    public static String decode(String str) {
        return getInstance().dec(str);
    }

    private String enc(String str) {
        str = bte.encrypt(str);
        return str;
    }

    private String dec(String str) {
        str = bte.decrypt(str);
        return str;
    }
}
