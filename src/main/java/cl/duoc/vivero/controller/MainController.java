package cl.duoc.vivero.controller;

import cl.duoc.vivero.model.Planta;
import cl.duoc.vivero.model.PlantaExterior;
import cl.duoc.vivero.model.PlantaInterior;
import cl.duoc.vivero.repository.PersistenciaException;
import cl.duoc.vivero.repository.PlantaRepository;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class MainController {
    // Constantes para tipoChoice | Uno por cada tipo de clase hija
    public final static String TIPO_EXTERIOR = "Planta Exterior";
    public final static String TIPO_INTERIOR = "Planta Interior";

    // Constantes para alertaLabel | Uno por cada alerta
    public final static String ALERTA_DEFAULT = "Sin Alerta";
    public final static String ALERTA_EXITO = "Registro Exitoso";
    public final static String ALERTA_NO_VALIDO = "Dato no valido";
    public final static String ALERTA_ELIMINADO = "Registro eliminado";
    public final static String ALERTA_NO_SELECCIONADO = "Falta de seleccion";
    public final static String ALERTA_LECTURA = "Error al leer archivo";
    public final static String ALERTA_ESCRITURA = "Error al escribir archivo";

    // Espacio para alertas
    @FXML private Label alertaLabel;

    // Formulario de Planta
    // Campos Comunes
    @FXML private ChoiceBox<String> tipoChoice;
    @FXML private TextField nombreText;
    @FXML private TextField alturaText;
    @FXML private TextField cantidadText;
    // Campos Planta Interior
    @FXML private TextField volumenText;
    // Campos Planta Exterior
    @FXML private TextField especieText;
    @FXML private CheckBox resistenteCheck;
    @FXML private CheckBox disponibleCheck;

    // TableView
    @FXML private TableView<Planta> plantaTableView;
    @FXML private TableColumn<Planta, String> tipoColumn;
    @FXML private TableColumn<Planta, String> nombreColumn;
    @FXML private TableColumn<Planta, Integer> alturaColumn;
    @FXML private TableColumn<Planta, Integer> cantidadColumn;
    @FXML private TableColumn<Planta, String> precioColumn;

    // Implementar el repositorio en el controlador
    private final PlantaRepository repository = new PlantaRepository();
    private final ObservableList<Planta> plantas = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Inicializando tipos de datos
        this.tipoChoice.getItems().add(TIPO_INTERIOR);
        this.tipoChoice.getItems().add(TIPO_EXTERIOR);
        // Bloqueando campos no seleccionados
        // Los campos Interiores
        this.volumenText.setDisable(true);
        // Campos Exteriores
        this.especieText.setDisable(true);
        this.resistenteCheck.setDisable(true);
        this.disponibleCheck.setDisable(true);
        this.configurarTableView();
        try{
            this.repository.cargar();
            this.actualizarTableView();
        }catch (PersistenciaException e){
            this.setAlertaLabelMensaje(e.getMessage(),"Error en carga inicial de datos");
        }
    }

    private void configurarTableView(){
        this.tipoColumn.setCellValueFactory(celda -> new ReadOnlyStringWrapper(celda.getValue().getTipo()));
        this.nombreColumn.setCellValueFactory(celda -> new ReadOnlyStringWrapper(celda.getValue().getNombre()));
        this.alturaColumn.setCellValueFactory(celda -> new ReadOnlyObjectWrapper<>(celda.getValue().getAlturaCm()));
        this.cantidadColumn.setCellValueFactory(celda -> new ReadOnlyObjectWrapper<>(celda.getValue().getCantidad()));
        this.precioColumn.setCellValueFactory(celda -> new ReadOnlyStringWrapper(Planta.pesos(celda.getValue().calcularPrecio())));
        this.plantaTableView.setItems(this.plantas);
    }

    private void actualizarTableView(){
        plantas.setAll(repository.listar());
    }

    @FXML
    public void onSeleccionarTipo(){
        String tipoPlanta = this.tipoChoice.getValue();
        switch(tipoPlanta){
            case TIPO_INTERIOR:
                // Los campos Interiores
                this.volumenText.setDisable(false);
                // Campos Exteriores
                this.especieText.setDisable(true);
                this.resistenteCheck.setDisable(true);
                this.disponibleCheck.setDisable(true);
                break;
            case TIPO_EXTERIOR:
                // Los campos Interiores
                this.volumenText.setDisable(true);
                // Campos Exteriores
                this.especieText.setDisable(false);
                this.resistenteCheck.setDisable(false);
                this.disponibleCheck.setDisable(false);
                break;
            default:
                // Los campos Interiores
                this.volumenText.setDisable(true);
                // Campos Exteriores
                this.especieText.setDisable(true);
                this.resistenteCheck.setDisable(true);
                this.disponibleCheck.setDisable(true);
                this.setAlertaLabelMensaje(ALERTA_NO_SELECCIONADO,"Campo Seleccionar Tipo");
        }
    }

    @FXML
    public void onGuardar(){
        // Se crea variable para almacenamiento de datos
        Planta planta;
        try {
            // Se asignan las variables de los controladores generales
            String tipo = this.tipoChoice.getValue();
            String nombre = this.nombreText.getText();
            int alturaCm = Integer.parseInt(this.alturaText.getText());
            int cantidad = Integer.parseInt(this.cantidadText.getText());
            switch (tipo){
                case TIPO_INTERIOR:
                    double volumenMacetaLitros = Double.parseDouble(this.volumenText.getText());
                    if(volumenMacetaLitros <= 0.0){
                        this.setAlertaLabelMensaje(ALERTA_NO_VALIDO, "Campo volumen debe ser mayor que cero");
                        return;
                    }
                    planta = new PlantaInterior(nombre,alturaCm,cantidad,volumenMacetaLitros);
                    break;
                case TIPO_EXTERIOR:
                    String especie = this.especieText.getText();
                    if(especie.isEmpty() || especie.trim().isBlank()){
                        this.setAlertaLabelMensaje(ALERTA_NO_VALIDO, "campo especie no puede estar vacia ni contener solo espacios");
                    }
                    boolean resistenteHeladas = this.resistenteCheck.isSelected();
                    boolean listaParaDespacho = this.disponibleCheck.isSelected();
                    planta = new PlantaExterior(nombre,alturaCm,cantidad,especie,resistenteHeladas);
                    if(listaParaDespacho){
                        ((PlantaExterior) planta).marcarListaParaDespacho();
                    }
                    break;
                default:
                    this.setAlertaLabelMensaje(ALERTA_NO_SELECCIONADO, "Campo Tipo de Planta");
                    return;
            }

            try{
                this.repository.registrar(planta);
                this.actualizarTableView();
            }catch (PersistenciaException e){
                this.setAlertaLabelMensaje(e.getMessage(),"Error guardando nuevo registro");
                return;
            }

            this.setAlertaLabelMensaje(ALERTA_EXITO, "Planta - "+planta.getNombre());

        }catch (IllegalArgumentException e){
            this.setAlertaLabelMensaje(ALERTA_NO_VALIDO,e.getMessage());
        }
    }

    @FXML
    public void onLimpiar(){
        this.tipoChoice.setValue("");
        this.nombreText.setText("");
        this.alturaText.setText("");
        this.cantidadText.setText("");
        this.volumenText.setText("");
        this.especieText.setText("");
        this.resistenteCheck.setSelected(false);
        this.disponibleCheck.setSelected(false);
        this.setAlertaLabelMensaje(ALERTA_DEFAULT,"");
    }

    private void setAlertaLabelMensaje(String tipo,String mensaje){
        if(mensaje.trim().isBlank()){
            mensaje = tipo;
        }else{
            mensaje = tipo + ": "+ mensaje;
        }
        switch(tipo){
            case ALERTA_EXITO:
            case ALERTA_ELIMINADO:
                this.alertaLabel.setVisible(true);
                this.alertaLabel.setTextFill(Color.GREEN);
                break;
            case ALERTA_NO_VALIDO:
            case ALERTA_NO_SELECCIONADO:
            case ALERTA_LECTURA:
            case ALERTA_ESCRITURA:
                this.alertaLabel.setVisible(true);
                this.alertaLabel.setTextFill(Color.RED);
                break;
            default:
                this.alertaLabel.setVisible(false);
                this.alertaLabel.setTextFill(Color.BLACK);
        }

        this.alertaLabel.setText(mensaje);
    }

    @FXML
    public void onEliminar(){
        Planta seleccionada = this.plantaTableView.getSelectionModel().getSelectedItem();
        if(seleccionada == null){
            this.setAlertaLabelMensaje(ALERTA_NO_SELECCIONADO, "Debe indicarse la planta a eliminar del registro");
            return;
        }
        try{
            if(!this.repository.eliminar(seleccionada)){
                this.setAlertaLabelMensaje(ALERTA_NO_VALIDO, "No se encuentra lo solicitado en el registro");
                this.actualizarTableView();
                return;
            }
        } catch (PersistenciaException e){
            this.setAlertaLabelMensaje(ALERTA_ESCRITURA, "No se puede guardar los cambios");
            return;
        }
        this.actualizarTableView();
        this.setAlertaLabelMensaje(ALERTA_ELIMINADO, "");
    }

}