module es.jeremy.ejef {
    requires javafx.controls;
    requires javafx.fxml;


    opens es.jeremy.ejef to javafx.fxml;
    exports es.jeremy.ejef;
}