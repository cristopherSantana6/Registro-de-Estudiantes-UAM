package ni.edu.uam.vieos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                LoginApplication.class.getResource("/ni/edu/uam/vieos/login-view.fxml")
        );
        Scene scene = new Scene(loader.load(), 430, 620);
        stage.setTitle("VIDEOS - Iniciar sesión");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
