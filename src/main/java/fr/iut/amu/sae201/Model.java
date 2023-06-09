package fr.iut.amu.sae201;

import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Model {
    FileChooser fileChooser = new FileChooser();
    ArrayList<ArrayList<String>> donneesCSV = new ArrayList<>();;

    public Model() {
        if (donneesCSV.isEmpty()) {
            choisirCsv();
        }
    }

    public ArrayList<ArrayList<String>> getDonneesCSV() {
        return donneesCSV;
    }

    public ArrayList<String> RendreLigneConforme(String ligne) {
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

    public String ExtraireAnnee(String str) {
        String builderAnnee = "";
        for (char c : str.toCharArray()) {
            if (c == '/') {
                break;
            }
            builderAnnee += c;
        }
        return builderAnnee;
    }

    public ArrayList<Integer> getNombre(ArrayList<String> annees) {
        ArrayList<Integer> nombre = new ArrayList<>(annees.size());
        for (int i = 0; i < donneesCSV.size(); i += 1) {
            for (String Annee : annees) {
                if (Annee == ExtraireAnnee(donneesCSV.get(i).get(1))) {
                    nombre.set(annees.indexOf(Annee), 1);
                }
                System.out.println(nombre);
            }
        }
        return nombre;
    }

    public ArrayList<String> getAnnee() {
        if(donneesCSV.isEmpty()) {
            chargerCsv();
        }
        boolean estDejaPresent;
        ArrayList<String> Annees = new ArrayList<>();
        for (int i = 0; i < donneesCSV.size(); i += 1) {
            estDejaPresent = false;
            for (String Annee : Annees) {
                if (Annee == ExtraireAnnee(donneesCSV.get(i).get(1))) {
                    estDejaPresent = true;
                }
            }
            if (!estDejaPresent) {
                System.out.println(donneesCSV.get(i).get(1));
                Annees.add(ExtraireAnnee(donneesCSV.get(i).get(1)));
            }
        }
        return Annees;
    }

    public void choisirCsv() {
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
        fileChooser.setTitle("Ouvrir un fichier CSV");
    }
    public void chargerCsv() {
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    donneesCSV.add(RendreLigneConforme(line));
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
