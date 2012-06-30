package principal;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Eduardo Hernandorena
 */
public class Login implements Initializable {

    @FXML
    private static TextField login;
    @FXML
    private static PasswordField password;
    @FXML
    private AnchorPane arch;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    protected static boolean valida() {
        if (login.getText().isEmpty()) {
            return false;
        }
        if (password.getText().isEmpty()) {
            return false;
        }

        return logar(login.getText(), password.getText());
    }

    private static boolean logar(String user, String senha) {
        if (user.equalsIgnoreCase("eduardo")) {
            if (senha.equals("sEnhA")) {
                return true;
            } else {
                return false;
            }
        } else {
            
            return true;
        }
    }
}
