package cz.vse.sven.main.ListCell;

import cz.vse.sven.logika.objekty.Vec;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

/**
 * Třída slouží k úpravě buňěk (věcí) v panelu věcí v prostoru a batohu (listView)
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class ListCellVeci extends ListCell<Vec> {

    /**
     * Metoda nastavuje jména a obrázky jednotlivých věcí
     *
     * @param vec   jaká věc se v buňce nachází
     * @param empty jestli je buňka prázdná
     */
    @Override
    protected void updateItem(Vec vec, boolean empty) {
        super.updateItem(vec, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(vec.getJmenoCele());
            setFont(Font.font(13));
            String cesta = getClass().getResource("/cz/vse/sven/main/veci/" + vec.getJmeno().toLowerCase() + ".png").toExternalForm();
            ImageView iw = new ImageView(cesta);
            iw.setFitWidth(35);
            iw.setPreserveRatio(true);
            setGraphic(iw);
        }
    }
}
