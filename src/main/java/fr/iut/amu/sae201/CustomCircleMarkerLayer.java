package fr.iut.amu.sae201;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Affiche un point rouge sur la carte créée par le Tuto
 */
public class CustomCircleMarkerLayer extends MapLayer {
    private final MapPoint mapPoint;
    private final Circle circle;
    public CustomCircleMarkerLayer(MapPoint mapPoint) {
        this.mapPoint = mapPoint;
        this.circle = new Circle(2, Color.RED);
        this.getChildren().add(circle);
    }
    @Override
    protected void layoutLayer() {
        /* Conversion du MapPoint vers Point2D */
        Point2D point2d = this.getMapPoint(mapPoint.getLatitude(), mapPoint.getLongitude());
        circle.setTranslateX(point2d.getX());
        circle.setTranslateY(point2d.getY());
    }
}