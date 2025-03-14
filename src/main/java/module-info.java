module kusi.paska.ohii_harjoitustyo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.github.LillaNudde to javafx.fxml;
    exports com.github.LillaNudde;
}