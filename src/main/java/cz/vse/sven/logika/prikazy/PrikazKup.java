package cz.vse.sven.logika.prikazy;

import cz.vse.sven.logika.hra.HerniPlan;
import cz.vse.sven.logika.hra.Penize;
import cz.vse.sven.logika.objekty.Batoh;
import cz.vse.sven.logika.objekty.Prostor;
import cz.vse.sven.logika.objekty.Vec;

/**
 * Třída PrikazKup implementuje pro hru příkaz kup.
 * příkazem hráč koupí věci v obchodě
 *
 * @author Tomáš Kotouč
 * @version únor 2024
 */
public class PrikazKup implements IPrikaz {

    public static final String NAZEV = "kup";
    private HerniPlan plan;
    private Batoh batoh;
    private Penize penize;

    /**
     * Konstruktor
     *
     * @param plan   herní plán, ve kterém se bude kupovat
     * @param batoh  do kterého se mají koupené věci vložit
     * @param penize které se mají po nákupu odečíst
     */
    public PrikazKup(HerniPlan plan, Batoh batoh, Penize penize) {
        this.plan = plan;
        this.batoh = batoh;
        this.penize = penize;
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
        Prostor aktualniProstor = plan.getAktualniProstor();

        if (aktualniProstor.obsahujeVec(jmenoVeci)) {
            Vec vec = aktualniProstor.jeVecKoupitelna(jmenoVeci);
            if (vec == null) {
                return "Taková věc není koupitelná";
            } else {
                if (penize.getPenize() >= vec.getCena()) {
                    if (batoh.vlozVec(vec)) {
                        aktualniProstor.removeVec(jmenoVeci);
                        penize.odectiPenize(vec.getCena());
                        vec.setSebratelna(true);
                        vec.setKoupitelna(false);
                        return "Koupili jste " + jmenoVeci + " za " + vec.getCena() + " Euro";
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
     *
     * @ return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
