package fr.iut.amu.sae201;

import javafx.scene.chart.*;

import java.util.ArrayList;

public class ModelView {
    Model CSV = new Model();
    public ModelView() {

    }
    public LineChart<String, Number> actuDonnees() {
        CSV.getDonneesCSV();
        ArrayList<String> annees = CSV.getAnnee();
        ArrayList<Integer> nombres = CSV.getNombre(annees);

        // Configuration des axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Année");
        yAxis.setLabel("Nombre");

        // Création du graphique en ligne
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

        // Ajout des données au graphique
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (int i = 0; i < annees.size(); i++) {
            series.getData().add(new XYChart.Data<>(annees.get(i), nombres.get(i)));
        }
        lineChart.getData().add(series);
        return lineChart;
    }
}
