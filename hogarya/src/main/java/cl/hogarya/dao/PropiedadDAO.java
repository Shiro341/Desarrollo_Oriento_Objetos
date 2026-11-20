package cl.hogarya.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import cl.hogarya.database.ConexionDB;
import cl.hogarya.model.Casa;
import cl.hogarya.model.Departamento;
import cl.hogarya.model.Propiedad;

public class PropiedadDAO {

    public boolean guardar(Propiedad propiedad) {

        String SQL = "INSERT INTO PROPIEDADES(tipo, direccion, arriendo_base, metros_cuadrados, cantidad_pisos, tiene_patio, numero_piso, gastos_comunes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL)) {

            ps.setString(1, propiedad.getTipo());
            ps.setString(2, propiedad.getDireccion());
            ps.setInt(3, propiedad.getArriendoBase());
            ps.setInt(4, propiedad.getMetrosCuadrados());

            if (propiedad instanceof Casa miCasa) {

                ps.setInt(5, miCasa.getCantidadPisos());
                ps.setInt(6, miCasa.isTienePatio() ? 1 : 0);

                ps.setNull(7, Types.INTEGER);
                ps.setNull(8, Types.INTEGER);

            } else if (propiedad instanceof Departamento miDepartamento) {

                ps.setNull(5, Types.INTEGER);
                ps.setNull(6, Types.INTEGER);

                ps.setInt(7, miDepartamento.getNumeroPiso());
                ps.setInt(8, miDepartamento.getGastosComunes());

            } else {
                throw new IllegalArgumentException("Tipo de propiedad no válido");
            }

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException | IllegalArgumentException e) {

            System.out.println("Error al guardar la propiedad: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Propiedad> obtenerTodos() {
        ArrayList<Propiedad> lista = new ArrayList<>();

        String SQL = "SELECT id, tipo, direccion, arriendo_base, metros_cuadrados, cantidad_pisos, tiene_patio, numero_piso, gastos_comunes FROM propiedades ORDER BY id ASC";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt(1);
                String tipo = rs.getString(2);
                String direccion = rs.getString(3);
                int arriendoBase = rs.getInt(4);
                int metrosCuadrados = rs.getInt(5);
                int cantidadPisos = rs.getInt(6);
                boolean tienePatio = rs.getBoolean(7);
                int numeroPiso = rs.getInt(8);
                int gastosComunes = rs.getInt(9);

                switch (tipo) {
                    case "CASA" -> {
                        Casa newCasa = new Casa(id, direccion, arriendoBase, metrosCuadrados, cantidadPisos,
                                tienePatio);
                        lista.add(newCasa);
                    }
                    case "DEPARTAMENTO" -> {
                        Departamento newDepartameto = new Departamento(id, direccion, arriendoBase, metrosCuadrados,
                                numeroPiso, gastosComunes);
                        lista.add(newDepartameto);
                    }
                    default -> throw new IllegalArgumentException("Tipo de propiedad no válida");
                }
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error al guardar la propiedad: " + e.getMessage());

        }

        return lista;

    }

    public boolean actualizar(Propiedad propiedad){

        String SQL = "UPDATE propiedades SET tipo = ?, direccion = ?, arriendo_base = ?, metros_cuadrados = ?, cantidad_pisos = ?, tiene_patio = ?, numero_piso = ?, gastos_comunes = ? WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
        PreparedStatement ps = conn.prepareStatement(SQL)){
            
            ps.setString(1, propiedad.getTipo());
            ps.setString(2, propiedad.getDireccion());
            ps.setInt(3, propiedad.getArriendoBase());
            ps.setInt(4, propiedad.getMetrosCuadrados());
            ps.setInt(9, propiedad.getId());

            if (propiedad instanceof Casa actulizarCasa){
                ps.setInt(5, actulizarCasa.getCantidadPisos());
                ps.setInt(6, actulizarCasa.isTienePatio() ? 1:0);
                ps.setNull(7, Types.INTEGER);
                ps.setNull(8, Types.INTEGER);

            } else if (propiedad instanceof Departamento actualizarDepartamento) {
                ps.setNull(5, Types.INTEGER);
                ps.setNull(6, Types.INTEGER);
                ps.setInt(7, actualizarDepartamento.getNumeroPiso());
                ps.setInt(8, actualizarDepartamento.getGastosComunes());

            } else {
                throw new IllegalArgumentException("Tipo de propiedad no válida");
            }
            
            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error al actulizar la propiedad");
            return false;
        }

    }

    public boolean eliminar(int id){

        String SQL = "DELETE FROM propiedades WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
        PreparedStatement ps = conn.prepareStatement(SQL)){

            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;  
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar");
            return false;

        }   

    }

}