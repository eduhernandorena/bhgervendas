package br.com.controller;

import br.com.ejb.bean.Encomenda;
import br.com.ejb.bean.Produto;
import br.com.ejb.bean.Viagem;
import br.com.principal.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
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
    @FXML
    private TableView<Produto> gridProd;
    @FXML
    private TableView<Encomenda> gridEncomenda;

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
        if (valida()) {
            Viagem viagem = new Viagem();
            viagem.setValor(Double.valueOf(txtValor.getText()));
            try {
                viagem.setDataHora(new SimpleDateFormat("dd/MM/yyyy").parse(txtData.getText()));
            } catch (ParseException ex) {
                Logger.getLogger(ViagemController.class.getName()).log(Level.SEVERE, null, ex);
            }
            viagem.setEncomenda(gridEncomenda.getItems());
            viagem.setGuia(txtGuia.getText());
            viagem.setLocal(txtLocal.getText());
            viagem.setValor(Double.NaN);

        } else {
            System.out.println("Não foi possível salvar!");
        }
        p.gotoPrincipal();
    }

    public void voltar(ActionEvent event) {
        p.gotoPrincipal();
    }

    private boolean valida() {
        return false;
    }

    public void fill(Viagem viag) {
        if (viag != null) {
            txtCod.setText(viag.getId().toString());
            txtData.setText(viag.getData());
            txtGuia.setText(viag.getGuia());
            txtHora.setText(viag.getHora());
            txtLocal.setText(viag.getLocal());
            //txtQtde.setText(viag.);
            //txtValor.setText(null);
        }
    }
}
