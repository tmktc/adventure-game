package cz.vse.sven.main.ListCell;

import cz.vse.sven.logika.objekty.Prostor;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

/**
 * Třída slouží k úpravě buňěk (východů) v panelu východů (listView)
 *
 * @author Tomáš Kotouč
 * @version únor 2024
 */
public class ListCellProstor extends ListCell<Prostor> {

    /**
     * Metoda nastavuje obrázky jednotlivých východů
     *
     * @param prostor jaký prostor se v buňce nachází
     * @param empty   jestli je buňka prázdná
     */
    @Override
    protected void updateItem(Prostor prostor, boolean empty) {
        super.updateItem(prostor, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(prostor.getNazev());
            String cesta = getClass().getResource("/cz/vse/sven/main/prostory/" + prostor.getNazev() + ".png").toExternalForm();
            ImageView iw = new ImageView(cesta);
            iw.setFitWidth(30);
            iw.setPreserveRatio(true);
            setGraphic(iw);
        }
    }
}
