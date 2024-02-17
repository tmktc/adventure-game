package cz.vse.sven.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Controller pro home.fxml
 */
public class HomeController {

    @FXML
    private TextArea vystup;

    @FXML
    private TextField vstup;

    /**
     * Metoda vypisuje vstup (zadaný v TextField) do výstupu (TextArea)
     *
     * @param actionEvent
     */
    @FXML
    private void odesliVstup(ActionEvent actionEvent) {
        vystup.appendText(vstup.getText() + "\n");
        vstup.clear();
    }
}
