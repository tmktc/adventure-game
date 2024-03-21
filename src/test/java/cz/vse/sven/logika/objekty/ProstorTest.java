package cz.vse.sven.logika.objekty;

import cz.vse.sven.logika.hra.HerniPlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída ProstorTest slouží k otestování třídy Prostor
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class ProstorTest {

    private HerniPlan plan;
    private Prostor test;
    private Vec sebratelna;
    private Vec koupitelna;

    @BeforeEach
    public void setUp() {
        plan = new HerniPlan();
        test = new Prostor("test", "test", "testpopis");
        sebratelna = new Vec("sebratelna", "Sebratelná",true, false, false, 0);
        koupitelna = new Vec("koupitelna", "Koupitelná",false, true, false, 1);
    }

    /**
     * Hra začíná u Svena doma
     * otestujeme, zda hra správně vypíše možné východy
     * a zda se po přidání dalšího východu seznam správně aktualizuje
     */
    @Test
    public void popisVychodu() {
        assertEquals("východy: jidelna  ", plan.getAktualniProstor().popisVychodu());
        plan.getAktualniProstor().setVychod(test);
        assertEquals("východy: test  jidelna  ", plan.getAktualniProstor().popisVychodu());
    }

    /**
     * Otestuje, zda metoda vrátí správný prostor
     * a pokud prostor není sousední prostorem, tak zda vrátí null
     */
    @Test
    public void vratSousedniProstor() {
        assertEquals("jidelna", plan.getAktualniProstor().vratSousedniProstor("jidelna").getNazev());
        assertNull(plan.getAktualniProstor().vratSousedniProstor("test"));
    }


    /**
     * Zde otestujeme správné fungování metod pro přidání a odebrání věci do prostoru,
     * pro zjištění, zda se daná věc v prostoru nachází a zda je daná věc sebratelná nebo koupitelná
     * také otestuje výpis seznamu věcí v prostoru
     */
    @Test
    public void metodySouvisejiciSVeci() {
        //v prostoru se nenachází žádné věci
        assertEquals("věci: ", plan.getAktualniProstor().seznamVeci());

        //přidání a odebrání věci
        plan.getAktualniProstor().addVec(new Vec("test", "Test",true, false, false, 0));
        assertEquals("věci: test  ", plan.getAktualniProstor().seznamVeci());
        plan.getAktualniProstor().removeVec("test");
        assertEquals("věci: ", plan.getAktualniProstor().seznamVeci());

        // zjištění, zda se věc v prostoru nachází
        assertFalse(plan.getAktualniProstor().obsahujeVec("test"));
        plan.getAktualniProstor().addVec(sebratelna);
        assertTrue(plan.getAktualniProstor().obsahujeVec("sebratelna"));

        // zjištění, zda je věc sebratelná nebo koupitelná
        plan.getAktualniProstor().addVec(koupitelna);
        assertEquals(sebratelna, plan.getAktualniProstor().jeVecSebratelna("sebratelna"));
        assertNull(plan.getAktualniProstor().jeVecSebratelna("koupitelna"));
        assertEquals(koupitelna, plan.getAktualniProstor().jeVecKoupitelna("koupitelna"));
        assertNull(plan.getAktualniProstor().jeVecKoupitelna("sebratelna"));
    }

    /**
     * Zde otestujeme správné fungování metod pro přidání a odebrání postavy do prostoru
     * dále testujeme správné fungování zjištění, zda se postava v prostoru nachází
     * a správný výpis seznamu postav
     */
    @Test
    public void metodySouvisejiciSPostavou() {
        // seznam postav
        assertEquals("postavy: Pepa  ", plan.getAktualniProstor().seznamPostav());

        // zjištění, zda se postava v prostoru nachází
        assertTrue(plan.getAktualniProstor().obsahujePostavu("Pepa"));
        assertFalse(plan.getAktualniProstor().obsahujePostavu("test"));

        // odebrání a přidání postavy
        plan.getAktualniProstor().removePostava("Pepa");
        assertEquals("postavy: ", plan.getAktualniProstor().seznamPostav());

        plan.getAktualniProstor().addPostava(new Postava("postava", "postava"));
        assertEquals("postavy: postava  ", plan.getAktualniProstor().seznamPostav());
    }
}