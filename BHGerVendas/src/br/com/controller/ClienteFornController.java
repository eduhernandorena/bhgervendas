package br.com.controller;

import br.com.principal.Principal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author Eduardo Hernandorena
 */
public class ClienteFornController {

    private static Principal p = Principal.getInstance();
    @FXML
    private Button btSalvar;
    @FXML
    private CheckBox chkNew;
    @FXML
    private ComboBox<String> cmbEst;
    @FXML
    private ComboBox<String> cmbEstCivil;
    @FXML
    private ComboBox<String> cmbGenero;
    @FXML
    private ComboBox<String> cmbMun;
    @FXML
    private ComboBox<String> cmbTpEnd;
    @FXML
    private ComboBox<String> cmbTpEntidade;
    @FXML
    private TextArea txtArea;
    @FXML
    private TextField txtBairro;
    @FXML
    private TextField txtCel;
    @FXML
    private TextField txtCep;
    @FXML
    private TextField txtCod;
    @FXML
    private TextField txtCodEnd;
    @FXML
    private TextField txtCpfCnpj;
    @FXML
    private TextField txtCpl;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtLogradouro;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtNum;
    @FXML
    private TextField txtTel;
    @FXML
    private TextField txtUltPed;

    @FXML
    public void clearCad(ActionEvent event) {
        txtArea.setText(null);
        txtBairro.setText(null);
        txtCel.setText(null);
        txtCep.setText(null);
        txtCod.setText(null);
        txtCodEnd.setText(null);
        txtCpfCnpj.setText(null);
        txtCpl.setText(null);
        txtEmail.setText(null);
        txtLogradouro.setText(null);
        txtNome.setText(null);
        txtNum.setText(null);
        txtTel.setText(null);
        txtUltPed.setText(null);
        cmbEst.setValue(null);
        cmbEstCivil.setValue(null);
        cmbGenero.setValue(null);
        cmbMun.setValue(null);
        cmbTpEnd.setValue(null);
        chkNew.setSelected(false);
    }

    @FXML
    public void saveCad(ActionEvent event) {
        p.gotoPrincipal();
    }

    @FXML
    public void voltar(ActionEvent event) {
        p.gotoPrincipal();
    }
}
