package cz.vse.sven.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Start - main class
 */
public class Start extends Application {

    /**
     * If the parameter is "text", the game is launched in text version
     * otherwise the game is launched in the GUI version
     *
     * @param args parameter
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * GUI version launch - FXML file load, creating and setting of the scene,
     * displaying the introduction window
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Sven's Adventure");

        Alert introduction = new Alert(Alert.AlertType.INFORMATION);
        introduction.setTitle("Introduction");
        introduction.setHeaderText("\nYou play as Sven, who lives under the bridge with his dog Peppa.\n" +
                "They are both hungry, but Sven has no food and no money to buy it.\n" +
                "He decides to leave Peppa at home and go to a near Soup kitchen (it gives homeless people food for free).\n" +
                "His main goal is to obtain food for Peppa and himself.");
        introduction.show();
    }
}
