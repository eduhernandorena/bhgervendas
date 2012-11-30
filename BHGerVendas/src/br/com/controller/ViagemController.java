package br.com.controller;

import br.com.ejb.bean.Entidade;
import br.com.ejb.bean.Pedido;
import br.com.ejb.bean.Produto;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViagemController implements Initializable {

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
    private TextField txtCod;
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
    private TableColumn<Pedido, Entidade> colFornPed;
    @FXML
    private TableColumn<Pedido, Double> colValPed;
    private ViagemRest viagDAO = new ViagemRest();
    private static Viagem viagem;

    public ViagemController(Viagem vi) {
        viagem = vi;
    }

    public ViagemController() {
    }

    public void clearCad(ActionEvent event) {
        txtCod.setText("");
        txtData.setText("");
        txtGuia.setText("");
        txtHora.setText("");
        txtLocal.setText("");
        txtQtde.setText("");
        txtValor.setText("");
    }

    public void saveCad(ActionEvent event) {
        if (valida()) {
            Viagem vi = viagem != null ? viagem : new Viagem();
            vi.setData(txtData.getText());
            vi.setHora(txtHora.getText());
            vi.setPedidos(gridPed.getItems());
            vi.setGuia(txtGuia.getText());
            vi.setLocal(txtLocal.getText());
            if (!vi.getPedidos().isEmpty()) {
                vi.setValor(Double.valueOf(txtValor.getText()));
                vi.setQtdeProdutos(Integer.valueOf(txtQtde.getText()));
            }
            viagDAO.create(vi);
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
            txtCod.setText(viag.getId() != null ? viag.getId().toString() : null);
            txtData.setText(viag.getData());
            txtGuia.setText(viag.getGuia());
            txtHora.setText(viag.getHora());
            txtLocal.setText(viag.getLocal());
            fillPedidoGrid(viag.getPedidos());
            txtQtde.setText(viag.getQtdeProdutos().toString());
            txtValor.setText(viag.getValor().toString());
        }
    }

    private void fillPedidoGrid(List<Pedido> list) {
        colCodPed.setCellValueFactory(new PropertyValueFactory<Pedido, Long>("codigo"));
        colFormPed.setCellValueFactory(new PropertyValueFactory<Pedido, FormaPagamento>("formaPagamento"));
        colFornPed.setCellValueFactory(new PropertyValueFactory<Pedido, Entidade>("fornecedor"));
        colParcPed.setCellValueFactory(new PropertyValueFactory<Pedido, Integer>("nroParcelas"));
        colValPed.setCellValueFactory(new PropertyValueFactory<Pedido, Double>("valor"));

        gridPed.getItems().setAll(list.isEmpty() ? new ArrayList<Pedido>() : list);
    }

    private void refreshValor() {
        if (!gridPed.getItems().isEmpty()) {
            Double valor = 0.0;
            Integer prods = 0;
            for (Pedido ped : gridPed.getItems()) {
                prods += ped.getProdutos().size();
                for (Produto prod : ped.getProdutos()) {
                    valor += prod.getPrecoCusto();
                }
            }
            txtValor.setText(valor.toString());
            txtQtde.setText(prods.toString());
            viagem.setValor(valor);
            viagem.setQtdeProdutos(prods);
        } else {
            txtValor.setText("");
            txtQtde.setText("");
            viagem.setValor(null);
            viagem.setQtdeProdutos(null);
        }
    }

    public void buscaPedidos(ActionEvent event) {
        fillBuscaPedido();
        p.gotoFindPedido(viagem);
    }

    private void fillBuscaPedido() {
        if (viagem == null) {
            viagem = new Viagem();
        }
        viagem.setData(txtData.getText());
        viagem.setHora(txtHora.getText());
        viagem.setGuia(txtGuia.getText());
        viagem.setLocal(txtLocal.getText());
        if (txtQtde.getText() != null && !txtQtde.getText().isEmpty()) {
            viagem.setQtdeProdutos(Integer.valueOf(txtQtde.getText()));
        }
        if (txtValor.getText() != null && !txtValor.getText().isEmpty()) {
            viagem.setValor(Double.valueOf(txtValor.getText()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (viagem != null) {
            if (viagem.getId() != null) {
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
            } else if (!viagem.getPedidos().isEmpty()) {
                if (viagem.getGuia() != null) {
                    txtGuia.setText(viagem.getGuia());
                }
                if (viagem.getLocal() != null) {
                    txtLocal.setText(viagem.getLocal());
                }
                if (viagem.getData() != null) {
                    txtData.setText(viagem.getData());
                }
                if (viagem.getHora() != null) {
                    txtHora.setText(viagem.getHora());
                }
                if (viagem.getValor() != null) {
                    txtValor.setText(Double.valueOf(viagem.getValor()).toString());
                }
                if (viagem.getQtdeProdutos() != null) {
                    txtQtde.setText(Integer.valueOf(viagem.getQtdeProdutos()).toString());
                }
                fillPedidoGrid(viagem.getPedidos());
                refreshValor();
            }
        }
    }
}
