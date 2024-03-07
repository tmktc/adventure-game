package cz.vse.sven.logika.hra;

import cz.vse.sven.logika.objekty.Batoh;
import cz.vse.sven.logika.prikazy.*;
import cz.vse.sven.main.Observer.Pozorovatel;
import cz.vse.sven.main.Observer.ZmenaHry;

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
 * @version únor 2024
 */

public class Hra implements IHra {

    private SeznamPrikazu platnePrikazy;    // obsahuje seznam přípustných příkazů
    private HerniPlan herniPlan;
    private boolean konecHry = false;
    private Batoh batoh;
    private Penize penize;
    private Progress progress;
    private Map<ZmenaHry, Set<Pozorovatel>> seznamPozorovatelu = new HashMap<>();

    /**
     * Vytváří hru a inicializuje místnosti (prostřednictvím třídy HerniPlan), seznam platných příkazů a seznam pozorovatelů.
     */
    public Hra() {
        herniPlan = new HerniPlan();
        batoh = new Batoh();
        penize = new Penize();
        progress = new Progress();
        platnePrikazy = new SeznamPrikazu();
        platnePrikazy.vlozPrikaz(new PrikazNapoveda(platnePrikazy));
        platnePrikazy.vlozPrikaz(new PrikazJdi(herniPlan, progress));
        platnePrikazy.vlozPrikaz(new PrikazKonec(this));
        platnePrikazy.vlozPrikaz(new PrikazSeber(herniPlan, batoh));
        platnePrikazy.vlozPrikaz(new PrikazKup(herniPlan, batoh, penize));
        platnePrikazy.vlozPrikaz(new PrikazBatoh(batoh));
        platnePrikazy.vlozPrikaz(new PrikazPromluv(herniPlan, batoh, penize, progress, this));
        platnePrikazy.vlozPrikaz(new PrikazVymen(herniPlan, batoh, penize));
        platnePrikazy.vlozPrikaz(new PrikazPenize(penize));
        platnePrikazy.vlozPrikaz(new PrikazVyndej(herniPlan, batoh));
        for (ZmenaHry zmenaHry : ZmenaHry.values()) {
            seznamPozorovatelu.put(zmenaHry, new HashSet<>());
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
        if (herniPlan.isPerfektniVyhra()) {
            epilog = "Obstarali jste pro všechny jídlo a Sven si koupil snus.\n" +
                    "Perfektni výhra, gratuluji.\n";
        }
        if (herniPlan.isVyhra()) {
            epilog = "Obstarali jste jídlo pro sebe a pro Pepu.\n" +
                    "Kim ale dneska bude o hladu - příště by jste to mohli napravit.\n" +
                    "Výhra, dobrá práce.\n";
        }
        if (herniPlan.isProhra()) {
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
     * Vrací true, pokud hra skončila.
     */
    public boolean konecHry() {
        return konecHry;
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
            IPrikaz prikaz = platnePrikazy.vratPrikaz(slovoPrikazu);
            textKVypsani = prikaz.provedPrikaz(parametry);
        } else {
            textKVypsani = "Neznámý příkaz";
        }
        upozorniPozorovatele(ZmenaHry.ZMENA_VECI);
        upozorniPozorovatele(ZmenaHry.ZMENA_POSTAV);
        upozorniPozorovatele(ZmenaHry.ZMENA_PENEZ);
        return textKVypsani;
    }

    /**
     * Nastaví, že je konec hry a upozorní na to pozorovatele
     */
    public void setKonecHry() {
        this.konecHry = true;
        upozorniPozorovatele(ZmenaHry.KONEC_HRY);
    }

    /**
     * Metoda vrátí odkaz na herní plán, je využita hlavně v testech,
     * kde se jejím prostřednictvím získává aktualní místnost hry.
     *
     * @return odkaz na herní plán
     */
    public HerniPlan getHerniPlan() {
        return herniPlan;
    }

    /**
     * Metoda vrátí odkaz na batoh ve hře
     *
     * @return odkaz na batoh
     */
    public Batoh getBatoh() {
        return batoh;
    }

    /**
     * Vrátí instanci třídy peněz, která uchovává stav peněz v aktuální instanci hry
     *
     * @return instance peněz
     */
    public Progress getProgressInstance() {
        return progress;
    }

    /**
     * Vrátí hodnotu peněz
     *
     * @return hodnota peněz
     */
    public String getPenize() {return penize.toString();}

    /**
     * Metoda přidá pozorovatele do seznamu pozorovatelů dané změny hry
     *
     * @param pozorovatel který má být přidán
     */
    @Override
    public void registruj(ZmenaHry zmenaHry, Pozorovatel pozorovatel) {
        seznamPozorovatelu.get(zmenaHry).add(pozorovatel);
    }

    /**
     * Pokud je metoda zavolána, tak je pro každého pozorovatele v seznamu dané změny hry zavolána aktualizační metoda
     */
    private void upozorniPozorovatele(ZmenaHry zmenaHry) {
        for (Pozorovatel pozorovatel : seznamPozorovatelu.get(zmenaHry)) {
            pozorovatel.aktualizuj();
        }
    }
}
