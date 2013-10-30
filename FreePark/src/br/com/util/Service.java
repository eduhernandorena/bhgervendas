package br.com.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import org.w3c.dom.Document;

/**
 *
 * @author Roger
 * @author Eduardo
 */
public class Service {

    /**
     * Método que recebe uma variável String e testa se a mesma é nula, no caso
     * negativo, ele testa se a mesma está vazia, setando o valor nulo na mesma,
     * no caso contrario, ele retorna o valor desta variável sem os espaços a
     * direita.
     *
     * @param str variável a ser testada.
     * @return str com o seu valor sem os espaços ou null em caso de nula ou
     * vazia.
     */
    public static String value(Object str) {
        String value;
        if (str != null) {
            if (str instanceof BigDecimal) {
                if (Double.parseDouble(str.toString()) == 0.00) {
                    return null;
                }
            }
            if (str instanceof String) {
                value = (String) str;
            } else {
                value = String.valueOf(str);
            }
            if (value.trim().isEmpty()) {
                value = null;
            } else {
                value = value.trim();
            }
        } else {
            value = null;
        }
        return value;
    }

    /**
     * Método que mostra uma mensagem com o parâmetros passados.
     *
     * @param title Titulo da caixa de mensagem
     * @param text Texto da caixa de mensagem
     * @param messageType Tipo de mensagem <br> 0 - ERRO <BR> 1 - INFORMAÇÃO
     */
    public static void message(String title, String text, int messageType) {
        //Concertar erro que apresenta as tags de fechamento.
        JOptionPane.showMessageDialog(null, "<html><center>" + text, title, messageType);
    }

    /**
     * Método que busca o valor da tag passada de dentro da tag principal
     *
     * @param tag
     * @param doc
     * @return
     */
    public static String valueToTag(String tag, Document doc) {
        return doc.getDocumentElement().getElementsByTagName(tag).item(0).getChildNodes().item(0).getTextContent();
    }

    /**
     * Método que valida um email.
     *
     * @param email email passado por parametro
     * @return retorna uma variavel boolean informando se o email é valido ou
     * não;
     */
    public static boolean isValidateEmail(String email) {
        Pattern pattern = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    /**
     * Remove acentos e cedilhas.
     *
     * @param str string a ser corrigida.
     * @return a string sem os acentos e cedilhas.
     */
    public static String SemAcento(String str) {
        if (str != null && !str.isEmpty()) {
            str = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return removeMore(pattern.matcher(str).replaceAll("")).replaceAll("[^\\p{ASCII}]", "");
        } else {
            return str;
        }
    }

    private static String removeMore(String text) {
        text = text.replaceAll("[\\~\\^\\'\\ª\\º\\°\\´\\`\\¨\\£\\¢\\¬]", " ").trim();
        return text;
    }

    /**
     * Método que recebe um CEP e o formata, adicionando zeros a esquerda.
     *
     * @param cep
     * @return
     */
    public static String formatCEP(String cep) {
        if (cep != null && !cep.trim().isEmpty()) {
            while (cep.trim().length() < 8) {
                cep = "0" + cep;
            }
        }
        return cep;
    }

    /**
     * Método que testa se um valor especifico é numérico.
     *
     * @param s valor a ser testado.
     * @return Retorna true se for numérico e false do contrario.
     */
    public static boolean isNumeric(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static void escCloser(JFrame frame) {
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        frame.getRootPane().registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        }, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    /**
     * Método que testa se o valor do campo passado por parametro é do formato
     * de data dd/MM/yyyy, retornando true para formato correto e false para o
     * contrario.
     *
     * @param data
     * @return
     */
    public static boolean isDate(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(data);
            return true;
        } catch (ParseException x) {
            //Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, x);
            return false;
        }
    }

    /**
     * Log ERROR.
     *
     * @param error
     */
    public static void error(String error) {
        System.out.println("| ERROR: " + error);
    }

    /**
     * Log INFO.
     *
     * @param info
     */
    public static void info(String info) {
        System.out.println("| INFO: " + info);
    }

    public static boolean isValid(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.toString().equalsIgnoreCase("null")) {
            return false;
        }
        if (obj.toString().isEmpty()) {
            return false;
        }

        return true;
    }

    public static String getTime(long time) {
        long hora = time / 3600,
                minutos = (time - (3600 * hora)) / 60,
                seg = time - ((minutos * 60) + (3600 * hora));
        return hora + ":" + completaZero(minutos) + ":" + completaZero(seg);
    }

    private static String completaZero(long val) {
        String valor = String.valueOf(val);
        if (valor.length() == 1) {
            return "0" + valor;
        }
        return valor;
    }
}
