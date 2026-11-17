package com.hachimakidev.userapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.hachimakidev.userapp.database.ConexionDB;
import com.hachimakidev.userapp.model.Usuario;

public class UsuarioDAO {

    public boolean guardar(Usuario usuario) {

        String sql = "INSERT INTO USUARIOS (username, correo, password, rol) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.obtenerConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getPassword());
            ps.setString(4, usuario.getRol());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al guardar usuario: " + e.getMessage());
            return false;
        }

    }

    public ArrayList<Usuario> obtenerTodos() {
        ArrayList<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, username, correo, password, rol FROM usuarios ORDER BY id ASC";

        try (Connection conn = ConexionDB.obtenerConexion();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String correo = rs.getString("correo");
                String password = rs.getString("password");
                String rol = rs.getString("rol");

                // se agrega un nuevo usuario llamado u
                Usuario u = new Usuario(id, username, correo, password, rol);
                lista.add(u);
            }

        } catch (SQLException e) {
            System.err.println("Error en la lista de usuarios: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE USUARIOS SET username = ?, correo = ?, password = ?, rol = ? WHERE id = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getPassword());
            ps.setString(4, usuario.getRol());
            ps.setInt(5, usuario.getId());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }

    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

}
