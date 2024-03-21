package cz.vse.sven.main.ListCell;

import cz.vse.sven.logika.objekty.Postava;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

/**
 * Třída slouží k úpravě buňěk (postav) v panelu postav v prostoru (listView)
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class ListCellPostavy extends ListCell<Postava> {

    /**
     * Metoda nastavuje jména a obrázky jednotlivých postav
     *
     * @param postava jaká postava se v buňce nachází
     * @param empty   jestli je buňka prázdná
     */
    @Override
    protected void updateItem(Postava postava, boolean empty) {
        super.updateItem(postava, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(postava.getJmenoGrafickaVerze());
            String cesta = getClass().getResource("/cz/vse/sven/main/postavy/" + postava.getJmeno().toLowerCase() + ".png").toExternalForm();
            ImageView iw = new ImageView(cesta);
            iw.setFitWidth(30);
            iw.setPreserveRatio(true);
            setGraphic(iw);
        }
    }
}
