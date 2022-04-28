package us.group41.propertytycoon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * This class shows the monopoly GUI screen.
 *
 * @author 235288
 * @version 0.5.1
 * @since 29/04/2022
 */
public class HelloApplication extends Application {
    /**
     * main class which takes you to start()
     *
     * @param args unused.
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Start sets some properties for the stage and then launches it.
     *
     * @param stage the primary stage for this application, onto which
     *              the application scene can be set.
     *              Applications may create other stages, if needed, but they will not be
     *              primary stages.
     * @throws IOException If it can't find the hello-view.fxml
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Property Tycoon");
        stage.setScene(scene);
        stage.show();
    }
}