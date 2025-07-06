package procesos;

import dao.LibroDAO;
import modelos.Libro;

import java.util.List;

public class LibroServiceImpl implements LibroService {

    LibroDAO libroDAO = new LibroDAO();

    @Override
    public boolean registrarLibro(Libro libro) {
        // Implementación del método para registrar un libro
        Boolean res = libroDAO.registrarLibro(libro);
        if (res) {
            System.out.println("Libro registrado correctamente.");
        } else {
            System.out.println("Error al registrar el libro.");
        }
        return res;
    }

    @Override
    public boolean editarLibro(Libro libro) {
        // Implementación del método para editar un libro
        Boolean res = libroDAO.actualizarLibro(libro);
        if (res) {
            System.out.println("Libro editado correctamente.");
        } else {
            System.out.println("Error al editar el libro.");
        }
        return res;
    }

    @Override
    public boolean eliminarLibro(int id) {
        libroDAO.eliminarLibro(id);
        return true;
    }

    @Override
    public Libro obtenerLibro(int id) {
        Libro libro = libroDAO.obtenerLibroPorId(id);
        return libro;
    }

    @Override
    public List<Libro> listarLibros() {
        List<Libro> libros = libroDAO.obtenerLibros();
        return libros;
    }
}
