package fr.iut.amu.sae201;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.converter.FloatStringConverter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SceneController {
    private boolean DejaOuvert = false;
    private Stage StageAvances = new Stage();
    private ArrayList<ArrayList<String>> ListOfEvent = new ArrayList<>();
    private MainApp mainApp;
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public void initialize() {
        //permet de ne pouvoir rentrer que des float de 3 chiffre
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
    }

    @FXML
    private TextField forceMax = new TextField("10.0");
    @FXML
    private TextField forceMin = new TextField("0.1");
    @FXML
    private TextField dateDebut = new TextField();
    @FXML
    private TextField dateFin = new TextField();


    @FXML
    private void goToMainMenu(MouseEvent event) throws Exception {
        mainApp.showScene("MainMenu.fxml");
    }
    @FXML
    private void goToDashboard(MouseEvent event) throws Exception {
        mainApp.showScene("Dashboard.fxml");
    }
    @FXML
    private void goToCarte(MouseEvent event) throws Exception {
        mainApp.showCarte("Carte.fxml");
    }
    @FXML
    private void goToCSVLoader(MouseEvent event) throws Exception {
        mainApp.showScene("CSVLoader.fxml");
    }
    @FXML
    private void FenetreParametres(MouseEvent event) throws Exception {
        if (!DejaOuvert) {
            Parent root = new FXMLLoader(getClass().getResource("AdvancedSettings.fxml")).load();
            StageAvances.setScene(new Scene(root));
            StageAvances.setResizable(false);
            StageAvances.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    DejaOuvert = false;}});
            StageAvances.show();
            DejaOuvert = true;
        }
    }

    @FXML
    private void ValiderParametres (MouseEvent event) throws Exception {
        Float forceMinFloat = Float.valueOf(forceMin.getText());
        if (forceMinFloat >= 0.1 && forceMinFloat <= 10.0){
            //action à faire
        }
        Float forceMaxFloat = Float.valueOf(forceMax.getText());
        if (forceMaxFloat >= 0.1 && forceMinFloat <= 10.0){
            //action à faire
        }
            dateDebut.getText();
            dateFin.getText();
            //ajouter un moyen de fermer la fenêtre
    }

    public ArrayList<String> RendreLigneConforme (String ligne) {
        ArrayList<String> liste = new ArrayList<>();
        String StrBuilder = "";
        for (char c : ligne.toCharArray()) {
            if (c == ',') {
                liste.add(StrBuilder);
                StrBuilder = "";
            } else if (c != '\"') {
                StrBuilder += c;
            }
        }
        liste.add(StrBuilder);
        return liste;
    }

    private void loadCsv(){
        try {
            BufferedReader file = new BufferedReader(new FileReader("/amuhome/m22009213/Bureau/SAE2.01/src/main/resources/fr/iut/amu/sae201/SisFrance_seismes.csv"));
            System.out.println("File found");
            String line;
            while ((line = file.readLine()) != null) {
                ListOfEvent.add(RendreLigneConforme(line));
                System.out.println(line);
            }
            file.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File not found");
        }
    }
}
