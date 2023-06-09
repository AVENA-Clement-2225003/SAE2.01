package fr.iut.amu.sae201;

import javafx.scene.chart.*;

public class ModelView {
    Model CSV = new Model();
    public ModelView() {

    }
    public LineChart<String, Number> actuDonnees() { //290404
        CSV.getDonneesCSV();
        String[] annees = {"1", "2"};
        int[] nombres = {5, 7};

        // Configuration des axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Année");
        yAxis.setLabel("Nombre");

        // Création du graphique en ligne
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

        // Ajout des données au graphique
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (int i = 0; i < annees.length; i++) {
            series.getData().add(new XYChart.Data<>(annees[i], nombres[i]));
        }
        lineChart.getData().add(series);
        return lineChart;
    }
}
