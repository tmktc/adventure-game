package cz.vse.sven.main.ListCell;

import cz.vse.sven.logic.objects.NPC;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

/**
 * Třída slouží k úpravě buňěk (postav) v panelu postav v prostoru (listView)
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class ListCellNPC extends ListCell<NPC> {

    /**
     * Metoda nastavuje jména a obrázky jednotlivých postav
     *
     * @param NPC jaká postava se v buňce nachází
     * @param empty   jestli je buňka prázdná
     */
    @Override
    protected void updateItem(NPC NPC, boolean empty) {
        super.updateItem(NPC, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(NPC.getFullName());
            setFont(Font.font(13));
            String path = getClass().getResource("/cz/vse/sven/main/NPCs/" + NPC.getName().toLowerCase() + ".png").toExternalForm();
            ImageView iw = new ImageView(path);
            iw.setFitWidth(35);
            iw.setPreserveRatio(true);
            setGraphic(iw);
        }
    }
}
