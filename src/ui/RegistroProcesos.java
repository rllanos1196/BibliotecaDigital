package ui;

import modelos.Libro;
import modelos.Prestamo;
import modelos.Usuario;
import procesos.*;
import procesos.PrestamoService;
import procesos.PrestamoServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*; // Asegúrate de importar java.awt.* para LayoutManager si lo usas en el código
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.toedter.calendar.JDateChooser; // Necesario para JDateChooser

public class RegistroProcesos {
    private JPanel panel1; // Este panel es el root del formulario generado
    private JTabbedPane tabbedPane1;
    // ... (Todos tus otros campos declarados por el diseñador: txtNombre, txtApellido, etc.)
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
    private JTable tbLibros; // O tbRegistrados como lo llamabas antes
    private JComboBox comboBox1; // ComboBox de Usuario en pestaña Préstamo
    private JLabel lblUser;
    private JLabel lblLibro;
    private JComboBox comboBox2; // ComboBox de Libro en pestaña Préstamo
    private JPanel panelFechaPrestamo; // <-- ¡Este es el JPanel que añadiste para el JDateChooser!
    private JDateChooser dateChooserPrestamo; // <-- El JDateChooser para la pestaña de Préstamo
    private JButton btnRegistrarP;
    private JButton btnCancelarP;

    // Componentes para la pestaña de Devolución
    private JComboBox comboBox3; // Usuario para devolución
    private JComboBox comboBox4; // Libro para devolución
    private JButton btnDevolver;
    private JButton btnCancelarD;
    private JTable tbReporte;
    private JPanel jpDevol; // Tu panel de la pestaña de Devolución

    private DefaultTableModel tableModel;
    private DefaultTableModel tableModelR;

    private UsuarioService usuarioService;
    private LibroService libroService;
    private PrestamoService prestamoService;

