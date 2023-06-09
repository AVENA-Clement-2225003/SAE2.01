package fr.iut.amu.sae201;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Intégration données SisFrance");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/iut/amu/sae201/Carte.fxml"));
            Pane root = loader.load();
            SceneController controller = loader.getController(); // Renvoie une instance valide du contrôleur
            controller.setMainApp(this); // Vérification si le contrôleur est null
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showScene(String fxmlFileName) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
        Pane root = loader.load();
        // Obtenir le contrôleur de la scène chargée
        SceneController controller = loader.getController();
        // Définir le contrôleur principal pour permettre la navigation entre les scènes
        controller.setMainApp(this);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    // Affichage carte interactive
    public void showCarte(String fxmlFileName) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(fxmlFileName));
        Pane root = fxmlLoader.load();
        WebView webView = new WebView();
        String mapURL = "https://www.openstreetmap.org/export/embed.html?bbox=2.944,46.476,2.944,46.476&layer=mapnik";
        webView.getEngine().load(mapURL);
        VBox carte = (VBox) root.lookup("#carte");
        carte.getChildren().add(webView);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        // Obtenir le contrôleur de la scène chargée
        SceneController controller = fxmlLoader.getController();
        // Définir le contrôleur principal pour permettre la navigation entre les scènes
        controller.setMainApp(this);
    }

        public static void main(String[] args) {
        launch(args);
    }
}
