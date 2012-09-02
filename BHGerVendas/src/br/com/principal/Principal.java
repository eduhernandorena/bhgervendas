package br.com.principal;

import br.com.controller.TelaPrincipalController;
import br.com.ejb.bean.Usuario;
import br.com.ejb.ejb.UsuarioDAORemote;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.ejb.EJB;

/**
 *
 * @author Eduardo Hernandorena
 */
public class Principal extends Application {

    private Stage stage;
    private Usuario loggedUser;
    private static Principal instance;
    @EJB
    private UsuarioDAORemote facade;

    public Principal() {
        instance = this;
    }

    public static Principal getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            stage.centerOnScreen();
            gotoLogin();
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean userLogging(String username, String password) {
        if (validate(username, password)) {
            gotoPrincipal();
            return true;
        } else {
            return false;
        }
    }

    private boolean validate(String user, String senha) {
        boolean userValid = false;
        Usuario u = facade.find(user);
        if (u != null) {
            if (u.getSenha() == null ? senha == null : u.getSenha().equals(senha)) {
                userValid = true;
                loggedUser = u;
            } else {
                loggedUser = null;
            }
        }

        return userValid;
    }

    public Usuario getLoggedUser() {
        return loggedUser;
    }

    public void userLogout() {
        loggedUser = null;
        gotoLogin();
    }

    public void gotoLogin() {
        try {
            Parent page = (Parent) FXMLLoader.load(Principal.class.getResource("../telas/Login.fxml"), null, new JavaFXBuilderFactory());
            Scene scene = new Scene(page, 293, 186);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("BHGerVendas -- Login");
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoPrincipal() {
        try {
            replaceSceneContent("TelaPrincipal.fxml", "Sistema Gerenciador de Compra & Venda");
            TelaPrincipalController tela = new TelaPrincipalController();
            tela.init();
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoClienteCad() {
        try {
            replaceSceneContentCad("ClienteCad.fxml", "Cadastro de Clientes");
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoProdutosCad() {
        try {
            replaceSceneProdutoCad("ProdutoCad.fxml", "Cadastro de Produtos");
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoViagemCad() {
        try {
            replaceSceneContentCad("ViagemCad.fxml", "Cadastro de Viagens");
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoFornecedorCad() {
        try {
            replaceSceneContentCad("FornecedorCad.fxml", "Cadastro de Viagens");
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoPedidoCad() {
        try {
            replaceSceneContentCad("PedidosCad.fxml", "Cadastro de Viagens");
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoEncomendaCad() {
        try {
            replaceSceneContentCad("EncomendaCad.fxml", "Cadastro de Viagens");
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void replaceSceneContent(String fxml, String title) throws Exception {
        Parent page = (Parent) FXMLLoader.load(Principal.class.getResource("../telas/" + fxml), null, new JavaFXBuilderFactory());
        Scene scene = new Scene(page, 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("BHGerVendas -- " + title);
    }

    private void replaceSceneContentCad(String fxml, String title) throws Exception {
        Parent page = (Parent) FXMLLoader.load(Principal.class.getResource("../telas/" + fxml), null, new JavaFXBuilderFactory());
        Scene scene = new Scene(page, 754, 555);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("BHGerVendas -- " + title);
    }

    private void replaceSceneProdutoCad(String fxml, String title) throws Exception {
        Parent page = (Parent) FXMLLoader.load(Principal.class.getResource("../telas/" + fxml), null, new JavaFXBuilderFactory());
        Scene scene = new Scene(page, 474, 175);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("BHGerVendas -- " + title);
    }
}