package fr.iut.amu.sae201;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Controller {
    @FXML
    private Label welcomeText;

    @FXML
    private void ValiderMenuPrincipal(){

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

}