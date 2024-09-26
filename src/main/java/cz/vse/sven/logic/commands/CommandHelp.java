package cz.vse.sven.logic.commands;

/**
 * Třída PrikazNapoveda implementuje pro hru příkaz napoveda.
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author Jarmila Pavlickova, Luboš Pavlíček
 * @version pro školní rok 2016/2017
 */
public class CommandHelp implements ICommand {

    private static final String NAME = "help";
    private ListOfCommands validCommands;

    /**
     * Konstruktor třídy
     *
     * @param validCommands seznam příkazů,
     *                      které je možné ve hře použít,
     *                      aby je nápověda mohla zobrazit uživateli.
     */
    public CommandHelp(ListOfCommands validCommands) {
        this.validCommands = validCommands;
    }

    /**
     * Vrací seznam dostupných příkazů
     *
     * @return napoveda ke hre
     */
    @Override
    public String executeCommand(String... parameters) {
        return "The game has four possible endings. Two of which are bad, one good and one perfect.\n" +
                "The game always progresses through talking to an NPC. Pay attention to what NPCs are saying.\n\n" +
                "Available commands: " + validCommands.returnCommandNames();
    }

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     * <p>
     * @ return nazev prikazu
     */
    @Override
    public String getName() {
        return NAME;
    }
}
