package br.com.controller;

import br.com.ejb.bean.Cidade;
import br.com.ejb.bean.Endereco;
import br.com.ejb.bean.Entidade;
import br.com.ejb.bean.Pedido;
import br.com.ejb.bean.UF;
import br.com.ejb.bean.enumeration.EstadoCivil;
import br.com.ejb.bean.enumeration.FormaPagamento;
import br.com.ejb.bean.enumeration.Genero;
import br.com.ejb.bean.enumeration.TipoEndereco;
import br.com.ejb.bean.enumeration.TipoEntidade;
import br.com.principal.FXOptionPane;
import br.com.principal.Principal;
import br.com.ws.CidadeRest;
import br.com.ws.EnderecoRest;
import br.com.ws.EntidadeRest;
import br.com.ws.PedidoRest;
import br.com.ws.UFRest;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.PropertyValueFactoryBuilder;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Builder;

/**
 *
 * @author Eduardo Hernandorena
 */
public class ClienteFornController implements Initializable {

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
    private CheckBox chkAtivo;
    @FXML
    private CheckBox chkNew;
    @FXML
    private ComboBox<UF> cmbEst;
    @FXML
    private ComboBox<String> cmbEstCivil;
    @FXML
    private ComboBox<String> cmbGenero;
    @FXML
    private ComboBox<Cidade> cmbMun;
    @FXML
    private ComboBox<String> cmbTpEnd;
    @FXML
    private ComboBox<String> cmbTpEntidade;
    @FXML
    private TableView<Pedido> gridPedidos;
    @FXML
    private TableColumn<Pedido, Long> colCodPed;
    @FXML
    private TableColumn<Pedido, String> colForn;
    @FXML
    private TableColumn<Pedido, String> colCli;
    @FXML
    private TableColumn<Pedido, String> colDtPed;
    @FXML
    private TableColumn<Pedido, FormaPagamento> colFormaPed;
    @FXML
    private TableColumn<Pedido, Integer> colParcPed;
    @FXML
    private TableColumn<Pedido, Double> colValPed;
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
    @FXML
    private TextField txtCliCod;
    @FXML
    private TextField txtCliNome;
    @FXML
    private TableView<Entidade> tbClientes;
    @FXML
    private TextField txtFornCod;
    @FXML
    private TextField txtFornNome;
    @FXML
    private TableColumn<Entidade, Long> colCodCliente;
    @FXML
    private TableColumn<Entidade, String> colNomeCliente;
    @FXML
    private TableColumn<Entidade, Long> colTelCliente;
    @FXML
    private TableColumn<Entidade, Long> colCelCliente;
    @FXML
    private TableColumn<Entidade, Endereco> colEndCliente;
    @FXML
    private TableView<Entidade> tbFornecedores;
    @FXML
    private TableColumn<Entidade, Long> colCodForn;
    @FXML
    private TableColumn<Entidade, String> colNomeForn;
    @FXML
    private TableColumn<Entidade, Long> colTelForn;
    @FXML
    private TableColumn<Entidade, Long> colCelForn;
    @FXML
    private TableColumn<Entidade, Endereco> colEndForn;
    private static EntidadeRest entDao = new EntidadeRest();
    private static EnderecoRest endDao = new EnderecoRest();
    private static CidadeRest cidDao = new CidadeRest();
    private static Entidade entidade = null;
    private static Pedido pedido = null;

    public ClienteFornController(Entidade ent) {
        entidade = ent;
    }

    public ClienteFornController(Pedido ped) {
        pedido = ped;
    }

    public ClienteFornController() {
    }

