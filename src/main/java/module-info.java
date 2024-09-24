module cz.vse.sven {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    opens cz.vse.sven.main to javafx.fxml;
    exports cz.vse.sven.main;
    exports cz.vse.sven.main.ListCell;
    opens cz.vse.sven.main.ListCell to javafx.fxml;
    exports cz.vse.sven.main.Observer;
    opens cz.vse.sven.main.Observer to javafx.fxml;
}