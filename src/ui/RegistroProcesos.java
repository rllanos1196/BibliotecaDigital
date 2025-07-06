package ui;

import modelos.Libro;
import modelos.Usuario;
import procesos.LibroService;
import procesos.UsuarioService;
import procesos.UsuarioServiceImpl;
import procesos.LibroServiceImpl;
import util.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RegistroProcesos {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JTextField txtDNI;
    private JLabel lblDNI;
    private JButton btnRegistrar;
    private JButton btnCancelar;
    private JTextField txtAutor;
    private JTextField txtTitulo;
    private JLabel lblAutor;
    private JLabel lblTitulo;
    private JLabel lblAnioPublicacion;
    private JTextField txtAnioPublicacion;
    private JButton btnGuardar;
    private JButton btnVer;
    private JTable tbLibros;
    private JComboBox comboBox1;
    private JLabel lblUser;
    private JLabel lblLibro;
    private JComboBox comboBox2;
    private JLabel lblDevolucion;
    private JFormattedTextField formattedTextField1;
    private JButton btnRegistrarP;
    private JButton btnCancelarP;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JButton btnDevolver;
    private JButton btnCancelarD;
    private DefaultTableModel tableModel;

    private UsuarioService usuarioService;
    private LibroService libroService;

    public RegistroProcesos() {
        usuarioService = new UsuarioServiceImpl();
        libroService = new LibroServiceImpl();
        // Initialize components and set up the UI
        JFrame frame = new JFrame("Registro de Procesos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);
        frame.setContentPane(panel1);
        frame.pack();
        frame.setVisible(true);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellido","Edad"}, 0);
        tbLibros.setModel(tableModel);

        panel1 = new JPanel();
        tabbedPane1 = new JTabbedPane();

        txtNombre = new JTextField();
        txtApellido = new JTextField();
        lblNombre = new JLabel("Nombre:");
        lblApellido = new JLabel("Apellido:");
        txtDNI = new JTextField();
        lblDNI = new JLabel("DNI:");
        btnRegistrar = new JButton("Registrar");
        btnCancelar = new JButton("Cancelar");
        txtAutor = new JTextField();
        txtTitulo = new JTextField();
        lblAutor = new JLabel("Autor:");
        lblTitulo = new JLabel("Título:");
        lblAnioPublicacion = new JLabel("Año de Publicación:");
        txtAnioPublicacion = new JTextField();
        btnGuardar = new JButton("Guardar");
        btnVer = new JButton("Ver");
        tbLibros = new JTable();
        comboBox1 = new JComboBox();
        lblUser = new JLabel("Usuario:");
        lblLibro = new JLabel("Libro:");
        comboBox2 = new JComboBox();
        lblDevolucion = new JLabel("Fecha de Devolución:");
        formattedTextField1 = new JFormattedTextField();
        btnRegistrarP = new JButton("Registrar Proceso");
        btnCancelarP = new JButton("Cancelar Proceso");
        comboBox3 = new JComboBox();
        comboBox4 = new JComboBox();
        btnDevolver = new JButton("Devolver");
        btnCancelarD = new JButton("Cancelar Devolución");

        // Set layout and add components to the panel
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                String dni = txtDNI.getText();

                if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty()) {
                    JOptionPane.showMessageDialog(panel1, "Por favor, complete todos los campos.",
                            "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                } else {
                    Usuario user = new Usuario();
                    user.setNombre(nombre);
                    user.setApellidos(apellido);
                    user.setDni(dni);
                    usuarioService.registrarUsuario(user);
                    JOptionPane.showMessageDialog(panel1, "Usuario registrado: " + nombre + " " + apellido);
                    LimpiarUsuaros();
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LimpiarUsuaros();
            }
        });
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String autor = txtAutor.getText();
                String titulo = txtTitulo.getText();
                String anioPublicacion = txtAnioPublicacion.getText();

                if (autor.isEmpty() || titulo.isEmpty() || anioPublicacion.isEmpty()) {
                    JOptionPane.showMessageDialog(panel1, "Por favor, complete todos los campos.",
                            "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                } else {
                    Libro libro = new Libro();
                    libro.setAutor(autor);
                    libro.setTitulo(titulo);
                    libro.setAnioPublicacion(anioPublicacion);
                    libroService.registrarLibro(libro);
                    JOptionPane.showMessageDialog(panel1, "Libro guardado: " + titulo + " de " + autor);
                    limpiarCamposEnContenedor(panel1);
                    cargarLibros();
                }
            }
        });

        cargarLibros();
        cargarUsuariosEnCombo(comboBox1);
        cargarLibrosEnCombo(comboBox2);


    }
    private void LimpiarUsuaros() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtDNI.setText("");
        txtNombre.requestFocus();
    }

    //Libros
    private void cargarLibros() {
        tableModel.setRowCount(0);

        libroService = new LibroServiceImpl();
        List<Libro> libros = libroService.listarLibros();
        if (libros != null) {
            for (Libro libro : libros) {
                tableModel.addRow(new Object[]{libro.getId(),libro.getAutor(), libro.getTitulo(), libro.getAnioPublicacion()});
            }
        }
    }


    //Genericos
    private void limpiarCamposEnContenedor(java.awt.Container contenedor) {
        for (java.awt.Component c : contenedor.getComponents()) {
            if (c instanceof JTextField) {
                ((JTextField) c).setText("");
            } else if (c instanceof java.awt.Container) {
                limpiarCamposEnContenedor((java.awt.Container) c);
            }
        }
    }

    // Cargar usuarios en comboBox1
    private void cargarUsuariosEnCombo(JComboBox<Usuario> combo) {
        if (usuarioService == null) {
            usuarioService = new UsuarioServiceImpl(); // Asegurarse de que el servicio esté inicializado
        }
        combo.removeAllItems();
        // No es necesario reinicializar usuarioService aquí
        combo.addItem(new Usuario(0, "Seleccionar usuario", "", "")); // Opción por defecto
        List<Usuario> usuarios = usuarioService.listarUsuarios();

        if (usuarios != null && !usuarios.isEmpty()) { // Verifica tanto null COMO lista vacía
            for (Usuario u : usuarios) {
                combo.addItem(u);
            }
        } else {
            System.out.println("DEBUG: No se encontraron usuarios para cargar en el combo.");
            JOptionPane.showMessageDialog(panel1, "No hay usuarios registrados para mostrar.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        combo.setSelectedIndex(0); // Selecciona el primer elemento (generalmente "Seleccionar usuario")
        combo.revalidate();        // Le dice al layout manager que vuelva a calcular los tamaños y posiciones
        combo.repaint();

    }

    // Cargar libros en comboBox2
    private void cargarLibrosEnCombo(JComboBox<Libro> combo) {
        combo.removeAllItems();
        combo.addItem(new Libro(0, "Seleccionar libro", "", ""));
        List<Libro> libros = libroService.listarLibros();
        if (libros != null && !libros.isEmpty()) {
            for (Libro l : libros) {
                combo.addItem(l);
            }
        } else {
            System.out.println("DEBUG: No se encontraron libros para cargar en el combo.");
            JOptionPane.showMessageDialog(panel1, "No hay libros registrados para mostrar.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }

    }


}
