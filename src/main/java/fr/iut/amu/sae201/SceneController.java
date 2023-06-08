package fr.iut.amu.sae201;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
public class SceneController {

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

    private void loadCsv(){
        //Chargement fichier csv
        try {
            BufferedReader file = new BufferedReader(new FileReader("/amuhome/m22009213/Bureau/SAE2.01/src/main/resources/fr/iut/amu/sae201/SisFrance_seismes.csv"));
            System.out.println("File found");
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("File not found");
        }
    }

    // Ajoutez d'autres méthodes pour naviguer vers d'autres scènes
}
