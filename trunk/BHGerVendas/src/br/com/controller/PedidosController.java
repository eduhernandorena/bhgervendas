package br.com.controller;

import br.com.ejb.bean.Pedido;
import br.com.ejb.bean.Produto;
import br.com.ejb.bean.Viagem;
import br.com.ejb.bean.enumeration.FormaPagamento;
import br.com.principal.Principal;
import br.com.ws.EntidadeRest;
import br.com.ws.PedidoRest;
import br.com.ws.ViagemRest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PedidosController {

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
    private PedidoRest pedDAO = new PedidoRest();
    private EntidadeRest entDAO = new EntidadeRest();
    private ViagemRest viDAO = new ViagemRest();

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
            ped.setCliente(entDAO.find(txtCodCliente.getText()));
            try {
                ped.setDataCompra(new SimpleDateFormat("dd/MM/yyyy").parse(txtDta.getText()));
            } catch (ParseException ex) {
                Logger.getLogger(PedidosController.class.getName()).log(Level.SEVERE, null, ex);
            }
            ped.setFormaPagamento(FormaPagamento.valueOf(cmbFormaPag.getValue()));
            ped.setFornecedor(entDAO.find(txtCodForn.getText()));
            ped.setNroParcelas(Integer.valueOf(txtNParcelas.getText()));
            ped.setProdutos(gridProd.getItems());
            ped.setValor(Double.valueOf(txtValor.getText()));
            ped.setObsPagamento(txtObsPag.getText());
            if (txtCodViagem.getText() != null && !txtCodViagem.getText().trim().isEmpty()) {
                ped.setViagem(viDAO.find(txtCodViagem.getText()));
            } else {
                ped.setViagem(null);
            }
            pedDAO.create(ped);
        } else {
            System.out.println("Não foi possível salvar!");
        }

        p.gotoPrincipal();
    }

    public void voltar(ActionEvent event) {
        p.gotoPrincipal();
    }

    private boolean valida() {
        return false;
    }

    public void fill(Pedido ped) {
        if (ped != null) {
            txtCod.setText(ped.getCodigo().toString());
            cmbFormaPag.setValue(ped.getFormaPagamento().name());
            txtCodCliente.setText(ped.getCliente() != null ? ped.getCliente().getId().toString() : "");
            txtCliente.setText(ped.getCliente() != null ? ped.getCliente().getNome() : "");
            txtCodForn.setText(ped.getFornecedor() != null ? ped.getFornecedor().getId().toString() : "");
            txtFornecedor.setText(ped.getFornecedor() != null ? ped.getFornecedor().getNome() : "");
            txtDta.setText(ped.getDataCompra());
            txtNParcelas.setText(ped.getNroParcelas().toString());
            txtObsPag.setText(ped.getObsPagamento());
            String valParc = String.valueOf(ped.getValor() / Double.valueOf(ped.getNroParcelas().doubleValue()));
            txtValParcelas.setText(valParc);
            txtValor.setText(ped.getValor().toString());
            txtCodViagem.setText(ped.getViagem().getId().toString());
        }
    }
}