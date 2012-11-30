package br.com.controller;

import br.com.ejb.bean.Usuario;
import br.com.principal.ConfInicial;
import br.com.principal.Cripto;
import br.com.principal.FXOptionPane;
import br.com.principal.Principal;
import br.com.ws.UsuarioRest;
import com.sun.jersey.api.client.UniformInterfaceException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UsuarioController implements Initializable {

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
    private PasswordField txtSenha;
    @FXML
    private PasswordField txtSenhaConfirm;
    @FXML
    private TextField txtUser;
    @FXML
    private TextField txtIp;
    private static UsuarioRest userDAO = new UsuarioRest();
    private static Stage st = null;
    private static Usuario usuario = null;
    private static Principal p = Principal.getInstance();

    public UsuarioController(Stage stage, Usuario u) {
        st = stage;
        usuario = u;
    }

    public UsuarioController() {
    }

    public void onClickClear(ActionEvent event) {
        txtUser.setText("");
        txtSenha.setText("");
        txtSenhaConfirm.setText("");
        txtIp.setText("");
    }

    public void onClickSave(ActionEvent event) {
        if (valida()) {
            try {
                new ConfInicial().gravar(txtIp.getText());
                Usuario user = usuario != null ? usuario : new Usuario();
                user.setNome(txtUser.getText());
                user.setSenha(Cripto.encode(txtSenha.getText()));
                user = userDAO.create(user);
                if (user.getId() != null) {
                    FXOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!", "Usuário Cadastrado!");
                    p.start(st);
                } else {
                    FXOptionPane.showMessageDialog(null, "Não foi possível cadastrar o usuário!", "Usuário Não Cadastrado!");
                }
            } catch (UniformInterfaceException ex) {
                System.out.println(ex.getMessage());
                FXOptionPane.showMessageDialog(null, "Não foi possível conectar ao banco de dados\nverifique sua conexão e o IP do banco!", "Erro Banco de dados!");
                System.exit(0);
            }
        }
    }

    private boolean valida() {
        if (txtUser.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O nome de usuário deve ser preenchido!", "Campo Vazio!");
            txtUser.requestFocus();
            return false;
        }

        if (txtIp.getText().isEmpty()) {
            FXOptionPane.showMessageDialog(null, "O endereço de IP deve ser preenchido!", "Campo Vazio!");
            txtIp.requestFocus();
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
    public void initialize(URL url, ResourceBundle rb) {
        if (usuario != null) {
            txtUser.setText(usuario.getNome());
            txtSenha.setText(Cripto.decode(usuario.getSenha()));
            txtSenhaConfirm.setText(Cripto.decode(usuario.getSenha()));
            txtIp.setText(new ConfInicial().getIp());
        }
    }
}
