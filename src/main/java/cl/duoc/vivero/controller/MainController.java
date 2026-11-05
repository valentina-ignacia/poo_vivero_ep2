package cl.duoc.vivero.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainController {

    public final static String TIPO_EXTERIOR = "Planta Exterior";
    public final static String TIPO_INTERIOR = "Planta Interior";

    // Espacio para alertas
    @FXML
    private Label alertLabel;

    // Formulario de planta
        //Campos comúnes
        @FXML private ChoiceBox<String> tipoChoice;
        @FXML private TextField nombreText;
        @FXML private TextField alturaText;
        @FXML private TextField cantidadText;

        //Campos planta interior
        @FXML private TextField volumenText;

        //Campos planta exterior
        @FXML private TextField especieText;
        @FXML private CheckBox resistenteCheck;
        @FXML private CheckBox disponibleCheck;

        //Acciones
        @FXML
        public void initialize() {
            this.tipoChoice.getItems().add(TIPO_INTERIOR);
            this.tipoChoice.getItems().add(TIPO_EXTERIOR);
        }

        @FXML
        public void onGuardar() {}

        @FXML
        public void onLimpiar() { // Sirve para resetear todos los campos llenados
            this.tipoChoice.setValue("");
            this.nombreText.setText("");
            this.alturaText.setText("");
            this.cantidadText.setText("");
            this.volumenText.setText("");
            this.especieText.setText("");
            this.resistenteCheck.setSelected(false);
            this.disponibleCheck.setSelected(false);
            this.alertLabel.setVisible(false);
        }
}
