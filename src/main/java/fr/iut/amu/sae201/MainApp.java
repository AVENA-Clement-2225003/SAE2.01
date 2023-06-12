package fr.iut.amu.sae201;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import java.io.IOException;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;

public class MainApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Intégration données SisFrance");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/iut/amu/sae201/MainMenu.fxml"));
            Pane root = loader.load();
            View controller = loader.getController(); // Renvoie une instance valide du contrôleur
            controller.setMainApp(this); // Vérification si le contrôleur est null
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.primaryStage.setResizable(false);
    }

    public void showScene(String fxmlFileName) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
        Pane root = loader.load();
        // Obtenir le contrôleur de la scène chargée
        View controller = loader.getController();
        // Définir le contrôleur principal pour permettre la navigation entre les scènes
        controller.setMainApp(this);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void UpShow() {
        primaryStage.show();
    }

    // Affichage carte interactive
    public void showCarte(String fxmlFileName) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(fxmlFileName));
        Pane root = fxmlLoader.load();
        View controller = fxmlLoader.getController();
        controller.setMainApp(this);

        VBox carte = (VBox) root.lookup("#carte");

        MapView mapView = new MapView();

        // Création point
        MapPoint mapPoint = new MapPoint(47, 2);

        // Définition zoom et centrage carte
        mapView.setZoom(6);
        mapView.flyTo(0, mapPoint, 0.1);

        carte.getChildren().add(mapView);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
