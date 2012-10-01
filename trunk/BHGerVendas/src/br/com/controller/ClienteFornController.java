package br.com.controller;

import br.com.ejb.bean.Endereco;
import br.com.ejb.bean.Entidade;
import br.com.ejb.bean.Pedido;
import br.com.ejb.bean.enumeration.EstadoCivil;
import br.com.ejb.bean.enumeration.Genero;
import br.com.ejb.bean.enumeration.TipoEndereco;
import br.com.ejb.bean.enumeration.TipoEntidade;
import br.com.principal.Principal;
import br.com.ws.CidadeRest;
import br.com.ws.EnderecoRest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author Eduardo Hernandorena
 */
public class ClienteFornController {

    private static Principal p = Principal.getInstance();
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
    @FXML
    private TableView<Pedido> gridPedidos;
    private static EnderecoRest endDao = new EnderecoRest();
    private static CidadeRest cidDao = new CidadeRest();

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
        gridPedidos.setItems(null);
    }

    @FXML
    public void saveCad(ActionEvent event) {
        if (valida()) {
            Entidade ent = new Entidade();
            ent.setCelular(Long.valueOf(txtCel.getText()));
            ent.setCpfCnpj(txtCpfCnpj.getText());
            ent.setEmail(txtEmail.getText());
            Endereco end = endDao.find(txtCodEnd.getText());
            if (end != null) {
                ent.setEndereco(end);
            } else {
                end = new Endereco();
                end.setRua(txtLogradouro.getText());
                end.setNumero(Integer.valueOf(txtNum.getText()));
                end.setComplemento(txtCpl.getText());
                end.setBairro(txtBairro.getText());
                end.setCep(txtCep.getText());
                end.setTipoEndereco(TipoEndereco.valueOf(cmbTpEnd.getValue()));
                end.setCidade(cidDao.find(cmbMun.getValue()));
                ent.setEndereco(end);
            }
            ent.setEstadoCivil(EstadoCivil.valueOf(cmbEstCivil.getValue()));
            ent.setGenero(Genero.valueOf(cmbGenero.getValue()));
            ent.setNome(txtNome.getText());
            ent.setObs(txtArea.getText());
            ent.setPedidos(gridPedidos.getItems());
            ent.setTelefone(Long.valueOf(txtTel.getText()));
            ent.setTipoEntidade(TipoEntidade.valueOf(cmbTpEntidade.getValue()));
            ent.setUltimoPedido(Long.valueOf(txtUltPed.getText()));
        } else {
            System.out.println("Não foi possível salvar!");
        }
        p.gotoPrincipal();
    }

    private void fillEndereco(Endereco end) {
        txtCodEnd.setText(end.getId().toString());
        txtLogradouro.setText(end.getRua());
        txtLogradouro.setDisable(true);
        txtBairro.setText(end.getBairro());
        txtBairro.setDisable(true);
        txtCep.setText(end.getCep());
        txtCep.setDisable(true);
        txtNum.setText(end.getNumero().toString());
        txtNum.setDisable(true);
        txtCpl.setText(end.getComplemento());
        txtCpl.setDisable(true);
        cmbTpEnd.setValue(end.getTipoEndereco().name());
        cmbTpEnd.setDisable(true);
        cmbMun.setValue(end.getCidade().getDescricao());
        cmbMun.setDisable(true);
        cmbEst.setValue(end.getCidade().getUf().getSigla());
        cmbEst.setDisable(true);
    }

    @FXML
    public void voltar(ActionEvent event) {
        p.gotoPrincipal();
    }

    private boolean valida() {
        return false;
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
            txtUltPed.setText(ent.getUltimoPedido().toString());
            cmbEstCivil.setValue(ent.getEstadoCivil().name());
            cmbGenero.setValue(ent.getGenero().name());
            chkNew.setSelected(false);
            //gridPedidos.setItems(null);
        }
    }
}/**
 * txtCodEnd.focusedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                fillEndereco(endDao.find(txtCodEnd.getText()));
            }
        });
 */