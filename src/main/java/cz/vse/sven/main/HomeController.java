package cz.vse.sven.main;

import cz.vse.sven.logika.hra.Hra;
import cz.vse.sven.logika.hra.IHra;
import cz.vse.sven.logika.objekty.Postava;
import cz.vse.sven.logika.objekty.Prostor;
import cz.vse.sven.logika.objekty.Vec;
import cz.vse.sven.logika.prikazy.*;
import cz.vse.sven.main.ListCell.ListCellPostavy;
import cz.vse.sven.main.ListCell.ListCellProstor;
import cz.vse.sven.main.ListCell.ListCellVeci;
import cz.vse.sven.main.Observer.ZmenaHry;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Controller pro home.fxml
 *
 * @author Tomáš Kotouč
 * @version únor 2024
 */
public class HomeController {

    @FXML
    private Label labelVeciVProstoru;

    @FXML
    private Label labelPostavyVProstoru;

    @FXML
    private Label labelVeciVBatohu;

    @FXML
    private Label labelVychody;

    @FXML
    private ListView<Postava> panelPostavVProstoru;

    @FXML
    private ListView<Vec> panelVeciVBatohu;

    @FXML
    private ListView<Vec> panelVeciVProstoru;

    @FXML
    private ImageView hrac;

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

    private ObservableList<Vec> seznamVeciVProstoru = FXCollections.observableArrayList();

    private ObservableList<Vec> seznamVeciVBatohu = FXCollections.observableArrayList();

    private ObservableList<Postava> seznamPostavVProstoru = FXCollections.observableArrayList();

    private Map<String, Point2D> souradniceProstoru = new HashMap<>();

    /**
     * Metoda na začátku hry:
     * vrací uvítání, focusuje na TextField, spojí panely se seznamy, registruje pozorovatele,
     * do panelu vloží aktualizované seznamy, nastavuje továrnu buňek pro panel východů a věcí v prostoru/batohu
     */
    @FXML
    private void initialize() {
        vystup.appendText(hra.vratUvitani());
        Platform.runLater(() -> vstup.requestFocus());
        panelVychodu.setItems(seznamVychodu);
        panelVeciVProstoru.setItems(seznamVeciVProstoru);
        panelVeciVBatohu.setItems(seznamVeciVBatohu);
        panelPostavVProstoru.setItems(seznamPostavVProstoru);
        hra.getHerniPlan().registruj(ZmenaHry.ZMENA_PROSTORU, () -> {
            aktualizujSeznamVychodu();
            aktualizujPolohuHrace();
        });
        hra.registruj(ZmenaHry.KONEC_HRY, () -> aktualizujKonecHry());
        hra.registruj(ZmenaHry.ZMENA_VECI, () -> {
            aktualizujSeznamVeciVProstoru();
            aktualizujSeznamVeciVBatohu();
        });
        hra.registruj(ZmenaHry.ZMENA_POSTAV, () -> aktualizujSeznamPostavVProstoru());
        vlozSouradnice();
        aktualizujSeznamVychodu();
        aktualizujSeznamVeciVBatohu();
        aktualizujSeznamVeciVProstoru();
        aktualizujSeznamPostavVProstoru();
        aktualizujPolohuHrace();
        aktualizujKonecHry();
        panelVychodu.setCellFactory(param -> new ListCellProstor());
        panelVeciVProstoru.setCellFactory(param -> new ListCellVeci());
        panelVeciVBatohu.setCellFactory(param -> new ListCellVeci());
        panelPostavVProstoru.setCellFactory(param -> new ListCellPostavy());
    }

    /**
     * Metoda nastaví souřadnice prostorů na mapě
     */
    private void vlozSouradnice() {
        souradniceProstoru.put("domov", new Point2D(380, 110));
        souradniceProstoru.put("jidelna", new Point2D(435, 140));
        souradniceProstoru.put("smetiste", new Point2D(260, 160));
        souradniceProstoru.put("pracak", new Point2D(150, 70));
        souradniceProstoru.put("sekac", new Point2D(10, 135));
        souradniceProstoru.put("zastavarna", new Point2D(125, 215));
        souradniceProstoru.put("trafika", new Point2D(590, 115));
        souradniceProstoru.put("lidl", new Point2D(590, 175));
    }

    /**
     * Metoda nejdříve vyčistí seznam východů v panelu východů (ListView) a vloží do něj aktualizovaný seznam
     */
    @FXML
    private void aktualizujSeznamVychodu() {
        seznamVychodu.clear();
        seznamVychodu.addAll(hra.getHerniPlan().getAktualniProstor().getVychody());
    }

    /**
     * Metoda nejdřívé vyčistí seznam věcí v prostoru (ListView) a vloží do něj aktualizovaný seznam
     */
    @FXML
    private void aktualizujSeznamVeciVProstoru() {
        seznamVeciVProstoru.clear();
        seznamVeciVProstoru.addAll(hra.getHerniPlan().getAktualniProstor().getSeznamVeci());
    }

    /**
     * Metoda nejdříve vyčistí seznam věcí v batohu (ListView) a vloží do něj aktualizovaný seznam
     */
    @FXML
    private void aktualizujSeznamVeciVBatohu() {
        seznamVeciVBatohu.clear();
        seznamVeciVBatohu.addAll(hra.getBatoh().getObsahBatohu());
    }

