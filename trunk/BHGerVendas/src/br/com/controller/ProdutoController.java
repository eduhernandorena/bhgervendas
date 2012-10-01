package br.com.controller;

import br.com.ejb.bean.Produto;
import br.com.principal.Principal;
import br.com.ws.ProdutoRest;
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
    private static ProdutoRest prodDAO = new ProdutoRest();

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
        if (valida()) {
            Produto prod = new Produto();
            prod.setDescricao(txtDesc.getText());
            prod.setNroNota(Long.valueOf(txtNfe.getText()));
            Double pVenda = Double.valueOf(txtValVenda.getText());
            Double pCompra = Double.valueOf(txtValCompra.getText());
            prod.setPrecoCusto(pCompra);
            prod.setPrecoVenda(pVenda);
            prod.setLucro(pVenda - pCompra);
            prod.setReferencia(txtRef.getText());
            prod.setTamanho(txtTam.getText());
            prodDAO.create(prod);
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

    public void fill(Produto prod) {
        if (prod != null) {
            txtCod.setText(prod.getId().toString());
            //txtCodViagem.setText();
            txtDesc.setText(prod.getDescricao());
            txtNfe.setText(prod.getNroNota().toString());
            txtRef.setText(prod.getReferencia());
            txtTam.setText(prod.getTamanho());
            txtValCompra.setText(prod.getPrecoCusto().toString());
            txtValVenda.setText(prod.getPrecoVenda().toString());
            txtLucro.setText(prod.getLucro().toString());
            cmbTpLucro.setValue(prod.getPrecoVenda() - prod.getPrecoCusto() == prod.getLucro() ? "R$" : "%");
        }
    }
}