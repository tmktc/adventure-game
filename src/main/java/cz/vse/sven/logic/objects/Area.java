package cz.vse.sven.logic.objects;

import java.util.*;

/**
 * Trida Prostor - popisuje jednotlivé prostory (místnosti) hry
 * <p>
 * Tato třída je součástí jednoduché textové hry.
 * <p>
 * "Prostor" reprezentuje jedno místo (místnost, prostor, ..) ve scénáři hry.
 * Prostor může mít sousední prostory připojené přes východy. Pro každý východ
 * si prostor ukládá odkaz na sousedící prostor.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Tomáš Kotouč
 * @version březen 2024
 */
public class Area {

    private String name;
    private String fullName;
    private String description;
    private Set<Area> exits;    // obsahuje sousedni mistnosti
    private Map<String, Item> itemList;
    private Map<String, NPC> NPCList;

    /**
     * Vytvoření prostoru se zadaným popisem, např. "kuchyň", "hala", "trávník
     * před domem"
     *
     * @param name nazev prostoru, jednoznačný identifikátor, jedno slovo nebo
     *              víceslovný název bez mezer.
     * @param description Popis prostoru.
     */
    public Area(String name, String fullName, String description) {
        this.name = name;
        this.fullName = fullName;
        this.description = description;
        exits = new HashSet<>();
        itemList = new HashMap<>();
        NPCList = new HashMap<>();
    }

    /**
     * Definuje východ z prostoru (sousední/vedlejsi prostor). Vzhledem k tomu,
     * že je použit Set pro uložení východů, může být sousední prostor uveden
     * pouze jednou (tj. nelze mít dvoje dveře do stejné sousední místnosti).
     * Druhé zadání stejného prostoru tiše přepíše předchozí zadání (neobjeví se
     * žádné chybové hlášení). Lze zadat též cestu ze do sebe sama.
     *
     * @param area prostor, který sousedi s aktualnim prostorem.
     */
    public void setExit(Area area) {
        exits.add(area);
    }

    /**
     * Metoda equals pro porovnání dvou prostorů. Překrývá se metoda equals ze
     * třídy Object. Dva prostory jsou shodné, pokud mají stejný název. Tato
     * metoda je důležitá z hlediska správného fungování seznamu východů (Set).
     * <p>
     * Bližší popis metody equals je u třídy Object.
     *
     * @param o object, který se má porovnávat s aktuálním
     * @return hodnotu true, pokud má zadaný prostor stejný název, jinak false
     */
    @Override
    public boolean equals(Object o) {
        // porovnáváme zda se nejedná o dva odkazy na stejnou instanci

        if (this == o) {
            return true;
        }
        // porovnáváme jakého typu je parametr
        if (!(o instanceof Area)) {
            return false; // pokud parametr není typu Prostor, vrátíme false
        }
        // přetypujeme parametr na typ Prostor
        Area second = (Area) o;

        //metoda equals třídy java.util.Objects porovná hodnoty obou názvů.
        //Vrátí true pro stejné názvy a i v případě, že jsou oba názvy null,
        //jinak vrátí false.

        return (java.util.Objects.equals(this.name, second.name));
    }

    /**
     * metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object
     */
    @Override
    public int hashCode() {
        int result = 3;
        int nameHash = java.util.Objects.hashCode(this.name);
        result = 37 * result + nameHash;
        return result;
    }

    /**
     * Vrací název prostoru (byl zadán při vytváření prostoru jako parametr
     * konstruktoru)
     *
     * @return název prostoru
     */
    public String getName() {
        return name;
    }

    /**
     * Getter pro název s mezerami a diakritikou
     *
     * @return název s mezerami a diakritikou
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Vrací "dlouhý" popis prostoru
     *
     * @return Dlouhý popis prostoru
     */
    public String longDescription() {
        return "\n\nNacházíš se " + description + "\n\n"
                + exitDescription() + "\n"
                + itemList() + "\n"
                + NPCList() + "\n";
    }

    /**
     * Vrací textový řetězec, který popisuje sousední východy, například:
     * "vychody: hala ".
     *
     * @return Popis východů - názvů sousedních prostorů
     */
    public String exitDescription() {
        String returnedText = "východy: ";
        for (Area neighbor : exits) {
            returnedText += neighbor.getName() + "  ";
        }
        return returnedText;
    }

    /**
     * Vrací prostor, který sousedí s aktuálním prostorem a jehož název je zadán
     * jako parametr. Pokud prostor s udaným jménem nesousedí s aktuálním
     * prostorem, vrací se hodnota null.
     *
     * @param neighborName Jméno sousedního prostoru (východu)
     * @return Prostor, který se nachází za příslušným východem, nebo hodnota
     * null, pokud prostor zadaného jména není sousedem.
     */

