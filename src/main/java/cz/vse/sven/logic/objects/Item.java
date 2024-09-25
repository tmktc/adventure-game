package cz.vse.sven.logic.objects;

import java.util.Objects;

/**
 * Třída Vec - realizuje věci
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class Item {

    private final String name;
    private final String fullName;
    private final boolean returnable;
    private final int price;
    private boolean canBePickedUp;
    private boolean purchasable;


    /**
     * Konstruktor
     *
     * @param name       veci
     * @param canBePickedUp  jestli jde sebrat
     * @param purchasable  jestli jde koupit
     * @param returnable jestli jde vymenit
     * @param price        veci
     */
    public Item(String name, String fullName, boolean canBePickedUp, boolean purchasable, boolean returnable, int price) {
        this.name = name;
        this.fullName = fullName;
        this.canBePickedUp = canBePickedUp;
        this.purchasable = purchasable;
        this.returnable = returnable;
        this.price = price;
    }

    /**
     * Getter pro jméno věci
     *
     * @return jméno věci
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
     * Getter pro boolean sebratelna
     *
     * @return true/false
     */
    public boolean getCanBePickedUp() {
        return canBePickedUp;
    }

    /**
     * Setter pro boolean hodnotu sebratelna
     *
     * @param s hodnota boolean, která se má nastavit
     */
    public void setCanBePickedUp(boolean s) {
        this.canBePickedUp = s;
    }

    /**
     * Getter pro boolean koupitelna
     *
     * @return true/false
     */
    public boolean isPurchasable() {
        return purchasable;
    }

    /**
     * Setter pro boolean hodnotu koupitelna
     *
     * @param k hodnota boolean, která se má nastavit
     */
    public void setPurchasable(boolean k) {
        this.purchasable = k;
    }

    /**
     * Getter pro boolean vymenitelna
     *
     * @return true/false
     */
    public boolean isReturnable() {
        return returnable;
    }

    /**
     * Getter pro cenu věci
     *
     * @return cena věci
     */
    public int getPrice() {
        return price;
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
        return Objects.equals(name, item.name);
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
     * Metoda toString věci
     *
     * @return jméno věci
     */
    @Override
    public String toString() {
        return getName();
    }
}