    /**
     * Metoda nejdříve vyčistí seznam postav v prostoru a vloží do něj aktualizovaný seznam
     */
    @FXML
    private void aktualizujSeznamPostavVProstoru() {
        seznamPostavVProstoru.clear();
        seznamPostavVProstoru.addAll(hra.getHerniPlan().getAktualniProstor().getSeznamPostav());
    }

    /**
     * Metoda aktualizuje polohu hráče na mapě
     */
    private void aktualizujPolohuHrace() {
        String prostor = hra.getHerniPlan().getAktualniProstor().getNazev();
        hrac.setLayoutX(souradniceProstoru.get(prostor).getX());
        hrac.setLayoutY(souradniceProstoru.get(prostor).getY());
    }

    /**
     * Pokud je výsledkem příkazu konec hry, tak metoda:
     * vypíše epilog
     * zamezí interakci se vstupem, panely a tlačítkem
     */
    private void aktualizujKonecHry() {
        if (hra.konecHry()) {
            vystup.appendText(hra.vratEpilog());
        }

        vstup.setDisable(hra.konecHry());
        tlacitkoOdesli.setDisable(hra.konecHry());
        panelVychodu.setDisable(hra.konecHry());
        panelVeciVProstoru.setDisable(hra.konecHry());
        panelVeciVBatohu.setDisable(hra.konecHry());
        panelPostavVProstoru.setDisable(hra.konecHry());
        labelVychody.setDisable(hra.konecHry());
        labelVeciVBatohu.setDisable(hra.konecHry());
        labelPostavyVProstoru.setDisable(hra.konecHry());
        labelVeciVProstoru.setDisable(hra.konecHry());
    }

    /**
     * Metoda zachytí vstup (zadaný v TextField) jako příkaz a zavolá metodu na jeho zpracování
     */
    @FXML
    private void odesliVstup() {
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
     * Metoda zajistí, aby se po kliknutí na prostor v panelu východů (ListView) do daného prostoru přešlo
     */
    @FXML
    private void klikPanelVychodu() {
        Prostor cil = panelVychodu.getSelectionModel().getSelectedItem();
        Platform.runLater(() -> vstup.requestFocus());
        if (cil == null) return;
        String prikaz = PrikazJdi.NAZEV + " " + cil.getNazev();
        zpracujPrikaz(prikaz);
    }

    /**
     * Metoda zajistí, aby se po kliknutí na věc v panelu věcí v prostoru
     * věc buď sebrala nebo koupila (podle toho, kde se hráč nachází)
     */
    @FXML
    private void klikPanelVeciVProstoru() {
        Vec cil = panelVeciVProstoru.getSelectionModel().getSelectedItem();
        Platform.runLater(() -> vstup.requestFocus());
        if (cil == null) return;
        String prikaz;
        if (hra.getHerniPlan().getAktualniProstor().getNazev().equals("lidl") || hra.getHerniPlan().getAktualniProstor().getNazev().equals("trafika")) {
            prikaz = PrikazKup.NAZEV + " " + cil.getJmeno();
        } else {
            prikaz = PrikazSeber.NAZEV + " " + cil.getJmeno();
        }
        zpracujPrikaz(prikaz);
    }

    /**
     * Metoda zajistí, aby se po kliknutí na věc v panelu věcí v batohu
     * věc buď vyndala nebo vyměnila (podle toho, kde se hráč nachází)
     */
    @FXML
    private void klikPanelVeciVBatohu() {
        Vec cil = panelVeciVBatohu.getSelectionModel().getSelectedItem();
        Platform.runLater(() -> vstup.requestFocus());
        if (cil == null) return;
        String prikaz;
        if (hra.getHerniPlan().getAktualniProstor().getNazev().equals("lidl")) {
            prikaz = PrikazVymen.NAZEV + " " + cil.getJmeno();
        } else {
            prikaz = PrikazVyndej.NAZEV + " " + cil.getJmeno();
        }
        zpracujPrikaz(prikaz);
    }

    /**
     * Metoda zajistí, aby se po kliknutí na postavu v panelu postav v prostoru
     * s danou postavou promluvilo
     */
    @FXML
    private void klikPanelPostavVProstoru() {
        Postava cil = panelPostavVProstoru.getSelectionModel().getSelectedItem();
        Platform.runLater(() -> vstup.requestFocus());
        if (cil == null) return;
        zpracujPrikaz(PrikazPromluv.NAZEV + " " + cil.getJmeno());
    }

    /**
     * Metoda zajistí, že po kliknutí na "Nápověda" v horní liště (MenuBar)
     * se zobrazí nápověda v podobě vyskakovacího okna (WevView) z html souboru
     */
    @FXML
    private void napovedaKlik() {
        Stage napovedaStage = new Stage();
        WebView wv = new WebView();
        Scene napovedaScena = new Scene(wv);
        napovedaStage.setScene(napovedaScena);
        napovedaStage.show();
        wv.getEngine().load(getClass().getResource("napoveda.html").toExternalForm());
    }

    /**
     * Metoda zajistí, že po kliknutí na položku "Ukončit" a následným potvrzením "OK" se hra zavře
     */
    public void ukoncitHru() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Opravdu chtete ukončit hru? Veškerý postup bude ztracen.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    /**
     * Metoda zajistí, že po kliknutí na položku "Nová hra" a následným potvrzením "OK" se spustí nová hra (hrajeme od znova, vše je v původním stavu)
     */
    public void novaHra() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Opravdu chcete začít od znova? Veškerý postup bude ztracen.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            hra = new Hra();
            vstup.clear();
            vystup.clear();
            initialize();
        }
    }
}
