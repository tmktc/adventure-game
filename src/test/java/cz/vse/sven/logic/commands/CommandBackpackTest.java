package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.logic.objects.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testovací třída PrikazBatohTest slouží k otestování třídy PrikazBatoh
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class CommandBackpackTest {

    private Backpack backpack;


    @BeforeEach
    public void setUp() {
        backpack = new Backpack();
        Item item1 = new Item("item1", "Item1", true, false, false, 0);
        Item item2 = new Item("item2", "Item2", true, false, false, 0);
        backpack.putItem(item1);
        backpack.putItem(item2);
    }

    /**
     * Otestuje, zda příkaz správně vypíše obsah batohu
     */
    @Test
    public void executeCommand() {
        CommandBackpack commandBackpack = new CommandBackpack(backpack);
        assertEquals("items in backpack: item2 item1 ", commandBackpack.executeCommand());

    }
}