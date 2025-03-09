module kusi.paska.ohii_harjoitustyo {
    requires javafx.controls;
    requires javafx.fxml;


    opens kusi.paska.ohii_harjoitustyo to javafx.fxml;
    exports kusi.paska.ohii_harjoitustyo;
}