package com.hachimakidev.musicstore.model;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InstrumentoManager {


    private final ObservableList<Instrumento> instrumentos;

    public InstrumentoManager() {
        this.instrumentos = FXCollections.observableArrayList();
        cargarDatos();
    }

    public boolean eliminar(Instrumento item) {
        return this.instrumentos.remove(item);
    }


    public final void cargarDatos() {

        InputStream input = getClass().getResourceAsStream("/instrumentos.json");

        if (input == null) {
            System.err.println("Error: Verifique que este en la carpeta resources o en la carpeta raiz.");
        }
        try {
            InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8);

            Gson gson = new Gson();

            JsonObject[] arregloJson = gson.fromJson(reader, JsonObject[].class);

            for (JsonObject obj : arregloJson) {

                String tipo = obj.get("tipo").getAsString();

                if (tipo.equals("teclado")) {
                    Teclado teclado = gson.fromJson(obj, Teclado.class);
                    instrumentos.add(teclado);
                } else {
                    Guitarra guitarra = gson.fromJson(obj, Guitarra.class);
                    instrumentos.add(guitarra);
                }

            }

        } catch (JsonIOException | JsonSyntaxException e) {
            System.err.println("Error al transformar el archivo");
        }

    }

    public ObservableList<Instrumento> getIntrumentos() {
        return instrumentos;
    }

    public void agregarInstrumento(Instrumento nuevo) {
        instrumentos.add(nuevo);
    }

}
