module fr.iut.amu.sae201 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.web;
    requires com.gluonhq.maps;

    opens fr.iut.amu.sae201 to javafx.fxml;
    exports fr.iut.amu.sae201;
}