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
    private Item canBePickedUp;
    private Item purchasable;

    @BeforeEach
    public void setUp() {
        plan = new GamePlan();
        test = new Area("test", "test", "testdesc");
        canBePickedUp = new Item("canBePickedUp", "CanBePickedUp", true, false, false, 0);
        purchasable = new Item("purchasable", "Purchasable", false, true, false, 1);
    }

    /**
     * Hra začíná u Svena doma
     * otestujeme, zda hra správně vypíše možné východy
     * a zda se po přidání dalšího východu seznam správně aktualizuje
     */
    @Test
    public void exitDescription() {
        assertEquals("exits: soupKitchen  ", plan.getCurrentArea().exitDescription());
        plan.getCurrentArea().setExit(test);
        assertEquals("exits: test  soupKitchen  ", plan.getCurrentArea().exitDescription());
    }

    /**
     * Otestuje, zda metoda vrátí správný prostor
     * a pokud prostor není sousední prostorem, tak zda vrátí null
     */
    @Test
    public void returnNeighboringArea() {
        assertEquals("soupKitchen", plan.getCurrentArea().returnNeighboringArea("soupKitchen").getName());
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
        assertEquals("items: ", plan.getCurrentArea().itemList());

        //přidání a odebrání věci
        plan.getCurrentArea().addItem(new Item("test", "Test", true, false, false, 0));
        assertEquals("items: test  ", plan.getCurrentArea().itemList());
        plan.getCurrentArea().removeItem("test");
        assertEquals("items: ", plan.getCurrentArea().itemList());

        // zjištění, zda se věc v prostoru nachází
        assertFalse(plan.getCurrentArea().containsItem("test"));
        plan.getCurrentArea().addItem(canBePickedUp);
        assertTrue(plan.getCurrentArea().containsItem("canBePickedUp"));

        // zjištění, zda je věc sebratelná nebo koupitelná
        plan.getCurrentArea().addItem(purchasable);
        assertEquals(canBePickedUp, plan.getCurrentArea().canItemBePickedUp("canBePickedUp"));
        assertNull(plan.getCurrentArea().canItemBePickedUp("purchasable"));
        assertEquals(purchasable, plan.getCurrentArea().isItemPurchasable("purchasable"));
        assertNull(plan.getCurrentArea().isItemPurchasable("canBePickedUp"));
    }

    /**
     * Zde otestujeme správné fungování metod pro přidání a odebrání postavy do prostoru
     * dále testujeme správné fungování zjištění, zda se postava v prostoru nachází
     * a správný výpis seznamu postav
     */
    @Test
    public void metodySouvisejiciSPostavou() {
        // seznam postav
        assertEquals("NPCs: pepa  ", plan.getCurrentArea().NPCList());

        // zjištění, zda se postava v prostoru nachází
        assertTrue(plan.getCurrentArea().containsNPC("pepa"));
        assertFalse(plan.getCurrentArea().containsNPC("test"));

        // odebrání a přidání postavy
        plan.getCurrentArea().removeNPC("pepa");
        assertEquals("NPCs: ", plan.getCurrentArea().NPCList());

        plan.getCurrentArea().addNPC(new NPC("npc", "NPC"));
        assertEquals("NPCs: npc  ", plan.getCurrentArea().NPCList());
    }
}