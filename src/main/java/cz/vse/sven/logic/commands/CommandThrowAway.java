package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.GamePlan;
import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.logic.objects.Area;
import cz.vse.sven.logic.objects.Item;

/**
 * Třída PrikazPoloz - implementuje pro hru příkaz vyndej
 * příkaz vyndá věc z batohu a položí ji na zem
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */

public class CommandThrowAway implements ICommand {

    public static final String NAME = "throwaway";
    private GamePlan plan;
    private Backpack backpack;

    /**
     * Konstruktor
     *
     * @param plan  ve kterém se bude pokládat
     * @param backpack ze kterého se má daná věc vyndat
     */
    public CommandThrowAway(GamePlan plan, Backpack backpack) {
        this.plan = plan;
        this.backpack = backpack;
    }

    /**
     * Metoda nejdříve zkontroluje, zda se věc v batohu nachází,
     * pak zkontroluje, jestli se hráč nenachází v lidlu nebo trafice,
     * pak ji odstraní z batohu a vloží ji do aktuálního prostoru
     *
     * @param parameters počet parametrů závisí na konkrétním příkazu.
     * @return zpráva, zda se vyndání zdařilo nebo ne
     */
    @Override
    public String executeCommand(String... parameters) {
        if (parameters.length == 0) {
            return "You must name the item you want to throw away.";
        }

        String itemName = parameters[0];
        Area currentArea = plan.getCurrentArea();
        if (backpack.containsItem(itemName)) {

            if (!(currentArea.getName().equals("lidl") || currentArea.getName().equals("kiosk"))) {
                Item item = backpack.selectItem(itemName);
                backpack.removeItem(itemName);
                currentArea.addItem(item);
                return item.getFullName() + " now lies on the ground.";
            }
            return "You can not throw away items in this area.";


        }
        return "There is no such thing in you backpack.";
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
