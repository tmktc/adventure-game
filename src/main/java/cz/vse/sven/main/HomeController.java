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
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Controller for home.fxml
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

    private final ObservableList<Area> listOfExits = FXCollections.observableArrayList();

    private final ObservableList<Item> listOfItemsInArea = FXCollections.observableArrayList();

    private final ObservableList<Item> listOfItemsInBackpack = FXCollections.observableArrayList();

    private final ObservableList<NPC> listOfNPCsInArea = FXCollections.observableArrayList();

    private final Map<String, Point2D> AreaCoordinates = new HashMap<>();

    /**
     * At the start of a new game:
     * connects panels with lists, registers observers,
     * puts updated lists into panels and sets cell factories for the panels
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
        game.register(GameChange.GAME_END, this::updateGameEnd);
        game.register(GameChange.ITEM_CHANGE, () -> {
            updateListOfItemsInArea();
            updateListOfItemsInBackpack();
        });
        game.register(GameChange.NPC_CHANGE, this::updateListOfNPCsInArea);
        game.register(GameChange.MONEY_CHANGE, this::updateMoney);
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
     * Sets coordinates for the areas on the map
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
     * Clears the list of exits and puts an updated list into the panel of exits
     */
    @FXML
    private void updateListOfExits() {
        listOfExits.clear();
        listOfExits.addAll(game.getGamePlan().getCurrentArea().getExits());
    }

    /**
     * Clears the list of items in the area and puts an updated list into the panel of items in the area
     */
    @FXML
    private void updateListOfItemsInArea() {
        listOfItemsInArea.clear();
        listOfItemsInArea.addAll(game.getGamePlan().getCurrentArea().getItems());
    }

    /**
     * Clears the list of items in the backpack and puts an updated list into the panel of items in the backpack
     */
    @FXML
    private void updateListOfItemsInBackpack() {
        listOfItemsInBackpack.clear();
        listOfItemsInBackpack.addAll(game.getBackpack().getBackpackContents());
    }

    /**
     * Clears the list of NPCs in the area and puts an updated list into the panel of NPCs in the area
     */
    @FXML
    private void updateListOfNPCsInArea() {
        listOfNPCsInArea.clear();
        listOfNPCsInArea.addAll(game.getGamePlan().getCurrentArea().getNPCs());
    }

    /**
     * Updates player's position on the map and the area description
     */
    @FXML
    private void updatePlayerLocation() {
        String area = game.getGamePlan().getCurrentArea().getName();
        player.setLayoutX(AreaCoordinates.get(area).getX());
        player.setLayoutY(AreaCoordinates.get(area).getY());
        labelAreaDescription.setText("You are " + game.getGamePlan().getCurrentArea().getDescription().replaceAll("\n", ""));
    }

    /**
     * Updates the money balance indicator
     */
    @FXML
    private void updateMoney() {
        labelCurrentMoney.setText(game.getMoney());
    }

    /**
     * If a command results in the game end it disables panels and buttons and shows end window
     */
    @FXML
    private void updateGameEnd() {
        buttonHelp.setDisable(game.isGameEnd());
        panelExits.setDisable(game.isGameEnd());
        panelItemsInArea.setDisable(game.isGameEnd());
        panelItemsInBackpack.setDisable(game.isGameEnd());
        panelNPCsInArea.setDisable(game.isGameEnd());
        labelExits.setDisable(game.isGameEnd());
        labelItemsInBackpack.setDisable(game.isGameEnd());
        labelNPCsInArea.setDisable(game.isGameEnd());
        labelItemsInArea.setDisable(game.isGameEnd());
        labelMoneyStatus.setDisable(game.isGameEnd());
        labelCurrentMoney.setDisable(game.isGameEnd());

        if (game.isGameEnd()) {
            gameFinish();
        }
    }

    /**
     * Makes the area change when the player clicks on an area in the Exits panel
     */
    @FXML
    private void clickExitsPanel() {
        Area target = panelExits.getSelectionModel().getSelectedItem();
        if (target == null) return;

        labelItemInteractionInfo.setText("");
        game.processCommand(Go.NAME, target.getName());
    }

    /**
     * Buys or picks up the item after player clicks on it in the Items in the area panel
     */
    @FXML
    private void clickItemsInAreaPanel() {
        Item target = panelItemsInArea.getSelectionModel().getSelectedItem();
        if (target == null) return;
        if (game.getGamePlan().getCurrentArea().getName().equals("lidl") || game.getGamePlan().getCurrentArea().getName().equals("kiosk")) {
            labelItemInteractionInfo.setText(game.processCommand(Buy.NAME, target.getName()));
        } else {
            labelItemInteractionInfo.setText(game.processCommand(PickUp.NAME, target.getName()));
        }
    }

    /**
     * Returns or throws away an item after player clicks on it in the Items in backpack panel
     */
    @FXML
    private void clickItemsInBackpackPanel() {
        Item target = panelItemsInBackpack.getSelectionModel().getSelectedItem();
        if (target == null) return;
        if (game.getGamePlan().getCurrentArea().getName().equals("lidl")) {
            labelItemInteractionInfo.setText(game.processCommand(Return.NAME,target.getName()));
        } else {
            labelItemInteractionInfo.setText(game.processCommand(ThrowAway.NAME, target.getName()));
        }
    }

    /**
     * Starts a dialogue with an NPC after player clicks on it in the NPCs in the area panel
     * If the game is finished with this action, a method that does not display a popup window is called.
     * (The game already shows a game end window, so the dialogue window would potentially show up in different game instance - we don't want that to happen)
     */
    @FXML
    private void clickNPCsInAreaPanel() {
        NPC target = panelNPCsInArea.getSelectionModel().getSelectedItem();
        if (target == null) return;
        if (
                (game.getGamePlan().getCurrentArea().containsNPC("peppa") && (game.getProgressInstance().getProgress() >= 6))
                        || (game.getGamePlan().getCurrentArea().containsNPC("suspect") && game.getProgressInstance().getProgress() == 3)
        ) {
            game.processCommand(Talk.NAME, target.name());
        } else {
            Alert dialogWindow = new Alert(Alert.AlertType.INFORMATION);
            dialogWindow.setTitle("Dialogue");
            dialogWindow.setHeaderText(game.processCommand(Talk.NAME, target.name()));
            dialogWindow.show();
        }
    }

    /**
     * Shows the Help window after player clicks on the Help button
     */
    @FXML
    private void clickHelp() {
        Alert introduction = new Alert(Alert.AlertType.INFORMATION);
        introduction.setTitle("Help");
        introduction.setHeaderText("""
                The game has four possible endings. Two of which are bad, one good and one perfect.
                The game always progresses through talking to an NPC. Pay attention to what NPCs are saying.

                You control the game by clicking on exits, NPCs and items in the side panels.""");
        introduction.show();
    }

    /**
     * Shows game end windows and offers the player to either start a new game or exit (close all game widows)
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
     * Exits the game if the player chooses to
     */
    @FXML
    private void exitGame() {
        Platform.exit();
    }

    /**
     * Starts a new game if the player chooses to
     */
    @FXML
    private void newGame() {
        game = new Game();
        initialize();
    }
}