    // --- ¡¡¡IMPORTANTE: main NO debe estar en el constructor!!! ---
    // El main debe estar fuera y crear una instancia de RegistroProcesos
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Registro de Procesos");
            RegistroProcesos app = new RegistroProcesos(); // Crea la instancia de tu UI
            frame.setContentPane(app.panel1); // Accede al panel raíz del formulario generado
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null); // Centrar la ventana
            frame.setVisible(true);
        });
    }


    public RegistroProcesos() {

        usuarioService = new UsuarioServiceImpl();
        libroService = new LibroServiceImpl();
        prestamoService = new PrestamoServiceImpl();


        tableModel = new DefaultTableModel(new Object[]{"ID", "Autor", "Título", "Anio"}, 0); // Ajusta las columnas si son de Usuarios
        tbLibros.setModel(tableModel);
        tbLibros.getTableHeader().repaint();

        tableModelR = new DefaultTableModel(new Object[]{"Nombres", "Apellidos","Dni", "Título", "Autor","Fecha Reg."}, 0);
        tbReporte.setModel(tableModelR);
        tbReporte.getTableHeader().repaint();

        dateChooserPrestamo = new JDateChooser();
        dateChooserPrestamo.setDateFormatString("dd/MM/yyyy");

        if (panelFechaPrestamo != null) {
            // Asegúrate de que panelFechaPrestamo tenga un LayoutManager para organizar el JDateChooser
            panelFechaPrestamo.setLayout(new BorderLayout()); // Usa BorderLayout o FlowLayout
            panelFechaPrestamo.add(dateChooserPrestamo, BorderLayout.CENTER); // O usa FlowLayout si es más simple
        } else {
            System.err.println("ERROR: El panel 'panelFechaPrestamo' no está vinculado. Verifica el diseñador de UI.");
        }
        // ----------------------------------------------------------------------


        // --- ActionListeners (estos están bien, asumiendo que los componentes ya están vinculados) ---
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
                    cargarUsuariosEnCombo(comboBox1); // Recargar el combo después de registrar un usuario
                    cargarUsuariosEnCombo(comboBox3); // Si comboBox3 es para devolución también
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
                    // Asegúrate de que anioPublicacion es un int en tu modelo de Libro
                    try {
                        libro.setAnioPublicacion(anioPublicacion);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panel1, "El año de publicación debe ser un número válido.",
                                "Error de Formato", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    libroService.registrarLibro(libro);
                    JOptionPane.showMessageDialog(panel1, "Libro guardado: " + titulo + " de " + autor);
                    limpiarCamposEnContenedor(panel1); // Esto limpia todo, considera un método más específico
                    cargarLibros(); // Recargar la tabla después de guardar un libro
                    cargarLibrosEnCombo(comboBox2); // Recargar el combo de libros para préstamo
                    cargarLibrosEnCombo(comboBox4); // Si comboBox4 es para devolución también
                }
            }
        });



        // --- Listener para el botón Devolver (btnDevolver) ---
        btnDevolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario usuarioSeleccionado = (Usuario) comboBox3.getSelectedItem();
                Libro libroSeleccionado = (Libro) comboBox4.getSelectedItem();
                // Aquí podrías tener otro JDateChooser o simplemente usar la fecha actual para la devolución
                // java.util.Date fechaDevolucionReal = new java.util.Date(); // Fecha actual de devolución
                // Si tienes un JDateChooser específico para la devolución en jpDevol:
                // Date fechaDevolucionReal = dateChooserDevolucionActual.getDate();

                if (usuarioSeleccionado == null || usuarioSeleccionado.getId() == 0 ||
                        libroSeleccionado == null || libroSeleccionado.getId() == 0) {
                    JOptionPane.showMessageDialog(jpDevol, "Por favor, seleccione un usuario y un libro para la devolución.",
                            "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // Lógica de devolución aquí...
                JOptionPane.showMessageDialog(jpDevol, "Devolución simulada: Usuario " + usuarioSeleccionado.getNombre() +
                        ", Libro " + libroSeleccionado.getTitulo());
            }
        });


        // --- Cargas iniciales ---
        cargarLibros(); // Carga la tabla de libros
        cargarUsuariosEnCombo(comboBox1); // Carga combo de usuarios para Préstamo
        cargarLibrosEnCombo(comboBox2);   // Carga combo de libros para Préstamo
        cargarUsuariosEnCombo(comboBox3); // Carga combo de usuarios para Devolución
        cargarLibrosEnCombo(comboBox4);   // Carga combo de libros para Devolución
        cargarReporte();

        btnRegistrarP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario usuarioSeleccionado = (Usuario) comboBox1.getSelectedItem();
                Libro libroSeleccionado = (Libro) comboBox2.getSelectedItem();
                Date fechaActual = new java.util.Date();
                java.util.Date fechaPrestamo =fechaActual;
                Calendar cal = Calendar.getInstance();
                cal.setTime(fechaPrestamo);
                cal.add(Calendar.DAY_OF_MONTH, 5);
                java.util.Date fechaDevolucion = cal.getTime();
                // Date fechaDevolucionPrestamo = dateChooserPrestamo.getDate(); // Usamos el JDateChooser

                if (usuarioSeleccionado == null || usuarioSeleccionado.getId() == 0 ||
                        libroSeleccionado == null || libroSeleccionado.getId() == 0 ) {
                    JOptionPane.showMessageDialog(panel1, "Por favor, seleccione un usuario, un libro y una fecha de devolución.",
                            "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // Aquí iría la lógica para registrar el préstamo...
                Prestamo pres = new Prestamo();
                pres.setIdUsuario(usuarioSeleccionado.getId());
                pres.setIdLibro(libroSeleccionado.getId());
                pres.setFechaRegistro(fechaActual);
                pres.setFechaDevolucion(fechaDevolucion);

                prestamoService.registrarPrestamo(pres);
                JOptionPane.showMessageDialog(panel1, "Préstamo simulado: Usuario " + usuarioSeleccionado.getNombre() +
                        ", Libro " + libroSeleccionado.getTitulo() + ", Devolución: " + fechaDevolucion.toString());
                cargarReporte(); // Recargar el reporte después de registrar un préstamo

            }
        });
    }


    private void cargarUsuariosEnCombo(JComboBox<Usuario> combo) {
        // Quita la verificación de null para usuarioService aquí, ya se inicializa en el constructor.
        combo.removeAllItems();
        // Asegúrate de que Usuario tenga un constructor que acepte estos parámetros
        combo.addItem(new Usuario(0, "Seleccionar usuario", "", "")); // Opción por defecto con ID 0

        List<Usuario> usuarios = usuarioService.listarUsuarios();

        System.out.println("DEBUG: En cargarUsuariosEnCombo - Intentando cargar " + (usuarios != null ? usuarios.size() : "0") + " usuarios.");
        if (usuarios != null && !usuarios.isEmpty()) {
            for (Usuario u : usuarios) {
                combo.addItem(u);
                System.out.println("DEBUG: Usuario añadido al combo: " + u.toString());
            }
        } else {
            System.out.println("DEBUG: No se encontraron usuarios para cargar en el combo.");
            // Evita el JOptionPane aquí si se puede mostrar repetidamente al inicio
            // JOptionPane.showMessageDialog(panel1, "No hay usuarios registrados para mostrar.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        // Estas líneas son importantes para asegurar la actualización visual
        combo.setSelectedIndex(0);
        combo.revalidate();
        combo.repaint();
    }

    private void cargarLibrosEnCombo(JComboBox<Libro> combo) {
        combo.removeAllItems();
        combo.addItem(new Libro(0, "Seleccionar libro", "", "")); // Asegúrate de un constructor similar en Libro
        List<Libro> libros = libroService.listarLibros();
        if (libros != null && !libros.isEmpty()) {
            for (Libro l : libros) {
                combo.addItem(l);
            }
        } else {
            System.out.println("DEBUG: No se encontraron libros para cargar en el combo.");
            // JOptionPane.showMessageDialog(panel1, "No hay libros registrados para mostrar.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        combo.setSelectedIndex(0);
        combo.revalidate();
        combo.repaint();
    }

    private void cargarLibros() {
        tableModel.setRowCount(0);
        List<Libro> libros = libroService.listarLibros();
        if (libros != null && !libros.isEmpty()) {
            for (Libro libro : libros) {
                tableModel.addRow(new Object[]{libro.getId(), libro.getAutor(), libro.getTitulo(), libro.getAnioPublicacion()});
            }
        } else {
            System.out.println("DEBUG: No se encontraron libros para cargar en la tabla.");
        }
        tbLibros.revalidate();
        tbLibros.repaint();
    }
    private void cargarReporte() {
        tableModelR.setRowCount(0);
        List<Prestamo> prestamos = prestamoService.listarPrestamos();
        if (prestamos != null && !prestamos.isEmpty()) {
            for (Prestamo pres : prestamos) {
                Usuario usuario = pres.getUsuario();
                Libro libro = pres.getLibro();
                tableModelR.addRow(new Object[]{
                        usuario.getNombre(),
                        usuario.getApellidos(),
                        usuario.getDni(),
                        libro.getTitulo(),
                        libro.getAutor(),
                        pres.getFechaRegistro()
                });
            }
        } else {
            System.out.println("DEBUG: No se encontraron préstamos para cargar en el reporte.");
        }
        tbReporte.revalidate();
        tbReporte.repaint();
    }


    private void LimpiarUsuaros() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtDNI.setText("");
        txtNombre.requestFocus();
        // Si necesitas limpiar los combos de usuario:
        // comboBox1.setSelectedIndex(0);
        // comboBox3.setSelectedIndex(0);
    }

    private void limpiarCamposEnContenedor(java.awt.Container contenedor) {
        for (java.awt.Component c : contenedor.getComponents()) {
            if (c instanceof JTextField) {
                ((JTextField) c).setText("");
            } else if (c instanceof JTextArea) {
                ((JTextArea) c).setText("");
            } else if (c instanceof JFormattedTextField) {
                ((JFormattedTextField) c).setText("");
            } else if (c instanceof java.awt.Container) {
                limpiarCamposEnContenedor((java.awt.Container) c);
            }
        }
    }

}