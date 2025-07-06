package dao;

import modelos.Libro;
import modelos.Usuario;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {
    public boolean registrarLibro(Libro libro) {
        String sql = "INSERT INTO Libro(TITULO, AUTOR, ANIO_PUBLICACION) VALUES (?, ?, ?)";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getAnioPublicacion());
            ps.executeUpdate();
            return Boolean.TRUE;
        } catch (SQLException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
    public List<Libro> obtenerLibros() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libro";
        try (Connection con = Conexion.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Libro u = new Libro(
                        rs.getLong("ID"),
                        rs.getString("TITULO"),
                        rs.getString("AUTOR"),
                        rs.getString("ANIO_PUBLICACION"));
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    public Libro obtenerLibroPorId(long id) {
        String sql = "SELECT * FROM Libro WHERE ID = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Libro(
                            rs.getLong("ID"),
                            rs.getString("TITULO"),
                            rs.getString("AUTOR"),
                            rs.getString("ANIO_PUBLICACION")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizarLibro(Libro l) {
        String sql = "UPDATE Libro SET TITULO=?, AUTOR=?, ANIO_PUBLICACION=? WHERE ID=?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, l.getTitulo());
            ps.setString(2, l.getAutor());
            ps.setString(3, l.getAnioPublicacion());
            ps.setLong(4, l.getId());
            ps.executeUpdate();
            return Boolean.TRUE;
        } catch (SQLException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
    public void eliminarLibro(int id) {
        String sql = "DELETE FROM Libro WHERE ID=?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
