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
        CommandHelp prikazNapoveda = new CommandHelp(listOfCommands);
        String spravnyText = "Hra má čtyři možné konce. Dva z nich jsou špatné, jeden dobrý a jeden perfektní.\n" +
                "Průběh hry se vždy posune dál díky promluvení s nějakou postavou. Dávejte pozor, co postavy říkají.\n\n" +
                "Možné příkazy: batoh jdi kup ";

        assertEquals(spravnyText, prikazNapoveda.executeCommand());
    }
}