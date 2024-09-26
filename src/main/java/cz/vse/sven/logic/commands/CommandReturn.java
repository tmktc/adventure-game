package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.GamePlan;
import cz.vse.sven.logic.game.Money;
import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.logic.objects.Area;

/**
 * Třída PrikazVymen - implementuje pro hru příkaz vymen
 * příkaz slouží k vyměnění láhví v automatu
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class CommandReturn implements ICommand {

    public static final String NAME = "return";
    private GamePlan plan;
    private Backpack backpack;
    private Money money;


    /**
     * Konstruktor
     *
     * @param plan   ve kterém se má vyměňovat
     * @param backpack  ze kterého se mají vyměňované věci brát
     * @param money které se mají po výměně zvýšit
     */
    public CommandReturn(GamePlan plan, Backpack backpack, Money money) {
        this.plan = plan;
        this.backpack = backpack;
        this.money = money;
    }

    /**
     * Metoda nejdříve zjistí, zda se věc nachází v batohu
     * dále zjistí, zda se jedná o láhev (zda je vyměnitelná)
     * dále věc vymění
     *
     * @param parameters počet parametrů závisí na konkrétním příkazu.
     * @return hlášení, zda se výměna povedla
     */
    @Override
    public String executeCommand(String... parameters) {
        if (parameters.length == 0) {
            return "You must name the item you want to return.";
        }

        String bottle = parameters[0];
        Area currentArea = plan.getCurrentArea();
        if (currentArea.containsItem("bottleMachine")) {
            if (backpack.containsItem(bottle)) {
                if (backpack.selectItem(bottle).isReturnable()) {
                    String l = backpack.selectItem(bottle).getFullName();
                    backpack.removeItem(bottle);
                    money.addMoney(1);
                    return "You returned " + l + " and got 1 Euro.";
                } else {
                    return "You cannot return this item.";
                }
            } else {
                return "There is no such thing in you backpack.";
            }
        } else {
            return "There is not Bottle machine in here.";
        }
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
