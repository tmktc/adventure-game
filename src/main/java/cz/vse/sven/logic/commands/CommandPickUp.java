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
     * @param parametry - věc, co chceme sebrat
     * @return hlášení, zda se sebrání povedlo
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Nezadali jste název věci, kterou chcete sebrat";
        }

        String jmenoVeci = parametry[0];
        Area aktualniArea = plan.getAktualniProstor();
        if (aktualniArea.obsahujeVec(jmenoVeci)) {
            Item item = aktualniArea.jeVecSebratelna(jmenoVeci);
            if (item == null) {
                return "Takovou věc nelze sebrat";
            } else {
                if (backpack.vlozVec(item)) {
                    aktualniArea.removeVec(jmenoVeci);
                    return "Sebrali jste " + item.getJmenoCele();
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
    public String getNazev() {
        return NAZEV;
    }
}
