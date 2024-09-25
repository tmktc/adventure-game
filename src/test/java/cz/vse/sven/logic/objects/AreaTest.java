package cz.vse.sven.logic.objects;

import cz.vse.sven.logic.game.GamePlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída ProstorTest slouží k otestování třídy Prostor
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class AreaTest {

    private GamePlan plan;
    private Area test;
    private Item sebratelna;
    private Item koupitelna;

    @BeforeEach
    public void setUp() {
        plan = new GamePlan();
        test = new Area("test", "test", "testpopis");
        sebratelna = new Item("sebratelna", "Sebratelná", true, false, false, 0);
        koupitelna = new Item("koupitelna", "Koupitelná", false, true, false, 1);
    }

    /**
     * Hra začíná u Svena doma
     * otestujeme, zda hra správně vypíše možné východy
     * a zda se po přidání dalšího východu seznam správně aktualizuje
     */
    @Test
    public void exitDescription() {
        assertEquals("východy: jidelna  ", plan.getCurrentArea().exitDescription());
        plan.getCurrentArea().setExit(test);
        assertEquals("východy: test  jidelna  ", plan.getCurrentArea().exitDescription());
    }

    /**
     * Otestuje, zda metoda vrátí správný prostor
     * a pokud prostor není sousední prostorem, tak zda vrátí null
     */
    @Test
    public void returnNeighboringArea() {
        assertEquals("jidelna", plan.getCurrentArea().returnNeighboringArea("jidelna").getName());
        assertNull(plan.getCurrentArea().returnNeighboringArea("test"));
    }


    /**
     * Zde otestujeme správné fungování metod pro přidání a odebrání věci do prostoru,
     * pro zjištění, zda se daná věc v prostoru nachází a zda je daná věc sebratelná nebo koupitelná
     * také otestuje výpis seznamu věcí v prostoru
     */
    @Test
    public void metodySouvisejiciSVeci() {
        //v prostoru se nenachází žádné věci
        assertEquals("věci: ", plan.getCurrentArea().itemList());

        //přidání a odebrání věci
        plan.getCurrentArea().addItem(new Item("test", "Test", true, false, false, 0));
        assertEquals("věci: test  ", plan.getCurrentArea().itemList());
        plan.getCurrentArea().removeItem("test");
        assertEquals("věci: ", plan.getCurrentArea().itemList());

        // zjištění, zda se věc v prostoru nachází
        assertFalse(plan.getCurrentArea().containsItem("test"));
        plan.getCurrentArea().addItem(sebratelna);
        assertTrue(plan.getCurrentArea().containsItem("sebratelna"));

        // zjištění, zda je věc sebratelná nebo koupitelná
        plan.getCurrentArea().addItem(koupitelna);
        assertEquals(sebratelna, plan.getCurrentArea().canItemBePickedUp("sebratelna"));
        assertNull(plan.getCurrentArea().canItemBePickedUp("koupitelna"));
        assertEquals(koupitelna, plan.getCurrentArea().isItemPurchasable("koupitelna"));
        assertNull(plan.getCurrentArea().isItemPurchasable("sebratelna"));
    }

    /**
     * Zde otestujeme správné fungování metod pro přidání a odebrání postavy do prostoru
     * dále testujeme správné fungování zjištění, zda se postava v prostoru nachází
     * a správný výpis seznamu postav
     */
    @Test
    public void metodySouvisejiciSPostavou() {
        // seznam postav
        assertEquals("postavy: Pepa  ", plan.getCurrentArea().NPCList());

        // zjištění, zda se postava v prostoru nachází
        assertTrue(plan.getCurrentArea().containsNPC("Pepa"));
        assertFalse(plan.getCurrentArea().containsNPC("test"));

        // odebrání a přidání postavy
        plan.getCurrentArea().removeNPC("Pepa");
        assertEquals("postavy: ", plan.getCurrentArea().NPCList());

        plan.getCurrentArea().addNPC(new NPC("postava", "postava"));
        assertEquals("postavy: postava  ", plan.getCurrentArea().NPCList());
    }
}