    public Area returnNeighboringArea(String neighborName) {
        List<Area> searchedAreas =
                exits.stream()
                        .filter(neighbor -> neighbor.getName().equals(neighborName))
                        .toList();
        if (searchedAreas.isEmpty()) {
            return null;
        } else {
            return searchedAreas.getFirst();
        }
    }

    /**
     * Vrací kolekci obsahující prostory, se kterými tento prostor sousedí.
     * Takto získaný seznam sousedních prostor nelze upravovat (přidávat,
     * odebírat východy) protože z hlediska správného návrhu je to plně
     * záležitostí třídy Prostor.
     *
     * @return Nemodifikovatelná kolekce prostorů (východů), se kterými tento
     * prostor sousedí.
     */
    public Collection<Area> getExits() {
        return Collections.unmodifiableCollection(exits);
    }

    /**
     * Metoda vrátí kolekci věcí nacházejících se v prostoru
     *
     * @return kolekce věcí nacházejících se v prostoru
     */
    public Collection<Item> getItemList() {
        return Collections.unmodifiableCollection(itemList.values());
    }

    /**
     * Metoda vrátí kolekci postav nacházejích se v prostoru
     *
     * @return kolekce postav nacházejících se v prostoru
     */
    public Collection<NPC> getNPCList() {
        return Collections.unmodifiableCollection(NPCList.values());
    }

    /**
     * Přidá věc do prostoru
     *
     * @param item k přidání
     */
    public void addItem(Item item) {
        itemList.put(item.getName(), item);
    }

    /**
     * Odebere věc z prostoru
     *
     * @param item k odebrání
     */
    public void removeItem(String item) {
        itemList.remove(item);
    }

    /**
     * Zkontroluje, zda prostor obsahuje danou věc
     *
     * @param itemName ke zkontrolování
     * @return true/false
     */
    public boolean containsItem(String itemName) {
        return itemList.containsKey(itemName);
    }

    /**
     * Zkontroluje, zda daná věc nacházejíci se v prostoru je sebratelná
     *
     * @param itemName ke zkontrolovaní
     * @return Pokud je sebratelná, tak věc vrátí, pokud ne, vrátí null
     */
    public Item canItemBePickedUp(String itemName) {
        Item item;
        if (itemList.containsKey(itemName)) {
            item = itemList.get(itemName);
            if (item.getCanBePickedUp()) {
                return item;
            }
            return null;
        }
        return null;
    }

    /**
     * Zkontroluje, zda daná věc nacházejíci se v prostoru je koupitelná
     *
     * @param itemName ke zkontrolovaní
     * @return Pokud je koupitelná, tak věc vrátí, pokud ne, vrátí null
     */
    public Item isItemPurchasable(String itemName) {
        Item item;
        if (itemList.containsKey(itemName)) {
            item = itemList.get(itemName);
            if (item.isPurchasable()) {
                return item;
            }
            return null;
        }
        return null;
    }

    /**
     * Vrátí abecedně seřazený seznam věcí v prostoru
     *
     * @return seznam věcí
     */
    public String itemList() {
        List<String> items = new ArrayList<>(itemList.keySet());
        Collections.sort(items);

        String itemList = "věci: ";
        for (String item : items) {
            itemList += item + "  ";
        }
        return itemList;
    }

    /**
     * Vloží postavu do prostoru v případě, že se postava v tomot prostoru ještě nenachází
     *
     * @param NPC k vložení
     */
    public void addNPC(NPC NPC) {
        if (!(NPCList.containsKey(NPC.getName()))) {
            NPCList.put(NPC.getName(), NPC);
        }
    }

    /**
     * Odebere věc z prostoru
     *
     * @param NPCName k odebrání
     */
    public void removeNPC(String NPCName) {
        NPCList.remove(NPCName);
    }

    /**
     * zjistí, zda se postava v prostoru nachází
     *
     * @param NPCName ke kontrole
     * @return true - postava se v prostoru nachazi, false - nenachazi
     */
    public boolean containsNPC(String NPCName) {
        return NPCList.containsKey(NPCName);
    }

    /**
     * Vrátí abecedně seřazený seznam postav v prostoru
     *
     * @return seznam postav
     */
    public String NPCList() {
        List<String> NPCs = new ArrayList<>(NPCList.keySet());
        Collections.sort(NPCs);

        String NPCList = "postavy: ";
        for (String NPCName : NPCs) {
            NPCList += NPCName + "  ";
        }
        return NPCList;
    }

    /**
     * Vrátí popis prostoru
     *
     * @return popis prostoru
     */
    public String getDescription() {
        return description;
    }

    /**
     * toString metoda třídy Prostor
     *
     * @return název prostoru
     */
    @Override
    public String toString() {
        return name;
    }
}
