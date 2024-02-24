package cz.vse.sven.main;

import cz.vse.sven.logika.objekty.Postava;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

/**
 * Třída slouží k úpravě buňěk (postav) v panelu postav v prostoru (listView)
 */
public class ListCellPostavy extends ListCell<Postava> {

    /**
     * Metoda nastavuje obrázky jednotlivých postav
     *
     * @param postava jaká postava se v buňce nachází
     * @param empty jestli je buňka prázdná
     */
    @Override
    protected void updateItem(Postava postava, boolean empty) {
        super.updateItem(postava, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(postava.getJmeno());
            String cesta = getClass().getResource("postavy/" + postava.getJmeno().toLowerCase() + ".png").toExternalForm();
            ImageView iw = new ImageView(cesta);
            iw.setFitWidth(30);
            iw.setPreserveRatio(true);
            setGraphic(iw);
        }
    }
}
