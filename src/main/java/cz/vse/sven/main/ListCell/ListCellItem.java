package cz.vse.sven.main.ListCell;

import cz.vse.sven.logic.objects.Item;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

/**
 * Edits item cells in item panels
 */
public class ListCellItem extends ListCell<Item> {

    /**
     * Edits item cells in item panels
     *
     * @param item what item the cell represents
     * @param empty   whether the cell is empty
     */
    @Override
    protected void updateItem(Item item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item.getFullName());
            setFont(Font.font(13));
            String path = getClass().getResource("/cz/vse/sven/main/items/" + item.getName().toLowerCase() + ".png").toExternalForm();
            ImageView iw = new ImageView(path);
            iw.setFitWidth(35);
            iw.setPreserveRatio(true);
            setGraphic(iw);
        }
    }
}
