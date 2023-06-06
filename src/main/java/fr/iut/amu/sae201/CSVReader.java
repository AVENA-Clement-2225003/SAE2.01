package fr.iut.amu.sae201;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CSVReader {
    Scanner sc;
    public CSVReader(String nomFichier) {
        try {
            sc = new Scanner(new File(nomFichier));
            sc.useDelimiter(",");
        }catch (FileNotFoundException e){
            System.out.println("Le fichier " + nomFichier + " n'as pas été trouvé");
        }

    }

    public void Lire() {
        while (sc.hasNext()) {
            System.out.print(sc.next());
        }
        sc.close();
    }
}