package cz.vse.sven.main;

import cz.vse.sven.logic.game.Game;
import cz.vse.sven.logic.game.IGame;
import cz.vse.sven.uiText.TextInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/*******************************************************************************
 * Třída  Start je hlavní třídou projektu,
 * který představuje jednoduchou textovou adventuru určenou k dalším úpravám a rozšiřování
 *
 * @author Jarmila Pavlíčková, Tomáš Kotouč
 * @version únor 2024
 */
public class Start extends Application {

    /**
     * Pokud je parametr "text", tak se hra spustí v textové verzi
     * pokud parametr není "text", tak se hra spustí v grafické verzi
     *
     * @param args Parametry příkazového řádku
     */
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("text")) {
            IGame game = new Game();
            TextInterface ui = new TextInterface(game);
            ui.hraj();
            Platform.exit();
        } else {
            launch();
        }
    }

    /**
     * Spuštění grafické verze - načtení FXML souboru, vytvoření a nastavení scény,
     * zobrazí okno s uvítáním
     *
     * @param stage instance okna
     * @throws Exception pokud nastane chyba
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
        introduction.setHeaderText("\nYou play as Sven, who lives under the bridge with his dog Pepa.\n" +
                "They are both hungry, but Sven has no food and no money to buy it.\n" +
                "He decides to leave Pepa at home and go to a near Soup kitchen (it gives homeless people food for free).\n" +
                "His main goal is to obtain food for Pepa and himself.");
        introduction.show();
    }
}
