package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.objects.Backpack;

/**
 * CommandBackpack - returns the list of items in a backpack
 */
public class CommandBackpack implements ICommand {

    private static final String NAME = "backpack";
    private Backpack backpack;

    /**
     * Constructor
     *
     * @param backpack who contents are to be returned
     */
    public CommandBackpack(Backpack backpack) {
        this.backpack = backpack;
    }

    /**
     * Returns backpack contents list
     *
     * @return backpack contents list
     */
    @Override
    public String executeCommand(String... parameters) {
        return backpack.itemList();
    }

    /**
     * Returns the name of the command
     *
     * @return name of the command
     */
    @Override
    public String getName() {
        return NAME;
    }
}
