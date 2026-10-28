module cl.duoc.vivero {
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.google.gson;

    opens cl.duoc.vivero.model to com.google.gson;

    exports cl.duoc.vivero;
}