package br.com.controller;

import br.com.ejb.bean.Usuario;
import br.com.principal.FXOptionPane;
import br.com.principal.Principal;
import br.com.ws.UsuarioRest;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UsuarioController implements Initializable {

    @FXML
    private Button btClear;
    @FXML
    private Button btSave;
    @FXML
    private TextField txtSenha;
    @FXML
    private TextField txtSenhaConfirm;
    @FXML
    private TextField txtUser;
    private static UsuarioRest userDAO = new UsuarioRest();
    private static Stage st = null;

    public UsuarioController(Stage stage) {
        st = stage;
    }

    public void onClickClear(ActionEvent event) {
        txtUser.setText(null);
        txtSenha.setText(null);
        txtSenhaConfirm.setText(null);
    }

    public void onClickSave(ActionEvent event) {
        if (valida()) {
            Usuario user = new Usuario();
            user.setNome(txtUser.getText());
            user.setSenha(txtSenha.getText());

            user = userDAO.create(user);
            if (user.getId() != null) {
                FXOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!", "Usuário Cadastrado!");
                Principal.getInstance().start(st);
            } else {
                FXOptionPane.showMessageDialog(null, "Não foi possível cadastrar o usuário!", "Usuário Não Cadastrado!");
            }
        }
    }

    private boolean valida() {
        if (txtUser.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O nome de usuário deve ser preenchido!", "Campo Vazio!");
            txtUser.requestFocus();
            return false;
        }

        if (txtSenha.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "A senha deve ser preenchida!", "Campo Vazio!");
            txtSenha.requestFocus();
            return false;
        }

        if (txtSenhaConfirm.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "A confirmação de senha deve ser preenchida!", "Campo Vazio!");
            txtSenhaConfirm.requestFocus();
            return false;
        }

        if (!txtSenha.getText().equals(txtSenhaConfirm.getText())) {
            FXOptionPane.showMessageDialog(null, "A confirmação de senha deve ser igual a senha!", "Senhas diferentes!");
            txtSenhaConfirm.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    }
}
