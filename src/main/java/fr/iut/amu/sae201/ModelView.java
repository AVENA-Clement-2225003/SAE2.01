package fr.iut.amu.sae201;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Classement des données par année
 */
public class ModelView {
    Model CSV = new Model();
    ArrayList<ArrayList<String>> donneesCSV = CSV.getDonneesCSV();
    ArrayList<String> Annee = new ArrayList<>();
    ArrayList<Integer> NombreParAnne = new ArrayList<>();

    public ArrayList<String> getAnnee () {
        return Annee;
    }
    public ArrayList<Integer> getNombreParAnne () {
        return NombreParAnne;
    }

    /**
     * @param str
     * @return String
     */
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

    /**
     * @param IndiceDep
     * @param IndiceFin
     */
    public void setAnnee(int IndiceDep, int IndiceFin) {
        ArrayList<String> Annees = new ArrayList<>();
        for (int i = IndiceDep; i < IndiceFin; i++) {
            String annee = ExtraireAnnee(donneesCSV.get(i).get(1));
            if (!Annees.contains(annee)) {
                Annees.add(annee);
            }
        }
        Annee = Annees;
    }

    /**
     * @param annees
     */
    public void setNombre(ArrayList<String> annees) {
        ArrayList<Integer> nombre = new ArrayList<>(annees.size());
        for (int i = 0; i < annees.size(); i++) {
            nombre.add(0);
        }
        for (int i = 0; i < donneesCSV.size(); i++) {
            String annee = ExtraireAnnee(donneesCSV.get(i).get(1));
            for (String Annee : annees) {
                if (Annee.equals(annee)) {
                    int index = annees.indexOf(Annee);
                    int value = nombre.get(index) + 1;
                    nombre.set(index, value);
                    break;
                }
            }
        }
        NombreParAnne = nombre;
    }

    /**
     * @param DateDeb
     * @param DateFin
     * @param IntensiteMin
     * @param IntensiteMax
     */
    public void SelectionEchantillonDonneesCarte (String DateDeb, String DateFin, float IntensiteMin, float IntensiteMax) {
        donneesCSV = CSV.getDonneesCSV();
        int IndiceDebSelection = 0;
        int IndiceFinSelection = donneesCSV.size();
        while(donneesCSV.get(IndiceDebSelection).get(1) != DateDeb && Float.parseFloat(donneesCSV.get(IndiceDebSelection).get(10)) != IntensiteMin) {++IndiceDebSelection;}
        while(donneesCSV.get(IndiceFinSelection).get(1) != DateDeb && Float.parseFloat(donneesCSV.get(IndiceFinSelection).get(10)) != IntensiteMin) {--IndiceDebSelection;}
    }

    /**
     * @param DateDeb
     * @param DateFin
     * @param Region
     */
    public void SelectionEchantillonDonneesDashboard (String DateDeb, String DateFin, String Region) { //29042004
        donneesCSV = CSV.getDonneesCSV();
        int IndiceDebSelection = 0;
        int IndiceFinSelection = donneesCSV.size();
        if (!DateDeb.isEmpty() && !DateFin.isEmpty() && !Region.isEmpty()) {
            while (IndiceDebSelection < donneesCSV.size() && (!dateEstInferieure(donneesCSV.get(IndiceDebSelection).get(1), DateDeb) || donneesCSV.get(IndiceDebSelection).get(4) != Region)) {IndiceDebSelection++;}
            while (IndiceFinSelection >= 0 && (!dateEstSuperieure(donneesCSV.get(IndiceFinSelection).get(1), DateFin) || donneesCSV.get(IndiceFinSelection).get(4) != Region)) {IndiceFinSelection--;}
        } else if (DateDeb.isEmpty() && !DateFin.isEmpty() && !Region.isEmpty()) {
            while (IndiceFinSelection >= 0 && donneesCSV.get(IndiceFinSelection).get(4) != Region) {IndiceFinSelection--;}
        } else if (!DateDeb.isEmpty() && DateFin.isEmpty() && !Region.isEmpty()) {
            while (IndiceDebSelection < donneesCSV.size() && (donneesCSV.get(IndiceDebSelection).get(1) != DateDeb || donneesCSV.get(IndiceDebSelection).get(4) != Region)) {IndiceDebSelection++;}
        } else if (!DateDeb.isEmpty() && !DateFin.isEmpty() && Region.isEmpty()) {
            while (IndiceDebSelection < donneesCSV.size() && donneesCSV.get(IndiceDebSelection).get(1) != DateDeb) {IndiceDebSelection++;}
            while (IndiceFinSelection >= 0 && donneesCSV.get(IndiceFinSelection).get(1) != DateFin) {IndiceFinSelection--;}
        } else if (!DateDeb.isEmpty() && DateFin.isEmpty() && Region.isEmpty()) {
            while (IndiceDebSelection < donneesCSV.size() && donneesCSV.get(IndiceDebSelection).get(1) != DateDeb) {IndiceDebSelection++;}
        } else if (DateDeb.isEmpty() && !DateFin.isEmpty() && Region.isEmpty()) {
            while (IndiceFinSelection >= 0 && donneesCSV.get(IndiceFinSelection).get(1) != DateFin) {IndiceFinSelection--;}
        } else if (DateDeb.isEmpty() && DateFin.isEmpty() && !Region.isEmpty()) {
            while (IndiceDebSelection < donneesCSV.size() && donneesCSV.get(IndiceDebSelection).get(4) != Region) {IndiceDebSelection++;}
        }
        setAnnee(IndiceDebSelection, IndiceFinSelection);
        setNombre(Annee);
    }

