package cl.hogarya;

import cl.hogarya.view.PropiedadView;
import javafx.application.Application;
import javafx.stage.Stage;

public class HogarYAApp extends Application {

    @Override
    public void start(Stage stage) {
        PropiedadView vista = new PropiedadView();
        stage.setTitle("HogarYA — Gestión de Propiedades (Oracle JDBC + MVC + DAO)");
        stage.setScene(vista.crearEscena());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}