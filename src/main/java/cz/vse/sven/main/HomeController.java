package cz.vse.sven.main;

import cz.vse.sven.logika.hra.Hra;
import cz.vse.sven.logika.hra.IHra;
import cz.vse.sven.logika.objekty.Prostor;
import cz.vse.sven.logika.prikazy.PrikazJdi;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

/**
 * Controller pro home.fxml
 */
public class HomeController {

    @FXML
    private ListView<Prostor> panelVychodu;

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
     * vrací uvítání, focusuje na TextField, do panelu východů (ListView) vloží seznam východů, registruje pozorovatele
     */
    @FXML
    private void initialize() {
        vystup.appendText(hra.vratUvitani());
        Platform.runLater(() -> vstup.requestFocus());
        panelVychodu.setItems(seznamVychodu);
        hra.getHerniPlan().registruj(ZmenaHry.ZMENA_MISTNOSTI, () -> aktualizujSeznamVychodu());
        hra.registruj(ZmenaHry.KONEC_HRY, () -> aktualizujKonecHry());
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
     * Pokud je výsledkem příkazu konec hry, tak metoda:
     * vypíše epilog
     * zamezí interakci se vstupem(TextField), tlačítkem "Odešli" (Button) a panelem východů (ListView)
     */
    private void aktualizujKonecHry() {
        if (hra.konecHry()) {
            vystup.appendText(hra.vratEpilog());
        }

        vstup.setDisable(hra.konecHry());
        tlacitkoOdesli.setDisable(hra.konecHry());
        panelVychodu.setDisable(hra.konecHry());
    }

    /**
     * Metoda zachytí vstup (zadaný v TextField) jako příkaz a zavolá metodu na jeho zpracování
     *
     * @param actionEvent
     */
    @FXML
    private void odesliVstup(ActionEvent actionEvent) {
        String prikaz = vstup.getText();
        vstup.clear();

        zpracujPrikaz(prikaz);
    }

    /**
     * Metoda vypíše zpracovávaný příkaz, nechá ho zpracovat metodou ve třídě "Hra" a vypíše výsledek zadaného příkazu
     *
     * @param prikaz který se má zpracovat
     */
    private void zpracujPrikaz(String prikaz) {
        vystup.appendText("> " + prikaz + "\n");
        String vysledek = hra.zpracujPrikaz(prikaz);
        vystup.appendText(vysledek + "\n");
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
     * Metoda zajistí, aby se po kliknutí na prostor v panelu východů (ListView) do daného prostoru přešlo
     *
     * @param mouseEvent
     */
    @FXML
    private void klikPanelVychodu(MouseEvent mouseEvent) {
        Prostor cil = panelVychodu.getSelectionModel().getSelectedItem();
        if (cil == null) return;
        String prikaz = PrikazJdi.NAZEV + " " + cil;
        zpracujPrikaz(prikaz);
    }
}
