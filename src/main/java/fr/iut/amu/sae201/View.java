package fr.iut.amu.sae201;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;

public class View {
    Model M = new Model();
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    float ValueForceMax;
    float ValueForceMin;
    String ValueDateDebut;
    String ValueDateFin;
    private boolean DejaOuvert = false;
    ModelView DMV = new ModelView();
    private Stage StageAvances = new Stage();
    private MainApp mainApp;
    @FXML
    private TextField longitude = new TextField();
    @FXML
    private TextField latitude = new TextField();
    @FXML
    private TextField forceMax = new TextField();
    @FXML
    private TextField forceMin = new TextField();
    @FXML
    private TextField dateDebut = new TextField();
    @FXML
    private TextField dateFin = new TextField();
    @FXML
    private Label LabelErreurParametre = new Label();
    @FXML
    TextField Region = new TextField();
    @FXML
    TextField DateDeb = new TextField();
    @FXML
    TextField DateFin = new TextField();
    @FXML
    LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
    @FXML
    Label Frequence = new Label();
    @FXML
    Label MaxLabel = new Label();
    @FXML
    Label Moy = new Label();
    @FXML
    Label MagnetudeMoy = new Label();
    @FXML
    Label LPR = new Label();

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
    }
    @FXML
    private void Actu(MouseEvent event) throws Exception {
        DMV.SelectionEchantillonDonneesDashboard(DMV.TransformerDate(DateDeb.getText()), DMV.TransformerDate(DateFin.getText()), Region.getText());
        ArrayList<String> Annee = new ArrayList<>(DMV.getAnnee());
        ArrayList<Integer> NombreParAnne = new ArrayList<>(DMV.getNombreParAnne());
        if(M.getDonneesCSV().isEmpty()) {
            M.chargerCsv();
            DMV.SelectionEchantillonDonneesDashboard(DMV.TransformerDate(DateDeb.getText()), DMV.TransformerDate(DateFin.getText()), Region.getText());
            Annee = DMV.getAnnee();
            NombreParAnne = DMV.getNombreParAnne();
        }
        lineChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (int i = 0; i < Annee.size(); ++i) {
            series.getData().add(new XYChart.Data<>(Annee.get(i), NombreParAnne.get(i)));
        }
        lineChart.getData().add(series);
        Frequence.setText(String.format("Il y a environ %.1f jour entre chaques évèments", DMV.Frequence()));
        MaxLabel.setText(String.valueOf(DMV.Maximum()));
        MaxLabel.setFont(new Font(60));
        MaxLabel.setAlignment(Pos.BOTTOM_CENTER);
        Moy.setText(String.format("Environ %.2f/par an", DMV.MoyenneSeisme()));
        Moy.setAlignment(Pos.CENTER);
        LPR.setText(DMV.LePlusRecent());
        MagnetudeMoy.setText(String.format("%.3f en moyenne sur la période donnée", DMV.MagnitudeMoyenne()));
        mainApp.UpShow();
        //System.out.println("Passe");
    }
    @FXML
    private void goToCarte(MouseEvent event) throws Exception {
        mainApp.showCarte("Carte.fxml");
        LabelErreurParametre.setVisible(false);
        LabelErreurParametre.setText("cc");
    }
    @FXML
    private void goToCSVLoader(MouseEvent event) throws Exception {
        M.chargerCsv();
    }
    @FXML
    private void FenetreParametres(MouseEvent event) throws Exception {
        if (!DejaOuvert) {
            Parent root = new FXMLLoader(getClass().getResource("AdvancedSettings.fxml")).load();
            StageAvances.setScene(new Scene(root));
            StageAvances.setResizable(false);
            StageAvances.setOnCloseRequest(event1 -> DejaOuvert = false);
            StageAvances.show();
            DejaOuvert = true;
        }
    }
    @FXML
    private void ValiderParametres (MouseEvent event) throws Exception { //Fonctionne sauf pour quitter la fenetre
        if ((forceMax.getText().isEmpty() && forceMin.getText().isEmpty())) {
            ValueForceMin = 0.1f;
            ValueForceMax = 10.0f;
        } else {
            ValueForceMax = Float.parseFloat(forceMax.getText());
            ValueForceMin = Float.parseFloat(forceMin.getText());
        }
        if ((dateDebut.getText().isEmpty() && dateFin.getText().isEmpty())) {
            ValueDateDebut = "";
            ValueDateFin = "";
        } else {
            ValueDateDebut = dateDebut.getText();
            ValueDateFin = dateFin.getText();
        } if (((ValueForceMin >= 0.1 && ValueForceMax <= 10.0) && ValueForceMin <= ValueForceMax) && ((ValueDateDebut == "" && ValueDateFin == "") || DMV.dateEstInferieure(ValueDateDebut, ValueDateFin))) {
            LabelErreurParametre.setVisible(false);
            StageAvances.close();
        } else {
            LabelErreurParametre.setVisible(true);
        }
    }
    public void initialize() {
        forceMin.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,2}([.]\\d{0,1})?")) {
                forceMin.setText(oldValue);
            }
            if (newValue.indexOf('.') != newValue.lastIndexOf('.')) {
                forceMin.setText(oldValue);
            }
        });
        forceMax.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,2}([.]\\d{0,1})?")) {
                forceMax.setText(oldValue);
            }
            if (newValue.indexOf('.') != newValue.lastIndexOf('.')) {
                forceMax.setText(oldValue);
            }
        });
        latitude.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d{0,2}(\\.\\d{0,2})?")) {
                latitude.setText(oldValue);
            }
            if (newValue.indexOf('.') != newValue.lastIndexOf('.')) {
                latitude.setText(oldValue);
            }
        });
        longitude.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d{0,3}(\\.\\d{0,2})?")) {
                longitude.setText(oldValue);
            }
            if (newValue.indexOf('.') != newValue.lastIndexOf('.')) {
                longitude.setText(oldValue);
            }
        });
    }
}
