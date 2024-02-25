package cz.vse.sven.main.ListCell;

import cz.vse.sven.logika.objekty.Vec;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

/**
 * Třída slouží k úpravě buňěk (věcí) v panelu věcí v prostoru a batohu (listView)
 *
 * @author Tomáš Kotouč
 * @version únor 2024
 */
public class ListCellVeci extends ListCell<Vec> {

    /**
     * Metoda nastavuje obrázky jednotlivých věcí
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
            setText(vec.getJmeno());
            String cesta = getClass().getResource("/cz/vse/sven/main/veci/" + vec.getJmeno().toLowerCase() + ".png").toExternalForm();
            ImageView iw = new ImageView(cesta);
            iw.setFitWidth(30);
            iw.setPreserveRatio(true);
            setGraphic(iw);
        }
    }
}
