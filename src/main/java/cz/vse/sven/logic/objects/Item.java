package cz.vse.sven.logic.objects;

import java.util.Objects;

/**
 * Třída Vec - realizuje věci
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class Item {

    private final String jmeno;
    private final String jmenoCele;
    private final boolean vymenitelna;
    private final int cena;
    private boolean sebratelna;
    private boolean koupitelna;


    /**
     * Konstruktor
     *
     * @param jmeno       veci
     * @param sebratelna  jestli jde sebrat
     * @param koupitelna  jestli jde koupit
     * @param vymenitelna jestli jde vymenit
     * @param cena        veci
     */
    public Item(String jmeno, String jmenoCele, boolean sebratelna, boolean koupitelna, boolean vymenitelna, int cena) {
        this.jmeno = jmeno;
        this.jmenoCele = jmenoCele;
        this.sebratelna = sebratelna;
        this.koupitelna = koupitelna;
        this.vymenitelna = vymenitelna;
        this.cena = cena;
    }

    /**
     * Getter pro jméno věci
     *
     * @return jméno věci
     */
    public String getJmeno() {
        return jmeno;
    }

    /**
     * Getter pro jméno s mezerami a diakritikou
     *
     * @return jméno s mezerami a diakritikou
     */
    public String getJmenoCele() {
        return jmenoCele;
    }

    /**
     * Getter pro boolean sebratelna
     *
     * @return true/false
     */
    public boolean isSebratelna() {
        return sebratelna;
    }

    /**
     * Setter pro boolean hodnotu sebratelna
     *
     * @param s hodnota boolean, která se má nastavit
     */
    public void setSebratelna(boolean s) {
        this.sebratelna = s;
    }

    /**
     * Getter pro boolean koupitelna
     *
     * @return true/false
     */
    public boolean isKoupitelna() {
        return koupitelna;
    }

    /**
     * Setter pro boolean hodnotu koupitelna
     *
     * @param k hodnota boolean, která se má nastavit
     */
    public void setKoupitelna(boolean k) {
        this.koupitelna = k;
    }

    /**
     * Getter pro boolean vymenitelna
     *
     * @return true/false
     */
    public boolean isVymenitelna() {
        return vymenitelna;
    }

    /**
     * Getter pro cenu věci
     *
     * @return cena věci
     */
    public int getCena() {
        return cena;
    }

    /**
     * Dvě věci jsou stejné, pokud mají stejný název
     *
     * @param o věc, která se má porovnat
     * @return true pokud je stejná, false pokud ne
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(jmeno, item.jmeno);
    }

    /**
     * Metoda hashCode
     *
     * @return ciselny identifikator instance
     */
    @Override
    public int hashCode() {
        return Objects.hash(jmeno);
    }

    /**
     * Metoda toString věci
     *
     * @return jméno věci
     */
    @Override
    public String toString() {
        return getJmeno();
    }
}
