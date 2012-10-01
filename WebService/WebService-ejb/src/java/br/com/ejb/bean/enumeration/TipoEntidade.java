package br.com.ejb.bean.enumeration;

/**
 *
 * @author Eduardo Hernandorena
 */
public enum TipoEntidade {

    CLIENTE, FORNECEDOR;
    
    public boolean isCliente(){
        if (this == CLIENTE) {
            return true;
        }else{
            return false;
        }
    }
}
