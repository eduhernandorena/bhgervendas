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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Eduardo Hernandorena
 */
public class ClienteFornController implements Initializable {

    private static Principal p = Principal.getInstance();
    @FXML
    private Button btSalvar;
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
    private TableColumn<Pedido, Long> colCodViag;
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
    private static EntidadeRest entDao = new EntidadeRest();
    private static EnderecoRest endDao = new EnderecoRest();
    private static CidadeRest cidDao = new CidadeRest();
    private static Entidade entidade = null;

    public ClienteFornController(Entidade ent) {
        entidade = ent;
    }

    public ClienteFornController() {
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
        chkNew.setSelected(false);
        gridPedidos.getItems().clear();
    }

    @FXML
    public void saveCad(ActionEvent event) {
        if (valida()) {
            Entidade ent = new Entidade();
            Long id = !txtCod.getText().isEmpty() ? Long.valueOf(txtCod.getText()) : null;
            ent.setId(id);
            Long cel = !txtCel.getText().isEmpty() ? Long.valueOf(txtCel.getText()) : null;
            ent.setCelular(cel);
            ent.setCpfCnpj(txtCpfCnpj.getText());
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
        cmbMun.setValue(end.getCidade());
        cmbEst.setValue(end.getCidade().getUf());
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

        if (cmbEstCivil.getSelectionModel().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O Estado Civil deve ser selecionado!", "Campo Vazio!");
            cmbEstCivil.requestFocus();
            return false;
        }

        if (cmbGenero.getSelectionModel().isEmpty()) {
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

        if (cmbTpEnd.getSelectionModel().isEmpty()) {
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

        if (cmbEst.getSelectionModel().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O Estado deve ser selecionado!", "Campo Vazio!");
            cmbEst.requestFocus();
            return false;
        }

        if (cmbMun.getSelectionModel().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "A cidade deve ser selecionada!", "Campo Vazio!");
            cmbMun.requestFocus();
            return false;
        }

        return true;
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
            if (ent.getUltimoPedido() != null) {
                chkNew.setSelected(false);
                txtUltPed.setText(ent.getUltimoPedido().toString());
            } else {
                chkNew.setSelected(true);
            }
            cmbEstCivil.setValue(ent.getEstadoCivil().name());
            cmbGenero.setValue(ent.getGenero().name());
            if (ent.getId() != null) {
                fillPedidoGrid();
            }
        }
    }

    private void fillPedidoGrid() {
        colCodPed.setCellValueFactory(new PropertyValueFactory<Pedido, Long>("id"));
        colCodViag.setCellValueFactory(new PropertyValueFactory<Pedido, Long>("Nome"));
        colDtPed.setCellValueFactory(new PropertyValueFactory<Pedido, String>("Telefone"));
        colFormaPed.setCellValueFactory(new PropertyValueFactory<Pedido, FormaPagamento>("Celular"));
        colParcPed.setCellValueFactory(new PropertyValueFactory<Pedido, Integer>("Endereco"));
        colValPed.setCellValueFactory(new PropertyValueFactory<Pedido, Double>("valor"));

        gridPedidos.getItems().setAll(parsePedidoList());
    }

    private List<Pedido> parsePedidoList() {
        PedidoRest pedDAO = new PedidoRest();
        List<Pedido> listaEnt = pedDAO.findAllByEntidade(TipoEntidade.valueOf(cmbTpEntidade.getValue()), Long.valueOf(txtCod.getText()));

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
        fill(entidade);

        txtCodEnd.focusedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String cod = txtCodEnd.getText();
                if (!cod.isEmpty()) {
                    fillEndereco(endDao.find(Long.valueOf(cod)));
                }
            }
        });

        txtCod.focusedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String cod = txtCod.getText();
                if (!cod.isEmpty()) {
                    Entidade ent = entDao.find(Long.valueOf(cod));
                    if (ent.getTipoEntidade().equals(TipoEntidade.valueOf(cmbTpEntidade.getValue()))) {
                        fill(ent);
                    } else {
                        FXOptionPane.showMessageDialog(null, "Enntidade difere do tipo do cadastro.", "Entidade Incompativel!");
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