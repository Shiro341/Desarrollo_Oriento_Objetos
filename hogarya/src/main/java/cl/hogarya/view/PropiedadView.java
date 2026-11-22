package cl.hogarya.view;

import cl.hogarya.controller.PropiedadController;
import cl.hogarya.model.Casa;
import cl.hogarya.model.Departamento;
import cl.hogarya.model.Propiedad;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PropiedadView {

    private final PropiedadController propiedadController;

    public PropiedadView() {
        this.propiedadController = new PropiedadController();
    }

    // --- Componentes del formulario ---
    private final ComboBox<String> cmbTipo = new ComboBox<>();
    private final TextField txtDireccion = new TextField();
    private final TextField txtArriendoBase = new TextField();
    private final TextField txtMetrosCuadrados = new TextField();
    private final TextField txtCantidadPisos = new TextField();
    private final TextField txtNumeroPiso = new TextField();
    private final TextField txtGastosComunes = new TextField();
    private final CheckBox chkTienePatio = new CheckBox();

    // --- Botones ---
    private final Button btnGuardar = new Button();
    private final Button btnEliminar = new Button();
    private final Button btnActualizar = new Button();
    private final Button btnLimpiar = new Button();

    // --- Tabla y mensaje ---
    private final TableView<Propiedad> tblPropiedades = new TableView<>();
    private final Label lblMensajes = new Label();

    // Guarda el ID de la fila seleccionada en la tabla (-1 indica que no hay selección)
    private int idSeleccionado = -1;

    public Scene crearEscena() {
        // --- Configuración inicial de los controles ---
        cmbTipo.getItems().addAll("CASA", "DEPARTAMENTO");
        cmbTipo.setValue("CASA");

        txtDireccion.setPromptText("Direccion");
        txtArriendoBase.setPromptText("Arriendo Base");
        txtMetrosCuadrados.setPromptText("Metros Cuadrados");
        txtCantidadPisos.setPromptText("Cantidad Piso de Casa");
        txtNumeroPiso.setPromptText("Numero de Piso Departamento");
        txtGastosComunes.setPromptText("Gastos Comunes");
        chkTienePatio.setText("Tiene Patio");

        btnGuardar.setText("Guardar");
        btnEliminar.setText("Eliminar");
        btnActualizar.setText("Actualizar");
        btnLimpiar.setText("Limpiar");

        // --- Columnas de la TableView ---
        TableColumn<Propiedad, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        TableColumn<Propiedad, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Propiedad, String> colDireccion = new TableColumn<>("Direccion");
        colId.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        TableColumn<Propiedad, Integer> colArriendoBase = new TableColumn<>("Arriendo Base");
        colArriendoBase.setCellValueFactory(new PropertyValueFactory<>("arriendoBase"));

        TableColumn<Propiedad, Integer> colMetrosCuadrados = new TableColumn<>("Metros Cuadrados");
        colMetrosCuadrados.setCellValueFactory(new PropertyValueFactory<>("metrosCuadrados"));

        TableColumn<Propiedad, String> colDetalle = new TableColumn<>("Detalle");
        colDetalle.setCellValueFactory(new PropertyValueFactory<>("detalle"));

        TableColumn<Propiedad, Integer> colPagoMensual = new TableColumn<>("Pago Mensual");
        colPagoMensual.setCellValueFactory(new PropertyValueFactory<>("pagoMensual"));

        tblPropiedades.getColumns().addAll(colTipo, colId, colDireccion, colArriendoBase,
                colMetrosCuadrados, colDetalle, colPagoMensual);

        // --- Escuchador: al hacer clic en una fila, cargamos sus datos en el formulario ---
        tblPropiedades.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccionado) -> {
            if (seleccionado != null) {
                idSeleccionado = seleccionado.getId();
                cmbTipo.setValue(seleccionado.getTipo());
                txtDireccion.setText(seleccionado.getDireccion());
                txtArriendoBase.setText(String.valueOf(seleccionado.getArriendoBase()));
                txtMetrosCuadrados.setText(String.valueOf(seleccionado.getMetrosCuadrados()));

                if (seleccionado instanceof Casa casa) {
                    txtCantidadPisos.setText(String.valueOf(casa.getCantidadPisos()));
                    chkTienePatio.setSelected(casa.isTienePatio());
                    txtNumeroPiso.clear();
                    txtGastosComunes.clear();
                } else if (seleccionado instanceof Departamento depto) {
                    txtNumeroPiso.setText(String.valueOf(depto.getNumeroPiso()));
                    txtGastosComunes.setText(String.valueOf(depto.getGastosComunes()));
                    txtCantidadPisos.clear();
                    chkTienePatio.setSelected(false);
                }
                lblMensajes.setText("Editando propiedad ID #" + idSeleccionado);
            }
        });

        // --- Escuchador del ComboBox: limpia campos específicos al cambiar de tipo ---
        cmbTipo.setOnAction(e -> {
            txtCantidadPisos.clear();
            txtNumeroPiso.clear();
            txtGastosComunes.clear();
            chkTienePatio.setSelected(false);
        });

        // --- Layout ---
        HBox botones = new HBox(10, btnGuardar, btnActualizar, btnEliminar, btnLimpiar);

        VBox panelIzquierdo = new VBox(10, cmbTipo, txtDireccion, txtArriendoBase, txtMetrosCuadrados,
                txtCantidadPisos, txtNumeroPiso, txtGastosComunes, chkTienePatio, botones, lblMensajes);
        panelIzquierdo.setPadding(new Insets(15));
        panelIzquierdo.setPrefWidth(300);

        VBox panelDerecho = new VBox(tblPropiedades);
        VBox.setVgrow(tblPropiedades, Priority.ALWAYS);

        HBox hboxDividor = new HBox(panelIzquierdo, panelDerecho);
        HBox.setHgrow(panelDerecho, Priority.ALWAYS);

        // --- Eventos de los botones ---
        btnGuardar.setOnAction(e -> accionGuardar());
        btnActualizar.setOnAction(e -> accionActualizar());
        btnEliminar.setOnAction(e -> accionEliminar());
        btnLimpiar.setOnAction(e -> limpiarFormulario());

        // Apenas se construye la ventana, cargamos las filas desde Oracle
        cargarDatosEnTabla();

        return new Scene(hboxDividor, 950, 500);
    }

    // ============================================================
    // Acciones de los botones (igual ciclo que en UsuarioView)
    // ============================================================

    public void accionGuardar() {
        try {
            String tipo = cmbTipo.getValue().trim();
            String direccion = txtDireccion.getText().trim();
            String arriendoBase = txtArriendoBase.getText().trim();
            String metrosCuadrados = txtMetrosCuadrados.getText().trim();
            boolean exito = false;

            if (tipo.equals("CASA")) {
                String cantidadPisos = txtCantidadPisos.getText().trim();
                boolean tienePatio = chkTienePatio.isSelected();
                exito = propiedadController.registrarPropiedad(
                        tipo, direccion, arriendoBase, metrosCuadrados,
                        cantidadPisos, tienePatio, "", "");
            } else if (tipo.equals("DEPARTAMENTO")) {
                String numeroPiso = txtNumeroPiso.getText().trim();
                String gastosComunes = txtGastosComunes.getText().trim();
                exito = propiedadController.registrarPropiedad(
                        tipo, direccion, arriendoBase, metrosCuadrados,
                        "", false, numeroPiso, gastosComunes);
            } else {
                throw new IllegalArgumentException("Tipo no válido");
            }

            if (exito) {
                lblMensajes.setText("Propiedad guardada exitosamente");
                limpiarFormulario();
                cargarDatosEnTabla();
            } else {
                lblMensajes.setText("Error: completa todos los campos obligatorios");
            }
        } catch (IllegalArgumentException e) {
            lblMensajes.setText("Error: " + e.getMessage());
        } catch (Exception e) {
            lblMensajes.setText("Error al guardar: " + e.getMessage());
        }
    }

    public void accionActualizar() {
        if (idSeleccionado == -1) {
            lblMensajes.setText("Selecciona primero una propiedad en la tabla.");
            return;
        }
        try {
            String tipo = cmbTipo.getValue().trim();
            String direccion = txtDireccion.getText().trim();
            String arriendoBase = txtArriendoBase.getText().trim();
            String metrosCuadrados = txtMetrosCuadrados.getText().trim();
            boolean exito = false;

            if (tipo.equals("CASA")) {
                String cantidadPisos = txtCantidadPisos.getText().trim();
                boolean tienePatio = chkTienePatio.isSelected();
                exito = propiedadController.actualizarPropiedad(
                        idSeleccionado, tipo, direccion, arriendoBase, metrosCuadrados,
                        cantidadPisos, tienePatio, "", "");
            } else if (tipo.equals("DEPARTAMENTO")) {
                String numeroPiso = txtNumeroPiso.getText().trim();
                String gastosComunes = txtGastosComunes.getText().trim();
                exito = propiedadController.actualizarPropiedad(
                        idSeleccionado, tipo, direccion, arriendoBase, metrosCuadrados,
                        "", false, numeroPiso, gastosComunes);
            } else {
                throw new IllegalArgumentException("Tipo no válido");
            }

            if (exito) {
                lblMensajes.setText("Propiedad ID #" + idSeleccionado + " actualizada.");
                limpiarFormulario();
                cargarDatosEnTabla();
            } else {
                lblMensajes.setText("Error al actualizar los datos.");
            }
        } catch (Exception e) {
            lblMensajes.setText("Error al actualizar: " + e.getMessage());
        }
    }

    public void accionEliminar() {
        if (idSeleccionado == -1) {
            lblMensajes.setText("Selecciona primero una propiedad en la tabla.");
            return;
        }
        boolean exito = propiedadController.eliminarPropiedad(idSeleccionado);
        if (exito) {
            lblMensajes.setText("Propiedad eliminada de Oracle.");
            limpiarFormulario();
            cargarDatosEnTabla();
        } else {
            lblMensajes.setText("No se pudo eliminar la propiedad.");
        }
    }

    // ============================================================
    // Carga de datos y limpieza (igual patrón que UsuarioView)
    // ============================================================

    public void cargarDatosEnTabla() {
        ObservableList<Propiedad> datos = FXCollections.observableArrayList(
                propiedadController.obtenerPropiedades()
        );
        tblPropiedades.setItems(datos);
    }

    public void limpiarFormulario() {
        idSeleccionado = -1;
        txtDireccion.clear();
        txtArriendoBase.clear();
        txtMetrosCuadrados.clear();
        txtCantidadPisos.clear();
        txtNumeroPiso.clear();
        txtGastosComunes.clear();
        cmbTipo.setValue("CASA");
        chkTienePatio.setSelected(false);
        tblPropiedades.getSelectionModel().clearSelection();
        lblMensajes.setText("Formulario limpio");
    }
}