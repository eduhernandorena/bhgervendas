package br.com.controller;

import br.com.ejb.bean.Entidade;
import br.com.ejb.bean.Pedido;
import br.com.ejb.bean.Produto;
import br.com.ejb.bean.Sync;
import br.com.ejb.bean.Viagem;
import br.com.ejb.bean.enumeration.FormaPagamento;
import br.com.ejb.bean.enumeration.StatusPedido;
import br.com.ejb.bean.enumeration.TipoEntidade;
import br.com.principal.FXOptionPane;
import br.com.principal.Principal;
import br.com.ws.EntidadeRest;
import br.com.ws.PedidoRest;
import br.com.ws.ProdutoRest;
import br.com.ws.SyncRest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class PedidosController implements Initializable {

    private static Principal p = Principal.getInstance();
    @FXML
    private MenuItem mnClear;
    @FXML
    private MenuItem mnSave;
    @FXML
    private MenuItem mnVoltar;
    @FXML
    private Button btClear;
    @FXML
    private Button btSave;
    @FXML
    private Button btVoltar;
    @FXML
    private Button btCliente;
    @FXML
    private Button btFornecedor;
    @FXML
    private Button btProduto;
    @FXML
    private TextField txtValor;
    @FXML
    private ComboBox<String> cmbFormaPag;
    @FXML
    private ComboBox<String> cmbStatus;
    @FXML
    private TextField txtCliente;
    @FXML
    private TextField txtCliNome;
    @FXML
    private TextField txtPedCod;
    @FXML
    private TextField txtCod;
    @FXML
    private TextField txtCodCliente;
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
    @FXML
    private TableView<Pedido> gridPedidos;
    @FXML
    private TableColumn<Pedido, Long> colCodPed;
    @FXML
    private TableColumn<Pedido, String> colFornPed;
    @FXML
    private TableColumn<Pedido, String> colDtPed;
    @FXML
    private TableColumn<Pedido, FormaPagamento> colFormaPed;
    @FXML
    private TableColumn<Pedido, Integer> colParcPed;
    @FXML
    private TableColumn<Pedido, Double> colValPed;
    @FXML
    private TableView<Produto> gridProd;
    @FXML
    private TableColumn<Produto, Long> colCodProd;
    @FXML
    private TableColumn<Produto, String> colDescProd;
    @FXML
    private TableColumn<Produto, Long> colNFeProd;
    @FXML
    private TableColumn<Produto, String> colRefProd;
    @FXML
    private TableColumn<Produto, String> colTamProd;
    @FXML
    private TableColumn<Produto, Double> colPrecProd;
    private PedidoRest pedDAO = new PedidoRest();
    private ProdutoRest prodDAO = new ProdutoRest();
    private EntidadeRest entDAO = new EntidadeRest();
    private SyncRest syncDAO = new SyncRest();
    private static Pedido pedido;
    private static Viagem viagem;

    public PedidosController(Pedido ped, Viagem v) {
        pedido = ped;
        viagem = v;
    }

    public PedidosController() {
    }

    private Pedido fillPedidoFind() {
        Pedido ped = new Pedido();
        Entidade cli = null;
        if (!txtCodCliente.getText().isEmpty()) {
            cli = entDAO.findCli(Long.valueOf(txtCodCliente.getText()));
        }
        ped.setCliente(cli);
        ped.setDataCompra(txtDta.getText());
        if (cmbFormaPag.getValue() != null) {
            ped.setFormaPagamento(FormaPagamento.valueOf(cmbFormaPag.getValue()));
        }
        Entidade forn = null;
        if (!txtCodForn.getText().isEmpty()) {
            forn = entDAO.findForn(Long.valueOf(txtCodForn.getText()));
        }
        ped.setFornecedor(forn);
        int parcelas = !txtNParcelas.getText().isEmpty() ? Integer.valueOf(txtNParcelas.getText()) : 1;
        ped.setNroParcelas(parcelas);
        ped.setProdutos(gridProd.getItems() != null ? gridProd.getItems() : new ArrayList<Produto>());
        if (!txtValor.getText().isEmpty()) {
            ped.setValor(Double.valueOf(txtValor.getText()));
        }
        if (cmbStatus.getValue() != null) {
            ped.setStatus(StatusPedido.valueOf(cmbStatus.getValue()));
        }
        ped.setObsPagamento(txtObsPag.getText());
        return ped;
    }

    public void buscarProd(ActionEvent event) {
        p.gotoFindProdutos(fillPedidoFind());
    }

    public void buscarCliente(ActionEvent event) {
        System.out.println("Busca Cliente");
        p.gotoFindCliente(fillPedidoFind(), TipoEntidade.Cliente);
    }

    public void buscarFornecedor(ActionEvent event) {
        System.out.println("Busca Fornecedor");
        p.gotoFindCliente(fillPedidoFind(), TipoEntidade.Fornecedor);
    }

    public void clearCad(ActionEvent event) {
        cmbFormaPag.setValue(null);
        txtCliente.setText("");
        txtCod.setText("");
        txtCodCliente.setText("");
        txtCodForn.setText("");
        txtDta.setText("");
        txtFornecedor.setText("");
        txtNParcelas.setText("");
        txtObsPag.setText("");
        txtValParcelas.setText("");
        txtValor.setText("");
        cmbStatus.setValue(null);
        gridProd.getItems().clear();
        fillPedidoGrid(new ArrayList<Produto>());
        pedido = null;
        unblockAll();
    }

    public void saveCad(ActionEvent event) {
        if (valida()) {
            Pedido ped = pedido != null ? pedido : new Pedido();
            Entidade cli = null;
            if (!txtCodCliente.getText().isEmpty()) {
                cli = entDAO.findCli(Long.valueOf(txtCodCliente.getText()));
                ped.setCliente(cli);
            }
            ped.setDataCompra(txtDta.getText());
            String formPag = cmbFormaPag.getSelectionModel().getSelectedItem();
            ped.setFormaPagamento(formPag != null ? FormaPagamento.valueOf(formPag) : null);
            Entidade forn = entDAO.findForn(Long.valueOf(txtCodForn.getText()));
            ped.setFornecedor(forn);
            int parcelas = !txtNParcelas.getText().isEmpty() ? Integer.valueOf(txtNParcelas.getText()) : 1;
            ped.setNroParcelas(parcelas);
            ped.setProdutos(gridProd.getItems());
            Double lucro = 0.0;
            Double precoCompra = 0.0;
            for (Produto produto : gridProd.getItems()) {
                lucro += produto.getLucro();
                precoCompra += produto.getPrecoCusto();
            }
            ped.setLucro(lucro);
            ped.setStatus(StatusPedido.valueOf(cmbStatus.getValue()));
            if (cli!=null) {
                ped.setValor(Double.valueOf(txtValor.getText()));
            }else{
                ped.setValor(precoCompra);
            }
            ped.setObsPagamento(txtObsPag.getText());
            pedDAO.create(ped);
            if (cmbStatus.getValue().equals(StatusPedido.EMITIDO.name())) {
                for (int i = 1; i <= ped.getNroParcelas(); i++) {
                    Sync sync = new Sync();
                    if (i == 1) {
                        sync.setDataPag(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                    } else {
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.MONTH, i - 1);
                        sync.setDataPag(new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime()));
                    }
                    sync.setParc(i + "/" + ped.getNroParcelas());
                    sync.setSincronizado(false);
                    if (cli != null) {
                        sync.setTpMov("E");
                        sync.setNome(ped.getCliente().getNome());
                        for (Produto prod : ped.getProdutos()) {
                            prod.setEsgotado(true);
                            prodDAO.create(prod);
                        }
                    } else {
                        sync.setTpMov("S");
                        sync.setNome(ped.getFornecedor().getNome());
                    }
                    BigDecimal div = new BigDecimal(ped.getNroParcelas());
                    BigDecimal res = new BigDecimal(ped.getValor()).divide(div, BigDecimal.ROUND_HALF_EVEN);
                    Double r = res.doubleValue();
                    sync.setValor(r);
                    syncDAO.create(sync);
                }
            }

            p.gotoPrincipal();
        } else {
            System.out.println("Não foi possível salvar!");
        }
    }

    @FXML
    public void keyExit(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            p.gotoPedidoCad(pedido, null);
        }
    }

    public void voltar(ActionEvent event) {
        p.gotoPrincipal();
    }

    private boolean valida() {
        if (txtDta.getText() == null || txtDta.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O campo de Data deve ser preenchido!", "Campo Vazio!");
            txtDta.requestFocus();
            return false;
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(true);
                sdf.parse(txtDta.getText());
            } catch (ParseException ex) {
                FXOptionPane.showMessageDialog(null, "O campo de Data deve ter o fomato DD/MM/AAAA!", "Campo Vazio!");
                txtDta.requestFocus();
                return false;
            }
        }
        if (txtCodForn.getText() == null || txtCodForn.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O fornecedor deve ser preenchido!", "Campo Vazio!");
            txtCodForn.requestFocus();
            return false;
        }

        if (gridProd.getItems().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O pedido deve conter itens!", "Campo Vazio");
            return false;
        }

        if (txtValor.getText() == null || txtValor.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O campo de valor total não pode ficar vazio!", "Campo Vazio!");
            txtValor.requestFocus();
            return false;
        }

        if ((txtNParcelas.getText() == null || !txtNParcelas.getText().isEmpty()) && Integer.valueOf(txtNParcelas.getText()) <= 0) {
            FXOptionPane.showMessageDialog(null, "O numero de parcelas deve ser maior do que zero!", "Campo Vazio!");
            txtValor.requestFocus();
            return false;
        }
        if (cmbStatus.getValue() == null) {
            FXOptionPane.showMessageDialog(null, "O status do pedido deve ser selecionado!", "Campo Vazio!");
            cmbStatus.requestFocus();
            return false;
        }
        if (cmbFormaPag.getValue() == null) {
            FXOptionPane.showMessageDialog(null, "A forma de pagamento deve ser selecionado!", "Campo Vazio!");
            cmbFormaPag.requestFocus();
            return false;
        }
        return true;
    }

    public void fill(Pedido ped) {
        if (ped != null) {
            txtCod.setText(ped.getCodigo() != null ? ped.getCodigo().toString() : "");
            cmbFormaPag.setValue(ped.getFormaPagamento() != null ? ped.getFormaPagamento().name() : FormaPagamento.AVISTA.name());
            cmbStatus.setValue(ped.getStatus() != null ? ped.getStatus().name() : StatusPedido.PENDENTE.name());
            txtCodCliente.setText(ped.getCliente() != null ? ped.getCliente().getId().toString() : "");
            txtCliente.setText(ped.getCliente() != null ? ped.getCliente().getNome() : "");
            txtCodForn.setText(ped.getFornecedor() != null ? ped.getFornecedor().getId().toString() : "");
            txtFornecedor.setText(ped.getFornecedor() != null ? ped.getFornecedor().getNome() : "");
            txtDta.setText(ped.getDataCompra());
            txtNParcelas.setText(ped.getNroParcelas() != null ? ped.getNroParcelas().toString() : "");
            txtObsPag.setText(ped.getObsPagamento());
            String valParc = String.valueOf(ped.getValor() / Double.valueOf(ped.getNroParcelas() != null ? ped.getNroParcelas().doubleValue() : 0));
            txtValParcelas.setText(valParc);
            txtValor.setText(ped.getValor() != null ? ped.getValor().toString() : "");
            fillPedidoGrid(ped.getProdutos());
        }
        if (ped.getStatus().equals(StatusPedido.EMITIDO)) {
            blockAll();
        }
    }

    private void blockAll() {
        txtCod.setDisable(true);
        cmbFormaPag.setDisable(true);
        cmbStatus.setDisable(true);
        txtCodCliente.setDisable(true);
        txtCliente.setDisable(true);
        txtCodForn.setDisable(true);
        txtFornecedor.setDisable(true);
        txtDta.setDisable(true);
        txtNParcelas.setDisable(true);
        txtObsPag.setDisable(true);
        txtValParcelas.setDisable(true);
        txtValor.setDisable(true);
        btCliente.setDisable(true);
        btFornecedor.setDisable(true);
        btProduto.setDisable(true);
        mnSave.setDisable(true);
        btSave.setDisable(true);
        gridProd.setDisable(true);
    }

    private void unblockAll() {
        txtCod.setDisable(false);
        cmbFormaPag.setDisable(false);
        cmbStatus.setDisable(false);
        txtCodCliente.setDisable(false);
        txtCliente.setDisable(false);
        txtCodForn.setDisable(false);
        txtFornecedor.setDisable(false);
        txtDta.setDisable(false);
        txtNParcelas.setDisable(false);
        txtObsPag.setDisable(false);
        txtValParcelas.setDisable(false);
        txtValor.setDisable(false);
        btCliente.setDisable(false);
        btFornecedor.setDisable(false);
        btProduto.setDisable(false);
        mnSave.setDisable(false);
        btSave.setDisable(false);
        gridProd.setDisable(false);
    }

    private void fillPedidoGrid(List<Produto> list) {
        colCodProd.setCellValueFactory(new PropertyValueFactory<Produto, Long>("id"));
        colDescProd.setCellValueFactory(new PropertyValueFactory<Produto, String>("descricao"));
        colNFeProd.setCellValueFactory(new PropertyValueFactory<Produto, Long>("nroNota"));
        colRefProd.setCellValueFactory(new PropertyValueFactory<Produto, String>("referencia"));
        colTamProd.setCellValueFactory(new PropertyValueFactory<Produto, String>("tamanho"));
        colPrecProd.setCellValueFactory(new PropertyValueFactory<Produto, Double>("precoVenda"));

        gridProd.getItems().setAll(list.isEmpty() ? new ArrayList<Produto>() : list);
    }

    private void fillViagemGrid(List<Pedido> list) {
        colCodPed.setCellValueFactory(new PropertyValueFactory<Pedido, Long>("codigo"));
        colFornPed.setCellValueFactory(new PropertyValueFactory<Pedido, String>("fornecedor"));
        colValPed.setCellValueFactory(new PropertyValueFactory<Pedido, Double>("valor"));
        colFormaPed.setCellValueFactory(new PropertyValueFactory<Pedido, FormaPagamento>("formaPagamento"));
        colParcPed.setCellValueFactory(new PropertyValueFactory<Pedido, Integer>("nroParcelas"));

        gridPedidos.getItems().setAll(!list.isEmpty() ? list : new ArrayList<Pedido>());
    }

    public void onClickPedido(MouseEvent event) {
        if (event.getClickCount() > 1) {
            Pedido ped = gridPedidos.getSelectionModel().getSelectedItem();
            if (ped != null) {
                Double valorProd = viagem.getValor() != null ? viagem.getValor() : .0;
                System.out.println("Pedido: " + ped.getCodigo());
                if (viagem.getPedidos() == null) {
                    viagem.setPedidos(new ArrayList<Pedido>());
                }
                viagem.getPedidos().add(ped);
                for (Produto prod : ped.getProdutos()) {
                    valorProd += prod.getPrecoCusto();
                }
                viagem.setValor(valorProd);
                if (viagem.getQtdeProdutos() != null) {
                    viagem.setQtdeProdutos(viagem.getQtdeProdutos() + ped.getProdutos().size());
                } else {
                    viagem.setQtdeProdutos(ped.getProdutos().size());
                }
            }
            p.gotoViagemCad(viagem);
        }
    }

    @FXML
    public void buscaPed(ActionEvent event) {
        List<Pedido> l = new ArrayList();
        if (!txtPedCod.getText().isEmpty()) {
            try {
                Long cod = Long.valueOf(txtPedCod.getText());
                l.add(pedDAO.find(cod));
            } catch (NumberFormatException n) {
                l.add(null);
                fillViagemGrid(l);
            }
            fillViagemGrid(l);
        } else if (!txtCliNome.getText().isEmpty()) {
            try {
                for (Pedido ped : pedDAO.findByCli(txtCliNome.getText() + "%")) {
                    l.add(ped);
                }
                fillViagemGrid(l);
            } catch (NullPointerException n) {
                l.add(null);
                fillViagemGrid(l);
            }
        } else {
            fillViagemGrid(l);
        }
    }

    private void refreshValor() {
        if (!gridProd.getItems().isEmpty()) {
            Double valor = 0.0;
            for (Produto produto : gridProd.getItems()) {
                valor += produto.getPrecoVenda();
            }
            txtValor.setText(valor.toString());
            pedido.setValor(valor);
            if (txtNParcelas.getText() != null && !txtNParcelas.getText().isEmpty()) {
                Double parcelas = Double.parseDouble(txtNParcelas.getText());
                txtValParcelas.setText(String.valueOf(valor / parcelas));
                pedido.setNroParcelas(parcelas.intValue());
            } else {
                txtNParcelas.setText("1");
                txtValParcelas.setText(valor.toString());
                pedido.setNroParcelas(1);
            }
        } else {
            txtValor.setText("");
            txtValParcelas.setText("");
            txtNParcelas.setText("");
            pedido.setValor(null);
            pedido.setNroParcelas(null);
        }
    }

    public void onClickRemove(MouseEvent event) {
        if (event.getClickCount() > 1) {
            Produto prod = gridProd.getSelectionModel().getSelectedItem();
            if (prod != null) {
                System.out.println("Produto: " + prod.getId());
                pedido.getProdutos().remove(prod);
                gridProd.getItems().remove(prod);
            }
            refreshValor();
        }
    }

    public void fillPedProd(Pedido ped) {
        if (ped.getCliente() != null) {
            txtCodCliente.setText(ped.getCliente().getId().toString());
            txtCliente.setText(ped.getCliente().getNome());
        }

        if (ped.getFornecedor() != null) {
            txtCodForn.setText(ped.getFornecedor().getId().toString());
            txtFornecedor.setText(ped.getFornecedor().getNome());
        }

        if (ped.getDataCompra() != null && !ped.getDataCompra().isEmpty()) {
            txtDta.setText(ped.getDataCompra());
        }
        if (ped.getStatus() != null) {
            cmbStatus.setValue(ped.getStatus().name());
        }
        if (ped.getCodigo() != null) {
            txtCod.setText(ped.getCodigo().toString());
        }
        if (ped.getFormaPagamento() != null) {
            cmbFormaPag.setValue(ped.getFormaPagamento().name());
        }

        if (ped.getStatus() != null) {
            cmbStatus.setValue(ped.getStatus().name());
        }

        if (ped.getObsPagamento() != null) {
            txtObsPag.setText(ped.getObsPagamento());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (viagem != null) {
            fillViagemGrid(pedDAO.findAll());
        } else {
            if (pedido != null) {
                if (pedido.getCodigo() != null) {
                    fill(pedido);
                } else if (pedido != null) {
                    fillPedProd(pedido);
                    if (!pedido.getProdutos().isEmpty()) {
                        fillPedidoGrid(pedido.getProdutos());
                        refreshValor();
                    }
                }
            }
            txtCod.focusedProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    String cod = txtCod.getText();
                    if (!cod.isEmpty()) {
                        fill(pedDAO.find(Long.valueOf(cod)));
                    }
                }
            });

            cmbFormaPag.focusedProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    if (FormaPagamento.valueOf(cmbFormaPag.getValue()).equals(FormaPagamento.AVISTA)) {
                        txtNParcelas.setText("1");
                        txtNParcelas.setDisable(true);
                    } else {
                        txtNParcelas.setText("2");
                        txtNParcelas.setDisable(false);
                    }
                }
            });

            txtNParcelas.focusedProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    String parcelas = txtNParcelas.getText();
                    if (!parcelas.isEmpty()) {
                        Double parc = Double.valueOf(parcelas);
                        if (parc > 1) {
                            cmbFormaPag.setValue(FormaPagamento.APRAZO.name());
                            txtValParcelas.setText(!txtValor.getText().isEmpty() ? String.valueOf(Double.valueOf(txtValor.getText()) / parc) : "");
                            pedido.setFormaPagamento(FormaPagamento.APRAZO);
                        } else if (parc == 1) {
                            cmbFormaPag.setValue(FormaPagamento.AVISTA.name());
                            txtValParcelas.setText(txtValor.getText());
                            pedido.setFormaPagamento(FormaPagamento.AVISTA);
                        }
                        pedido.setNroParcelas(parc.intValue());
                        if (!txtValor.getText().isEmpty()) {
                            pedido.setValor(Double.valueOf(txtValor.getText()));
                        }
                    }
                }
            });

            txtCodCliente.focusedProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    String cod = txtCodCliente.getText();
                    if (!cod.isEmpty()) {
                        Entidade ent = entDAO.findCli(Long.valueOf(cod));
                        if (ent != null && ent.getTipoEntidade().isCliente()) {
                            txtCliente.setText(ent.getNome());
                        }
                    }
                }
            });

            txtCodForn.focusedProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    String cod = txtCodForn.getText();
                    if (!cod.isEmpty()) {
                        Entidade ent = entDAO.findForn(Long.valueOf(cod));
                        if (ent != null && !ent.getTipoEntidade().isCliente()) {
                            txtFornecedor.setText(ent.getNome());
                        }
                    }
                }
            });
        }
    }
}