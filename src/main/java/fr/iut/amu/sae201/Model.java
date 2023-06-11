package fr.iut.amu.sae201;

import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Model {
    private FileChooser fileChooser = new FileChooser();
    private static ArrayList<ArrayList<String>> donneesCSV;

    public Model() {
        donneesCSV = new ArrayList<>();
    }

    public ArrayList<ArrayList<String>> getDonneesCSV() {
        return donneesCSV;
    }


    public ArrayList<String> RendreLigneConforme(String ligne) { //Vérification faire tous marche comme il faut
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



    public void chargerCsv() {
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
        fileChooser.setTitle("Ouvrir un fichier CSV");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            donneesCSV = new ArrayList<>();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    donneesCSV.add(RendreLigneConforme(line));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
