package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.GamePlan;
import cz.vse.sven.logic.game.Money;
import cz.vse.sven.logic.game.Progress;
import cz.vse.sven.logic.objects.Backpack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testovací třída PrikazNapovedaTest slouží k otestování třídy PrikazNapoveda
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class CommandHelpTest {

    private ListOfCommands listOfCommands;


    @BeforeEach
    public void setUp() {
        GamePlan plan = new GamePlan();
        Progress progress = new Progress();
        Backpack backpack = new Backpack();
        Money money = new Money();

        listOfCommands = new ListOfCommands();
        listOfCommands.insertCommand(new CommandGo(plan, progress));
        listOfCommands.insertCommand(new CommandBackpack(backpack));
        listOfCommands.insertCommand(new CommandBuy(plan, backpack, money));

    }

    /**
     * Otestuje, zda příkaz správně vypíše nápovědu
     */
    @Test
    public void executeCommand() {
        CommandHelp commandHelp = new CommandHelp(listOfCommands);
        String spravnyText = "The game has four possible endings. Two of which are bad, one good and one perfect.\n" +
                "The game always progresses through talking to an NPC. Pay attention to what NPCs are saying.\n\n" +
                "Available commands: backpack buy go ";

        assertEquals(spravnyText, commandHelp.executeCommand());
    }
}