package cz.vse.sven.logic.objects;

import java.util.Objects;

/**
 * Třída Postava - realizuje postavy ve hře
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class NPC {

    private final String name;

    private final String fullName;

    /**
     * Konstruktor
     *
     * @param name postavy
     */
    public NPC(String name, String fullName) {
        this.name = name;
        this.fullName = fullName;
    }

    /**
     * Getter pro jméno postavy
     *
     * @return jméno postavy
     */
    public String getName() {
        return name;
    }

    /**
     * Getter pro jméno s mezerami a diakritikou
     *
     * @return jméno s mezerami a diakritikou
     */
    public String getFullName() {
        return fullName;
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
        NPC NPC = (NPC) o;
        return Objects.equals(name, NPC.name);
    }

    /**
     * Metoda hashCode
     *
     * @return ciselny identifikator instance
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * toString metoda Postavy
     *
     * @return jméno postavy
     */
    @Override
    public String toString() {
        return getName();
    }
}