package cl.hogarya.model;

public abstract class Propiedad {

    protected int id;
    protected String direccion;
    protected int arriendoBase;
    protected int metrosCuadrados;

    // Constructor SIN id → para INSERT (la BD genera el id)
    public Propiedad(String direccion, int arriendoBase, int metrosCuadrados) {
        this.direccion = direccion;
        this.arriendoBase = arriendoBase;
        this.metrosCuadrados = metrosCuadrados;
    }

    // Constructor CON id → para SELECT y UPDATE
    public Propiedad(int id, String direccion, int arriendoBase, int metrosCuadrados) {
        this(direccion, arriendoBase, metrosCuadrados);   // reutiliza el anterior
        this.id = id;
    }

    // --- Métodos abstractos: cada subclase los implementa a su manera ---
    public abstract String getTipo();            // sirve además como discriminador en la BD
    public abstract int calcularPagoMensual();
    public abstract String getDetalle();

    // --- Getters y setters comunes ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public int getArriendoBase() { return arriendoBase; }
    public void setArriendoBase(int arriendoBase) { this.arriendoBase = arriendoBase; }

    public int getMetrosCuadrados() { return metrosCuadrados; }
    public void setMetrosCuadrados(int metrosCuadrados) { this.metrosCuadrados = metrosCuadrados; }
}