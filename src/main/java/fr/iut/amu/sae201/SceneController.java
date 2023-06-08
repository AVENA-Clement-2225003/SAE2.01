package fr.iut.amu.sae201;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class SceneController {
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
    }
    @FXML
    private void goToCarte(MouseEvent event) throws Exception {
        mainApp.showScene("Carte.fxml");
    }
    @FXML
    private void goToCSVLoader(MouseEvent event) throws Exception {
        mainApp.showScene("CSVLoader.fxml");
    }
    @FXML
    private void goToSettings(MouseEvent event) throws Exception {
        mainApp.showScene("Settings.fxml");
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
