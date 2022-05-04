module com.hermitfeather.ca482_pa_fx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.hermitfeather.ca482_pa_fx to javafx.fxml;
    exports com.hermitfeather.ca482_pa_fx;
}