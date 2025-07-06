package procesos;

import dao.UsuarioDAO;
import modelos.Usuario;

import java.util.List;

public class UsuarioServiceImpl implements UsuarioService {

    UsuarioDAO userDAO = new UsuarioDAO();

    @Override
    public boolean registrarUsuario(Usuario user) {

        Boolean res = userDAO.agregarUsuario(user);
        if (res) {
            System.out.println("Usuario registrado correctamente.");
        } else {
            System.out.println("Error al registrar el usuario.");
        }
        return res;
    }

    @Override
    public boolean editarUsuario(Usuario user) {
        // Implementación del método para editar un usuario
        return false; // Cambiar según la lógica de negocio
    }

    @Override
    public boolean eliminarUsuario(int id) {
        // Implementación del método para eliminar un usuario
        return false; // Cambiar según la lógica de negocio
    }

    @Override
    public Usuario obtenerUsuario(int id) {
        // Implementación del método para obtener un usuario por ID
        return null; // Cambiar según la lógica de negocio
    }

    @Override
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = userDAO.obtenerUsuarios();
        return usuarios;
    }
}
