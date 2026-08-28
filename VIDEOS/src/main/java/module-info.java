module ni.edu.uam.vieos {
    requires javafx.controls;
    requires javafx.fxml;

    opens ni.edu.uam.vieos to javafx.fxml;
    opens ni.edu.uam.vieos.controlers to javafx.fxml;
    exports ni.edu.uam.vieos;
}
