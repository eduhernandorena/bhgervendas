package br.com.principal;

import br.com.controller.ClienteFornController;
import br.com.controller.EncomendaController;
import br.com.controller.PedidosController;
import br.com.controller.ProdutoController;
import br.com.controller.TelaPrincipalController;
import br.com.controller.ViagemController;
import br.com.ejb.bean.Encomenda;
import br.com.ejb.bean.Entidade;
import br.com.ejb.bean.Pedido;
import br.com.ejb.bean.Produto;
import br.com.ejb.bean.Usuario;
import br.com.ejb.bean.Viagem;
import br.com.ws.UsuarioRest;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Eduardo Hernandorena
 */
public class Principal extends Application {

    private Stage stage;
    private Usuario loggedUser;
    private static Principal instance;

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
        Usuario u = new UsuarioRest().findByNome(user);
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
//            final TelaPrincipalController tela = new TelaPrincipalController();
            stage.centerOnScreen();
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoClienteCad(Entidade ent) {
        try {
            replaceSceneContentCad("ClienteCad.fxml", "Cadastro de Clientes");
            ClienteFornController control = new ClienteFornController();
            control.fill(ent);
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoProdutosCad(Produto prod) {
        try {
            replaceSceneProdutoCad("ProdutoCad.fxml", "Cadastro de Produtos");
            ProdutoController control = new ProdutoController();
            control.fill(prod);
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoViagemCad(Viagem viag) {
        try {
            replaceSceneContentCad("ViagemCad.fxml", "Cadastro de Viagens");
            ViagemController control = new ViagemController();
            control.fill(viag);
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoFornecedorCad(Entidade ent) {
        try {
            replaceSceneContentCad("FornecedorCad.fxml", "Cadastro de Viagens");
            ClienteFornController control = new ClienteFornController();
            control.fill(ent);
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoPedidoCad(Pedido ped) {
        try {
            replaceSceneContentCad("PedidosCad.fxml", "Cadastro de Viagens");
            PedidosController control = new PedidosController();
            control.fill(ped);
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoEncomendaCad(Encomenda enc) {
        try {
            replaceSceneContentCad("EncomendaCad.fxml", "Cadastro de Viagens");
            EncomendaController control = new EncomendaController();
            control.fill(enc);
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