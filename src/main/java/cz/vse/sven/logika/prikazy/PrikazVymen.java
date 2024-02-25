package cz.vse.sven.logika.prikazy;

import cz.vse.sven.logika.hra.HerniPlan;
import cz.vse.sven.logika.hra.Penize;
import cz.vse.sven.logika.objekty.Batoh;
import cz.vse.sven.logika.objekty.Prostor;

/**
 * Třída PrikazVymen - implementuje pro hru příkaz vymen
 * příkaz slouží k vyměnění láhví v automatu
 *
 * @author Tomáš Kotouč
 * @version únor 2024
 */
public class PrikazVymen implements IPrikaz {

    public static final String NAZEV = "vymen";
    private HerniPlan plan;
    private Batoh batoh;
    private Penize penize;


    /**
     * Konstruktor
     *
     * @param plan   ve kterém se má vyměňovat
     * @param batoh  ze kterého se mají vyměňované věci brát
     * @param penize které se mají po výměně zvýšit
     */
    public PrikazVymen(HerniPlan plan, Batoh batoh, Penize penize) {
        this.plan = plan;
        this.batoh = batoh;
        this.penize = penize;
    }

    /**
     * Metoda nejdříve zjistí, zda se věc nachází v batohu
     * dále zjistí, zda se jedná o láhev (zda je vyměnitelná)
     * dále věc vymění
     *
     * @param parametry počet parametrů závisí na konkrétním příkazu.
     * @return hlášení, zda se výměna povedla
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Nezadali jste název věci, kterou chcete vyměnit";
        }

        String lahev = parametry[0];
        Prostor aktualniProstor = plan.getAktualniProstor();
        if (aktualniProstor.obsahujeVec("AutomatNaLahve")) {
            if (batoh.obsahujeVec(lahev)) {
                if (batoh.vyberVec(lahev).isVymenitelna()) {
                    batoh.odstranVec(lahev);
                    penize.pridejPenize(1);
                    return "Vyměnili jste " + lahev + " a dostali jste 1 Euro";
                } else {
                    return "Tuto věc nelze vyměnit";
                }
            } else {
                return "Takovou věc u sebe nemáš";
            }
        } else {
            return "Nejsi u automatu na výměnu lahví";
        }
    }

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *
     * @ return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
