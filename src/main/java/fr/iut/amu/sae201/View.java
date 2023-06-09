package fr.iut.amu.sae201;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.control.PropertySheet;

import java.util.ArrayList;

public class View {
    @FXML
    LineChart<String, Number> lineChart;
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
        lineChart = DMV.actuDonnees();

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
