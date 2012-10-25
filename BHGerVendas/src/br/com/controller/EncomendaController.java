package br.com.controller;

import br.com.ejb.bean.Encomenda;
import br.com.principal.FXOptionPane;
import br.com.principal.Principal;
import br.com.ws.EncomendaRest;
import br.com.ws.PedidoRest;
import br.com.ws.ViagemRest;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EncomendaController implements Initializable {

    private static Principal p = Principal.getInstance();
    @FXML
    private Button btBuscaPedido;
    @FXML
    private Button btBuscaViagem;
    @FXML
    private Button btClear;
    @FXML
    private Button btSave;
    @FXML
    private Button btVoltar;
    @FXML
    private TextField txtCod;
    @FXML
    private TextField txtCodPedido;
    @FXML
    private TextField txtCodViagem;
    @FXML
    private TextField txtData;
    @FXML
    private TextField txtDataViagem;
    @FXML
    private TextField txtHoraViagem;
    @FXML
    private TextField txtLocalViagem;
    private static EncomendaRest encDAO = new EncomendaRest();
    private static PedidoRest pedDAO = new PedidoRest();
    private static ViagemRest viagemDAO = new ViagemRest();

    public void clearCad(ActionEvent event) {
        txtCod.setText(null);
        txtCodPedido.setText(null);
        txtCodViagem.setText(null);
        txtData.setText(null);
        txtDataViagem.setText(null);
        txtHoraViagem.setText(null);
        txtLocalViagem.setText(null);
    }

    public void saveCad(ActionEvent event) {
        if (valida()) {
            try {
                Encomenda enco = new Encomenda();
                enco.setDataCadastro(new SimpleDateFormat("dd/MM/yyyy").parse(txtData.getText()));
                enco.setPedido(pedDAO.find(Long.valueOf(txtCodPedido.getText())));
                enco.setViagem(viagemDAO.find(Long.valueOf(txtCodViagem.getText())));
                encDAO.create(enco);
            } catch (ParseException ex) {
                Logger.getLogger(EncomendaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Não foi possível salvar!");
        }

        p.gotoPrincipal();
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
        if (txtCodPedido.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O pedido deve ser preenchido!", "Campo Vazio!");
            txtCodPedido.requestFocus();
            return false;
        }

        if (txtCodViagem.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "A viagem deve ser preenchido!", "Campo Vazio!");
            txtCodViagem.requestFocus();
            return false;
        }

        if (txtLocalViagem.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O local deve ser preenchido!", "Campo Vazio!");
            txtLocalViagem.requestFocus();
            return false;
        }

        if (txtDataViagem.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "A Data da viagem deve ser preenchido!", "Campo Vazio!");
            txtDataViagem.requestFocus();
            return false;
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(true);
                sdf.parse(txtData.getText());
            } catch (ParseException ex) {
                FXOptionPane.showMessageDialog(null, "A Data da viagem deve ter o fomato DD/MM/AAAA!", "Campo Vazio!");
                txtData.requestFocus();
                return false;
            }
        }
        if (txtHoraViagem.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "A Hora da viagem deve ser preenchida!", "Campo Vazio!");
            txtHoraViagem.requestFocus();
            return false;
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                sdf.setLenient(true);
                sdf.parse(txtHoraViagem.getText());
            } catch (ParseException ex) {
                FXOptionPane.showMessageDialog(null, "A Hora da viagem deve ter o fomato HH:mm!", "Campo Vazio!");
                txtHoraViagem.requestFocus();
                return false;
            }
        }

        return true;
    }

    public void fill(Encomenda enc) {
        txtCod.setText(enc.getCodigo().toString());
        txtCodPedido.setText(enc.getPedido().getCodigo().toString());
        txtCodViagem.setText(enc.getViagem().getId().toString());
        txtData.setText(enc.getDataCadastro());
        txtDataViagem.setText(enc.getViagem().getData());
        txtHoraViagem.setText(enc.getViagem().getData());
        txtLocalViagem.setText(enc.getViagem().getLocal());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtCod.focusedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String cod = txtCod.getText();
                if (!cod.isEmpty()) {
                    fill(encDAO.find(Long.valueOf(cod)));
                }
            }
        });
    }
}
