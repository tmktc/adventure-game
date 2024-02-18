package cz.vse.sven.main;

import cz.vse.sven.logika.hra.Hra;
import cz.vse.sven.logika.hra.IHra;
import cz.vse.sven.logika.objekty.Prostor;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

/**
 * Controller pro home.fxml
 */
public class HomeController implements Pozorovatel {

    @FXML
    private ListView panelVychodu;

    @FXML
    private Button tlacitkoOdesli;

    @FXML
    private TextArea vystup;

    @FXML
    private TextField vstup;

    private IHra hra = new Hra();

    private ObservableList<Prostor> seznamVychodu = FXCollections.observableArrayList();

    /**
     * Metoda na začátku hry:
     * vrací uvítání, focusuje na TextField, do panelu východů (ListView) vloží seznam východů
     */
    @FXML
    private void initialize() {
        vystup.appendText(hra.vratUvitani());
        Platform.runLater(() -> vstup.requestFocus());
        panelVychodu.setItems(seznamVychodu);
        hra.getHerniPlan().registruj(this);
        aktualizujSeznamVychodu();
    }

    /**
     * Metoda nejdříve vyčistí seznam východů v panelu východů (ListView) a vloží do něj aktualizované východy
     */
    @FXML
    private void aktualizujSeznamVychodu() {
        seznamVychodu.clear();
        seznamVychodu.addAll(hra.getHerniPlan().getAktualniProstor().getVychody());
    }

    /**
     * Metoda vypisuje vstup (zadaný v TextField) do výstupu (TextArea)
     * dále daný vstup zpracuje a vypíše jeho výsledek
     *
     * pokud nastane konec hry, tak vypíše epilog a zamezí interakci se vstupem(TextField) a tlačítkem "Odešli"
     *
     * @param actionEvent
     */
    @FXML
    private void odesliVstup(ActionEvent actionEvent) {
        String prikaz = vstup.getText();
        vystup.appendText("> " + prikaz + "\n");
        String vysledek = hra.zpracujPrikaz(prikaz);
        vystup.appendText(vysledek + "\n");
        vstup.clear();

        if (hra.konecHry()) {
            vystup.appendText(hra.vratEpilog());
            vstup.setDisable(true);
            tlacitkoOdesli.setDisable(true);
        }
    }

    /**
     * Metoda zajistí, že po kliknutí na položku "Ukončit" a následným potvrzením "OK" se hra zavře
     *
     * @param actionEvent
     */
    public void ukoncitHru(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Opravdu chtete ukončit hru? Veškerý postup bude ztracen.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    /**
     * Zděděná metoda od Pozorovatele - pokaždé, když je zavolána, aktualizuje seznam východů
     */
    @Override
    public void aktualizuj() {
        aktualizujSeznamVychodu();
    }
}
