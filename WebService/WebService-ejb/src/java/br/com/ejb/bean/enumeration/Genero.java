package br.com.ejb.bean.enumeration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eduardo Hernandorena
 */
public enum Genero {

    Masculino, Feminino;

    public static List<String> getAll() {
        List<String> list = new ArrayList<>();
        list.add("Feminino");
        list.add("Masculino");
        return list;
    }
}
