package fr.iut.amu.sae201;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.control.PropertySheet;

import java.util.ArrayList;

public class View {
    @FXML
    LineChart<String, Number> lineChart;
    @FXML
    Label Frequence = new Label();
    @FXML
    Label Max = new Label();
    @FXML
    Label Moy = new Label();
    @FXML
    Label MagnetudeMoy = new Label();
    @FXML
    Label LPR = new Label();
    private boolean DejaOuvert = false;
    Model DCSV = new Model();
    ModelView DMV = new ModelView();
    private Stage StageAvances = new Stage();
    private ArrayList<ArrayList<String>> ListOfEvent = new ArrayList<>();
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void goToMainMenu(MouseEvent event) throws Exception {
        mainApp.showScene("MainMenu.fxml");
    }
    @FXML
    private void goToDashboard(MouseEvent event) throws Exception {
        mainApp.showScene("Dashboard.fxml");
        System.out.println("Passe");
        lineChart = DMV.actuDonnees();
        Frequence.setText("Pas de liaison"); //290404
        Max.setText("Pas de liaison");
        Moy.setText("Pas de liaison");
        LPR.setText("Pas de liaison");
        MagnetudeMoy.setText("Pas de liaison");
        System.out.println("Passe");
        mainApp.UpShow();
    }
    @FXML
    private void goToCarte(MouseEvent event) throws Exception {
        mainApp.showCarte("Carte.fxml");
    }
    @FXML
    private void goToCSVLoader(MouseEvent event) throws Exception {
        DCSV.chargerCsv();
        ListOfEvent = DCSV.getDonneesCSV();
    }
    @FXML
    private void FenetreParametres(MouseEvent event) throws Exception {
        if (!DejaOuvert) {
            Parent root = new FXMLLoader(getClass().getResource("AdvancedSettings.fxml")).load();
            StageAvances.setScene(new Scene(root));
            StageAvances.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    DejaOuvert = false;}});
            StageAvances.show();
            DejaOuvert = true;
        }
    }
}
