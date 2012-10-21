package br.com.controller;

import br.com.ejb.bean.Encomenda;
import br.com.principal.Principal;
import br.com.ws.EncomendaRest;
import br.com.ws.PedidoRest;
import br.com.ws.ViagemRest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EncomendaController {

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
}
