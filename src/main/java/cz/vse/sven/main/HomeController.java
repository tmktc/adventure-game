package cz.vse.sven.main;

import cz.vse.sven.logic.game.Game;
import cz.vse.sven.logic.game.IGame;
import cz.vse.sven.logic.objects.NPC;
import cz.vse.sven.logic.objects.Area;
import cz.vse.sven.logic.objects.Item;
import cz.vse.sven.logic.commands.*;
import cz.vse.sven.main.ListCell.ListCellNPC;
import cz.vse.sven.main.ListCell.ListCellArea;
import cz.vse.sven.main.ListCell.ListCellItem;
import cz.vse.sven.main.Observer.GameChange;
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
    private Label labelItemInteractionInfo;

    @FXML
    private Label labelAreaDescription;

    @FXML
    private Label labelMoneyStatus;

    @FXML
    private Label labelCurrentMoney;

    @FXML
    private Label labelItemsInArea;

    @FXML
    private Label labelNPCsInArea;

    @FXML
    private Label labelItemsInBackpack;

    @FXML
    private Label labelExits;

    @FXML
    private ListView<NPC> panelNPCsInArea;

    @FXML
    private ListView<Item> panelItemsInBackpack;

    @FXML
    private ListView<Item> panelItemsInArea;

    @FXML
    private ImageView player;

    @FXML
    private ListView<Area> panelExits;

    @FXML
    private Button buttonHelp;

    private IGame game = new Game();

    private ObservableList<Area> listOfExits = FXCollections.observableArrayList();

    private ObservableList<Item> listOfItemsInArea = FXCollections.observableArrayList();

    private ObservableList<Item> listOfItemsInBackpack = FXCollections.observableArrayList();

    private ObservableList<NPC> listOfNPCsInArea = FXCollections.observableArrayList();

    private Map<String, Point2D> AreaCoordinates = new HashMap<>();

    /**
     * Metoda na začátku hry:
     * spojí panely se seznamy, registruje pozorovatele,
     * do panelu vloží aktualizované seznamy, nastavuje továrnu buňek pro panel východů a věcí v prostoru/batohu
     */
    @FXML
    private void initialize() {
        labelCurrentMoney.setText(game.getMoney());
        panelExits.setItems(listOfExits);
        panelItemsInArea.setItems(listOfItemsInArea);
        panelItemsInBackpack.setItems(listOfItemsInBackpack);
        panelNPCsInArea.setItems(listOfNPCsInArea);
        game.getGamePlan().register(GameChange.AREA_CHANGE, () -> {
            updateListOfExits();
            updatePlayerLocation();
        });
        game.register(GameChange.GAME_END, () -> updateGameEnd());
        game.register(GameChange.ITEM_CHANGE, () -> {
            updateListOfItemsInArea();
            updateListOfItemsInBackpack();
        });
        game.register(GameChange.NPC_CHANGE, () -> updateListOfNPCsInArea());
        game.register(GameChange.MONEY_CHANGE, () -> updateMoney());
        insertCoordinates();
        updateListOfExits();
        updateListOfItemsInBackpack();
        updateListOfItemsInArea();
        updateListOfNPCsInArea();
        updatePlayerLocation();
        updateGameEnd();
        panelExits.setCellFactory(param -> new ListCellArea());
        panelItemsInArea.setCellFactory(param -> new ListCellItem());
        panelItemsInBackpack.setCellFactory(param -> new ListCellItem());
        panelNPCsInArea.setCellFactory(param -> new ListCellNPC());
    }

    /**
     * Metoda nastaví souřadnice prostorů na mapě
     */
    private void insertCoordinates() {
        AreaCoordinates.put("home", new Point2D(380, 100));
        AreaCoordinates.put("soupKitchen", new Point2D(435, 130));
        AreaCoordinates.put("junkyard", new Point2D(260, 155));
        AreaCoordinates.put("jobCenter", new Point2D(150, 60));
        AreaCoordinates.put("thriftShop", new Point2D(10, 130));
        AreaCoordinates.put("pawnshop", new Point2D(125, 205));
        AreaCoordinates.put("kiosk", new Point2D(590, 110));
        AreaCoordinates.put("lidl", new Point2D(590, 170));
    }

    /**
     * Metoda nejdříve vyčistí seznam východů v panelu východů (ListView) a vloží do něj aktualizovaný seznam
     */
    @FXML
    private void updateListOfExits() {
        listOfExits.clear();
        listOfExits.addAll(game.getGamePlan().getCurrentArea().getExits());
    }

    /**
     * Metoda nejdřívé vyčistí seznam věcí v prostoru (ListView) a vloží do něj aktualizovaný seznam
     */
    @FXML
    private void updateListOfItemsInArea() {
        listOfItemsInArea.clear();
        listOfItemsInArea.addAll(game.getGamePlan().getCurrentArea().getItemList());
    }

    /**
     * Metoda nejdříve vyčistí seznam věcí v batohu (ListView) a vloží do něj aktualizovaný seznam
     */
    @FXML
    private void updateListOfItemsInBackpack() {
        listOfItemsInBackpack.clear();
        listOfItemsInBackpack.addAll(game.getBackpack().getBackpackContents());
    }

    /**
     * Metoda nejdříve vyčistí seznam postav v prostoru a vloží do něj aktualizovaný seznam
     */
    @FXML
    private void updateListOfNPCsInArea() {
        listOfNPCsInArea.clear();
        listOfNPCsInArea.addAll(game.getGamePlan().getCurrentArea().getNPCList());
    }

    /**
     * Metoda aktualizuje polohu hráče na mapě, aktualizuje popis prostoru
     */
    @FXML
    private void updatePlayerLocation() {
        String area = game.getGamePlan().getCurrentArea().getName();
        player.setLayoutX(AreaCoordinates.get(area).getX());
        player.setLayoutY(AreaCoordinates.get(area).getY());
        labelAreaDescription.setText("You are " + game.getGamePlan().getCurrentArea().getDescription().replaceAll("\n", ""));
    }

    /**
     * Metoda aktualizuje ukazatel peněz
     */
    @FXML
    private void updateMoney() {
        labelCurrentMoney.setText(game.getMoney());
    }

    /**
     * Pokud je výsledkem příkazu konec hry, tak metoda:
     * zamezí interakci s panely a tlačítkem, ukáže konečné okno
     */
    @FXML
    private void updateGameEnd() {
        buttonHelp.setDisable(game.gameEnd());
        panelExits.setDisable(game.gameEnd());
        panelItemsInArea.setDisable(game.gameEnd());
        panelItemsInBackpack.setDisable(game.gameEnd());
        panelNPCsInArea.setDisable(game.gameEnd());
        labelExits.setDisable(game.gameEnd());
        labelItemsInBackpack.setDisable(game.gameEnd());
        labelNPCsInArea.setDisable(game.gameEnd());
        labelItemsInArea.setDisable(game.gameEnd());
        labelMoneyStatus.setDisable(game.gameEnd());
        labelCurrentMoney.setDisable(game.gameEnd());

        if (game.gameEnd()) {
            gameFinish();
        }
    }

    /**
     * Metoda nechá zpracovat zadaný příkaz "jdi" metodou ve třídě "Hra" a vypíše výsledek zadaného příkazu
     * dále vymaže obsah hlášení při interakci s věcmi
     *
     * @param command který se má zpracovat
     */
    @FXML
    private void processAreaTransition(String command) {
        labelItemInteractionInfo.setText("");
        game.processCommand(command);
    }

    /**
     * Metoda nechá zpracovat zadaný příkaz "seber"/"vymen"/"kup"/"vyndej" metodou ve třídě "Hra"
     * a aktualizuje hlášení při interakci s věcí
     *
     * @param prikaz který se má zpracovat
     */
    @FXML
    private void processItemInteraction(String prikaz) {
        String info = game.processCommand(prikaz);
        labelItemInteractionInfo.setText(info);
    }

    /**
     * Metoda nechá zpracovat zadaný příkaz "promluv" metodou ve třídě "Hra" a výsledek vrátí v podobě vyskakovacího okna
     *
     * @param command který se má zpracovat
     */
    @FXML
    private void processDialogWindow(String command) {
        String dialog = game.processCommand(command);
        Alert dialogWindow = new Alert(Alert.AlertType.INFORMATION);
        dialogWindow.setTitle("Dialogue");
        dialogWindow.setHeaderText(dialog);
        dialogWindow.show();
    }

    /**
     * Metoda nechá zpracovat zadaný příkaz metodou ve třídě "Hra"
     *
     * @param command který se má zpracovat
     */
    private void processDialog(String command) {
        game.processCommand(command);
    }

    /**
     * Metoda zajistí, aby se po kliknutí na prostor v panelu východů (ListView) do daného prostoru přešlo
     */
    @FXML
    private void clickExitsPanel() {
        Area target = panelExits.getSelectionModel().getSelectedItem();
        if (target == null) return;
        String command = CommandGo.NAME + " " + target.getName();
        processAreaTransition(command);
    }

    /**
     * Metoda zajistí, aby se po kliknutí na věc v panelu věcí v prostoru
     * věc buď sebrala nebo koupila (podle toho, kde se hráč nachází)
     */
    @FXML
    private void clickItemsInAreaPanel() {
        Item target = panelItemsInArea.getSelectionModel().getSelectedItem();
        if (target == null) return;
        String command;
        if (game.getGamePlan().getCurrentArea().getName().equals("lidl") || game.getGamePlan().getCurrentArea().getName().equals("kiosk")) {
            command = CommandBuy.NAME + " " + target.getName();
        } else {
            command = CommandPickUp.NAME + " " + target.getName();
        }
        processItemInteraction(command);
    }

    /**
     * Metoda zajistí, aby se po kliknutí na věc v panelu věcí v batohu
     * věc buď vyndala nebo vyměnila (podle toho, kde se hráč nachází)
     */
    @FXML
    private void clickItemsInBackpackPanel() {
        Item target = panelItemsInBackpack.getSelectionModel().getSelectedItem();
        if (target == null) return;
        String command;
        if (game.getGamePlan().getCurrentArea().getName().equals("lidl")) {
            command = CommandReturn.NAME + " " + target.getName();
        } else {
            command = CommandThrowAway.NAME + " " + target.getName();
        }
        processItemInteraction(command);
    }

    /**
     * Metoda zajistí, aby se po kliknutí na postavu v panelu postav v prostoru
     * s danou postavou promluvilo
     * <p>
     * Pokud se daným promluvením dohraje hra, tak se zavolá taková metoda zpracování příkazu, která nevytvoří okno dialogu.
     * (protože se již ukazuje okno s epilogem, tak by se dané okno s dialogem ukázalo až v případné nové hře - což nechceme)
     */
    @FXML
    private void clickNPCsInAreaPanel() {
        NPC target = panelNPCsInArea.getSelectionModel().getSelectedItem();
        if (target == null) return;
        String command = CommandTalk.NAME + " " + target.getName();

        if (
                (game.getGamePlan().getCurrentArea().containsNPC("Pepa") && (game.getProgressInstance().getProgress() >= 6))
                        || (game.getGamePlan().getCurrentArea().containsNPC("Suspect") && game.getProgressInstance().getProgress() == 3)
        ) {
            processDialog(command);
        } else {
            processDialogWindow(command);
        }
    }

    /**
     * Metoda zajistí, že po kliknutí na "Nápověda" v horní liště (MenuBar)
     * se zobrazí nápověda v podobě vyskakovacího okna (WevView) z html souboru
     */
    @FXML
    private void clickHelp() {
        Stage helpStage = new Stage();
        WebView wv = new WebView();
        Scene helpScene = new Scene(wv);
        helpStage.setScene(helpScene);
        helpStage.show();
        wv.getEngine().load(getClass().getResource("help.html").toExternalForm());
    }

    /**
     * Metoda zobrazí okno s epilogem a nabídne buď ukončení hry nebo spuštění nové
     */
    @FXML
    private void gameFinish() {
        Alert end = new Alert(Alert.AlertType.CONFIRMATION);
        end.setTitle("End of the game");
        end.setContentText("This is the end of the game, thank you for playing!");
        end.setHeaderText(game.returnEpilogue());

        ButtonType newGame = new ButtonType("New game");
        ButtonType exit = new ButtonType("Exit");
        end.getButtonTypes().setAll(newGame, exit);

        Optional<ButtonType> result = end.showAndWait();
        if (result.isPresent() && result.get() == exit) {
            exitGame();
        } else if (result.isPresent() && result.get() == newGame) {
            newGame();
        }
    }

    /**
     * Metoda zajistí, že po kliknutí na "Ukončit" se hra zavře
     */
    @FXML
    private void exitGame() {
        Platform.exit();
    }

    /**
     * Metoda zajistí, že po kliknutí na položku "Nová hra" se spustí nová hra (hrajeme od znova, vše je v původním stavu)
     */
    @FXML
    private void newGame() {
        game = new Game();
        initialize();
    }
}
