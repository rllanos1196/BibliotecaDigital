package procesos;

import modelos.Prestamo;

import java.util.List;

public interface PrestamoService {
    public boolean registrarPrestamo(Prestamo prestamo);
    public boolean editarPrestamo(Prestamo prestamo);
    public boolean eliminarPrestamo(int id);
    public Prestamo obtenerPrestamo(int id);
    public List<Prestamo> listarPrestamos();
    public List<Prestamo> listarPrestamosPorUsuario(int idUsuario);
    public List<Prestamo> listarPrestamosPorLibro(int idLibro);
}
