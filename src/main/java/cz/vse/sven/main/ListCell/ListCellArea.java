package cz.vse.sven.main.ListCell;

import cz.vse.sven.logic.objects.Area;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

/**
 * Třída slouží k úpravě buňěk (východů) v panelu východů (listView)
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class ListCellArea extends ListCell<Area> {

    /**
     * Metoda nastavuje jména a obrázky jednotlivých východů
     *
     * @param area jaký prostor se v buňce nachází
     * @param empty   jestli je buňka prázdná
     */
    @Override
    protected void updateItem(Area area, boolean empty) {
        super.updateItem(area, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(area.getNazevCely());
            setFont(Font.font(13));
            String cesta = getClass().getResource("/cz/vse/sven/main/areas/" + area.getNazev() + ".png").toExternalForm();
            ImageView iw = new ImageView(cesta);
            iw.setFitWidth(35);
            iw.setPreserveRatio(true);
            setGraphic(iw);
        }
    }
}
