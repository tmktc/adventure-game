module cz.vse.sven {
    requires javafx.controls;
    requires javafx.fxml;


    opens cz.vse.sven.main to javafx.fxml;
    exports cz.vse.sven.main;
}