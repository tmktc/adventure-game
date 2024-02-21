package cz.vse.sven.logika.objekty;

import java.util.Objects;

/**
 * Třída Vec - realizuje věci
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class Vec {

    private final String jmeno;
    private final boolean sebratelna;
    private final boolean koupitelna;
    private final boolean vymenitelna;
    private final int cena;


    /**
     * Konstruktor
     *
     * @param jmeno       veci
     * @param sebratelna  jestli jde sebrat
     * @param koupitelna  jestli jde koupit
     * @param vymenitelna jestli jde vymenit
     * @param cena        veci
     */
    public Vec(String jmeno, boolean sebratelna, boolean koupitelna, boolean vymenitelna, int cena) {
        this.jmeno = jmeno;
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
     * Getter pro boolean sebratelna
     *
     * @return true/false
     */
    public boolean isSebratelna() {
        return sebratelna;
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
        Vec vec = (Vec) o;
        return Objects.equals(jmeno, vec.jmeno);
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
