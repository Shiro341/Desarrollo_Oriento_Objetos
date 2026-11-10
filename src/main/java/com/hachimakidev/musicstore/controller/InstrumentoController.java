package com.hachimakidev.musicstore.controller;

import com.hachimakidev.musicstore.model.Instrumento;
import com.hachimakidev.musicstore.model.InstrumentoManager;

import javafx.collections.ObservableList;

public class InstrumentoController {

    private final InstrumentoManager manager = new InstrumentoManager();

    public ObservableList<Instrumento> obtenerInstrumento() {
        return manager.getIntrumentos();
    }

    public void agregarInstrumento(Instrumento nuevo) {
        manager.agregarInstrumento(nuevo);
    }

    public boolean eliminarInstrumento(Instrumento item){
        if(item == null) return false;
        return manager.eliminar(item);
    }

}
