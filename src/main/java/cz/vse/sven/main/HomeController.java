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
 * @version březen 2024
 */
public class HomeController {

    @FXML
    private Label hlaseniPriInterakciSVecmi;

    @FXML
    private Label popisProstoru;

    @FXML
    private Label labelPenizeVKapse;

    @FXML
    private Label ukazatelPenez;

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
    private Button tlacitkoNapoveda;

    private IHra hra = new Hra();

    private ObservableList<Prostor> seznamVychodu = FXCollections.observableArrayList();

    private ObservableList<Vec> seznamVeciVProstoru = FXCollections.observableArrayList();

    private ObservableList<Vec> seznamVeciVBatohu = FXCollections.observableArrayList();

    private ObservableList<Postava> seznamPostavVProstoru = FXCollections.observableArrayList();

    private Map<String, Point2D> souradniceProstoru = new HashMap<>();

    /**
     * Metoda na začátku hry:
     * spojí panely se seznamy, registruje pozorovatele,
     * do panelu vloží aktualizované seznamy, nastavuje továrnu buňek pro panel východů a věcí v prostoru/batohu
     */
    @FXML
    private void initialize() {
        ukazatelPenez.setText(hra.getPenize());
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
        hra.registruj(ZmenaHry.ZMENA_PENEZ, () -> aktualizujPenize());
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
        souradniceProstoru.put("domov", new Point2D(380, 100));
        souradniceProstoru.put("jidelna", new Point2D(435, 130));
        souradniceProstoru.put("smetiste", new Point2D(260, 155));
        souradniceProstoru.put("pracak", new Point2D(150, 60));
        souradniceProstoru.put("sekac", new Point2D(10, 130));
        souradniceProstoru.put("zastavarna", new Point2D(125, 205));
        souradniceProstoru.put("trafika", new Point2D(590, 110));
        souradniceProstoru.put("lidl", new Point2D(590, 170));
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
     * Metoda aktualizuje polohu hráče na mapě, aktualizuje popis prostoru
     */
    @FXML
    private void aktualizujPolohuHrace() {
        String prostor = hra.getHerniPlan().getAktualniProstor().getNazev();
        hrac.setLayoutX(souradniceProstoru.get(prostor).getX());
        hrac.setLayoutY(souradniceProstoru.get(prostor).getY());
        popisProstoru.setText("Nacházíš se " + hra.getHerniPlan().getAktualniProstor().getPopis().replaceAll("\n", ""));
    }

    /**
     * Metoda aktualizuje ukazatel peněz
     */
    @FXML
    private void aktualizujPenize() {
        ukazatelPenez.setText(hra.getPenize());
    }

    /**
     * Pokud je výsledkem příkazu konec hry, tak metoda:
     * zamezí interakci s panely a tlačítkem, ukáže konečné okno
     */
    @FXML
    private void aktualizujKonecHry() {
        tlacitkoNapoveda.setDisable(hra.konecHry());
        panelVychodu.setDisable(hra.konecHry());
        panelVeciVProstoru.setDisable(hra.konecHry());
        panelVeciVBatohu.setDisable(hra.konecHry());
        panelPostavVProstoru.setDisable(hra.konecHry());
        labelVychody.setDisable(hra.konecHry());
        labelVeciVBatohu.setDisable(hra.konecHry());
        labelPostavyVProstoru.setDisable(hra.konecHry());
        labelVeciVProstoru.setDisable(hra.konecHry());
        labelPenizeVKapse.setDisable(hra.konecHry());
        ukazatelPenez.setDisable(hra.konecHry());

        if (hra.konecHry()) {
            dohraniHry();
        }
    }

    /**
     * Metoda nechá zpracovat zadaný příkaz "jdi" metodou ve třídě "Hra" a vypíše výsledek zadaného příkazu
     * dále vymaže obsah hlášení při interakci s věcmi
     *
     * @param prikaz který se má zpracovat
     */
    @FXML
    private void zpracujPrechodDoJinehoProstoru(String prikaz) {
        hlaseniPriInterakciSVecmi.setText("");
        hra.zpracujPrikaz(prikaz);
    }

    /**
     * Metoda nechá zpracovat zadaný příkaz "seber"/"vymen"/"kup"/"vyndej" metodou ve třídě "Hra"
     * a aktualizuje hlášení při interakci s věcí
     *
     * @param prikaz který se má zpracovat
     */
    @FXML
    private void zpracujInterakciSVeci(String prikaz) {
        String hlaseni = hra.zpracujPrikaz(prikaz);
        hlaseniPriInterakciSVecmi.setText(hlaseni);
    }

    /**
     * Metoda nechá zpracovat zadaný příkaz "promluv" metodou ve třídě "Hra" a výsledek vrátí v podobě vyskakovacího okna
     *
     * @param prikaz který se má zpracovat
     */
    @FXML
    private void zpracujDialogOkno(String prikaz) {
        String dialog = hra.zpracujPrikaz(prikaz);
        Alert dialogOkno = new Alert(Alert.AlertType.INFORMATION);
        dialogOkno.setTitle("Dialog");
        dialogOkno.setHeaderText(dialog);
        dialogOkno.show();
    }

    /**
     * Metoda nechá zpracovat zadaný příkaz metodou ve třídě "Hra"
     *
     * @param prikaz který se má zpracovat
     */
    private void zpracujDialog(String prikaz) {
        hra.zpracujPrikaz(prikaz);
    }

    /**
     * Metoda zajistí, aby se po kliknutí na prostor v panelu východů (ListView) do daného prostoru přešlo
     */
    @FXML
    private void klikPanelVychodu() {
        Prostor cil = panelVychodu.getSelectionModel().getSelectedItem();
        if (cil == null) return;
        String prikaz = PrikazJdi.NAZEV + " " + cil.getNazev();
        zpracujPrechodDoJinehoProstoru(prikaz);
    }

    /**
     * Metoda zajistí, aby se po kliknutí na věc v panelu věcí v prostoru
     * věc buď sebrala nebo koupila (podle toho, kde se hráč nachází)
     */
    @FXML
    private void klikPanelVeciVProstoru() {
        Vec cil = panelVeciVProstoru.getSelectionModel().getSelectedItem();
        if (cil == null) return;
        String prikaz;
        if (hra.getHerniPlan().getAktualniProstor().getNazev().equals("lidl") || hra.getHerniPlan().getAktualniProstor().getNazev().equals("trafika")) {
            prikaz = PrikazKup.NAZEV + " " + cil.getJmeno();
        } else {
            prikaz = PrikazSeber.NAZEV + " " + cil.getJmeno();
        }
        zpracujInterakciSVeci(prikaz);
    }

    /**
     * Metoda zajistí, aby se po kliknutí na věc v panelu věcí v batohu
     * věc buď vyndala nebo vyměnila (podle toho, kde se hráč nachází)
     */
    @FXML
    private void klikPanelVeciVBatohu() {
        Vec cil = panelVeciVBatohu.getSelectionModel().getSelectedItem();
        if (cil == null) return;
        String prikaz;
        if (hra.getHerniPlan().getAktualniProstor().getNazev().equals("lidl")) {
            prikaz = PrikazVymen.NAZEV + " " + cil.getJmeno();
        } else {
            prikaz = PrikazVyndej.NAZEV + " " + cil.getJmeno();
        }
        zpracujInterakciSVeci(prikaz);
    }

    /**
     * Metoda zajistí, aby se po kliknutí na postavu v panelu postav v prostoru
     * s danou postavou promluvilo
     * <p>
     * Pokud se daným promluvením dohraje hra, tak se zavolá taková metoda zpracování příkazu, která nevytvoří okno dialogu.
     * (protože se již ukazuje okno s epilogem, tak by se dané okno s dialogem ukázalo až v případné nové hře - což nechceme)
     */
    @FXML
    private void klikPanelPostavVProstoru() {
        Postava cil = panelPostavVProstoru.getSelectionModel().getSelectedItem();
        if (cil == null) return;
        String prikaz = PrikazPromluv.NAZEV + " " + cil.getJmeno();

        if (
                (hra.getHerniPlan().getAktualniProstor().obsahujePostavu("Pepa") && (hra.getProgressInstance().getProgress() >= 6))
                        || (hra.getHerniPlan().getAktualniProstor().obsahujePostavu("Podezrely") && hra.getProgressInstance().getProgress() == 3)
        ) {
            zpracujDialog(prikaz);
        } else {
            zpracujDialogOkno(prikaz);
        }
    }

    /**
     * Metoda zajistí, že po kliknutí na "Nápověda" v horní liště (MenuBar)
     * se zobrazí nápověda v podobě vyskakovacího okna (WevView) z html souboru
     */
    @FXML
    private void klikNapoveda() {
        Stage napovedaStage = new Stage();
        WebView wv = new WebView();
        Scene napovedaScena = new Scene(wv);
        napovedaStage.setScene(napovedaScena);
        napovedaStage.show();
        wv.getEngine().load(getClass().getResource("napoveda.html").toExternalForm());
    }

    /**
     * Metoda zobrazí okno s epilogem a nabídne buď ukončení hry nebo spuštění nové
     */
    @FXML
    private void dohraniHry() {
        Alert konec = new Alert(Alert.AlertType.CONFIRMATION);
        konec.setTitle("Konec hry");
        konec.setContentText("Toto je konec hry, díky za zahrání!");
        konec.setHeaderText(hra.vratEpilog());

        ButtonType novaHra = new ButtonType("Nová hra");
        ButtonType ukoncit = new ButtonType("Ukončit");
        konec.getButtonTypes().setAll(novaHra, ukoncit);

        Optional<ButtonType> result = konec.showAndWait();
        if (result.isPresent() && result.get() == ukoncit) {
            ukoncitHru();
        } else if (result.isPresent() && result.get() == novaHra) {
            novaHra();
        }
    }

    /**
     * Metoda zajistí, že po kliknutí na "Ukončit" se hra zavře
     */
    @FXML
    private void ukoncitHru() {
        Platform.exit();
    }

    /**
     * Metoda zajistí, že po kliknutí na položku "Nová hra" se spustí nová hra (hrajeme od znova, vše je v původním stavu)
     */
    @FXML
    private void novaHra() {
        hra = new Hra();
        initialize();
    }
}
