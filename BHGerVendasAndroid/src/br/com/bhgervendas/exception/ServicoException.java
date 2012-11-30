package br.com.bhgervendas.exception;

/**
 *
 * @author mertins
 */
public class ServicoException extends Exception{

    public ServicoException() {
    }

    public ServicoException(String message) {
        super(message);
    }

    public ServicoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServicoException(Throwable cause) {
        super(cause);
    }
    
}
