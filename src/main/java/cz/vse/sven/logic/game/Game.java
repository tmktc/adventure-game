package cz.vse.sven.logic.game;

import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.logic.commands.*;
import cz.vse.sven.main.Observer.Observer;
import cz.vse.sven.main.Observer.GameChange;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Třída Hra - třída představující logiku adventury.
 * <p>
 * Toto je hlavní třída  logiky aplikace.  Tato třída vytváří instanci třídy HerniPlan, která inicializuje mistnosti hry
 * a vytváří seznam platných příkazů a instance tříd provádějící jednotlivé příkazy.
 * Vypisuje uvítací a ukončovací text hry.
 * Také vyhodnocuje jednotlivé příkazy zadané uživatelem.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Tomáš Kotouč
 * @version březen 2024
 */

public class Game implements IGame {

    private ListOfCommands validCommands;    // obsahuje seznam přípustných příkazů
    private GamePlan gamePlan;
    private boolean gameEnd = false;
    private Backpack backpack;
    private Money money;
    private Progress progress;
    private Map<GameChange, Set<Observer>> listOfObservers = new HashMap<>();

    /**
     * Vytváří hru a inicializuje místnosti (prostřednictvím třídy HerniPlan), seznam platných příkazů a seznam pozorovatelů.
     */
    public Game() {
        gamePlan = new GamePlan();
        backpack = new Backpack();
        money = new Money();
        progress = new Progress();
        validCommands = new ListOfCommands();
        validCommands.insertCommand(new CommandHelp(validCommands));
        validCommands.insertCommand(new CommandGo(gamePlan, progress));
        validCommands.insertCommand(new CommandEnd(this));
        validCommands.insertCommand(new CommandPickUp(gamePlan, backpack));
        validCommands.insertCommand(new CommandBuy(gamePlan, backpack, money));
        validCommands.insertCommand(new CommandBackpack(backpack));
        validCommands.insertCommand(new CommandTalk(gamePlan, backpack, money, progress, this));
        validCommands.insertCommand(new CommandReturn(gamePlan, backpack, money));
        validCommands.insertCommand(new CommandMoney(money));
        validCommands.insertCommand(new CommandThrowAway(gamePlan, backpack));
        for (GameChange gameChange : GameChange.values()) {
            listOfObservers.put(gameChange, new HashSet<>());
        }
    }

    /**
     * Vrátí úvodní zprávu pro hráče.
     */
    public String returnIntroduction() {
        return "\nV této hře hrajete za Svena, který žije pod mostem se svým psem Pepou.\n" +
                "Oba mají hlad, Sven u sebe však žádné jídlo nemá. Peníze mu také chybí.\n" +
                "Rozhodne se, že Pepu nechá doma a vydá se do nedaleké jídelny (která dává bezdomovcům jídlo zdarma).\n" +
                "Jeho hlavním cílem je obstarat jídlo pro sebe a pro Pepu.";
    }

    /**
     * Vrátí závěrečnou zprávu pro hráče.
     */
    public String returnEpilogue() {
        String epilogue = "";
        if (gamePlan.isPerfectWin()) {
            epilogue = "Obstarali jste pro všechny jídlo a Sven si koupil snus.\n" +
                    "Perfektni výhra, gratuluji.\n";
        }
        if (gamePlan.isWin()) {
            epilogue = "Obstarali jste jídlo pro sebe a pro Pepu.\n" +
                    "Kim ale dneska bude o hladu - příště by jste to mohli napravit.\n" +
                    "Výhra, dobrá práce.\n";
        }
        if (gamePlan.isLoss()) {
            if (progress.getProgress() == 3) {
                epilogue = "Sven byl sám na lupiče krátký, lupičovi se podařilo s oblečením utéct." +
                        "\nProhra, hodně štěstí příště.\n";
            } else {
                epilogue = "Nestihli jste koupit pro jídlo pro sebe a pro Pepu" +
                        "\nProhra, hodně štěstí příště.\n";
            }
        }
        return epilogue;
    }

    /**
     * Metoda zpracuje řetězec uvedený jako parametr, rozdělí ho na slovo příkazu a další parametry.
     * Pak otestuje zda příkaz je klíčovým slovem  např. jdi.
     * Pokud ano spustí samotné provádění příkazu
     * a upozorní pozorovatele na kontrolu možné změny věcí a postav v prostoru.
     *
     * @param line text, který zadal uživatel jako příkaz do hry.
     * @return vrací se řetězec, který se má vypsat na obrazovku
     */
    public String processCommand(String line) {
        String[] words = line.split("[ \t]+");
        String commandWord = words[0];
        String[] parameters = new String[words.length - 1];
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = words[i + 1];
        }
        String textToShow;
        if (validCommands.isValidCommand(commandWord)) {
            ICommand command = validCommands.returnCommand(commandWord);
            textToShow = command.executeCommand(parameters);
        } else {
            textToShow = "Neznámý příkaz";
        }
        notifyObserver(GameChange.ITEM_CHANGE);
        notifyObserver(GameChange.NPC_CHANGE);
        notifyObserver(GameChange.MONEY_CHANGE);
        return textToShow;
    }

    /**
     * Vrací true, pokud hra skončila.
     */
    public boolean gameEnd() {
        return gameEnd;
    }

    /**
     * Nastaví, že je konec hry a upozorní na to pozorovatele
     */
    public void setGameEnd() {
        this.gameEnd = true;
        notifyObserver(GameChange.GAME_END);
    }

    /**
     * Metoda vrátí odkaz na herní plán, je využita hlavně v testech,
     * kde se jejím prostřednictvím získává aktualní místnost hry.
     *
     * @return odkaz na herní plán
     */
    public GamePlan getGamePlan() {
        return gamePlan;
    }

    /**
     * Metoda vrátí odkaz na batoh ve hře
     *
     * @return odkaz na batoh
     */
    public Backpack getBackpack() {
        return backpack;
    }

    /**
     * Vrátí instanci třídy progress, která uchovává stav postupu v aktuální instanci hry
     *
     * @return instance třídy progress
     */
    public Progress getProgressInstance() {
        return progress;
    }

    /**
     * Vrátí hodnotu peněz
     *
     * @return hodnota peněz
     */
    public String getMoney() {
        return money.toString();
    }

    /**
     * Metoda přidá pozorovatele do seznamu pozorovatelů dané změny hry
     *
     * @param observer který má být přidán
     */
    @Override
    public void register(GameChange gameChange, Observer observer) {
        listOfObservers.get(gameChange).add(observer);
    }

    /**
     * Pokud je metoda zavolána, tak je pro každého pozorovatele v seznamu dané změny hry zavolána aktualizační metoda
     */
    private void notifyObserver(GameChange gameChange) {
        for (Observer observer : listOfObservers.get(gameChange)) {
            observer.update();
        }
    }
}
