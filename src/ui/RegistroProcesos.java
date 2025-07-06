package ui;

import modelos.Usuario;
import util.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public RegistroProcesos() {
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
//                    usuarioService = new UsuarioServiceImpl();
//                    usuarioService.registrarUsuario(user);
                    //userDao.agregarUsuario(user);
                    JOptionPane.showMessageDialog(panel1, "Usuario registrado: " + nombre + " " + apellido);
//                    LimpiarUsuaros();
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtNombre.setText("");
                txtApellido.setText("");
                txtDNI.setText("");
                // Cerrar la aplicación
                System.exit(0);
            }
        });
    }
//    public static void main(String[] args) {
//        new RegistroProcesos();
////        SwingUtilities.invokeLater(RegistroProcesos::new);
//        Conexion cn = new Conexion();
//        cn.conectar();
//    }


}
