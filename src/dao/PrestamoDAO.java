package dao;

import modelos.Libro;
import modelos.Prestamo;
import modelos.Usuario;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    public List<Usuario> getUserConLibros() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "{CALL sp_obtener_usuariosConLibros()}";
        try (Connection con = Conexion.conectar();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getLong("ID"),
                        rs.getString("NOMBRE"),
                        rs.getString("APELLIDOS"),
                        rs.getString("DNI"));
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Libro> getLibrosPrestadosPorUsuario(long idUsuario) {
        List<Libro> lista = new ArrayList<>();
        String sql = "{CALL sp_obtener_librosPrestadosPorUser(?)}";
        try (Connection con = Conexion.conectar();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setLong(1, idUsuario);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    Libro l = new Libro();
                    l.setId(rs.getLong("ID"));
                    l.setTitulo(rs.getString("TITULO"));
                    l.setAutor(rs.getString("AUTOR"));
                    lista.add(l);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Libro> obtenerLibrosDisponibles() {
        List<Libro> lista = new ArrayList<>();
        String sql = "{CALL sp_obtener_librosDisponibles()}";
        try (Connection con = Conexion.conectar();
             CallableStatement cs = con.prepareCall(sql)) {
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    Libro l = new Libro(
                            rs.getLong("ID"),
                            rs.getString("TITULO"),
                            rs.getString("AUTOR"),
                            ""
                    );
                    lista.add(l);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Prestamo> getReportePrestamo() {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "{CALL sp_reportePrestamo()}";
        try (Connection con = Conexion.conectar();
             CallableStatement cs = con.prepareCall(sql)) {
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    Prestamo p = new Prestamo( );
                    p.setId(rs.getLong("ID"));
                    p.setIdUsuario(rs.getLong("ID_USUARIO"));
                    p.setIdLibro(rs.getLong("ID_LIBRO"));
                    p.setFechaRegistro(rs.getDate("FECHA_REGISTRO"));
                    p.setFechaDevolucion(rs.getDate("FECHA_DEVOLUCION"));
                    //p.getUsuario().setNombre(rs.getString("NOMBRE"));
                    p.setUsuario(new Usuario(
                            rs.getLong("ID_USUARIO"),
                            rs.getString("NOMBRE"),
                            rs.getString("APELLIDOS"),
                            rs.getString("DNI")
                    ));
                    p.setLibro(new Libro(
                            rs.getLong("ID_LIBRO"),
                            rs.getString("TITULO"),
                            rs.getString("AUTOR"),
                            ""
                    ));


                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }


    public boolean registrarPrestamo(Prestamo pres) {
        String sql = "INSERT INTO prestamo(ID_USUARIO, ID_LIBRO, FECHA_REGISTRO,FECHA_DEVOLUCION) VALUES (?, ?, ?,?)";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, pres.getIdUsuario());
            ps.setLong(2, pres.getIdLibro());
            ps.setDate(3, new java.sql.Date(pres.getFechaRegistro().getTime()));
            ps.setDate(4, new java.sql.Date(pres.getFechaDevolucion().getTime()));
            ps.executeUpdate();
            return Boolean.TRUE;
        } catch (SQLException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }



}
