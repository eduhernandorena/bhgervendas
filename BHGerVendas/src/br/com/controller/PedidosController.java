package br.com.controller;

import br.com.principal.Principal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class PedidosController {

    private static Principal p = Principal.getInstance();
    @FXML
    private Button btCliente;
    @FXML
    private Button btEncomenda;
    @FXML
    private Button btFornecedor;
    @FXML
    private Button btProduto;
    @FXML
    private TextField txtValor;
    @FXML
    private Button btVoltar;
    @FXML
    private ComboBox<String> cmbFormaPag;
    @FXML
    private CheckBox rdPedido;
    @FXML
    private TextField txtCliente;
    @FXML
    private TextField txtCod;
    @FXML
    private TextField txtCodCliente;
    @FXML
    private TextField txtCodEncomenda;
    @FXML
    private TextField txtCodForn;
    @FXML
    private TextField txtDta;
    @FXML
    private TextField txtFornecedor;
    @FXML
    private TextField txtNParcelas;
    @FXML
    private TextField txtObsPag;
    @FXML
    private TextField txtValParcelas;

    public void clearCad(ActionEvent event) {
        cmbFormaPag.setValue("A Vista");
        rdPedido.setSelected(false);
        txtCliente.setText(null);
        txtCod.setText(null);
        txtCodCliente.setText(null);
        txtCodEncomenda.setText(null);
        txtCodForn.setText(null);
        txtDta.setText(null);
        txtFornecedor.setText(null);
        txtNParcelas.setText(null);
        txtObsPag.setText(null);
        txtValParcelas.setText(null);
        txtValor.setText(null);
    }

    public void saveCad(ActionEvent event) {
        p.gotoPrincipal();
    }

    public void voltar(ActionEvent event) {
        p.gotoPrincipal();
    }
}