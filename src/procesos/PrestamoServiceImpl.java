package procesos;
import dao.PrestamoDAO;
import modelos.Prestamo;
import java.util.List;


public class PrestamoServiceImpl implements PrestamoService {


    PrestamoDAO presDAO = new PrestamoDAO();



    @Override
    public boolean registrarPrestamo(Prestamo prestamo) {

        // Implementación del método para registrar un préstamo
        Boolean res = presDAO.registrarPrestamo(prestamo);
        if (res) {
            System.out.println("Préstamo registrado correctamente.");
        } else {
            System.out.println("Error al registrar el préstamo.");
        }
        return res;
    }

    @Override
    public boolean editarPrestamo(Prestamo prestamo) {
        // Implementación del método para editar un préstamo
        return false; // Cambiar según la lógica de negocio
    }

    @Override
    public boolean eliminarPrestamo(int id) {
        // Implementación del método para eliminar un préstamo
        return false; // Cambiar según la lógica de negocio
    }

    @Override
    public Prestamo obtenerPrestamo(int id) {
        // Implementación del método para obtener un préstamo por ID
        return null; // Cambiar según la lógica de negocio
    }

    @Override
    public List<Prestamo> listarPrestamos() {
        List<Prestamo> prestamos = presDAO.getReportePrestamo();
        return prestamos;
    }

    @Override
    public List<Prestamo> listarPrestamosPorUsuario(int idUsuario) {
        // Implementación del método para listar préstamos por usuario
        return null; // Cambiar según la lógica de negocio
    }

    @Override
    public List<Prestamo> listarPrestamosPorLibro(int idLibro) {
        // Implementación del método para listar préstamos por libro
        return null; // Cambiar según la lógica de negocio
    }
}
