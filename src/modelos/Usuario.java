package modelos;

public class Usuario {
    private long id;
    private String nombre;
    private String apellidos;
    private String dni;

    // Inside modelos.Usuario.java
    @Override
    public String toString() {
        return nombre + " " + apellidos; // O el campo que prefieras mostrar
    }

    public Usuario() {
    }

    public Usuario(long id, String nombre, String apellidos, String dni) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
