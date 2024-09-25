package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.GamePlan;
import cz.vse.sven.logic.game.Money;
import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.logic.objects.Area;
import cz.vse.sven.logic.objects.Item;

/**
 * Třída PrikazKup implementuje pro hru příkaz kup.
 * příkazem hráč koupí věci v obchodě
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class CommandBuy implements ICommand {

    public static final String NAME = "kup";
    private GamePlan plan;
    private Backpack backpack;
    private Money money;

    /**
     * Konstruktor
     *
     * @param plan   herní plán, ve kterém se bude kupovat
     * @param backpack  do kterého se mají koupené věci vložit
     * @param money které se mají po nákupu odečíst
     */
    public CommandBuy(GamePlan plan, Backpack backpack, Money money) {
        this.plan = plan;
        this.backpack = backpack;
        this.money = money;
    }

    /**
     * Metoda nejdříve zkontroluje, zda se daná věc v prostoru nachází,
     * zkontroluje koupitelnost věci pomocí metody jeVecKoupitelna,
     * pokud máme ke koupi dostatek peněz, tak nákup proběhne
     * dále nastaví koupitelnost věci na false, protože věc již vlastníme
     * a sebratelnost na true, kdybom věc chtěli odložit
     *
     * @param parameters - věc, kterou chceme koupit.
     * @return hlášení, zda se nákup povedl
     */
    @Override
    public String executeCommand(String... parameters) {
        if (parameters.length == 0) {
            return "Nezadali jste název položky, kterou chcete koupit";
        }

        String itemName = parameters[0];
        Area currentArea = plan.getCurrentArea();

        if (currentArea.containsItem(itemName)) {
            Item item = currentArea.isItemPurchasable(itemName);
            if (item == null) {
                return "Taková věc není koupitelná";
            } else {
                if (money.getMoney() >= item.getPrice()) {
                    if (backpack.putItem(item)) {
                        currentArea.removeItem(itemName);
                        money.subtractMoney(item.getPrice());
                        item.setCanBePickedUp(true);
                        item.setPurchasable(false);
                        return "Koupili jste " + item.getFullName() + " za " + item.getPrice() + " Euro";
                    } else {
                        return "Nemáte dostatek místa v batohu";
                    }
                } else {
                    return "Nemáte dostatek peněz ke koupi této věci";
                }
            }
        }
        return "Taková věc tu není";
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
