package br.com.controller;

import br.com.principal.Principal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 *
 * @author Eduardo Hernandorena
 */
public class RelController {

    private static Principal p = Principal.getInstance();
    @FXML
    private Button btEmit;
    @FXML
    private Button btVoltar;
    @FXML
    private ComboBox<?> cmbGuia;
    @FXML
    private ComboBox<?> cmbLocal;
    @FXML
    private ComboBox<?> cmbOrder;
    @FXML
    private TextField txtDtFinal;
    @FXML
    private TextField txtDtInicio;

    public void emitir(ActionEvent event) {
        p.gotoPrincipal();
    }

    public void voltar(ActionEvent event) {
        p.gotoPrincipal();
    }
}
