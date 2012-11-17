package br.com.controller;

import br.com.ejb.bean.Entidade;
import br.com.ejb.bean.Pedido;
import br.com.ejb.bean.Viagem;
import br.com.ejb.bean.enumeration.FormaPagamento;
import br.com.principal.FXOptionPane;
import br.com.principal.Principal;
import br.com.ws.ViagemRest;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViagemController implements Initializable {

    private static Principal p = Principal.getInstance();
//    @FXML
//    private Button btEncomenda;
    @FXML
    private TextField txtCod;
//    @FXML
//    private ChoiceBox<String> cmbStatus;
    @FXML
    private TextField txtData;
    @FXML
    private TextField txtGuia;
    @FXML
    private TextField txtHora;
    @FXML
    private TextField txtLocal;
    @FXML
    private TextField txtQtde;
    @FXML
    private TextField txtValor;
    @FXML
    private TableView<Pedido> gridPed;
    @FXML
    private TableColumn<Pedido, FormaPagamento> colFormPed;
    @FXML
    private TableColumn<Pedido, Integer> colParcPed;
    @FXML
    private TableColumn<Pedido, Long> colCodPed;
    @FXML
    private TableColumn<Pedido, Entidade> colCliPed;
    @FXML
    private TableColumn<Pedido, Double> colValPed;
//    @FXML
//    private TableView<Encomenda> gridEncomenda;
//    @FXML
//    private TableColumn<Encomenda, Entidade> colCliEnc;
//    @FXML
//    private TableColumn<Encomenda, Long> colCodEnc;
//    @FXML
//    private TableColumn<Encomenda, Date> colDtEnc;
//    @FXML
//    private TableColumn<Encomenda, Pedido> colPedEnc;
    private ViagemRest viagDAO = new ViagemRest();
    private Viagem viagem;

    public ViagemController(Viagem viagem) {
        this.viagem = viagem;
    }

    public ViagemController() {
    }

    public void clearCad(ActionEvent event) {
        txtCod.setText(null);
        txtData.setText(null);
        txtGuia.setText(null);
        txtHora.setText(null);
        txtLocal.setText(null);
        txtQtde.setText(null);
        txtValor.setText(null);
//        cmbStatus.setValue(null);
    }

    public void saveCad(ActionEvent event) {
        if (valida()) {
            viagem = new Viagem();
            try {
                viagem.setDataHora(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(txtData.getText() + " " + txtHora.getText()));
            } catch (ParseException ex) {
                Logger.getLogger(ViagemController.class.getName()).log(Level.SEVERE, null, ex);
            }
//            viagem.setEncomendas(gridEncomenda.getItems());
            viagem.setPedidos(gridPed.getItems());
            viagem.setGuia(txtGuia.getText());
            viagem.setLocal(txtLocal.getText());
            viagem.setValor(Double.valueOf(txtValor.getText()));
            viagem.setQtdeProdutos(Integer.valueOf(txtQtde.getText()));
            viagDAO.create(viagem);
            p.gotoPrincipal();
        } else {
            System.out.println("Não foi possível salvar!");
        }
    }

    public void voltar(ActionEvent event) {
        p.gotoPrincipal();
    }

    private boolean valida() {
        if (txtData.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "A Data deve ser preenchido!", "Campo Vazio!");
            txtData.requestFocus();
            return false;
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(true);
                sdf.parse(txtData.getText());
            } catch (ParseException ex) {
                FXOptionPane.showMessageDialog(null, "A Data deve ter o fomato DD/MM/AAAA!", "Campo Vazio!");
                txtData.requestFocus();
                return false;
            }
        }
        if (txtHora.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "A Hora deve ser preenchida!", "Campo Vazio!");
            txtHora.requestFocus();
            return false;
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                sdf.setLenient(true);
                sdf.parse(txtHora.getText());
            } catch (ParseException ex) {
                FXOptionPane.showMessageDialog(null, "A Hora deve ter o fomato HH:mm!", "Campo Vazio!");
                txtHora.requestFocus();
                return false;
            }
        }

        if (txtLocal.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O local deve ser preenchido!", "Campo Vazio!");
            txtLocal.requestFocus();
            return false;
        }

        if (txtGuia.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "A guia deve ser preenchida!", "Campo Vazio!");
            txtGuia.requestFocus();
            return false;
        }

        return true;
    }

    public void fill(Viagem viag) {
        if (viag != null) {
            txtCod.setText(viag.getId().toString());
            txtData.setText(viag.getData());
            txtGuia.setText(viag.getGuia());
            txtHora.setText(viag.getHora());
            txtLocal.setText(viag.getLocal());
            txtQtde.setText(viag.getQtdeProdutos().toString());
            txtValor.setText(viag.getValor().toString());
            fillPedidoGrid(viag.getPedidos());
//            fillEncomendaGrid(viag.getEncomendas());
        }
    }

    private void fillPedidoGrid(List<Pedido> list) {
        colCodPed.setCellValueFactory(new PropertyValueFactory<Pedido, Long>("codigo"));
        colFormPed.setCellValueFactory(new PropertyValueFactory<Pedido, FormaPagamento>("formaPagamento"));
        colCliPed.setCellValueFactory(new PropertyValueFactory<Pedido, Entidade>("cliente"));
        colParcPed.setCellValueFactory(new PropertyValueFactory<Pedido, Integer>("nroParcelas"));
        colValPed.setCellValueFactory(new PropertyValueFactory<Pedido, Double>("valor"));

        gridPed.getItems().setAll(list.isEmpty() ? new ArrayList<Pedido>() : list);
    }

    private void refreshPedidos() {
        final List<Pedido> items = gridPed.getItems();
        if (items == null || items.isEmpty()) {
            return;
        }

        final Pedido item = gridPed.getItems().get(0);
        items.remove(0);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                items.add(0, item);
            }
        });
    }

//    private void fillEncomendaGrid(List<Encomenda> list) {
//        colCodEnc.setCellValueFactory(new PropertyValueFactory<Encomenda, Long>("codigo"));
//        colDtEnc.setCellValueFactory(new PropertyValueFactory<Encomenda, Date>("dataCadastro"));
//        colCliEnc.setCellValueFactory(new PropertyValueFactory<Encomenda, Entidade>("cliente"));
//        colPedEnc.setCellValueFactory(new PropertyValueFactory<Encomenda, Pedido>("pedido"));
//
//        gridEncomenda.getItems().setAll(list.isEmpty() ? new ArrayList<Encomenda>() : list);
//    }
//
//    private void refreshEncomenda() {
//        final List<Encomenda> items = gridEncomenda.getItems();
//        if (items == null || items.isEmpty()) {
//            return;
//        }
//
//        final Encomenda item = gridEncomenda.getItems().get(0);
//        items.remove(0);
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                items.add(0, item);
//            }
//        });
//    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fill(viagem);
        txtCod.focusedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String cod = txtCod.getText();
                if (!cod.isEmpty()) {
                    fill(viagDAO.find(Long.valueOf(cod)));
                }
            }
        });
    }
}
