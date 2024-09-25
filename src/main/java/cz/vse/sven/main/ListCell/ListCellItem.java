package cz.vse.sven.main.ListCell;

import cz.vse.sven.logic.objects.Item;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

/**
 * Třída slouží k úpravě buňěk (věcí) v panelu věcí v prostoru a batohu (listView)
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class ListCellItem extends ListCell<Item> {

    /**
     * Metoda nastavuje jména a obrázky jednotlivých věcí
     *
     * @param item   jaká věc se v buňce nachází
     * @param empty jestli je buňka prázdná
     */
    @Override
    protected void updateItem(Item item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item.getJmenoCele());
            setFont(Font.font(13));
            String cesta = getClass().getResource("/cz/vse/sven/main/items/" + item.getJmeno().toLowerCase() + ".png").toExternalForm();
            ImageView iw = new ImageView(cesta);
            iw.setFitWidth(35);
            iw.setPreserveRatio(true);
            setGraphic(iw);
        }
    }
}
