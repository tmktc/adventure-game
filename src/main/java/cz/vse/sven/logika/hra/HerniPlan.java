package cz.vse.sven.logika.hra;

import cz.vse.sven.logika.objekty.Postava;
import cz.vse.sven.logika.objekty.Prostor;
import cz.vse.sven.logika.objekty.Vec;
import cz.vse.sven.main.Observer.Pozorovatel;
import cz.vse.sven.main.Observer.PredmetPozorovani;
import cz.vse.sven.main.Observer.ZmenaHry;

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
public class HerniPlan implements PredmetPozorovani {

    private Prostor aktualniProstor;
    private boolean vyhra = false;
    private boolean prohra = false;
    private boolean perfektniVyhra = false;
    private Map<ZmenaHry, Set<Pozorovatel>> seznamPozorovatelu = new HashMap<>();

    /**
     * Konstruktor
     */
    public HerniPlan() {
        zalozProstoryHry();
        for (ZmenaHry zmenaHry : ZmenaHry.values()) {
            seznamPozorovatelu.put(zmenaHry, new HashSet<>());
        }
    }

    /**
     * Vytváří jednotlivé prostory a propojuje je pomocí východů.
     * Dále vytváří věci a postavy a vkládá je do prostorů
     * Jako výchozí aktuální prostor nastaví domeček.
     */
    private void zalozProstoryHry() {
        // vytvářejí se jednotlivé prostory
        Prostor domov = new Prostor("domov", "Domov","pod mostem, kde Sven bydlí.");
        Prostor jidelna = new Prostor("jidelna", "Jídelna","u jídelny, která má právě zavřeno, vedle ní postává váš kamarád Kim.");
        Prostor smetiste = new Prostor("smetiste", "Smetiště","na smetišti.");
        Prostor pracak = new Prostor("pracak", "Pracák","u pracáku.");
        Prostor sekac = new Prostor("sekac", "Sekáč","v sekáči.");
        Prostor zastavarna = new Prostor("zastavarna", "Zastavárna","v zastavárně.");
        Prostor lidl = new Prostor("lidl", "Lidl","v Lidlu, ceník: \nRohliky - 1   \nPsiGranule - 4   \nBezlepkovyChleba - 2");
        Prostor trafika = new Prostor("trafika", "Trafika","v trafice, ceník: \nSnus - 1");

        //vytváření jednotlivých věcí
        Vec stareHodiny = new Vec("StareHodiny", "Staré Hodiny", true, false, false, 0);
        Vec lahevBranik = new Vec("LahevOdBranika", "Láhev od Braníka",true, false, true, 0);
        Vec lahevSvijany = new Vec("LahevOdSvijan", "Láhev od Svijan",true, false, true, 0);
        Vec lahevPlzen = new Vec("LahevOdPlzne", "Láhev od Plzně",true, false, true, 0);
        Vec automatLahve = new Vec("AutomatNaLahve", "Automat na láhve",false, false, false, 0);

        Vec snus = new Vec("Snus", "Snus",false, true, false, 1);
        Vec bezlepkovyChleba = new Vec("BezlepkovyChleba", "Bezlepkový chleba",false, true, false, 2);
        Vec granule = new Vec("PsiGranule", "Psí granule",false, true, false, 4);
        Vec rohliky = new Vec("Rohliky", "Rohlíky",false, true, false, 1);


        //vytvareni jednotlivych postav
        Postava pepa = new Postava("Pepa", "Pepa");
        Postava kim = new Postava("Kim", "Kim");
        Postava lupic = new Postava("Podezrely", "Podezřelý");
        Postava prodavac = new Postava("Prodavac", "Prodavač");
        Postava zastavarnik = new Postava("Zastavarnik", "Zastavárník");


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

        aktualniProstor = domov;  // hra začíná u Svena doma

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
    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }

    /**
     * Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi prostory,
     * dále upozorní pozorovatele na změnu místnosti
     *
     * @param prostor nový aktuální prostor
     */
    public void setAktualniProstor(Prostor prostor) {
        aktualniProstor = prostor;
        upozorniPozorovatele(ZmenaHry.ZMENA_PROSTORU);
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
