package cz.vse.sven.logic.objects;

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
public class Backpack {

    private int capacity = 5;
    private Map<String, Item> itemList;

    /**
     * Konstruktor
     */
    public Backpack() {
        itemList = new HashMap<>();
    }

    /**
     * Metoda vkládá věc do batohu pod podmínkou, že v batohu je dostatek místa
     *
     * @param item, kterou chceme do batohu vložit
     * @return true, pokud byla věc do batohu vložena, false, pokud nikoliv
     */
    public boolean putItem(Item item) {
        if (itemList.size() < capacity) {
            itemList.put(item.getName(), item);
            return true;
        }
        return false;
    }

    /**
     * Metoda odstraní věc z batohu - používána při předávání věcí jiným postavám
     *
     * @param item, kterou chceme z batohu odstranit
     * @return zpráva, že věc byla předána
     */
    public String removeItem(String item) {
        if (itemList.containsKey(item)) {
            String v = selectItem(item).getFullName();
            itemList.remove(item);
            return " - Předali jste " + v;
        }
        return null;
    }

    /**
     * Metoda zkontroluje, zda se daná věc v batohu nachází
     *
     * @param itemName, kterou chceme zkontrolovat
     * @return true, pokud ano, false, pokud ne
     */
    public boolean containsItem(String itemName) {
        return itemList.containsKey(itemName);
    }


    /**
     * Metoda najde věc v batohu a vrátí ji
     *
     * @param itemName, kterou chceme najít
     * @return danou věc, pokud v batohu je, null, pokud v batohu není
     */
    public Item selectItem(String itemName) {
        Item itemInBackpack;
        if (itemList.containsKey(itemName)) {
            itemInBackpack = itemList.get(itemName);
            return itemInBackpack;
        }
        return null;
    }

    /**
     * Metoda vrátí vypsaný seznam věcí v batohu
     *
     * @return seznam věcí v batohu
     */
    public String itemList() {
        String itemList = "věci v batohu: ";
        for (String item : this.itemList.keySet()) {
            itemList += item + " ";
        }
        return itemList;
    }

    /**
     * Vrátí kolekci věcí v batohu
     *
     * @return kolekce věcí v batohu
     */
    public Collection<Item> getBackpackContents() {
        return Collections.unmodifiableCollection(itemList.values());
    }

    /**
     * Nastavuje hodnotu kapacita - použito v testech
     *
     * @param capacity na kterou má být nastavena
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
