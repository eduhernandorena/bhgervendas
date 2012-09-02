package br.com.controller;

import br.com.principal.Principal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EncomendaController{

    private static Principal p = Principal.getInstance();
    @FXML
    private Button btBuscaPedido;
    @FXML
    private Button btBuscaViagem;
    @FXML
    private Button btClear;
    @FXML
    private Button btSave;
    @FXML
    private Button btVoltar;
    @FXML
    private TextField txtCod;
    @FXML
    private TextField txtCodPedido;
    @FXML
    private TextField txtCodViagem;
    @FXML
    private TextField txtData;
    @FXML
    private TextField txtDataViagem;
    @FXML
    private TextField txtHoraViagem;
    @FXML
    private TextField txtLocalViagem;

    public void clearCad(ActionEvent event) {
        txtCod.setText(null);
        txtCodPedido.setText(null);
        txtCodViagem.setText(null);
        txtData.setText(null);
        txtDataViagem.setText(null);
        txtHoraViagem.setText(null);
        txtLocalViagem.setText(null);
    }

    public void saveCad(ActionEvent event) {
        p.gotoPrincipal();
    }

    public void voltar(ActionEvent event) {
        p.gotoPrincipal();
    }
}
