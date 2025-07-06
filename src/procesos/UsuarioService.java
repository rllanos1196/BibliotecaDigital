package procesos;

import modelos.Usuario;

import java.util.List;

public interface UsuarioService {
    public boolean registrarUsuario(Usuario user);
    public boolean editarUsuario(Usuario user);
    public boolean eliminarUsuario(int id);
    public Usuario obtenerUsuario(int id);
    public List<Usuario> listarUsuarios();


}
