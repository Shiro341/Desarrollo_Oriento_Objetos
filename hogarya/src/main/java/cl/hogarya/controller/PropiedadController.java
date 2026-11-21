package cl.hogarya.controller;

import java.util.ArrayList;

import cl.hogarya.dao.PropiedadDAO;
import cl.hogarya.model.Casa;
import cl.hogarya.model.Departamento;
import cl.hogarya.model.Propiedad;

public class PropiedadController {

    private final PropiedadDAO propiedadDAO;

    public PropiedadController() {
        propiedadDAO = new PropiedadDAO();
    }

    public boolean registrarPropiedad(String tipo, String direccion, String stringArriendoBase, String stringMetrosCuadrados, String stringCantidadPisos, boolean tienePatio, String stringNumeroPisos, String stringGastosComunes) {
        if (tipo == null || tipo.isBlank()
                || direccion == null || direccion.isBlank()
                || stringArriendoBase == null || stringArriendoBase.isBlank()
                || stringMetrosCuadrados == null || stringMetrosCuadrados.isBlank()) {
            return false;
        }

        try {
            int arriendoBase = Integer.parseInt(stringArriendoBase.trim());
            int metrosCuadrados = Integer.parseInt(stringMetrosCuadrados.trim());

            if (arriendoBase <= 0 || metrosCuadrados <= 0) {
                return false;
            }

            if (tipo.trim().equals("CASA")) {
                if (stringCantidadPisos == null || stringCantidadPisos.isBlank()) {
                    return false;
                }

                int cantidadPisos = Integer.parseInt(stringCantidadPisos.trim());

                if (cantidadPisos <= 0) {
                    return false;
                }

                Casa nuevaCasa = new Casa(direccion, arriendoBase, metrosCuadrados, cantidadPisos, tienePatio);
                return propiedadDAO.guardar(nuevaCasa);

            } else if (tipo.trim().equals("DEPARTAMENTO")) {
                if (stringNumeroPisos == null || stringNumeroPisos.isBlank()
                        || stringGastosComunes == null || stringGastosComunes.isBlank()) {
                    return false;
                }

                int numeroPiso = Integer.parseInt(stringNumeroPisos.trim());
                int gastosComunes = Integer.parseInt(stringGastosComunes.trim());

                if (numeroPiso <= 0 || gastosComunes < 0) {
                    return false;
                }

                Departamento nuevoDepartamento = new Departamento(direccion, arriendoBase, metrosCuadrados, numeroPiso, gastosComunes);
                return propiedadDAO.guardar(nuevoDepartamento);

            } else {
                throw new IllegalArgumentException("Tipo de propiedad no válido");
            }

        } catch (IllegalArgumentException e) {
            System.err.println("Error al transformar los numeros" + e.getMessage());
            return false;
        }
    }

    public ArrayList<Propiedad> obtenerPropiedades() {
        return propiedadDAO.obtenerTodos();
    }

    public boolean eliminarPropiedad(int id) {
        if (id <= 0) {
            return false;
        }

        return propiedadDAO.eliminar(id);
    }

    public boolean actualizarPropiedad(int id, String tipo, String direccion, String stringArriendoBase, String stringMetrosCuadrados, String stringCantidadPisos, boolean tienePatio, String stringNumeroPisos, String stringGastosComunes) {
        if (id <= 0) {
            return false;
        }

        if (tipo == null || tipo.trim().isBlank()
                || direccion == null || direccion.trim().isBlank()
                || stringArriendoBase == null || stringArriendoBase.trim().isBlank()
                || stringMetrosCuadrados == null || stringMetrosCuadrados.trim().isBlank()) {
            return false;
        }

        try {
            int arriendoBase = Integer.parseInt(stringArriendoBase.trim());
            int metrosCuadrados = Integer.parseInt(stringMetrosCuadrados.trim());

            if (arriendoBase <= 0 || metrosCuadrados <= 0) {
                return false;
            }

            if (tipo.trim().equals("CASA")) {
                if (stringCantidadPisos == null || stringCantidadPisos.trim().isBlank()) {
                    return false;
                }

                int cantidadPisos = Integer.parseInt(stringCantidadPisos.trim());

                if (cantidadPisos <= 0) {
                    return false;
                }

                Casa actualizarCasa = new Casa(id, direccion, arriendoBase, metrosCuadrados, cantidadPisos, tienePatio);
                return propiedadDAO.actualizar(actualizarCasa);

            } else if (tipo.trim().equals("DEPARTAMENTO")) {
                if (stringNumeroPisos == null || stringNumeroPisos.trim().isBlank()
                        || stringGastosComunes == null || stringGastosComunes.trim().isBlank()) {
                    return false;
                }

                int numeroPiso = Integer.parseInt(stringNumeroPisos.trim());
                int gastosComunes = Integer.parseInt(stringGastosComunes.trim());

                if (numeroPiso <= 0 || gastosComunes < 0) {
                    return false;
                }

                Departamento actualizarDepartamento = new Departamento(id, direccion, arriendoBase, metrosCuadrados, numeroPiso, gastosComunes);
                return propiedadDAO.actualizar(actualizarDepartamento);

            } else {
                throw new IllegalArgumentException("Tipo de propiedad no válido");
            }

        } catch (IllegalArgumentException e) {
            System.err.println("Error al actulizar la propiedad " + e.getMessage());
            return false;
        }
    }
}