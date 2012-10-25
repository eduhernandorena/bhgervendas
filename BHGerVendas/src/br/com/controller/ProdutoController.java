package br.com.controller;

import br.com.ejb.bean.Produto;
import br.com.principal.FXOptionPane;
import br.com.principal.Principal;
import br.com.ws.ProdutoRest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ProdutoController implements Initializable {

    private static Principal p = Principal.getInstance();
    @FXML
    private Button btBusca;
    @FXML
    private Button btClear;
    @FXML
    private Button btSave;
    @FXML
    private Button btVoltar;
    @FXML
    private ComboBox<String> cmbTpLucro;
    @FXML
    private TextField txtCod;
    @FXML
    private TextField txtDesc;
    @FXML
    private TextField txtNfe;
    @FXML
    private TextField txtRef;
    @FXML
    private TextField txtTam;
    @FXML
    private TextField txtValCompra;
    @FXML
    private TextField txtValVenda;
    @FXML
    private TextField txtLucro;
    private static ProdutoRest prodDAO = new ProdutoRest();

    public void clearCad(ActionEvent event) {
        txtCod.setText("");
        txtNfe.setText("");
        txtDesc.setText("");
        txtTam.setText("");
        txtRef.setText("");
        txtValCompra.setText("");
        txtValVenda.setText("");
        txtLucro.setText("");
        cmbTpLucro.setValue("%");
    }

    public void saveCad(ActionEvent event) {
        if (valida()) {
            Produto prod = new Produto();
            prod.setDescricao(txtDesc.getText());
            prod.setNroNota(Long.valueOf(txtNfe.getText()));
            Double pVenda = Double.valueOf(txtValVenda.getText());
            Double pCompra = Double.valueOf(txtValCompra.getText());
            prod.setPrecoCusto(pCompra);
            prod.setPrecoVenda(pVenda);
            prod.setLucro(pVenda - pCompra);
            prod.setReferencia(txtRef.getText());
            prod.setTamanho(txtTam.getText());
            prodDAO.create(prod);
            p.gotoPrincipal();
        } else {
            System.out.println("Não foi possível salvar!");
        }
    }

    public void voltar(ActionEvent event) {
        p.gotoPrincipal();
    }

    private boolean valida() {
        if (txtDesc.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "A descrição deve ser preenchida!", "Campo Vazio!");
            txtDesc.requestFocus();
            return false;
        }
        if (txtTam.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O tamanho deve ser preenchido!", "Campo Vazio!");
            txtTam.requestFocus();
            return false;
        }
        if (txtValCompra.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O preço de compra deve ser preenchido!", "Campo Vazio!");
            txtValCompra.requestFocus();
            return false;
        }
        if (txtValVenda.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O preço de venda deve ser preenchido!", "Campo Vazio!");
            txtValVenda.requestFocus();
            return false;
        }
        return true;
    }

    public void fill(Produto prod) {
        if (prod != null) {
            txtCod.setText(prod.getId().toString());
            //txtCodViagem.setText();
            txtDesc.setText(prod.getDescricao());
            txtNfe.setText(prod.getNroNota().toString());
            txtRef.setText(prod.getReferencia());
            txtTam.setText(prod.getTamanho());
            txtValCompra.setText(prod.getPrecoCusto().toString());
            txtValVenda.setText(prod.getPrecoVenda().toString());
            txtLucro.setText(prod.getLucro().toString());
            cmbTpLucro.setValue(prod.getPrecoVenda() - prod.getPrecoCusto() == prod.getLucro() ? "R$" : "%");
        }
    }

    private void calculoLucro() {
        if (!txtValCompra.getText().isEmpty() && !txtValVenda.getText().isEmpty()) {
            BigDecimal compra = BigDecimal.valueOf(Double.valueOf(txtValCompra.getText()));
            BigDecimal venda = BigDecimal.valueOf(Double.valueOf(txtValVenda.getText()));
            if (cmbTpLucro.getValue() != null && cmbTpLucro.getValue().equals("R$")) {
                txtLucro.setText("R$" + venda.subtract(compra).setScale(2, RoundingMode.HALF_EVEN).toPlainString());
            } else {
                BigDecimal lucro = venda.subtract(compra);
                BigDecimal valor = lucro.multiply(BigDecimal.valueOf(100d));
                valor = valor.divideToIntegralValue(compra);
                txtLucro.setText(valor.setScale(2, RoundingMode.HALF_EVEN).toPlainString() + "%");
            }
        } else if (!txtValCompra.getText().isEmpty() && !txtLucro.getText().isEmpty()) {
            BigDecimal compra = BigDecimal.valueOf(Double.valueOf(txtValCompra.getText()));
            BigDecimal lucro = BigDecimal.valueOf(Double.valueOf(txtLucro.getText()));
            if (cmbTpLucro.getValue() != null && cmbTpLucro.getValue().equals("R$")) {
                txtValVenda.setText(compra.add(lucro).setScale(2, RoundingMode.HALF_EVEN).toPlainString());
            } else {
                BigDecimal valor = compra.multiply(lucro);
                valor = valor.divideToIntegralValue(BigDecimal.valueOf(100d));
                txtValVenda.setText(valor.setScale(2, RoundingMode.HALF_EVEN).toPlainString());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtCod.focusedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String cod = txtCod.getText();
                if (!cod.isEmpty()) {
                    fill(prodDAO.find(Long.valueOf(cod)));
                }
            }
        });

        txtLucro.focusedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                calculoLucro();
            }
        });

        cmbTpLucro.focusedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                calculoLucro();
            }
        });

    }
}