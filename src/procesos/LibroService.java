package procesos;

import modelos.Libro;

import java.util.List;

public interface LibroService {
    boolean registrarLibro(Libro libro);
    boolean editarLibro(Libro libro);
    boolean eliminarLibro(int id);
    Libro obtenerLibro(int id);
    List<Libro> listarLibros();
}
