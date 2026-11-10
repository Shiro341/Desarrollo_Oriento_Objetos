package com.hachimakidev.musicstore.view;

import com.hachimakidev.musicstore.controller.InstrumentoController;
import com.hachimakidev.musicstore.model.Guitarra;
import com.hachimakidev.musicstore.model.Instrumento;
import com.hachimakidev.musicstore.model.Teclado;

import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CatalogoView {

    private final InstrumentoController controller = new InstrumentoController();

    public VBox catalogo() {

        Label lbl_titulo = new Label("MusicStore — Catálogo de Guitarras y Teclados");
        lbl_titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<Instrumento> table = new TableView<>();

        TableColumn<Instrumento, Integer> ColId = new TableColumn<>("ID");
        ColId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Instrumento, String> ColTipo = new TableColumn<>("Tipo");
        ColTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        TableColumn<Instrumento, String> ColMarca = new TableColumn<>("Marca");
        ColMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));

        TableColumn<Instrumento, String> ColModelo = new TableColumn<>("Modelo");
        ColModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));

        TableColumn<Instrumento, Integer> ColPrecio = new TableColumn<>("Precio");
        ColPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<Instrumento, String> ColEspecificacion = new TableColumn<>("Especificación");
        ColEspecificacion.setCellValueFactory(new PropertyValueFactory<>("especificacion"));

        TableColumn<Instrumento, Boolean> ColDisponible = new TableColumn<>("Disponible");
        ColDisponible.setCellValueFactory(new PropertyValueFactory<>("disponible"));

        table.getColumns().add(ColId);
        table.getColumns().add(ColTipo);
        table.getColumns().add(ColMarca);
        table.getColumns().add(ColModelo);
        table.getColumns().add(ColPrecio);
        table.getColumns().add(ColEspecificacion);
        table.getColumns().add(ColDisponible);

        table.setItems(FXCollections.observableArrayList(controller.obtenerInstrumento()));

        ComboBox<String> cmbTipo = new ComboBox<>();
        cmbTipo.getItems().addAll("Guitarra", "Teclado");
        cmbTipo.setValue("Guitarra");

        TextField txtMarca = new TextField();
        txtMarca.setPromptText("Marca");

        TextField txtModelo = new TextField();
        txtModelo.setPromptText("Modelo");

        TextField txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio");

        TextField txtEspecificacion = new TextField();
        txtEspecificacion.setPromptText("Especificacion");

        TextField txtCuerdas_Teclas = new TextField();
        txtCuerdas_Teclas.setPromptText("Atributo 1 (Cuerdas o N° Teclas):");

        TextField txtTipoPastilla_RitmosIntegrados = new TextField();
        txtTipoPastilla_RitmosIntegrados.setPromptText("Atributo 2 (Tipo Pastilla o N° Ritmos):");

        Button btnAgregar = new Button("Agregar Instrumento");
        Label lblEstado = new Label();

        Button btnEliminar = new Button("🗑️ Eliminar Seleccionado");
        Label lblEstadoEliminado = new Label();

        HBox fila1 = new HBox(10, cmbTipo, txtMarca, txtModelo);
        HBox fila2 = new HBox(10, txtPrecio);
        HBox fila3 = new HBox(10, txtCuerdas_Teclas, txtTipoPastilla_RitmosIntegrados);
        HBox fila4 = new HBox(10, btnAgregar, lblEstado);
        HBox fila5 = new HBox(10, btnEliminar, lblEstadoEliminado);

        VBox formulario = new VBox(fila1, fila2, fila3, fila4, fila5);

        btnAgregar.setOnAction(e -> {

            try {
                String tipo = cmbTipo.getValue();
                int id = controller.obtenerInstrumento().size() + 1;
                String marca = txtMarca.getText().trim();
                String modelo = txtModelo.getText().trim();
                int precio = Integer.parseInt(txtPrecio.getText().trim());

                if (tipo.equals("Guitarra")) {
                    int cuerdas = Integer.parseInt(txtCuerdas_Teclas.getText().trim());
                    String tipoPastilla = txtTipoPastilla_RitmosIntegrados.getText().trim();

                    Guitarra nuevo = new Guitarra(id, marca, modelo, precio, true, cuerdas, tipoPastilla);
                    controller.agregarInstrumento(nuevo);
                } else {
                    int teclas = Integer.parseInt(txtCuerdas_Teclas.getText().trim());
                    int ritmosIntegrados = Integer.parseInt(txtTipoPastilla_RitmosIntegrados.getText().trim());
                    Teclado nuevo = new Teclado(id, marca, modelo, precio, true, teclas, ritmosIntegrados);
                    controller.agregarInstrumento(nuevo);
                }

                txtMarca.clear();
                txtModelo.clear();
                txtPrecio.clear();
                txtEspecificacion.clear();
                txtCuerdas_Teclas.clear();
                txtTipoPastilla_RitmosIntegrados.clear();

                lblEstado.setText("Agregado :)");

                table.setItems(FXCollections.observableArrayList(controller.obtenerInstrumento()));

            } catch (NumberFormatException ex) {
                lblEstado.setText("Verifique los números");
            }

        });

        btnEliminar.setOnAction(e -> {
            // 1. Obtener el registro de la fila seleccionada
            Instrumento seleccionado = table.getSelectionModel().getSelectedItem();

            // 2. Validación preventiva contra NullPointerException
            if (seleccionado == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Atención");
                alerta.setHeaderText(null);
                alerta.setContentText("Debes seleccionar una fila en la tabla primero.");
                alerta.showAndWait();
                return;
            }
            controller.eliminarInstrumento(seleccionado);
            table.setItems(FXCollections.observableArrayList(controller.obtenerInstrumento()));

        });

        VBox root = new VBox(16, lbl_titulo, table, formulario);
        return root;
    }
}
