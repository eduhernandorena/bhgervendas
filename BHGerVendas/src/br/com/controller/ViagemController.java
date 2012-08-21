package br.com.controller;

import br.com.principal.Principal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ViagemController {

    private static Principal p = Principal.getInstance();
    @FXML
    private Button btEncomenda;
    @FXML
    private TextField txtCod;
    @FXML
    private TextField txtData;
    @FXML
    private TextField txtGuia;
    @FXML
    private TextField txtHora;
    @FXML
    private TextField txtLocal;
    @FXML
    private TextField txtQtde;
    @FXML
    private TextField txtValor;

    public void clearCad(ActionEvent event) {
        txtCod.setText(null);
        txtData.setText(null);
        txtGuia.setText(null);
        txtHora.setText(null);
        txtLocal.setText(null);
        txtQtde.setText(null);
        txtValor.setText(null);
    }

    public void saveCad(ActionEvent event) {
        p.gotoPrincipal();
    }

    public void voltar(ActionEvent event) {
        p.gotoPrincipal();
    }
}
