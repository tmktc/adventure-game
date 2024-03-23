package cz.vse.sven.logika.objekty;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Třída Batoh - třída představující inventář hráče
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class Batoh {

    private int kapacita = 5;
    private Map<String, Vec> seznamVeci;

    /**
     * Konstruktor
     */
    public Batoh() {
        seznamVeci = new HashMap<>();
    }

    /**
     * Metoda vkládá věc do batohu pod podmínkou, že v batohu je dostatek místa
     *
     * @param vec, kterou chceme do batohu vložit
     * @return true, pokud byla věc do batohu vložena, false, pokud nikoliv
     */
    public boolean vlozVec(Vec vec) {
        if (seznamVeci.size() < kapacita) {
            seznamVeci.put(vec.getJmeno(), vec);
            return true;
        }
        return false;
    }

    /**
     * Metoda odstraní věc z batohu - používána při předávání věcí jiným postavám
     *
     * @param vec, kterou chceme z batohu odstranit
     * @return zpráva, že věc byla předána
     */
    public String odstranVec(String vec) {
        if (seznamVeci.containsKey(vec)) {
            String v = vyberVec(vec).getJmenoCele();
            seznamVeci.remove(vec);
            return " - Předali jste " + v;
        }
        return null;
    }

    /**
     * Metoda zkontroluje, zda se daná věc v batohu nachází
     *
     * @param jmenoVeci, kterou chceme zkontrolovat
     * @return true, pokud ano, false, pokud ne
     */
    public boolean obsahujeVec(String jmenoVeci) {
        return seznamVeci.containsKey(jmenoVeci);
    }


    /**
     * Metoda najde věc v batohu a vrátí ji
     *
     * @param jmenoVeci, kterou chceme najít
     * @return danou věc, pokud v batohu je, null, pokud v batohu není
     */
    public Vec vyberVec(String jmenoVeci) {
        Vec vecVBatohu;
        if (seznamVeci.containsKey(jmenoVeci)) {
            vecVBatohu = seznamVeci.get(jmenoVeci);
            return vecVBatohu;
        }
        return null;
    }

    /**
     * Metoda vrátí vypsaný seznam věcí v batohu
     *
     * @return seznam věcí v batohu
     */
    public String seznamVeci() {
        String nazvy = "věci v batohu: ";
        for (String jmenoVeci : seznamVeci.keySet()) {
            nazvy += jmenoVeci + " ";
        }
        return nazvy;
    }

    /**
     * Vrátí kolekci věcí v batohu
     *
     * @return kolekce věcí v batohu
     */
    public Collection<Vec> getObsahBatohu() {
        return Collections.unmodifiableCollection(seznamVeci.values());
    }

    /**
     * Nastavuje hodnotu kapacita - použito v testech
     *
     * @param kapacita na kterou má být nastavena
     */
    public void setKapacita(int kapacita) {
        this.kapacita = kapacita;
    }
}
