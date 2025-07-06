package util;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class Conexion {
    private static final String URL ="jdbc:mysql://localhost:3306/crud_demo?useSSL=true&serverTimezone=America/Lima";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "mysql";
    public static Connection conectar() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USUARIO,
                    PASSWORD);
            System.out.println("Conectado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
        return con;
    }
}
