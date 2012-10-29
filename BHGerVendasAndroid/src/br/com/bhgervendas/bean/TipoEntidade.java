package br.com.bhgervendas.bean;

/**
 *
 * @author Eduardo Hernandorena
 */
public enum TipoEntidade {

    Cliente, Fornecedor;

    public boolean isCliente() {
        if (this == Cliente) {
            return true;
        } else {
            return false;
        }
    }
}
