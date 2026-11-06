package cl.duoc.vivero.controller;

import cl.duoc.vivero.model.Planta;
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
            this.volumenText.setDisable(true);
            this.especieText.setDisable(true);
            this.resistenteCheck.setDisable(true);
            this.disponibleCheck.setDisable(true);
        }

        @FXML
        public void onSeleccionarTipo() {
            String tipoPlanta = this.tipoChoice.getValue();
            switch (tipoPlanta) {
                case TIPO_INTERIOR:
                    // activado correspondiente
                    this.volumenText.setDisable(false);
                    // desactivado correspondiente
                    this.especieText.setDisable(true);
                    this.resistenteCheck.setDisable(true);
                    this.disponibleCheck.setDisable(true);
                    break;
                case TIPO_EXTERIOR:
                    // desactivado correspondiente
                    this.volumenText.setDisable(true);
                    // activado correspondiente
                    this.especieText.setDisable(false);
                    this.resistenteCheck.setDisable(false);
                    this.disponibleCheck.setDisable(false);
                    break;
                default:
                    this.volumenText.setDisable(true);
                    this.especieText.setDisable(true);
                    this.resistenteCheck.setDisable(true);
                    this.disponibleCheck.setDisable(true);
            }
        }

        @FXML
        public void onGuardar() {
            Planta planta;
            try {

            } catch (IllegalArgumentException) {
                alertLabel.setText("Dato no válido");
            }
        }

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

        private void setAlertLaberMensaje (String mensaje, String tipo){
            switch
        }
}
