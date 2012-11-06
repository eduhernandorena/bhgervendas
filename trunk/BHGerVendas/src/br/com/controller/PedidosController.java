package br.com.controller;

import br.com.ejb.bean.Encomenda;
import br.com.ejb.bean.Entidade;
import br.com.ejb.bean.Pedido;
import br.com.ejb.bean.Produto;
import br.com.ejb.bean.Sync;
import br.com.ejb.bean.enumeration.FormaPagamento;
import br.com.principal.FXOptionPane;
import br.com.principal.Principal;
import br.com.ws.EncomendaRest;
import br.com.ws.EntidadeRest;
import br.com.ws.PedidoRest;
import br.com.ws.ViagemRest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PedidosController implements Initializable {

    private static Principal p = Principal.getInstance();
    @FXML
    private Button btCliente;
    @FXML
    private Button btEncomenda;
    @FXML
    private Button btFornecedor;
    @FXML
    private Button btProduto;
    @FXML
    private TextField txtValor;
    @FXML
    private TextField txtCodViagem;
    @FXML
    private Button btVoltar;
    @FXML
    private ComboBox<String> cmbFormaPag;
    @FXML
    private CheckBox rdPedido;
    @FXML
    private TextField txtCliente;
    @FXML
    private TextField txtCod;
    @FXML
    private TextField txtCodCliente;
    @FXML
    private TextField txtCodEncomenda;
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
    private EntidadeRest entDAO = new EntidadeRest();
    private EncomendaRest encDAO = new EncomendaRest();
    private ViagemRest viDAO = new ViagemRest();
    private static Pedido pedido;

    public PedidosController(Pedido ped) {
        pedido = ped;
    }

    public PedidosController() {
    }

    public void buscarProd(ActionEvent event) {
        Pedido ped = new Pedido();
        Entidade cli = null;
        if (!txtCodCliente.getText().isEmpty()) {
            cli = entDAO.find(Long.valueOf(txtCodCliente.getText()));
        }
        ped.setCliente(cli);
        if (!txtDta.getText().isEmpty()) {
            try {
                ped.setDataCompra(new SimpleDateFormat("dd/MM/yyyy").parse(txtDta.getText()));
            } catch (ParseException ex) {
                Logger.getLogger(PedidosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (cmbFormaPag.getValue() != null) {
            ped.setFormaPagamento(FormaPagamento.valueOf(cmbFormaPag.getValue()));
        }
        Entidade forn = null;
        if (!txtCodForn.getText().isEmpty()) {
            forn = entDAO.find(Long.valueOf(txtCodForn.getText()));
        }
        ped.setFornecedor(forn);
        int parcelas = !txtNParcelas.getText().isEmpty() ? Integer.valueOf(txtNParcelas.getText()) : 1;
        ped.setNroParcelas(parcelas);
        ped.setProdutos(gridProd.getItems() != null ? gridProd.getItems() : new ArrayList<Produto>());
        if (!txtValor.getText().isEmpty()) {
            ped.setValor(Double.valueOf(txtValor.getText()));
        }

        ped.setObsPagamento(txtObsPag.getText());
        if (txtCodViagem.getText() != null && !txtCodViagem.getText().trim().isEmpty()) {
            ped.setViagem(viDAO.find(Long.valueOf(txtCodViagem.getText())));
        } else {
            ped.setViagem(null);
        }
        p.gotoFindProdutos(ped);
    }

    public void clearCad(ActionEvent event) {
        cmbFormaPag.setValue("A Vista");
        rdPedido.setSelected(false);
        txtCliente.setText(null);
        txtCod.setText(null);
        txtCodCliente.setText(null);
        txtCodEncomenda.setText(null);
        txtCodForn.setText(null);
        txtDta.setText(null);
        txtFornecedor.setText(null);
        txtNParcelas.setText(null);
        txtObsPag.setText(null);
        txtValParcelas.setText(null);
        txtValor.setText(null);
        txtCodViagem.setText(null);
    }

    public void saveCad(ActionEvent event) {
        if (valida()) {
            Pedido ped = new Pedido();
            Entidade cli = null;
            if (!txtCodCliente.getText().isEmpty()) {
                cli = entDAO.find(Long.valueOf(txtCodCliente.getText()));
            }
            ped.setCliente(cli);
            try {
                ped.setDataCompra(new SimpleDateFormat("dd/MM/yyyy").parse(txtDta.getText()));
            } catch (ParseException ex) {
                Logger.getLogger(PedidosController.class.getName()).log(Level.SEVERE, null, ex);
            }
            ped.setFormaPagamento(FormaPagamento.valueOf(cmbFormaPag.getValue()));
            Entidade forn = null;
            if (!txtCodForn.getText().isEmpty()) {
                forn = entDAO.find(Long.valueOf(txtCodForn.getText()));
            }
            ped.setFornecedor(forn);
            int parcelas = !txtNParcelas.getText().isEmpty() ? Integer.valueOf(txtNParcelas.getText()) : 1;
            ped.setNroParcelas(parcelas);
            ped.setProdutos(gridProd.getItems());
            ped.setValor(Double.valueOf(txtValor.getText()));
            ped.setObsPagamento(txtObsPag.getText());
            if (txtCodViagem.getText() != null && !txtCodViagem.getText().trim().isEmpty()) {
                ped.setViagem(viDAO.find(Long.valueOf(txtCodViagem.getText())));
            } else {
                ped.setViagem(null);
            }
            pedDAO.create(ped);
            for (int i = 1; i <= ped.getNroParcelas(); i++) {
                Sync sync = new Sync();
                sync.setDataPag(new Date());
                sync.setNome(ped.getCliente().getNome());
                sync.setParc(i + "/" + ped.getNroParcelas());
                sync.setSincronizado(false);
                if (cli != null) {
                    sync.setTpMov("E");
                } else if (forn != null) {
                    sync.setTpMov("S");
                }
                BigDecimal div = new BigDecimal(ped.getNroParcelas());
                BigDecimal res = new BigDecimal(ped.getValor()).divide(div);
                Double r = res.setScale(2, RoundingMode.HALF_EVEN).doubleValue();
                sync.setValor(r);

            }
            p.gotoPrincipal();
        } else {
            System.out.println("Não foi possível salvar!");
        }
    }

    public void voltar(ActionEvent event) {
        p.gotoPrincipal();
    }

    private boolean valida() {
        if (txtDta.getText().isEmpty()) {
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
        if (txtCodCliente.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O cliente deve ser preenchido!", "Campo Vazio!");
            txtCodCliente.requestFocus();
            return false;
        }
        if (txtCodForn.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O fornecedor deve ser preenchido!", "Campo Vazio!");
            txtCodForn.requestFocus();
            return false;
        }
        if (rdPedido.isSelected()) {
            if (txtCodEncomenda.getText().isEmpty()) {
                FXOptionPane.showMessageDialog(null, "Se o pedido for oriundo de encomenda, \n "
                        + "o codigo da encomenda deve ser preenchido!", "Campo Vazio");
                txtCodEncomenda.requestFocus();
                return false;
            }
        }
        if (gridProd.getItems().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O pedido deve conter itens!", "Campo Vazio");
            return false;
        }

        if (txtValor.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O campo de valor total não pode ficar vazio!", "Campo Vazio!");
            txtValor.requestFocus();
            return false;
        }

        if (!txtNParcelas.getText().isEmpty() && Integer.valueOf(txtNParcelas.getText()) <= 0) {
            FXOptionPane.showMessageDialog(null, "O numero de parcelas deve ser maior do que zero!", "Campo Vazio!");
            txtValor.requestFocus();
            return false;
        }
        return true;
    }

    public void fill(Pedido ped) {
        if (ped != null) {
            txtCod.setText(ped.getCodigo() != null ? ped.getCodigo().toString() : "");
            cmbFormaPag.setValue(ped.getFormaPagamento() != null ? ped.getFormaPagamento().name() : "A Vista");
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
            txtCodViagem.setText(ped.getViagem() != null ? ped.getViagem().getId().toString() : "");
            fillPedidoGrid(ped.getProdutos());
        }
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

    private void refreshPedidos() {
        final List<Produto> items = gridProd.getItems();
        if (items == null || items.isEmpty()) {
            return;
        }

        final Produto item = gridProd.getItems().get(0);
        items.remove(0);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                items.add(0, item);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fill(pedido);

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
                    txtNParcelas.setText("");
                    txtNParcelas.setDisable(false);
                }
            }
        });

        txtNParcelas.focusedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String parcelas = txtNParcelas.getText();
                if (!parcelas.isEmpty()) {
                    if (Integer.valueOf(parcelas) > 1) {
                        cmbFormaPag.setValue(FormaPagamento.APRAZO.name());
                    } else if (Integer.valueOf(parcelas) == 1) {
                        cmbFormaPag.setValue(FormaPagamento.AVISTA.name());
                    }
                }
            }
        });

        txtCodCliente.focusedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String cod = txtCodCliente.getText();
                if (!cod.isEmpty()) {
                    Entidade ent = entDAO.find(Long.valueOf(cod));
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
                    Entidade ent = entDAO.find(Long.valueOf(cod));
                    if (ent != null && !ent.getTipoEntidade().isCliente()) {
                        txtFornecedor.setText(ent.getNome());
                    }
                }
            }
        });

        txtCodEncomenda.focusedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String cod = txtCodEncomenda.getText();
                if (!cod.isEmpty()) {
                    Encomenda enc = encDAO.find(Long.valueOf(cod));
                    if (enc == null) {
                        FXOptionPane.showMessageDialog(null, "Encomenda Inexistente!", "Código Errado!");
                        txtCodEncomenda.setText("");
                        txtCodEncomenda.requestFocus();
                    }
                }
            }
        });
    }
}