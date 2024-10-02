package cz.vse.sven.main.ListCell;

import cz.vse.sven.logic.objects.Area;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.util.Objects;

/**
 * Edits area exit cells in exits panel
 */
public class ListCellArea extends ListCell<Area> {

    /**
     * Edits area exit cells in exits panel
     *
     * @param area what area the cell represents
     * @param empty   whether the cell is empty
     */
    @Override
    protected void updateItem(Area area, boolean empty) {
        super.updateItem(area, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(area.getFullName());
            setFont(Font.font(13));
            String path = Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("/cz/vse/sven/main/areas/" + area.getName() + ".png"))).toExternalForm();
            ImageView iw = new ImageView(path);
            iw.setFitWidth(35);
            iw.setPreserveRatio(true);
            setGraphic(iw);
        }
    }
}
