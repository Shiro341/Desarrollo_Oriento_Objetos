package cl.hogarya.model;

public class Casa extends Propiedad {

    private int cantidadPisos;
    private boolean tienePatio;

    public Casa(String direccion, int arriendoBase, int metrosCuadrados,
                int cantidadPisos, boolean tienePatio) {
        super(direccion, arriendoBase, metrosCuadrados);
        this.cantidadPisos = cantidadPisos;
        this.tienePatio = tienePatio;
    }

    public Casa(int id, String direccion, int arriendoBase, int metrosCuadrados,
                int cantidadPisos, boolean tienePatio) {
        super(id, direccion, arriendoBase, metrosCuadrados);
        this.cantidadPisos = cantidadPisos;
        this.tienePatio = tienePatio;
    }

    @Override
    public String getTipo() {
        return "CASA";
    }

    @Override
    public int calcularPagoMensual() {
        // Según la guía del Proyecto 2, Casa retorna solo el arriendoBase.
        return getArriendoBase();
    }

    @Override
    public String getDetalle() {
        return cantidadPisos + " piso(s) · " + (tienePatio ? "con patio" : "sin patio");
    }

    public int getCantidadPisos() {
        return cantidadPisos;
    }

    public void setCantidadPisos(int cantidadPisos) {
        this.cantidadPisos = cantidadPisos;
    }

    public boolean isTienePatio() {
        return tienePatio;
    }

    public void setTienePatio(boolean tienePatio) {
        this.tienePatio = tienePatio;
    }
}