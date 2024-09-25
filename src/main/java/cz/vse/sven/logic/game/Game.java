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

    private ListOfCommands platnePrikazy;    // obsahuje seznam přípustných příkazů
    private GamePlan gamePlan;
    private boolean konecHry = false;
    private Backpack backpack;
    private Money money;
    private Progress progress;
    private Map<GameChange, Set<Observer>> seznamPozorovatelu = new HashMap<>();

    /**
     * Vytváří hru a inicializuje místnosti (prostřednictvím třídy HerniPlan), seznam platných příkazů a seznam pozorovatelů.
     */
    public Game() {
        gamePlan = new GamePlan();
        backpack = new Backpack();
        money = new Money();
        progress = new Progress();
        platnePrikazy = new ListOfCommands();
        platnePrikazy.vlozPrikaz(new CommandHelp(platnePrikazy));
        platnePrikazy.vlozPrikaz(new CommandGo(gamePlan, progress));
        platnePrikazy.vlozPrikaz(new CommandEnd(this));
        platnePrikazy.vlozPrikaz(new CommandPickUp(gamePlan, backpack));
        platnePrikazy.vlozPrikaz(new CommandBuy(gamePlan, backpack, money));
        platnePrikazy.vlozPrikaz(new CommandBackpack(backpack));
        platnePrikazy.vlozPrikaz(new CommandTalk(gamePlan, backpack, money, progress, this));
        platnePrikazy.vlozPrikaz(new CommandReturn(gamePlan, backpack, money));
        platnePrikazy.vlozPrikaz(new CommandMoney(money));
        platnePrikazy.vlozPrikaz(new CommandThrowAway(gamePlan, backpack));
        for (GameChange gameChange : GameChange.values()) {
            seznamPozorovatelu.put(gameChange, new HashSet<>());
        }
    }

    /**
     * Vrátí úvodní zprávu pro hráče.
     */
    public String vratUvitani() {
        return "\nV této hře hrajete za Svena, který žije pod mostem se svým psem Pepou.\n" +
                "Oba mají hlad, Sven u sebe však žádné jídlo nemá. Peníze mu také chybí.\n" +
                "Rozhodne se, že Pepu nechá doma a vydá se do nedaleké jídelny (která dává bezdomovcům jídlo zdarma).\n" +
                "Jeho hlavním cílem je obstarat jídlo pro sebe a pro Pepu.";
    }

    /**
     * Vrátí závěrečnou zprávu pro hráče.
     */
    public String vratEpilog() {
        String epilog = "";
        if (gamePlan.isPerfektniVyhra()) {
            epilog = "Obstarali jste pro všechny jídlo a Sven si koupil snus.\n" +
                    "Perfektni výhra, gratuluji.\n";
        }
        if (gamePlan.isVyhra()) {
            epilog = "Obstarali jste jídlo pro sebe a pro Pepu.\n" +
                    "Kim ale dneska bude o hladu - příště by jste to mohli napravit.\n" +
                    "Výhra, dobrá práce.\n";
        }
        if (gamePlan.isProhra()) {
            if (progress.getProgress() == 3) {
                epilog = "Sven byl sám na lupiče krátký, lupičovi se podařilo s oblečením utéct." +
                        "\nProhra, hodně štěstí příště.\n";
            } else {
                epilog = "Nestihli jste koupit pro jídlo pro sebe a pro Pepu" +
                        "\nProhra, hodně štěstí příště.\n";
            }
        }
        return epilog;
    }

    /**
     * Metoda zpracuje řetězec uvedený jako parametr, rozdělí ho na slovo příkazu a další parametry.
     * Pak otestuje zda příkaz je klíčovým slovem  např. jdi.
     * Pokud ano spustí samotné provádění příkazu
     * a upozorní pozorovatele na kontrolu možné změny věcí a postav v prostoru.
     *
     * @param radek text, který zadal uživatel jako příkaz do hry.
     * @return vrací se řetězec, který se má vypsat na obrazovku
     */
    public String zpracujPrikaz(String radek) {
        String[] slova = radek.split("[ \t]+");
        String slovoPrikazu = slova[0];
        String[] parametry = new String[slova.length - 1];
        for (int i = 0; i < parametry.length; i++) {
            parametry[i] = slova[i + 1];
        }
        String textKVypsani;
        if (platnePrikazy.jePlatnyPrikaz(slovoPrikazu)) {
            ICommand prikaz = platnePrikazy.vratPrikaz(slovoPrikazu);
            textKVypsani = prikaz.provedPrikaz(parametry);
        } else {
            textKVypsani = "Neznámý příkaz";
        }
        upozorniPozorovatele(GameChange.ZMENA_VECI);
        upozorniPozorovatele(GameChange.ZMENA_POSTAV);
        upozorniPozorovatele(GameChange.ZMENA_PENEZ);
        return textKVypsani;
    }

    /**
     * Vrací true, pokud hra skončila.
     */
    public boolean konecHry() {
        return konecHry;
    }

    /**
     * Nastaví, že je konec hry a upozorní na to pozorovatele
     */
    public void setKonecHry() {
        this.konecHry = true;
        upozorniPozorovatele(GameChange.KONEC_HRY);
    }

    /**
     * Metoda vrátí odkaz na herní plán, je využita hlavně v testech,
     * kde se jejím prostřednictvím získává aktualní místnost hry.
     *
     * @return odkaz na herní plán
     */
    public GamePlan getHerniPlan() {
        return gamePlan;
    }

    /**
     * Metoda vrátí odkaz na batoh ve hře
     *
     * @return odkaz na batoh
     */
    public Backpack getBatoh() {
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
    public String getPenize() {
        return money.toString();
    }

    /**
     * Metoda přidá pozorovatele do seznamu pozorovatelů dané změny hry
     *
     * @param observer který má být přidán
     */
    @Override
    public void registruj(GameChange gameChange, Observer observer) {
        seznamPozorovatelu.get(gameChange).add(observer);
    }

    /**
     * Pokud je metoda zavolána, tak je pro každého pozorovatele v seznamu dané změny hry zavolána aktualizační metoda
     */
    private void upozorniPozorovatele(GameChange gameChange) {
        for (Observer observer : seznamPozorovatelu.get(gameChange)) {
            observer.aktualizuj();
        }
    }
}
