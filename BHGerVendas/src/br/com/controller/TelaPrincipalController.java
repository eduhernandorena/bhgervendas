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
    private TextField txtProdCod;
    @FXML
    private TextField txtProdDesc;
    @FXML
    private TextField txtViagCod;
    @FXML
    private TextField txtViagLocal;
    @FXML
    private TextField txtPedCod;
    @FXML
    private TextField txtPedCliNome;
    @FXML
    private TextField txtCliCod;
    @FXML
    private TextField txtCliNome;
    @FXML
    private TextField txtFornCod;
    @FXML
    private TextField txtFornNome;
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
    private EntidadeRest entDAO = new EntidadeRest();
    private ViagemRest viagDAO = new ViagemRest();
    private PedidoRest pedDAO = new PedidoRest();
    private ProdutoRest prodDAO = new ProdutoRest();
    private EncomendaRest encDAO = new EncomendaRest();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fill();
    }

    public void fill() {
        fillCliente(parseClienteList());
        fillEncomendas(parseEncomendaList());
        fillFornecedor(parseFornecedorList());
        fillPedido(parsePedidoList());
        fillProduto(parseProdutoList());
        fillViagem(parseViagemList());
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

    private void fillCliente(List<Entidade> list) {
        colCodCliente.setCellValueFactory(new PropertyValueFactory<Entidade, Long>("id"));
        colNomeCliente.setCellValueFactory(new PropertyValueFactory<Entidade, String>("Nome"));
        colTelCliente.setCellValueFactory(new PropertyValueFactory<Entidade, Long>("Telefone"));
        colCelCliente.setCellValueFactory(new PropertyValueFactory<Entidade, Long>("Celular"));
        colEndCliente.setCellValueFactory(new PropertyValueFactory<Entidade, Endereco>("Endereco"));

        tbClientes.getItems().setAll(list);
    }

    private List<Entidade> parseClienteList() {
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

    @FXML
    public void buscaCli(ActionEvent event) {
        List<Entidade> l = new ArrayList();
        if (!txtCliCod.getText().isEmpty()) {
            try {
                Long cod = Long.valueOf(txtCliCod.getText());
                l.add(entDAO.findCli(cod));
            } catch (NumberFormatException numberFormatException) {
                l.add(null);
                fillCliente(l);
            }
            fillCliente(l);
        } else if (!txtCliNome.getText().isEmpty()) {
            try {
                for (Entidade entidade : entDAO.findCliNome(txtCliNome.getText() + "%")) {
                    l.add(entidade);
                }
                fillCliente(l);
            } catch (NullPointerException nullPointerException) {
                l.add(null);
                fillCliente(l);
            }
        } else {
            fillCliente(parseClienteList());
        }
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

    private void fillFornecedor(List<Entidade> list) {
        colCodForn.setCellValueFactory(new PropertyValueFactory<Entidade, Long>("id"));
        colNomeForn.setCellValueFactory(new PropertyValueFactory<Entidade, String>("Nome"));
        colTelForn.setCellValueFactory(new PropertyValueFactory<Entidade, Long>("Telefone"));
        colCelForn.setCellValueFactory(new PropertyValueFactory<Entidade, Long>("Celular"));
        colEndForn.setCellValueFactory(new PropertyValueFactory<Entidade, Endereco>("Endereco"));

        tbFornecedor.getItems().setAll(list);
    }

    private List<Entidade> parseFornecedorList() {
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

    @FXML
    public void buscaForn(ActionEvent event) {
        List<Entidade> l = new ArrayList();
        if (!txtFornCod.getText().isEmpty()) {
            try {
                Long cod = Long.valueOf(txtFornCod.getText());
                l.add(entDAO.findForn(cod));
            } catch (NumberFormatException numberFormatException) {
                l.add(null);
                fillFornecedor(l);
            }
            fillFornecedor(l);
        } else if (!txtFornNome.getText().isEmpty()) {
            try {
                for (Entidade entidade : entDAO.findFornNome(txtFornNome.getText() + "%")) {
                    l.add(entidade);
                }
                fillFornecedor(l);
            } catch (NullPointerException nullPointerException) {
                l.add(null);
                fillCliente(l);
            }
        } else {
            fillFornecedor(parseFornecedorList());
        }
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

    private void fillEncomendas(List<Encomenda> list) {
        colCodEnc.setCellValueFactory(new PropertyValueFactory<Encomenda, Long>("id"));
        colViagemEnc.setCellValueFactory(new PropertyValueFactory<Encomenda, Viagem>("viagem"));

        tbEncomendas.getItems().setAll(list);
    }

    private List<Encomenda> parseEncomendaList() {
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

    @FXML
    public void buscaViag(ActionEvent event) {
        List<Viagem> l = new ArrayList();
        if (!txtViagCod.getText().isEmpty()) {
            try {
                Long cod = Long.valueOf(txtViagCod.getText());
                l.add(viagDAO.find(cod));
            } catch (NumberFormatException numberFormatException) {
                l.add(null);
                fillViagem(l);
            }
            fillViagem(l);
        } else if (!txtViagLocal.getText().isEmpty()) {
            try {
                for (Viagem viagem : viagDAO.findLocal(txtViagLocal.getText() + "%")) {
                    l.add(viagem);
                }
                fillViagem(l);
            } catch (NullPointerException nullPointerException) {
                l.add(null);
                fillViagem(l);
            }
        } else {
            fillViagem(parseViagemList());
        }
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

    private void fillPedido(List<Pedido> list) {
        colCodPed.setCellValueFactory(new PropertyValueFactory<Pedido, Long>("codigo"));
        colCliPed.setCellValueFactory(new PropertyValueFactory<Pedido, Entidade>("Cliente"));
        colValPed.setCellValueFactory(new PropertyValueFactory<Pedido, Double>("valor"));
        colFormaPed.setCellValueFactory(new PropertyValueFactory<Pedido, FormaPagamento>("formaPagamento"));
        colParcPed.setCellValueFactory(new PropertyValueFactory<Pedido, Integer>("nroParcelas"));

        tbPedidos.getItems().setAll(list);
    }

    private List<Pedido> parsePedidoList() {
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

    @FXML
    public void buscaPed(ActionEvent event) {
        List<Pedido> l = new ArrayList();
        if (!txtPedCod.getText().isEmpty()) {
            try {
                Long cod = Long.valueOf(txtPedCod.getText());
                l.add(pedDAO.find(cod));
            } catch (NumberFormatException numberFormatException) {
                l.add(null);
                fillPedido(l);
            }
            fillPedido(l);
        } else if (!txtPedCliNome.getText().isEmpty()) {
            try {
                for (Pedido ped : pedDAO.findByCli(txtPedCliNome.getText() + "%")) {
                    l.add(ped);
                }
                fillPedido(l);
            } catch (NullPointerException nullPointerException) {
                l.add(null);
                fillPedido(l);
            }
        } else {
            fillPedido(parsePedidoList());
        }
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

    private void fillProduto(List<Produto> list) {
        colCodProd.setCellValueFactory(new PropertyValueFactory<Produto, Long>("id"));
        colDescProd.setCellValueFactory(new PropertyValueFactory<Produto, String>("Descricao"));
        colTamProd.setCellValueFactory(new PropertyValueFactory<Produto, String>("Tamanho"));
        colPrcProd.setCellValueFactory(new PropertyValueFactory<Produto, Double>("precoVenda"));

        tbProdutos.getItems().setAll(list);
    }

    private List<Produto> parseProdutoList() {
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

    @FXML
    public void buscaProd(ActionEvent event) {
        List<Produto> l = new ArrayList();
        if (!txtProdCod.getText().isEmpty()) {
            try {
                Long cod = Long.valueOf(txtProdCod.getText());
                l.add(prodDAO.find(cod));
            } catch (NumberFormatException numberFormatException) {
                l.add(null);
                fillProduto(l);
            }
            fillProduto(l);
        } else if (!txtProdDesc.getText().isEmpty()) {
            try {
                for (Produto ped : prodDAO.findDesc(txtProdDesc.getText() + "%")) {
                    l.add(ped);
                }
                fillProduto(l);
            } catch (NullPointerException nullPointerException) {
                l.add(null);
                fillProduto(l);
            }
        } else {
            fillProduto(parseProdutoList());
        }
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

    private void fillViagem(List<Viagem> list) {
        colCodViag.setCellValueFactory(new PropertyValueFactory<Viagem, Long>("codigo"));
        colLocalViag.setCellValueFactory(new PropertyValueFactory<Viagem, String>("localizacao"));
        colDtHrViag.setCellValueFactory(new PropertyValueFactory<Viagem, String>("datahora"));
        colGuiaViag.setCellValueFactory(new PropertyValueFactory<Viagem, String>("guia"));

        tbViagens.getItems().setAll(list);
    }

    private List<Viagem> parseViagemList() {
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
