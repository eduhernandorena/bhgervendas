package br.com.controller;

import br.com.ejb.bean.*;
import br.com.principal.Principal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 *
 * @author Eduardo Hernandorena
 */
public class TelaPrincipalController {

    @FXML
    private TabPane tabPane;
    @FXML
    private TableView<Entidade> tbClientes;
    @FXML
    private TableView<Encomenda> tbEncomendas;
    @FXML
    private TableView<Entidade> tbFornecedor;
    @FXML
    private TableView<Pedido> tbPedidos;
    @FXML
    private TableView<Produto> tbProdutos;
    @FXML
    private TableView<Viagem> tbViagens;
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
    private static Principal p = Principal.getInstance();

    public void init() {
        fillCliente();
        fillEncomendas();
        fillFornecedor();
        fillPedido();
        fillProduto();
        fillViagem();
    }

    @FXML
    public void openCad(ActionEvent event) {
        if (tabPane.getTabs().get(0).isSelected()) {
            System.out.println("Cliente");
            p.gotoClienteCad();
        } else if (tabPane.getTabs().get(1).isSelected()) {
            System.out.println("Fornecedor");
            p.gotoFornecedorCad();
        } else if (tabPane.getTabs().get(2).isSelected()) {
            System.out.println("Produto");
            p.gotoProdutosCad();
        } else if (tabPane.getTabs().get(3).isSelected()) {
            System.out.println("Pedido");
            p.gotoPedidoCad();
        } else if (tabPane.getTabs().get(4).isSelected()) {
            System.out.println("Viagem");
            p.gotoViagemCad();
        } else if (tabPane.getTabs().get(5).isSelected()) {
            System.out.println("Encomenda");
            p.gotoEncomendaCad();
        }
    }

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
        cmbTpEntidade.setValue(null);
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

    private void fillCliente() {
    }

    private void fillFornecedor() {
    }

    private void fillEncomendas() {
    }

    private void fillPedido() {
    }

    private void fillProduto() {
    }

    private void fillViagem() {
    }
}
