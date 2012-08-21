package br.com.controller;

import br.com.principal.Principal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ProdutoController {

    private static Principal p = Principal.getInstance();
    @FXML
    private Button btBusca;
    @FXML
    private Button btClear;
    @FXML
    private Button btSave;
    @FXML
    private Button btVoltar;
    @FXML
    private ComboBox<String> cmbTpLucro;
    @FXML
    private TextField txtCod;
    @FXML
    private TextField txtCodViagem;
    @FXML
    private TextField txtDesc;
    @FXML
    private TextField txtNfe;
    @FXML
    private TextField txtRef;
    @FXML
    private TextField txtTam;
    @FXML
    private TextField txtValCompra;
    @FXML
    private TextField txtValVenda;
    @FXML
    private TextField txtLucro;

    public void clearCad(ActionEvent event) {
        txtCod.setText("");
        txtCodViagem.setText("");
        txtDesc.setText("");
        txtNfe.setText("");
        txtRef.setText("");
        txtTam.setText("");
        txtValCompra.setText("");
        txtValVenda.setText("");
        txtLucro.setText("");
        cmbTpLucro.setValue("%");
    }

    public void saveCad(ActionEvent event) {
        p.gotoPrincipal();
    }

    public void voltar(ActionEvent event) {
        p.gotoPrincipal();
    }
}