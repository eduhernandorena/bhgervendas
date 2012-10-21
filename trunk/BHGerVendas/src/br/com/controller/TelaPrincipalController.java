package br.com.controller;

import br.com.ejb.bean.*;
import br.com.ejb.bean.enumeration.FormaPagamento;
import br.com.ejb.bean.enumeration.TipoEntidade;
import br.com.principal.Principal;
import br.com.ws.EncomendaRest;
import br.com.ws.EntidadeRest;
import br.com.ws.PedidoRest;
import br.com.ws.ProdutoRest;
import br.com.ws.ViagemRest;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Eduardo Hernandorena
 */
public class TelaPrincipalController implements Initializable {

    private static Principal p = Principal.getInstance();
    @FXML
    private TabPane tabPane;
    //Cliente
    @FXML
    private TableView<Entidade> tbClientes;
    @FXML
    private TableColumn<Entidade, Long> colCelCliente;
    @FXML
    private TableColumn<Entidade, Long> colCodCliente;
    @FXML
    private TableColumn<Entidade, Endereco> colEndCliente;
    @FXML
    private TableColumn<Entidade, String> colNomeCliente;
    @FXML
    private TableColumn<Entidade, Long> colTelCliente;
    //Encomenda
    @FXML
    private TableView<Encomenda> tbEncomendas;
    @FXML
    private TableColumn<Encomenda, Long> colCodEnc;
    @FXML
    private TableColumn<Encomenda, Viagem> colViagemEnc;
    //Fornecedor
    @FXML
    private TableView<Entidade> tbFornecedor;
    @FXML
    private TableColumn<Entidade, Long> colCelForn;
    @FXML
    private TableColumn<Entidade, Long> colCodForn;
    @FXML
    private TableColumn<Entidade, Endereco> colEndForn;
    @FXML
    private TableColumn<Entidade, String> colNomeForn;
    @FXML
    private TableColumn<Entidade, Long> colTelForn;
    //Pedido
    @FXML
    private TableView<Pedido> tbPedidos;
    @FXML
    private TableColumn<Pedido, Long> colCodPed;
    @FXML
    private TableColumn<Pedido, Entidade> colCliPed;
    @FXML
    private TableColumn<Pedido, Double> colValPed;
    @FXML
    private TableColumn<Pedido, FormaPagamento> colFormaPed;
    @FXML
    private TableColumn<Pedido, Integer> colParcPed;
    //Produto
    @FXML
    private TableView<Produto> tbProdutos;
    @FXML
    private TableColumn<Produto, Long> colCodProd;
    @FXML
    private TableColumn<Produto, String> colDescProd;
    @FXML
    private TableColumn<Produto, String> colTamProd;
    @FXML
    private TableColumn<Produto, Double> colPrcProd;
    //Viagem
    @FXML
    private TableView<Viagem> tbViagens;
    @FXML
    private TableColumn<Viagem, Long> colCodViag;
    @FXML
    private TableColumn<Viagem, String> colLocalViag;
    @FXML
    private TableColumn<Viagem, String> colDtHrViag;
    @FXML
    private TableColumn<Viagem, String> colGuiaViag;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fill();
    }

    public void fill() {
        fillCliente();
        fillEncomendas();
        fillFornecedor();
        fillPedido();
        fillProduto();
        fillViagem();
    }

    private void openParam(Object obj) {
        if (tabPane.getTabs().get(0).isSelected()) {
            Entidade ent = (Entidade) obj;
            System.out.println("Cliente: " + ent.getId());
            p.gotoClienteCad(ent);
        } else if (tabPane.getTabs().get(1).isSelected()) {
            Entidade ent = (Entidade) obj;
            System.out.println("Fornecedor: " + ent.getId());
            p.gotoFornecedorCad(ent);
        } else if (tabPane.getTabs().get(2).isSelected()) {
            Produto prod = (Produto) obj;
            System.out.println("Produto: " + prod.getId());
            p.gotoProdutosCad(prod);
        } else if (tabPane.getTabs().get(3).isSelected()) {
            Pedido ped = (Pedido) obj;
            System.out.println("Pedido: " + ped.getCodigo());
            p.gotoPedidoCad(ped);
        } else if (tabPane.getTabs().get(4).isSelected()) {
            Viagem viag = (Viagem) obj;
            System.out.println("Viagem: " + viag.getId());
            p.gotoViagemCad(viag);
        } else if (tabPane.getTabs().get(5).isSelected()) {
            Encomenda enc = (Encomenda) obj;
            System.out.println("Encomenda: " + enc.getCodigo());
            p.gotoEncomendaCad(enc);
        }
    }

    @FXML
    public void openCad(ActionEvent event) {
        if (tabPane.getTabs().get(0).isSelected()) {
            System.out.println("Cliente");
            p.gotoClienteCad(null);
        } else if (tabPane.getTabs().get(1).isSelected()) {
            System.out.println("Fornecedor");
            p.gotoFornecedorCad(null);
        } else if (tabPane.getTabs().get(2).isSelected()) {
            System.out.println("Produto");
            p.gotoProdutosCad(null);
        } else if (tabPane.getTabs().get(3).isSelected()) {
            System.out.println("Pedido");
            p.gotoPedidoCad(null);
        } else if (tabPane.getTabs().get(4).isSelected()) {
            System.out.println("Viagem");
            p.gotoViagemCad(null);
        } else if (tabPane.getTabs().get(5).isSelected()) {
            System.out.println("Encomenda");
            p.gotoEncomendaCad(null);
        }
    }
    
    @FXML
    public void openCadKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.F2)) {
            openCad(null);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Métodos do Cliente">
    @FXML
    public void onClickCliente(MouseEvent event) {
        if (event.getClickCount() > 1) {
            Entidade ent = tbClientes.getSelectionModel().getSelectedItem();
            if (ent != null) {
                System.out.println("Cliente: " + ent.getId());
                openParam(ent);
            }
        }
    }

    private void fillCliente() {
        colCodCliente.setCellValueFactory(new PropertyValueFactory<Entidade, Long>("id"));
        colNomeCliente.setCellValueFactory(new PropertyValueFactory<Entidade, String>("Nome"));
        colTelCliente.setCellValueFactory(new PropertyValueFactory<Entidade, Long>("Telefone"));
        colCelCliente.setCellValueFactory(new PropertyValueFactory<Entidade, Long>("Celular"));
        colEndCliente.setCellValueFactory(new PropertyValueFactory<Entidade, Endereco>("Endereco"));

        tbClientes.getItems().setAll(parseClienteList());
    }

    private List<Entidade> parseClienteList() {
        EntidadeRest entDAO = new EntidadeRest();
        List<Entidade> listaEnt = entDAO.findAll(TipoEntidade.Cliente);

        if (!listaEnt.isEmpty()) {
            return listaEnt;
        } else {
            return new ArrayList<>();
        }
    }

    private void refreshCliente() {
        final List<Entidade> items = tbClientes.getItems();
        if (items == null || items.isEmpty()) {
            return;
        }

        final Entidade item = tbClientes.getItems().get(0);
        items.remove(0);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                items.add(0, item);
            }
        });
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Métodos do Fornecedor">
    @FXML
    public void onClickFornecedor(MouseEvent event) {
        if (event.getClickCount() > 1) {
            Entidade ent = tbFornecedor.getSelectionModel().getSelectedItem();
            if (ent != null) {
                System.out.println("Fornecedor: " + ent.getId());
                openParam(ent);
            }
        }
    }

    private void fillFornecedor() {
        colCodForn.setCellValueFactory(new PropertyValueFactory<Entidade, Long>("id"));
        colNomeForn.setCellValueFactory(new PropertyValueFactory<Entidade, String>("Nome"));
        colTelForn.setCellValueFactory(new PropertyValueFactory<Entidade, Long>("Telefone"));
        colCelForn.setCellValueFactory(new PropertyValueFactory<Entidade, Long>("Celular"));
        colEndForn.setCellValueFactory(new PropertyValueFactory<Entidade, Endereco>("Endereco"));

        tbFornecedor.getItems().setAll(parseFornecedorList());
    }

    private List<Entidade> parseFornecedorList() {
        EntidadeRest entDAO = new EntidadeRest();
        List<Entidade> listaEnt = entDAO.findAll(TipoEntidade.Fornecedor);

        if (!listaEnt.isEmpty()) {
            return listaEnt;
        } else {
            return new ArrayList<>();
        }
    }

    private void refreshFornecedor() {
        final List<Entidade> items = tbFornecedor.getItems();
        if (items == null || items.isEmpty()) {
            return;
        }

        final Entidade item = tbFornecedor.getItems().get(0);
        items.remove(0);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                items.add(0, item);
            }
        });
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Métodos da Encomenda">
    @FXML
    public void onClickEncomenda(MouseEvent event) {
        if (event.getClickCount() > 1) {
            Encomenda enc = tbEncomendas.getSelectionModel().getSelectedItem();
            if (enc != null) {
                System.out.println("Encomenda: " + enc.getCodigo());
                openParam(enc);
            }
        }
    }

    private void fillEncomendas() {
        colCodEnc.setCellValueFactory(new PropertyValueFactory<Encomenda, Long>("id"));
        colViagemEnc.setCellValueFactory(new PropertyValueFactory<Encomenda, Viagem>("viagem"));

        tbEncomendas.getItems().setAll(parseEncomendaList());
    }

    private List<Encomenda> parseEncomendaList() {
        EncomendaRest encDAO = new EncomendaRest();
        List<Encomenda> listaEnc = encDAO.findAll();

        if (!listaEnc.isEmpty()) {
            return listaEnc;
        } else {
            return new ArrayList<>();
        }
    }

    private void refreshEncomenda() {
        final List<Encomenda> items = tbEncomendas.getItems();
        if (items == null || items.isEmpty()) {
            return;
        }

        final Encomenda item = tbEncomendas.getItems().get(0);
        items.remove(0);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                items.add(0, item);
            }
        });
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Métodos do Pedido">
    @FXML
    public void onClickPedido(MouseEvent event) {
        if (event.getClickCount() > 1) {
            Pedido ped = tbPedidos.getSelectionModel().getSelectedItem();
            if (ped != null) {
                System.out.println("Pedido: " + ped.getCodigo());
                openParam(ped);
            }
        }
    }

    private void fillPedido() {
        colCodPed.setCellValueFactory(new PropertyValueFactory<Pedido, Long>("codigo"));
        colCliPed.setCellValueFactory(new PropertyValueFactory<Pedido, Entidade>("Cliente"));
        colValPed.setCellValueFactory(new PropertyValueFactory<Pedido, Double>("valor"));
        colFormaPed.setCellValueFactory(new PropertyValueFactory<Pedido, FormaPagamento>("formaPagamento"));
        colParcPed.setCellValueFactory(new PropertyValueFactory<Pedido, Integer>("nroParcelas"));

        tbPedidos.getItems().setAll(parsePedidoList());
    }

    private List<Pedido> parsePedidoList() {
        PedidoRest pedDAO = new PedidoRest();
        List<Pedido> listaPed = pedDAO.findAll();

        if (!listaPed.isEmpty()) {
            return listaPed;
        } else {
            return new ArrayList<>();
        }
    }

    private void refreshPedido() {
        final List<Pedido> items = tbPedidos.getItems();
        if (items == null || items.isEmpty()) {
            return;
        }

        final Pedido item = tbPedidos.getItems().get(0);
        items.remove(0);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                items.add(0, item);
            }
        });
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Métodos do Produto">
    @FXML
    public void onClickProduto(MouseEvent event) {
        if (event.getClickCount() > 1) {
            Produto prod = tbProdutos.getSelectionModel().getSelectedItem();
            if (prod != null) {
                System.out.println("Produto: " + prod.getId());
                openParam(prod);
            }
        }
    }

    private void fillProduto() {
        colCodProd.setCellValueFactory(new PropertyValueFactory<Produto, Long>("id"));
        colDescProd.setCellValueFactory(new PropertyValueFactory<Produto, String>("Descricao"));
        colTamProd.setCellValueFactory(new PropertyValueFactory<Produto, String>("Tamanho"));
        colPrcProd.setCellValueFactory(new PropertyValueFactory<Produto, Double>("precoVenda"));

        tbProdutos.getItems().setAll(parseProdutoList());
    }

    private List<Produto> parseProdutoList() {
        ProdutoRest prodDAO = new ProdutoRest();
        List<Produto> listaProd = prodDAO.findAll();

        if (!listaProd.isEmpty()) {
            return listaProd;
        } else {
            return new ArrayList<>();
        }
    }

    private void refreshProduto() {
        final List<Produto> items = tbProdutos.getItems();
        if (items == null || items.isEmpty()) {
            return;
        }

        final Produto item = tbProdutos.getItems().get(0);
        items.remove(0);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                items.add(0, item);
            }
        });
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Métodos da Viagem">
    @FXML
    public void onClickViagem(MouseEvent event) {
        if (event.getClickCount() > 1) {
            Viagem viag = tbViagens.getSelectionModel().getSelectedItem();
            if (viag != null) {
                System.out.println("Viagem: " + viag.getId());
                openParam(viag);
            }
        }
    }

    private void fillViagem() {
        colCodViag.setCellValueFactory(new PropertyValueFactory<Viagem, Long>("codigo"));
        colLocalViag.setCellValueFactory(new PropertyValueFactory<Viagem, String>("localizacao"));
        colDtHrViag.setCellValueFactory(new PropertyValueFactory<Viagem, String>("datahora"));
        colGuiaViag.setCellValueFactory(new PropertyValueFactory<Viagem, String>("guia"));

        tbViagens.getItems().setAll(parseViagemList());
    }

    private List<Viagem> parseViagemList() {
        ViagemRest viagDAO = new ViagemRest();
        List<Viagem> listaViag = viagDAO.findAll();

        if (!listaViag.isEmpty()) {
            return listaViag;
        } else {
            return new ArrayList<>();
        }
    }

    private void refreshViagem() {
        final List<Viagem> items = tbViagens.getItems();
        if (items == null || items.isEmpty()) {
            return;
        }

        final Viagem item = tbViagens.getItems().get(0);
        items.remove(0);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                items.add(0, item);
            }
        });
    }
    //</editor-fold>
}
