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

    public static final String NAZEV = "kup";
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
     * @param parametry - věc, kterou chceme koupit.
     * @return hlášení, zda se nákup povedl
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Nezadali jste název položky, kterou chcete koupit";
        }

        String jmenoVeci = parametry[0];
        Area aktualniArea = plan.getAktualniProstor();

        if (aktualniArea.obsahujeVec(jmenoVeci)) {
            Item item = aktualniArea.jeVecKoupitelna(jmenoVeci);
            if (item == null) {
                return "Taková věc není koupitelná";
            } else {
                if (money.getPenize() >= item.getCena()) {
                    if (backpack.vlozVec(item)) {
                        aktualniArea.removeVec(jmenoVeci);
                        money.odectiPenize(item.getCena());
                        item.setSebratelna(true);
                        item.setKoupitelna(false);
                        return "Koupili jste " + item.getJmenoCele() + " za " + item.getCena() + " Euro";
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
    public String getNazev() {
        return NAZEV;
    }
}
