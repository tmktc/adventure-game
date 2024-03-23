package cz.vse.sven.logika.objekty;

import java.util.Objects;

/**
 * Třída Postava - realizuje postavy ve hře
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class Postava {

    private final String jmeno;

    private final String jmenoCele;

    /**
     * Konstruktor
     *
     * @param jmeno postavy
     */
    public Postava(String jmeno, String jmenoCele) {
        this.jmeno = jmeno;
        this.jmenoCele = jmenoCele;
    }

    /**
     * Getter pro jméno postavy
     *
     * @return jméno postavy
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
     * Dvě postavy jsou stejné, pokud mají stejné jméno
     *
     * @param o postava, která se má porovnat
     * @return true pokud je stejná, false pokud ne
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Postava postava = (Postava) o;
        return Objects.equals(jmeno, postava.jmeno);
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
     * toString metoda Postavy
     *
     * @return jméno postavy
     */
    @Override
    public String toString() {
        return getJmeno();
    }
}