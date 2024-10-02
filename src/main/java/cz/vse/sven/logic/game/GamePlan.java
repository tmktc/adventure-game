package cz.vse.sven.logic.game;

import cz.vse.sven.logic.objects.NPC;
import cz.vse.sven.logic.objects.Area;
import cz.vse.sven.logic.objects.Item;
import cz.vse.sven.main.Observer.Observer;
import cz.vse.sven.main.Observer.Observable;
import cz.vse.sven.main.Observer.GameChange;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * GamePlan - sets up the game manages it's state.
 * It initializes game objects (areas, items, NPCs) and connects them.
 * It keeps track of current area.
 * It sets and checks game endings.
 */
@SuppressWarnings("SameParameterValue")
public class GamePlan implements Observable {

    private Area currentArea;
    private boolean win = false;
    private boolean loss = false;
    private boolean perfectWin = false;
    private final Map<GameChange, Set<Observer>> listOfObservers = new HashMap<>();

    /**
     * Constructor
     */
    public GamePlan() {
        setUpGame();
        for (GameChange gameChange : GameChange.values()) {
            listOfObservers.put(gameChange, new HashSet<>());
        }
    }

    /**
     * It initializes game objects (areas, items, NPCs) and connects them.
     */
    private void setUpGame() {
        // Areas
        Area home = new Area("home", "Home", "under the bridge, where Sven lives.");
        Area soupKitchen = new Area("soupKitchen", "Soup kitchen", "next to the Soup kitchen, which is closed for today, you see your friend Kim.");
        Area junkyard = new Area("junkyard", "Junkyard", "next to a Junkyard.");
        Area jobCenter = new Area("jobCenter", "Job center", "next to the Job center.");
        Area thriftShop = new Area("thriftShop", "Thrift Shop", "in the Thrift shop.");
        Area pawnshop = new Area("pawnshop", "Pawnshop", "in the Pawnshop.");
        Area lidl = new Area("lidl", "Lidl", "in Lidl, prices: \nBagels - 1   \nDog food - 4   \nGluten-free bread - 2");
        Area kiosk = new Area("kiosk", "Kiosk", "in the kiosk, prices: \nSnus - 1");

        // Items
        Item oldClock = new Item("oldClock", "Old clock", true, false, false, 0);
        Item branikBottle = new Item("branikBottle", "Branik bottle", true, false, true, 0);
        Item svijanyBottle = new Item("svijanyBottle", "Svijany bottle", true, false, true, 0);
        Item pilsnerBottle = new Item("pilsnerBottle", "Pilsner bottle", true, false, true, 0);
        Item bottleMachine = new Item("bottleMachine", "Bottle machine", false, false, false, 0);
        Item snus = new Item("snus", "Snus", false, true, false, 1);
        Item glutenFreeBread = new Item("glutenFreeBread", "Gluten-free bread", false, true, false, 2);
        Item dogFood = new Item("dogFood", "Dog Food", false, true, false, 4);
        Item bagels = new Item("bagels", "Bagels", false, true, false, 1);

        // NPCs
        NPC peppa = new NPC("peppa", "Peppa");
        NPC kim = new NPC("kim", "Kim");
        NPC suspect = new NPC("suspect", "Suspect");
        NPC shopAssistant = new NPC("shopAssistant", "Shop assistant");
        NPC pawnbroker = new NPC("pawnbroker", "Pawnbroker");


        // Area exits
        home.setExit(soupKitchen);
        soupKitchen.setExit(home);
        soupKitchen.setExit(junkyard);
        soupKitchen.setExit(lidl);
        soupKitchen.setExit(kiosk);
        junkyard.setExit(soupKitchen);
        junkyard.setExit(jobCenter);
        junkyard.setExit(thriftShop);
        junkyard.setExit(pawnshop);
        jobCenter.setExit(junkyard);
        jobCenter.setExit(thriftShop);
        jobCenter.setExit(pawnshop);
        thriftShop.setExit(junkyard);
        thriftShop.setExit(jobCenter);
        thriftShop.setExit(pawnshop);
        pawnshop.setExit(junkyard);
        pawnshop.setExit(jobCenter);
        pawnshop.setExit(thriftShop);
        kiosk.setExit(soupKitchen);
        kiosk.setExit(lidl);
        lidl.setExit(soupKitchen);
        lidl.setExit(kiosk);

        currentArea = home;

        // Put items into areas
        junkyard.addItem(oldClock);
        junkyard.addItem(branikBottle);
        junkyard.addItem(svijanyBottle);
        junkyard.addItem(pilsnerBottle);
        kiosk.addItem(snus);
        lidl.addItem(bottleMachine);
        lidl.addItem(glutenFreeBread);
        lidl.addItem(dogFood);
        lidl.addItem(bagels);

        // Put NPCs into areas
        home.addNPC(peppa);
        soupKitchen.addNPC(kim);
        jobCenter.addNPC(suspect);
        thriftShop.addNPC(shopAssistant);
        pawnshop.addNPC(pawnbroker);
    }

    /**
     * Returns current area
     *
     * @return current area
     */
    public Area getCurrentArea() {
        return currentArea;
    }

    /**
     * Sets current area and notifies the observer
     *
     * @param area to be set as new current area
     */
    public void setCurrentArea(Area area) {
        currentArea = area;
        notifyObserver(GameChange.AREA_CHANGE);
    }

    /**
     * Checks loss status
     *
     * @return loss status
     */
    public boolean isLoss() {
        return loss;
    }

    /**
     * Sets loss status
     *
     * @param status true or false
     */
    public void setLoss(boolean status) {
        this.loss = status;
    }

    /**
     * Checks win status
     *
     * @return wins status
     */
    public boolean isWin() {
        return win;
    }

    /**
     * Sets win status
     *
     * @param status true or false
     */
    public void setWin(boolean status) {
        this.win = status;
    }

    /**
     * Check perfect win status
     *
     * @return perfect win status
     */
    public boolean isPerfectWin() {
        return perfectWin;
    }

    /**
     * Sets perfect win status
     *
     * @param status true or false
     */
    public void setPerfectWin(boolean status) {
        this.perfectWin = status;
    }

    /**
     * Adds observer to the list of observers of given game change
     *
     * @param observer to be registered
     */
    @Override
    public void register(GameChange gameChange, Observer observer) {
        listOfObservers.get(gameChange).add(observer);
    }

    /**
     * When called, the update method for every observer in the list for a given game change is called.
     */
    private void notifyObserver(GameChange gameChange) {
        for (Observer observer : listOfObservers.get(gameChange)) {
            observer.update();
        }
    }
}
