package cz.vse.sven.main.ListCell;

import cz.vse.sven.logic.objects.NPC;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.util.Objects;

/**
 * Edits NPC cells in NPC panels
 */
public class ListCellNPC extends ListCell<NPC> {

    /**
     * Edits NPC cells in NPC panels
     *
     * @param NPC what NPC the cell represents
     * @param empty   whether the cell is empty
     */
    @Override
    protected void updateItem(NPC NPC, boolean empty) {
        super.updateItem(NPC, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(NPC.fullName());
            setFont(Font.font(13));
            String path = Objects.requireNonNull(getClass().getResource("/cz/vse/sven/main/NPCs/" + NPC.name().toLowerCase() + ".png")).toExternalForm();
            ImageView iw = new ImageView(path);
            iw.setFitWidth(35);
            iw.setPreserveRatio(true);
            setGraphic(iw);
        }
    }
}
