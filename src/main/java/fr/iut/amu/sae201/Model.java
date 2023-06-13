package fr.iut.amu.sae201;

import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Récupération des données du fichier CSV
 */
public class Model {
    private FileChooser fileChooser = new FileChooser();
    private static ArrayList<ArrayList<String>> donneesCSV;
    private static ArrayList<String> Categories = new ArrayList<>();
    public Model() {
        donneesCSV = new ArrayList<>();
    }

    public ArrayList<ArrayList<String>> getDonneesCSV() {
        return donneesCSV;
    }
    public ArrayList<String> getCategoriesCSV() {
        return Categories;
    }

    /**
     * @param ligne
     * @return Retourne une liste qui contient tous les valeur d'une ligne séparé par une virgule et qui prend en charge les colonnes vides des CSV
     */
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

    /**
     * @return Permet de charger un fichier CSV dans le code
     */
    public void chargerCsv() {
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
        fileChooser.setTitle("Ouvrir un fichier CSV");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            donneesCSV = new ArrayList<>();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                Categories = RendreLigneConforme(br.readLine());
                while ((line = br.readLine()) != null) {
                    donneesCSV.add(RendreLigneConforme(line));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
