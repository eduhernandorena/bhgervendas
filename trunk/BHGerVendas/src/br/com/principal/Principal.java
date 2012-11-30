package br.com.principal;

import br.com.controller.ClienteFornController;
import br.com.controller.PedidosController;
import br.com.controller.ProdutoController;
import br.com.controller.UsuarioController;
import br.com.controller.ViagemController;
import br.com.ejb.bean.Entidade;
import br.com.ejb.bean.Pedido;
import br.com.ejb.bean.Produto;
import br.com.ejb.bean.Usuario;
import br.com.ejb.bean.Viagem;
import br.com.ejb.bean.enumeration.TipoEntidade;
import br.com.ws.UsuarioRest;
import com.sun.jersey.api.client.UniformInterfaceException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Eduardo Hernandorena
 */
public class Principal extends Application {

    private Stage stage;
    private static Usuario loggedUser;
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
            if (!new UsuarioRest().isEmpty()) {
                gotoLogin();
            } else {
                FXOptionPane.showMessageDialog(null, "Não há usuário cadastrado!", "Sem Usuario!");
                gotoCadUser(null);
            }
            primaryStage.show();
        } catch (UniformInterfaceException ex) {
            System.out.println(ex.getMessage());
            FXOptionPane.showMessageDialog(null, "Não foi possível conectar ao banco de dados\nverifique sua conexão e endereco IP no arquivo de configuracoes!", "Erro Banco de dados!");
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
            if (u.getSenha() == null ? senha == null : Cripto.decode(u.getSenha()).equals(senha)) {
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

    public void gotoFindProdutos(Pedido ped) {
        try {
            new ProdutoController(ped);
            Parent page = (Parent) FXMLLoader.load(Principal.class.getResource("../telas/FindProdutos.fxml"), null, new JavaFXBuilderFactory());
            Scene scene = new Scene(page, 725, 384);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("BHGerVendas -- Busca Produtos");
            stage.centerOnScreen();
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoFindCliente(Pedido ped, TipoEntidade ent) {
        try {
            new ClienteFornController(ped);
            Parent page;
            if (ent.isCliente()) {
                page = (Parent) FXMLLoader.load(Principal.class.getResource("../telas/FindCliente.fxml"), null, new JavaFXBuilderFactory());
                stage.setTitle("BHGerVendas -- Busca Cliente");
            } else {
                page = (Parent) FXMLLoader.load(Principal.class.getResource("../telas/FindForn.fxml"), null, new JavaFXBuilderFactory());
                stage.setTitle("BHGerVendas -- Busca Fornecedor");
            }
            Scene scene = new Scene(page, 725, 384);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoFindPedido(Viagem v) {
        try {
            new PedidosController(null, v);
            Parent page = (Parent) FXMLLoader.load(Principal.class.getResource("../telas/FindPedido.fxml"), null, new JavaFXBuilderFactory());
            Scene scene = new Scene(page, 725, 384);
            stage.setTitle("BHGerVendas -- Busca Pedido");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoLogin() {
        try {
            Parent page = (Parent) FXMLLoader.load(Principal.class.getResource("../telas/Login.fxml"), null, new JavaFXBuilderFactory());
            Scene scene = new Scene(page, 293, 186);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("BHGerVendas -- Login");
            stage.centerOnScreen();
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoCadUser(Usuario user) {
        try {
            new UsuarioController(stage, user);
            Parent page = (Parent) FXMLLoader.load(Principal.class.getResource("../telas/UsuarioCad.fxml"), null, new JavaFXBuilderFactory());
            Scene scene = new Scene(page, 296, 115);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("BHGerVendas -- Config");
            stage.centerOnScreen();
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoPrincipal() {
        try {
            replaceSceneContent("TelaPrincipal.fxml", "Sistema Gerenciador de Compra & Venda");
            stage.centerOnScreen();
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoClienteCad(Entidade ent) {
        try {
            new ClienteFornController(ent);
            replaceSceneContentCad("ClienteCad.fxml", "Cadastro de Clientes");
            stage.centerOnScreen();
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoProdutosCad(Produto prod) {
        try {
            new ProdutoController(prod);
            replaceSceneProdutoCad("ProdutoCad.fxml", "Cadastro de Produtos");
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoViagemCad(Viagem viag) {
        try {
            new ViagemController(viag);
            Parent page = (Parent) FXMLLoader.load(Principal.class.getResource("../telas/ViagemCad.fxml"), null, new JavaFXBuilderFactory());
            Scene scene = new Scene(page, 754, 332);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("BHGerVendas -- Cadastro de Viagens");
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoFornecedorCad(Entidade ent) {
        try {
            new ClienteFornController(ent);
            replaceSceneContentCad("FornecedorCad.fxml", "Cadastro de Viagens");
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoPedidoCad(Pedido ped, Viagem v) {
        try {
            new PedidosController(ped, v);
            replaceSceneContentCad("PedidosCad.fxml", "Cadastro de Pedidos");
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