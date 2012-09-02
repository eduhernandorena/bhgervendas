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

    private static Principal p = Principal.getInstance();
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
