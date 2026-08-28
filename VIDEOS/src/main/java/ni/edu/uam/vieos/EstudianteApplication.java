package ni.edu.uam.vieos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EstudianteApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                EstudianteApplication.class.getResource("/ni/edu/uam/vieos/estudiante-view.fxml")
        );

        Scene scene = new Scene(loader.load(), 900, 600);
        stage.setTitle("Registro de estudiantes");
        stage.setMinWidth(820);
        stage.setMinHeight(560);
        stage.setScene(scene);
        stage.show();
    }
}
