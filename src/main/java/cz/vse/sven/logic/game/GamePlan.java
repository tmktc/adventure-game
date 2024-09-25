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
 * Class HerniPlan - třída představující mapu a stav adventury.
 * <p>
 * Tato třída inicializuje prvky ze kterých se hra skládá:
 * vytváří všechny prostory,
 * propojuje je vzájemně pomocí východů
 * vytváří všechny věci a postavy a vkládá je do prostorů
 * a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 * Dále upravuje a kontroluje stavy možných konců hry
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Tomáš Kotouč
 * @version březen 2024
 */
public class GamePlan implements Observable {

    private Area currentArea;
    private boolean win = false;
    private boolean loss = false;
    private boolean perfectWin = false;
    private Map<GameChange, Set<Observer>> listOfObservers = new HashMap<>();

    /**
     * Konstruktor
     */
    public GamePlan() {
        setUpGame();
        for (GameChange gameChange : GameChange.values()) {
            listOfObservers.put(gameChange, new HashSet<>());
        }
    }

    /**
     * Vytváří jednotlivé prostory a propojuje je pomocí východů.
     * Dále vytváří věci a postavy a vkládá je do prostorů
     * Jako výchozí aktuální prostor nastaví domeček.
     */
    private void setUpGame() {
        // vytvářejí se jednotlivé prostory
        Area home = new Area("home", "Home", "pod mostem, kde Sven bydlí.");
        Area soupKitchen = new Area("soupKitchen", "Soup kitchen", "u jídelny, která má právě zavřeno, vedle ní postává váš kamarád Kim.");
        Area junkyard = new Area("junkyard", "Junkyard", "na smetišti.");
        Area jobCenter = new Area("jobCenter", "Job center", "u pracáku.");
        Area thriftShop = new Area("thriftShop", "Thrift Shop", "v sekáči.");
        Area pawnshop = new Area("pawnshop", "Pawnshop", "v zastavárně.");
        Area lidl = new Area("lidl", "Lidl", "v Lidlu, ceník: \nRohlíky - 1   \nPsí granule - 4   \nBezlepkový chleba - 2");
        Area kiosk = new Area("kiosk", "Kiosk", "v trafice, ceník: \nSnus - 1");

        //vytváření jednotlivých věcí
        Item oldClock = new Item("oldClock", "Old clock", true, false, false, 0);
        Item branikBottle = new Item("branikBottle", "Branik bottle", true, false, true, 0);
        Item svijanyBottle = new Item("svijanyBottle", "Svijany bottle", true, false, true, 0);
        Item pilsnerBottle = new Item("pilsnerBottle", "Pilsner bottle", true, false, true, 0);
        Item bottleMachine = new Item("botleMachine", "Bottle machine", false, false, false, 0);

        Item snus = new Item("snus", "Snus", false, true, false, 1);
        Item glutenFreeBread = new Item("glutenFreeBread", "Gluten-free bread", false, true, false, 2);
        Item dogFood = new Item("dogFood", "Dog Food", false, true, false, 4);
        Item bagels = new Item("bagels", "Bagels", false, true, false, 1);


        //vytvareni jednotlivych postav
        NPC pepa = new NPC("pepa", "Pepa");
        NPC kim = new NPC("kim", "Kim");
        NPC suspect = new NPC("suspect", "Suspect");
        NPC shopAssistant = new NPC("shopAssistant", "Shop assistant");
        NPC pawnbroker = new NPC("pawnbroker", "Pawnbroker");


        // přiřazují se průchody mezi prostory (sousedící prostory)
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

        currentArea = home;  // hra začíná u Svena doma

        // davame veci do prostoru
        junkyard.addItem(oldClock);
        junkyard.addItem(branikBottle);
        junkyard.addItem(svijanyBottle);
        junkyard.addItem(pilsnerBottle);
        kiosk.addItem(snus);
        lidl.addItem(bottleMachine);
        lidl.addItem(glutenFreeBread);
        lidl.addItem(dogFood);
        lidl.addItem(bagels);

        // davame postavy do prostoru
        home.addNPC(pepa);
        soupKitchen.addNPC(kim);
        jobCenter.addNPC(suspect);
        thriftShop.addNPC(shopAssistant);
        pawnshop.addNPC(pawnbroker);
    }

    /**
     * Metoda vrací odkaz na aktuální prostor, ve ktetém se hráč právě nachází.
     *
     * @return aktuální prostor
     */
    public Area getCurrentArea() {
        return currentArea;
    }

    /**
     * Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi prostory,
     * dále upozorní pozorovatele na změnu místnosti
     *
     * @param area nový aktuální prostor
     */
    public void setCurrentArea(Area area) {
        currentArea = area;
        notifyObserver(GameChange.AREA_CHANGE);
    }

    /**
     * Metoda zjistí stav prohry
     *
     * @return stav prohry
     */
    public boolean isLoss() {
        return loss;
    }

    /**
     * Metoda mění stav prohry
     *
     * @param stav na který se má prohra změnit
     */
    public void setLoss(boolean stav) {
        this.loss = stav;
    }

    /**
     * Metoda zjistí stav výhry
     *
     * @return stav výhry
     */
    public boolean isWin() {
        return win;
    }

    /**
     * Metoda mění stav výhry
     *
     * @param stav na který se má výhra změnit
     */
    public void setWin(boolean stav) {
        this.win = stav;
    }

    /**
     * Metoda zjistí stav perfektní výhry
     *
     * @return stav perfektní prohry
     */
    public boolean isPerfectWin() {
        return perfectWin;
    }

    /**
     * Metoda mění stav perfektní výhry
     *
     * @param stav na který se má perfektní výhra změnit
     */
    public void setPerfectWin(boolean stav) {
        this.perfectWin = stav;
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
