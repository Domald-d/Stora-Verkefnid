package vidmot.simplebooks;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class simpleBooksApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(simpleBooksApplication.class.getResource("simpleBooks-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 420, 340);
        stage.setTitle("Simple Booking");
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.jpg")));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
