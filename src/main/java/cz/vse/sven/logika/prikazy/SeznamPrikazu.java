package cz.vse.sven.logika.prikazy;

import java.util.*;

/**
 * Class SeznamPrikazu - eviduje seznam přípustných příkazů adventury.
 * Používá se pro rozpoznávání příkazů
 * a vrácení odkazu na třídu implementující konkrétní příkaz.
 * Každý nový příkaz (instance implementující rozhraní Prikaz) se
 * musí do seznamu přidat metodou vlozPrikaz.
 * <p>
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 * @version pro školní rok 2016/2017
 */
public class SeznamPrikazu {
    private Map<String, IPrikaz> mapaSPrikazy;

    /**
     * Konstruktor
     */
    public SeznamPrikazu() {
        mapaSPrikazy = new HashMap<>();
    }

    /**
     * Vkládá nový příkaz.
     *
     * @param prikaz Instance třídy implementující rozhraní IPrikaz
     */
    public void vlozPrikaz(IPrikaz prikaz) {
        mapaSPrikazy.put(prikaz.getNazev(), prikaz);
    }

    /**
     * Vrací odkaz na instanci třídy implementující rozhraní IPrikaz,
     * která provádí příkaz uvedený jako parametr.
     *
     * @param retezec klíčové slovo příkazu, který chce hráč zavolat
     * @return instance třídy, která provede požadovaný příkaz
     */
    public IPrikaz vratPrikaz(String retezec) {
        return mapaSPrikazy.getOrDefault(retezec, null);
    }

    /**
     * Kontroluje, zda zadaný řetězec je přípustný příkaz.
     *
     * @param retezec Řetězec, který se má otestovat, zda je přípustný příkaz
     * @return Vrací hodnotu true, pokud je zadaný
     * řetězec přípustný příkaz
     */
    public boolean jePlatnyPrikaz(String retezec) {
        return mapaSPrikazy.containsKey(retezec);
    }

    /**
     * Vrací abecedně seřazený seznam přípustných příkazů, jednotlivé příkazy jsou odděleny mezerou.
     *
     * @return Řetězec, který obsahuje seznam přípustných příkazů
     */
    public String vratNazvyPrikazu() {
        List<String> prikazy = new ArrayList<>(mapaSPrikazy.keySet());
        Collections.sort(prikazy);

        String seznam = "";
        for (String slovoPrikazu : prikazy) {
            seznam += slovoPrikazu + " ";
        }
        return seznam;

    }
}
