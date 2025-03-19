module vidmot.simplebooks {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens vidmot.simplebooks to javafx.fxml;
    exports vidmot.simplebooks;
    exports vinnsla;
}
