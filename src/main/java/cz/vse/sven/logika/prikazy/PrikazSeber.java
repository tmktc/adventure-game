package cz.vse.sven.logika.prikazy;

import cz.vse.sven.logika.objekty.Batoh;
import cz.vse.sven.logika.objekty.HerniPlan;
import cz.vse.sven.logika.objekty.Prostor;
import cz.vse.sven.logika.objekty.Vec;

/**
 * Třída PrikazSeber - implementuje pro hru příkaz jdi.
 * příkazem hráč sebere vybranou věc
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class PrikazSeber implements IPrikaz {

    private static final String NAZEV = "seber";
    private HerniPlan plan;
    private Batoh batoh;


    /**
     * Konstruktor
     *
     * @param plan  ve kterém se bude sbírat
     * @param batoh do kterého se mají sebrané věci vložit
     */
    public PrikazSeber(HerniPlan plan, Batoh batoh) {
        this.plan = plan;
        this.batoh = batoh;
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
        Prostor aktualniProstor = plan.getAktualniProstor();
        if (aktualniProstor.obsahujeVec(jmenoVeci)) {
            Vec vec = aktualniProstor.jeVecSebratelna(jmenoVeci);
            if (vec == null) {
                return "Takovou věc nelze sebrat";
            } else {
                if (batoh.vlozVec(vec)) {
                    aktualniProstor.removeVec(jmenoVeci);
                    return "Sebrali jste " + jmenoVeci;
                } else {
                    return "Nemáte dostatek místa v batohu";
                }
            }
        }
        return "Taková věc tu není";
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
