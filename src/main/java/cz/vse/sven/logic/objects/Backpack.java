package cz.vse.sven.logic.objects;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Backpack - player's inventory
 */
public class Backpack {

    private int capacity = 5;
    private final Map<String, Item> itemList;

    /**
     * Constructor
     */
    public Backpack() {
        itemList = new HashMap<>();
    }

    /**
     * Puts an item into the backpack if the backpack is not full
     *
     * @param item to be added
     * @return true if the item was added, false if not
     */
    public boolean putItem(Item item) {
        if (itemList.size() < capacity) {
            itemList.put(item.getName(), item);
            return true;
        }
        return false;
    }

    /**
     * Removes item from the backpack
     *
     * @param item to be removed
     * @return confirmation
     */
    public String removeItem(String item) {
        if (itemList.containsKey(item)) {
            String v = selectItem(item).getFullName();
            itemList.remove(item);
            return " - You handed over " + v;
        }
        return null;
    }

    /**
     * Checks whether the backpack contains given item
     *
     * @param item to be checked
     * @return true/false
     */
    public boolean containsItem(String item) {
        return itemList.containsKey(item);
    }


    /**
     * Finds an item in the backpack and returns it
     *
     * @param item to be found
     * @return item if it is in the backpack, null if not
     */
    public Item selectItem(String item) {
        Item i;
        if (itemList.containsKey(item)) {
            i = itemList.get(item);
            return i;
        }
        return null;
    }

    /**
     * Returns list of items in the backpack
     *
     * @return items list
     */
    public String itemList() {
        StringBuilder itemList = new StringBuilder("items in backpack: ");
        for (String item : this.itemList.keySet()) {
            itemList.append(item).append(" ");
        }
        return itemList.toString();
    }

    /**
     * Returns an unmodifiable collection of items in the backpack
     *
     * @return unmodifiable collection of items in the backpack
     */
    public Collection<Item> getBackpackContents() {
        return Collections.unmodifiableCollection(itemList.values());
    }

    /**
     * Sets the capacity of the backpack
     *
     * @param capacity capacity value
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
