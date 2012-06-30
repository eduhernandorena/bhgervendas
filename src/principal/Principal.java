package principal;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Eduardo Hernandorena
 */
public class Principal extends Application {

    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            primaryStage.setTitle("Login");
            primaryStage.setResizable(false);
            installEventHandler(root);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void installEventHandler(final Node keyNode) {
        final EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {

            @Override
            public void handle(final KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    if (Login.valida()) {
                        System.out.println("Abre!");
                    } else {
                        System.out.println("Não abre!");
                    }
                } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    System.exit(0);
                }
            }
        };

        //keyNode.setOnKeyPressed(keyEventHandler);
        keyNode.setOnKeyReleased(keyEventHandler);
    }

    public void gotoLogin() {
        try {
            replaceSceneContent("Login.fxml");
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    private Parent replaceSceneContent(String fxml) throws Exception {
        Parent page = (Parent) FXMLLoader.load(Principal.class.getResource(fxml), null, new JavaFXBuilderFactory());
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(page, 700, 450);
            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(page);
        }
        stage.sizeToScene();
        return page;
    }
}