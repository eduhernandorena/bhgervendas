package br.com.controller;

import br.com.principal.Principal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author Eduardo Hernandorena
 */
public class LoginController {

    @FXML
    private Label txtError;
    @FXML
    private PasswordField txtPasswd;
    @FXML
    private TextField txtUser;

    @FXML
    protected void processLogin(ActionEvent event) {
        if (valida()) {
            if (!Principal.getInstance().userLogging(txtUser.getText(), txtPasswd.getText())) {
                txtError.setText("Usuário ou senha Inválidos: " + txtUser.getText());
            }
        }
    }

    protected boolean valida() {
        if (txtUser.getText().isEmpty()) {
            txtError.setText("Campo Usuário não poder ser nulo");
            txtError.requestFocus();
            return false;
        }
        if (txtPasswd.getText().isEmpty()) {
            txtError.setText("Campo Senha não poder ser nulo");
            txtPasswd.requestFocus();
            return false;
        }

        return true;
    }
}