    public void keyExit(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            p.gotoPedidoCad(pedido, null);
        }
    }

    @FXML
    public void buscaCli(ActionEvent event) {
        List<Entidade> l = new ArrayList();
        if (!txtCliCod.getText().isEmpty()) {
            try {
                Long cod = Long.valueOf(txtCliCod.getText());
                l.add(entDao.findCli(cod));
            } catch (NumberFormatException numberFormatException) {
                l.add(null);
                fillClienteGrid(l);
            }
            fillClienteGrid(l);
        } else if (!txtCliNome.getText().isEmpty()) {
            try {
                for (Entidade e : entDao.findCliNome(txtCliNome.getText() + "%")) {
                    l.add(e);
                }
                fillClienteGrid(l);
            } catch (NullPointerException nullPointerException) {
                l.add(null);
                fillClienteGrid(l);
            }
        } else {
            fillClienteGrid(entDao.findAll(TipoEntidade.Cliente));
        }
    }

    @FXML
    public void buscaForn(ActionEvent event) {
        List<Entidade> l = new ArrayList();
        if (!txtFornCod.getText().isEmpty()) {
            try {
                Long cod = Long.valueOf(txtFornCod.getText());
                l.add(entDao.findForn(cod));
            } catch (NumberFormatException numberFormatException) {
                l.add(null);
                fillFornecedorGrid(l);
            }
            fillFornecedorGrid(l);
        } else if (!txtFornNome.getText().isEmpty()) {
            try {
                for (Entidade e : entDao.findFornNome(txtFornNome.getText() + "%")) {
                    l.add(e);
                }
                fillFornecedorGrid(l);
            } catch (NullPointerException nullPointerException) {
                l.add(null);
                fillFornecedorGrid(l);
            }
        } else {
            fillFornecedorGrid(entDao.findAll(TipoEntidade.Fornecedor));
        }
    }

    @FXML
    public void onClickCliente(MouseEvent event) {
        if (event.getClickCount() > 1) {
            Entidade ent = tbClientes.getSelectionModel().getSelectedItem();
            if (ent != null) {
                System.out.println("Cliente: " + ent.getId());
                pedido.setCliente(ent);
            }
            p.gotoPedidoCad(pedido, null);
        }
    }

    @FXML
    public void onClickFornecedor(MouseEvent event) {
        if (event.getClickCount() > 1) {
            Entidade ent = tbFornecedores.getSelectionModel().getSelectedItem();
            if (ent != null) {
                System.out.println("Fornecedor: " + ent.getId());
                pedido.setFornecedor(ent);
            }
            p.gotoPedidoCad(pedido, null);
        }
    }

    @FXML
    public void clearCad(ActionEvent event) {
        txtArea.setText("");
        txtBairro.setText("");
        txtCel.setText("");
        txtCep.setText("");
        txtCod.setText("");
        txtCodEnd.setText("");
        txtCpfCnpj.setText("");
        txtCpl.setText("");
        txtEmail.setText("");
        txtLogradouro.setText("");
        txtNome.setText("");
        txtNum.setText("");
        txtTel.setText("");
        txtUltPed.setText("");
        cmbEst.setValue(null);
        cmbEstCivil.setValue(null);
        cmbGenero.setValue(null);
        cmbMun.setValue(null);
        cmbTpEnd.setValue(null);
        chkNew.setSelected(false);
        gridPedidos.getItems().clear();
    }

    @FXML
    public void saveCad(ActionEvent event) {
        if (valida()) {
            Entidade ent = entidade != null ? entidade : new Entidade();
            Long id = !txtCod.getText().isEmpty() ? Long.valueOf(txtCod.getText()) : null;
            ent.setId(id);
            Long cel = !txtCel.getText().isEmpty() ? Long.valueOf(txtCel.getText()) : null;
            ent.setCelular(cel);
            ent.setCpfCnpj(txtCpfCnpj.getText());
            ent.setAtivo(chkAtivo.isSelected());
            ent.setEmail(txtEmail.getText());
            Endereco end = new Endereco();
            Long idEnd = !txtCodEnd.getText().isEmpty() ? Long.valueOf(txtCodEnd.getText()) : null;
            end.setId(idEnd);
            end.setRua(txtLogradouro.getText());
            end.setNumero(txtNum.getText());
            end.setComplemento(txtCpl.getText());
            end.setBairro(txtBairro.getText());
            end.setCep(txtCep.getText());
            end.setTipoEndereco(TipoEndereco.valueOf(cmbTpEnd.getValue()));
            end.setCidade(cidDao.find(Long.valueOf(cmbMun.getValue().getCodIbge())));
            end = endDao.create(end);
            ent.setEndereco(end);
            ent.setEstadoCivil(EstadoCivil.valueOf(cmbEstCivil.getValue()));
            ent.setGenero(Genero.valueOf(cmbGenero.getValue()));
            ent.setNome(txtNome.getText());
            ent.setObs(txtArea.getText());
            ent.setPedidos(gridPedidos.getItems());
            ent.setTelefone(Long.valueOf(txtTel.getText()));
            ent.setTipoEntidade(TipoEntidade.valueOf(cmbTpEntidade.getValue()));
            if (!txtUltPed.getText().isEmpty()) {
                ent.setUltimoPedido(Long.valueOf(txtUltPed.getText()));
            }
            entDao.create(ent);
            p.gotoPrincipal();
        } else {
            System.out.println("Não foi possível salvar!");
        }
    }

    private void fillEndereco(Endereco end) {
        txtCodEnd.setText(end.getId().toString());
        txtLogradouro.setText(end.getRua());
        txtBairro.setText(end.getBairro());
        txtCep.setText(end.getCep());
        txtNum.setText(end.getNumero().toString());
        txtCpl.setText(end.getComplemento());
        cmbTpEnd.setValue(end.getTipoEndereco().name());
        fillCmbUF();
        cmbEst.getSelectionModel().select(end.getCidade().getUf());
        fillcmbMunicipio();
        cmbMun.getSelectionModel().select(end.getCidade());
    }

    @FXML
    public void voltar(ActionEvent event) {
        p.gotoPrincipal();
    }

    private boolean valida() {
        if (txtNome.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O campo de nome deve ser preenchido!", "Campo Vazio!");
            txtNome.requestFocus();
            return false;
        }

        if (txtCpfCnpj.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O campo de CPF/CNPJ deve ser preenchido!", "Campo Vazio!");
            txtCpfCnpj.requestFocus();
            return false;
        }

        if (cmbEstCivil.getValue() == null) {
            FXOptionPane.showMessageDialog(null, "O Estado Civil deve ser selecionado!", "Campo Vazio!");
            cmbEstCivil.requestFocus();
            return false;
        }

        if (cmbGenero.getValue() == null) {
            FXOptionPane.showMessageDialog(null, "O Genero deve ser selecionado!", "Campo Vazio!");
            cmbGenero.requestFocus();
            return false;
        }

        if (txtTel.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O campo de telefone deve ser preenchido!", "Campo Vazio");
            txtTel.requestFocus();
            return false;
        }

        if (!chkNew.isSelected()) {
            if (txtUltPed.getText().isEmpty()) {
                FXOptionPane.showMessageDialog(null, "O campo de último pedido deve ser preenchido!", "Campo Vazio");
                txtUltPed.requestFocus();
                return false;
            }
        }

        if (cmbTpEnd.getValue() == null) {
            FXOptionPane.showMessageDialog(null, "O Tipo de Endereço deve ser selecionado!", "Campo Vazio!");
            cmbTpEnd.requestFocus();
            return false;
        }

        if (txtLogradouro.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O campo de rua deve ser preenchido!", "Campo Vazio!");
            txtLogradouro.requestFocus();
            return false;
        }

        if (txtNum.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O campo de número deve ser preenchido!", "Campo Vazio!");
            txtNum.requestFocus();
            return false;
        }

        if (txtBairro.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O campo de bairro deve ser preenchido!", "Campo Vazio!");
            txtBairro.requestFocus();
            return false;
        }

        if (txtCep.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O campo de CEP deve ser preenchido!", "Campo Vazio!");
            txtCep.requestFocus();
            return false;
        }

        if (cmbEst.getValue() == null) {
            FXOptionPane.showMessageDialog(null, "O Estado deve ser selecionado!", "Campo Vazio!");
            cmbEst.requestFocus();
            return false;
        }

        if (cmbMun.getValue() == null) {
            FXOptionPane.showMessageDialog(null, "A cidade deve ser selecionada!", "Campo Vazio!");
            cmbMun.requestFocus();
            return false;
        }

        return true;
    }

    private void fillClienteGrid(List<Entidade> list) {
        colCodCliente.setCellValueFactory(new PropertyValueFactory<Entidade, Long>("id"));
        colNomeCliente.setCellValueFactory(new PropertyValueFactory<Entidade, String>("nome"));
        colTelCliente.setCellValueFactory(new PropertyValueFactory<Entidade, Long>("telefone"));
        colCelCliente.setCellValueFactory(new PropertyValueFactory<Entidade, Long>("celular"));
        colEndCliente.setCellValueFactory(new PropertyValueFactory<Entidade, Endereco>("endereco"));

        tbClientes.getItems().setAll(list.isEmpty() ? new ArrayList<Entidade>() : list);
    }

    private void fillFornecedorGrid(List<Entidade> list) {
        colCodForn.setCellValueFactory(new PropertyValueFactory<Entidade, Long>("id"));
        colNomeForn.setCellValueFactory(new PropertyValueFactory<Entidade, String>("nome"));
        colTelForn.setCellValueFactory(new PropertyValueFactory<Entidade, Long>("telefone"));
        colCelForn.setCellValueFactory(new PropertyValueFactory<Entidade, Long>("celular"));
        colEndForn.setCellValueFactory(new PropertyValueFactory<Entidade, Endereco>("endereco"));

        tbFornecedores.getItems().setAll(list.isEmpty() ? new ArrayList<Entidade>() : list);
    }

    public void fill(Entidade ent) {
        if (ent != null) {
            if (ent.getEndereco() != null) {
                fillEndereco(ent.getEndereco());
            }
            txtCod.setText(ent.getId().toString());
            txtArea.setText(ent.getObs());
            txtCel.setText(ent.getCelular().toString());
            txtCpfCnpj.setText(ent.getCpfCnpj());
            txtEmail.setText(ent.getEmail());
            txtNome.setText(ent.getNome());
            txtTel.setText(ent.getTelefone().toString());
            if (ent.getId() != null) {
                fillPedidoGrid();
            }
            if (ent.getUltimoPedido() != null) {
                chkNew.setSelected(false);
                txtUltPed.setText(ent.getUltimoPedido().toString());
            } else if (gridPedidos.getItems() == null || gridPedidos.getItems().isEmpty()) {
                chkNew.setSelected(true);
            } else {
                List<Pedido> l = gridPedidos.getItems();
                Collections.sort(l);
                Pedido lastPed = l.get(l.size() - 1);
                txtUltPed.setText(lastPed.getCodigo().toString());
                chkNew.setSelected(false);
            }
            cmbEstCivil.setValue(ent.getEstadoCivil().name());
            cmbGenero.setValue(ent.getGenero().name());
        }
    }

    private void fillPedidoGrid() {
        colCodPed.setCellValueFactory(new PropertyValueFactory<Pedido, Long>("codigo"));
        if (entidade.getTipoEntidade().isCliente()) {
            colForn.setCellValueFactory(new PropertyValueFactory<Pedido, String>("fornecedor"));
        } else {
            colCli.setCellValueFactory(new PropertyValueFactory<Pedido, String>("cliente"));
        }
        colDtPed.setCellValueFactory(new PropertyValueFactory<Pedido, String>("dataCompra"));
        colFormaPed.setCellValueFactory(new PropertyValueFactory<Pedido, FormaPagamento>("formaPagamento"));
        colParcPed.setCellValueFactory(new PropertyValueFactory<Pedido, Integer>("nroParcelas"));
        colValPed.setCellValueFactory(new PropertyValueFactory<Pedido, Double>("valor"));

        gridPedidos.getItems().setAll(parsePedidoList());
    }

    private List<Pedido> parsePedidoList() {
        PedidoRest pedDAO = new PedidoRest();
        List<Pedido> listaEnt = pedDAO.findAllByEntidade(entidade.getTipoEntidade(), entidade.getId());

        if (!listaEnt.isEmpty()) {
            return listaEnt;
        } else {
            return new ArrayList<>();
        }
    }

    private void refreshPedidos() {
        final List<Pedido> items = gridPedidos.getItems();
        if (items == null || items.isEmpty()) {
            return;
        }

        final Pedido item = gridPedidos.getItems().get(0);
        items.remove(0);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                items.add(0, item);
            }
        });
    }

    private void fillCmbUF() {
        UFRest ufDao = new UFRest();
        List<UF> listaUF = ufDao.findAll();
        Collections.sort(listaUF);
        cmbEst.getItems().clear();
        cmbEst.getItems().addAll(listaUF.isEmpty() ? new ArrayList<UF>() : listaUF);
    }

    private void fillcmbMunicipio() {
        CidadeRest munDao = new CidadeRest();
        if (cmbEst.getValue() != null) {
            List<Cidade> listaCidade = munDao.findByUF(cmbEst.getValue().getCodIbge());
            Collections.sort(listaCidade);
            cmbMun.getItems().clear();
            cmbMun.getItems().addAll(listaCidade);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (entidade != null && entidade.getId() != null) {
            fill(entidade);
        } else if (tbClientes != null) {
            fillClienteGrid(entDao.findAll(TipoEntidade.Cliente));
        } else if (tbFornecedores != null) {
            fillFornecedorGrid(entDao.findAll(TipoEntidade.Fornecedor));
        } else {
            txtCodEnd.focusedProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    String cod = txtCodEnd.getText();
                    if (cod != null && !cod.isEmpty()) {
                        fillEndereco(endDao.find(Long.valueOf(cod)));
                    }
                }
            });

            txtCod.focusedProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    String cod = txtCod.getText();
                    Entidade ent;
                    if (!cod.isEmpty()) {
                        if (TipoEntidade.valueOf(cmbTpEntidade.getValue()).isCliente()) {
                            ent = entDao.findCli(Long.valueOf(cod));
                        } else {
                            ent = entDao.findForn(Long.valueOf(cod));
                        }
                        if (ent != null) {
                            fill(ent);
                        }
                    }
                }
            });

            cmbEst.focusedProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    fillcmbMunicipio();
                }
            });
            fillCmbUF();
        }
    }
}
