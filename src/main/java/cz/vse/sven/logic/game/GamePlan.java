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

    private Area aktualniArea;
    private boolean vyhra = false;
    private boolean prohra = false;
    private boolean perfektniVyhra = false;
    private Map<GameChange, Set<Observer>> seznamPozorovatelu = new HashMap<>();

    /**
     * Konstruktor
     */
    public GamePlan() {
        zalozProstoryHry();
        for (GameChange gameChange : GameChange.values()) {
            seznamPozorovatelu.put(gameChange, new HashSet<>());
        }
    }

    /**
     * Vytváří jednotlivé prostory a propojuje je pomocí východů.
     * Dále vytváří věci a postavy a vkládá je do prostorů
     * Jako výchozí aktuální prostor nastaví domeček.
     */
    private void zalozProstoryHry() {
        // vytvářejí se jednotlivé prostory
        Area domov = new Area("domov", "Domov", "pod mostem, kde Sven bydlí.");
        Area jidelna = new Area("jidelna", "Jídelna", "u jídelny, která má právě zavřeno, vedle ní postává váš kamarád Kim.");
        Area smetiste = new Area("smetiste", "Smetiště", "na smetišti.");
        Area pracak = new Area("pracak", "Pracák", "u pracáku.");
        Area sekac = new Area("sekac", "Sekáč", "v sekáči.");
        Area zastavarna = new Area("zastavarna", "Zastavárna", "v zastavárně.");
        Area lidl = new Area("lidl", "Lidl", "v Lidlu, ceník: \nRohlíky - 1   \nPsí granule - 4   \nBezlepkový chleba - 2");
        Area trafika = new Area("trafika", "Trafika", "v trafice, ceník: \nSnus - 1");

        //vytváření jednotlivých věcí
        Item stareHodiny = new Item("StareHodiny", "Staré hodiny", true, false, false, 0);
        Item lahevBranik = new Item("LahevOdBranika", "Láhev od Braníka", true, false, true, 0);
        Item lahevSvijany = new Item("LahevOdSvijan", "Láhev od Svijan", true, false, true, 0);
        Item lahevPlzen = new Item("LahevOdPlzne", "Láhev od Plzně", true, false, true, 0);
        Item automatLahve = new Item("AutomatNaLahve", "Automat na láhve", false, false, false, 0);

        Item snus = new Item("Snus", "Snus", false, true, false, 1);
        Item bezlepkovyChleba = new Item("BezlepkovyChleba", "Bezlepkový chleba", false, true, false, 2);
        Item granule = new Item("PsiGranule", "Psí granule", false, true, false, 4);
        Item rohliky = new Item("Rohliky", "Rohlíky", false, true, false, 1);


        //vytvareni jednotlivych postav
        NPC pepa = new NPC("Pepa", "Pepa");
        NPC kim = new NPC("Kim", "Kim");
        NPC lupic = new NPC("Podezrely", "Podezřelý");
        NPC prodavac = new NPC("Prodavac", "Prodavač");
        NPC zastavarnik = new NPC("Zastavarnik", "Zastavárník");


        // přiřazují se průchody mezi prostory (sousedící prostory)
        domov.setVychod(jidelna);
        jidelna.setVychod(domov);
        jidelna.setVychod(smetiste);
        jidelna.setVychod(lidl);
        jidelna.setVychod(trafika);
        smetiste.setVychod(jidelna);
        smetiste.setVychod(pracak);
        smetiste.setVychod(sekac);
        smetiste.setVychod(zastavarna);
        pracak.setVychod(smetiste);
        pracak.setVychod(sekac);
        pracak.setVychod(zastavarna);
        sekac.setVychod(smetiste);
        sekac.setVychod(pracak);
        sekac.setVychod(zastavarna);
        zastavarna.setVychod(smetiste);
        zastavarna.setVychod(pracak);
        zastavarna.setVychod(sekac);
        trafika.setVychod(jidelna);
        trafika.setVychod(lidl);
        lidl.setVychod(jidelna);
        lidl.setVychod(trafika);

        aktualniArea = domov;  // hra začíná u Svena doma

        // davame veci do prostoru
        smetiste.addVec(stareHodiny);
        smetiste.addVec(lahevBranik);
        smetiste.addVec(lahevSvijany);
        smetiste.addVec(lahevPlzen);
        trafika.addVec(snus);
        lidl.addVec(automatLahve);
        lidl.addVec(bezlepkovyChleba);
        lidl.addVec(granule);
        lidl.addVec(rohliky);

        // davame postavy do prostoru
        domov.addPostava(pepa);
        jidelna.addPostava(kim);
        pracak.addPostava(lupic);
        sekac.addPostava(prodavac);
        zastavarna.addPostava(zastavarnik);
    }

    /**
     * Metoda vrací odkaz na aktuální prostor, ve ktetém se hráč právě nachází.
     *
     * @return aktuální prostor
     */
    public Area getAktualniProstor() {
        return aktualniArea;
    }

    /**
     * Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi prostory,
     * dále upozorní pozorovatele na změnu místnosti
     *
     * @param area nový aktuální prostor
     */
    public void setAktualniProstor(Area area) {
        aktualniArea = area;
        upozorniPozorovatele(GameChange.ZMENA_PROSTORU);
    }

    /**
     * Metoda zjistí stav prohry
     *
     * @return stav prohry
     */
    public boolean isProhra() {
        return prohra;
    }

    /**
     * Metoda mění stav prohry
     *
     * @param stav na který se má prohra změnit
     */
    public void setProhra(boolean stav) {
        this.prohra = stav;
    }

    /**
     * Metoda zjistí stav výhry
     *
     * @return stav výhry
     */
    public boolean isVyhra() {
        return vyhra;
    }

    /**
     * Metoda mění stav výhry
     *
     * @param stav na který se má výhra změnit
     */
    public void setVyhra(boolean stav) {
        this.vyhra = stav;
    }

    /**
     * Metoda zjistí stav perfektní výhry
     *
     * @return stav perfektní prohry
     */
    public boolean isPerfektniVyhra() {
        return perfektniVyhra;
    }

    /**
     * Metoda mění stav perfektní výhry
     *
     * @param stav na který se má perfektní výhra změnit
     */
    public void setPerfektniVyhra(boolean stav) {
        this.perfektniVyhra = stav;
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
