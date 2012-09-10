package br.com.controller;

import br.com.ejb.bean.*;
import br.com.ejb.bean.enumeration.TipoEndereco;
import br.com.principal.Principal;
import br.com.ws.EntidadeRest;
import java.util.List;
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
        EntidadeRest entDAO = new EntidadeRest();
        List<Entidade> list = entDAO.findAllCliente(List.class);
        if (!list.isEmpty()) {
            System.out.println("Tem coisa!");
        } else {
            Entidade ent = new Entidade();
            ent.setCelular(81093531l);
            ent.setCpfCnpj("01735654094");
            ent.setEmail("eduhernandorena@live.com");
            Endereco end = new Endereco();
            end.setBairro("Areal");
            end.setCep("96080080");
            Cidade cid = new Cidade();
            cid.setCodIbge(7236984l);
            cid.setDescricao("Pelotas");
            UF uf = new UF();
            uf.setCodIbge(98760987l);
            uf.setDescricao("Rio Grande do Sul");
            uf.setSigla("RS");
            entDAO.create(uf);
            cid.setUf(uf);
            entDAO.create(cid);
            end.setCidade(cid);
            end.setComplemento(null);
            end.setId(1l);
            end.setNumero(1559);
            end.setRua("Claudio Manoel da Costa");
            end.setTipoEndereco(TipoEndereco.RESIDENCIAL);
            entDAO.create(end);
            ent.setEndereco(end);
            entDAO.create(ent);
        }
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
