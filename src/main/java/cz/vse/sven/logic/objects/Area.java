package cz.vse.sven.logic.objects;

import java.util.*;

/**
 * Area - different areas the player moves between
 * Each area has at least one neighboring area, the player can only move between neighboring areas
 */
public class Area {

    private String name;
    private String fullName;
    private String description;
    private Set<Area> exits;
    private Map<String, Item> itemList;
    private Map<String, NPC> NPCList;

    /**
     * Constructor
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
     * Puts an area into the exits list of an area
     *
     * @param area to be added into the list
     */
    public void setExit(Area area) {
        exits.add(area);
    }

    /**
     * equals method - two areas are the same if the have the same name
     *
     * @param area to be compared
     * @return true if the name are the same, false if not
     */
    @Override
    public boolean equals(Object area) {
        if (this == area) {
            return true;
        }
        if (!(area instanceof Area)) {
            return false;
        }
        Area second = (Area) area;
        return (java.util.Objects.equals(this.name, second.name));
    }

    /**
     * hashCode method
     */
    @Override
    public int hashCode() {
        int result = 3;
        int nameHash = java.util.Objects.hashCode(this.name);
        result = 37 * result + nameHash;
        return result;
    }

    /**
     * Returns the name of an area
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the full name of an area
     *
     * @return full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Returns the long version of the description
     *
     * @return long description
     */
    public String longDescription() {
        return "\n\nYou are " + description + "\n\n"
                + exitsDescription() + "\n"
                + itemList() + "\n"
                + NPCList() + "\n";
    }

    /**
     * Returns exits description of an area
     *
     * @return all exits of an area
     */
    public String exitsDescription() {
        String returnedText = "exits: ";
        for (Area neighbor : exits) {
            returnedText += neighbor.getName() + "  ";
        }
        return returnedText;
    }

    /**
     * Returns an area, that neighbors the area and has the give name
     * If the two areas do not neighbor, null is returned
     *
     * @param neighborName potential neighboring area
     * @return Neighboring area or null
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
     * Returns an unmodifiable collection of areas exits
     *
     * @return unmodifiable collection of areas exits
     */
    public Collection<Area> getExits() {
        return Collections.unmodifiableCollection(exits);
    }

    /**
     * Returns an unmodifiable collection of areas items
     *
     * @return unmodifiable collection of areas items
     */
    public Collection<Item> getItems() {
        return Collections.unmodifiableCollection(itemList.values());
    }

    /**
     * Returns an unmodifiable collection of areas NPCs
     *
     * @return unmodifiable collection of areas NPCs
     */
    public Collection<NPC> getNPCs() {
        return Collections.unmodifiableCollection(NPCList.values());
    }

    /**
     * Adds an item into the area
     *
     * @param item to be added
     */
    public void addItem(Item item) {
        itemList.put(item.getName(), item);
    }

    /**
     * Removes an item from the area
     *
     * @param item to be removed
     */
    public void removeItem(String item) {
        itemList.remove(item);
    }

    /**
     * Checks whether the area contains given item
     *
     * @param item to be checked
     * @return true/false
     */
    public boolean containsItem(String item) {
        return itemList.containsKey(item);
    }

    /**
     * Checks whether an item in the area can be picked up
     *
     * @param item to be checked
     * @return the item if it can be picked up, null if it can not be picked up
     */
    public Item canItemBePickedUp(String item) {
        Item i;
        if (itemList.containsKey(item)) {
            i = itemList.get(item);
            if (i.getCanBePickedUp()) {
                return i;
            }
            return null;
        }
        return null;
    }

    /**
     * Checks whether an item in the area is purchasable
     *
     * @param item to be checked
     * @return the item if it is purchasable, null if it is not purchasable
     */
    public Item isItemPurchasable(String item) {
        Item i;
        if (itemList.containsKey(item)) {
            i = itemList.get(item);
            if (i.isPurchasable()) {
                return i;
            }
            return null;
        }
        return null;
    }

    /**
     * Returns a list of the items in the area sorted alphabetically
     *
     * @return items list
     */
    public String itemList() {
        List<String> items = new ArrayList<>(itemList.keySet());
        Collections.sort(items);

        String itemList = "items: ";
        for (String item : items) {
            itemList += item + "  ";
        }
        return itemList;
    }

    /**
     * Puts an NPC into the area if the NPC is not already located in the area
     *
     * @param NPC to be put into the area
     */
    public void addNPC(NPC NPC) {
        if (!(NPCList.containsKey(NPC.getName()))) {
            NPCList.put(NPC.getName(), NPC);
        }
    }

    /**
     * Removes NPC from the area
     *
     * @param NPCName to be removed
     */
    public void removeNPC(String NPCName) {
        NPCList.remove(NPCName);
    }

    /**
     * Checks whether the area contains given NPC
     *
     * @param NPC to be checked
     * @return true/false
     */
    public boolean containsNPC(String NPC) {
        return NPCList.containsKey(NPC);
    }

    /**
     * Returns a list of the NPCs in the area sorted alphabetically
     *
     * @return NPCs list
     */
    public String NPCList() {
        List<String> NPCs = new ArrayList<>(NPCList.keySet());
        Collections.sort(NPCs);

        String NPCList = "NPCs: ";
        for (String NPCName : NPCs) {
            NPCList += NPCName + "  ";
        }
        return NPCList;
    }

    /**
     * Returns area's description
     *
     * @return area description
     */
    public String getDescription() {
        return description;
    }

    /**
     * toString method
     *
     * @return area name
     */
    @Override
    public String toString() {
        return name;
    }
}
