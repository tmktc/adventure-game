package cz.vse.sven.logic.objects;

import java.util.Objects;

/**
 * Item
 */
public class Item {

    private final String name;
    private final String fullName;
    private final boolean returnable;
    private final int price;
    private boolean canBePickedUp;
    private boolean purchasable;

    /**
     * Constructor
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
     * Name getter
     */
    public String getName() {
        return name;
    }

    /**
     * Full name getter
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * CanBePickedUp getter
     */
    public boolean getCanBePickedUp() {
        return canBePickedUp;
    }

    /**
     * CanBePickedUp setter
     */
    public void setCanBePickedUp(boolean s) {
        this.canBePickedUp = s;
    }

    /**
     * Purchasable getter
     */
    public boolean isPurchasable() {
        return purchasable;
    }

    /**
     * Purchasable setter
     */
    public void setPurchasable(boolean k) {
        this.purchasable = k;
    }

    /**
     * Returnable getter
     */
    public boolean isReturnable() {
        return returnable;
    }

    /**
     * Price getter
     */
    public int getPrice() {
        return price;
    }

    /**
     * equals method - two items are the same if the have the same name
     *
     * @param item to be compared
     * @return true if the items are the same, false if not
     */
    @Override
    public boolean equals(Object item) {
        if (this == item) return true;
        if (item == null || getClass() != item.getClass()) return false;
        Item i = (Item) item;
        return Objects.equals(name, i.name);
    }

    /**
     * hashCode method
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * toString method
     *
     * @return item name
     */
    @Override
    public String toString() {
        return getName();
    }
}
