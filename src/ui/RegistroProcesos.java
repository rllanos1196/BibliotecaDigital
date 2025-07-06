package ui;

import com.toedter.calendar.JDateChooser;
import dao.UsuarioDAO;
import modelos.Libro;
import modelos.Usuario;
import procesos.LibroService;
import procesos.LibroServiceImpl;
import procesos.UsuarioService;
import procesos.UsuarioServiceImpl;
import util.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

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
    private JTable tbRegistrados;
    private JComboBox comboBox1;
    private JLabel lblUser;
    private JLabel lblLibro;
    private JComboBox comboBox2;
    private JLabel lblDevolucion;
    private JButton btnRegistrarP;
    private JButton btnCancelarP;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JButton btnDevolver;
    private JButton btnCancelarD;
    private JPanel jpPrestamo;
    private JPanel jpDevol;
    private JPanel jpdate;
    private DefaultTableModel tableModel;
    private JDateChooser dateChooserDevolucion;

    //private UsuarioDAO userDao;
    private UsuarioService usuarioService;
    private LibroService libroService;


    public RegistroProcesos() {
        usuarioService = new UsuarioServiceImpl();
        libroService = new LibroServiceImpl();

        tableModel = new DefaultTableModel(new Object[]{"ID", "Autor", "Título", "Anio"}, 0);
        tbRegistrados.setModel(tableModel);
        tbRegistrados.getTableHeader().repaint();

        dateChooserDevolucion = new JDateChooser();
        dateChooserDevolucion.setDateFormatString("dd/MM/yyyy");




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
                    //userDao.agregarUsuario(user);
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


        // procesos para libros
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





    private void LimpiarUsuaros(){
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegistroProcesos::new);
        JFrame frame = new JFrame("Registro de Procesos");
        frame.setContentPane(new RegistroProcesos().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

}
