package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.GamePlan;
import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.logic.objects.Area;
import cz.vse.sven.logic.objects.Item;

/**
 * Třída PrikazSeber - implementuje pro hru příkaz jdi.
 * příkazem hráč sebere vybranou věc
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class CommandPickUp implements ICommand {

    public static final String NAZEV = "seber";
    private GamePlan plan;
    private Backpack backpack;


    /**
     * Konstruktor
     *
     * @param plan  ve kterém se bude sbírat
     * @param backpack do kterého se mají sebrané věci vložit
     */
    public CommandPickUp(GamePlan plan, Backpack backpack) {
        this.plan = plan;
        this.backpack = backpack;
    }

    /**
     * Metoda nejdříve zjistí, zda se věc v aktuálním prostoru nachází,
     * dále pomocí metody jeVecSebratelna zjistí, zda je sebratelná,
     * pokud má hráč dostatek místa v batohu, tak se věc do batohu vloží
     *
     * @param parameters - věc, co chceme sebrat
     * @return hlášení, zda se sebrání povedlo
     */
    @Override
    public String executeCommand(String... parameters) {
        if (parameters.length == 0) {
            return "Nezadali jste název věci, kterou chcete sebrat";
        }

        String jmenoVeci = parameters[0];
        Area aktualniArea = plan.getCurrentArea();
        if (aktualniArea.containsItem(jmenoVeci)) {
            Item item = aktualniArea.canItemBePickedUp(jmenoVeci);
            if (item == null) {
                return "Takovou věc nelze sebrat";
            } else {
                if (backpack.putItem(item)) {
                    aktualniArea.removeItem(jmenoVeci);
                    return "Sebrali jste " + item.getFullName();
                } else {
                    return "Nemáte dostatek místa v batohu";
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
        return NAZEV;
    }
}