    /**
     * @param Date
     * @return String
     */
    public String TransformerDate (String Date) {
        char[] caracteres = Date.toCharArray();
        int debut = 0;
        int fin = caracteres.length - 1;

        while (debut < fin) {
            char temp = caracteres[debut];
            caracteres[debut] = caracteres[fin];
            caracteres[fin] = temp;
            debut++;
            fin--;
        }
        return new String(caracteres);
    }

    /**
     * @return String
     */
    public String LePlusRecent() {
        ArrayList<String> Dernier = donneesCSV.get(donneesCSV.size()-1);
        return ("Le dernier incident date du " + Dernier.get(1) + " a " + Dernier.get(2) + ", qui a eu lieu dans les " + Dernier.get(4) + ", avec une intensité de " + Dernier.get(10) + "\naux coordonnées: " + Dernier.get(8) + " " + Dernier.get(9) + " aux format WGS");
    }

    /**
     * @return int
     * Permet de caluler le maximum de seismes par années
     */
    public int Maximum() {
        int iMax = 0;
        for(int i = 0; i < NombreParAnne.size(); i+=1) {
            if (NombreParAnne.get(iMax) < NombreParAnne.get(i)) {
                iMax = i;
            }
        }
        return NombreParAnne.get(iMax);
    }

    /**
     * @param DateReference
     * @param DateComparee
     * @return boolean
     */
    public boolean dateEstSuperieure(String DateReference, String DateComparee) {
        String[] elements1 = DateReference.split("/");
        String[] elements2 = DateComparee.split("/");
        int jour1 = Integer.parseInt(elements1[0]);
        int mois1 = Integer.parseInt(elements1[1]);
        int annee1 = Integer.parseInt(elements1[2]);
        int jour2 = Integer.parseInt(elements2[0]);
        int mois2 = Integer.parseInt(elements2[1]);
        int annee2 = Integer.parseInt(elements2[2]);
        if (annee1 > annee2) {
            return true;
        } else if (annee1 == annee2) {
            if (mois1 > mois2) {
                return true;
            } else if (mois1 == mois2) {
                if (jour1 > jour2) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param DateReference
     * @param DateComparee
     * @return boolean
     */
    public boolean dateEstInferieure(String DateReference, String DateComparee) {
        String[] elements1 = DateReference.split("/");
        String[] elements2 = DateComparee.split("/");
        int jour1 = Integer.parseInt(elements1[0]);
        int mois1 = Integer.parseInt(elements1[1]);
        int annee1 = Integer.parseInt(elements1[2]);
        int jour2 = Integer.parseInt(elements2[0]);
        int mois2 = Integer.parseInt(elements2[1]);
        int annee2 = Integer.parseInt(elements2[2]);
        if (annee1 < annee2) {
            return true;
        } else if (annee1 == annee2) {
            if (mois1 < mois2) {
                return true;
            } else if (mois1 == mois2) {
                if (jour1 < jour2) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return float
     * Calcule la magnitude moyenne
     */
    public float MagnitudeMoyenne() {
        int somme = 0;
        int compteur = 0;
        for (ArrayList<String> Ligne: donneesCSV) {
            if (Ligne.get(10) != "") {
                somme += Float.parseFloat(Ligne.get(10));
                compteur += 1;
            }
        }
        return (float) somme / compteur;
    }

    /**
     * @return ArrayList<ArrayList<String>>
     * Permet de récuperer les coordonner des seismes du fichier CSV
     */
    public ArrayList<ArrayList<String>> RecupererTousLesPoints() {
        ArrayList<ArrayList<String>> Coord = new ArrayList<>();
        String[] Temp = {"", ""};
        if (CSV.getDonneesCSV().isEmpty()) {
            CSV.chargerCsv();
        }
        for (ArrayList<String> ligne:CSV.getDonneesCSV()) {
            if (!(ligne.get(8).isEmpty() || ligne.get(9).isEmpty())) {
                Temp[0] = ligne.get(8);
                Temp[1] = ligne.get(9);
                Coord.add(new ArrayList<>(List.of(Temp)));
            }
        }
        return Coord;
    }

    /**
     * @return TableView<ObservableList<String>>
     */
    public TableView<ObservableList<String>> CreerTableau() {
        ArrayList<ArrayList<String>> donneesCSV = CSV.getDonneesCSV();
        ArrayList<String> categories = CSV.getCategoriesCSV();
        TableView<ObservableList<String>> tableView = new TableView<>();
        for (int columnIndex = 0; columnIndex < categories.size(); columnIndex++) {
            final int index = columnIndex;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(categories.get(columnIndex));
            column.setCellValueFactory(cellData -> {
                ObservableList<String> rowData = cellData.getValue();
                return new javafx.beans.property.ReadOnlyObjectWrapper<>(rowData.get(index));
            });
            tableView.getColumns().add(column);
        }
        for (ArrayList<String> ligne : donneesCSV) {
            ObservableList<String> observableLigne = FXCollections.observableArrayList(ligne);
            tableView.getItems().add(observableLigne);
        }
        return tableView;
    }

    /**
     * @return TableView<ObservableList<String>>
     * Permet de gérer le tableau et d'y entrer les données du fichier CSV choisi
     */
    public TableView<ObservableList<String>> CreerTableauEvenement() {
        ArrayList<ArrayList<String>> donneesCSV = CSV.getDonneesCSV();
        ArrayList<String> categories = CSV.getCategoriesCSV();
        TableView<ObservableList<String>> tableView = new TableView<>();
        int[] indicesSelectionnes = {0, 4, 3};
        for (int columnIndex:indicesSelectionnes) {
            final int index = columnIndex;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(categories.get(columnIndex));
            column.setCellValueFactory(cellData -> {
                ObservableList<String> rowData = cellData.getValue();
                return new javafx.beans.property.ReadOnlyObjectWrapper<>(rowData.get(index));
            });
            tableView.getColumns().add(column);
        }
        for (ArrayList<String> ligne : donneesCSV) {
            ObservableList<String> observableLigne = FXCollections.observableArrayList(ligne.get(0), ligne.get(4), ligne.get(3));
            tableView.getItems().add(observableLigne);
        }
        return tableView;
    }

    /**
     * @return float
     * Calcule la fréquence
     */
    public float Frequence() {
        ArrayList<Date> ListeDate = new ArrayList<>();
        String[] temp = {"", "", ""};
        ArrayList<Long> ListeEspaceDate = new ArrayList<>();
        long Somme = 0;
        long DifferenceTemp;
        for (ArrayList<String> ligne : donneesCSV) {
            temp = ligne.get(1).split("/");
            if (temp.length == 1) {
                temp = new String[]{temp[0], "01", "01"};
            } else if (temp.length == 2) {
                temp = new String[]{temp[0], temp[1], "01"};
            }
            ListeDate.add(new Date( Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2])));
        }
        for (int i = 0; i < ListeDate.size()-1;  i+=1) {
            DifferenceTemp = ListeDate.get(i+1).getTime() - ListeDate.get(i).getTime();
            ListeEspaceDate.add(TimeUnit.DAYS.convert(DifferenceTemp, TimeUnit.MILLISECONDS));
        }
        for (long Espacement : ListeEspaceDate) {
            Somme += Espacement;
        }
        return Somme/ListeEspaceDate.size();
    }

    /**
     * @return float
     * Cette fonction fait la moyenne des seismes
     */
    public float MoyenneSeisme() {
        int somme = 0;
        for (int nbe: NombreParAnne) {
            somme += nbe;
        }
        return (float) somme / NombreParAnne.size();
    }

}
