package ui;

import util.Conexion;

import javax.swing.*;

public class Login {
    private JPanel Login;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton btnCancel;
    private JButton btnIngresarL;
    private JPanel login;


    private void createUIComponents() {
        // TODO: place custom component creation code here
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(500, 500);
        frame.setContentPane(login);
        frame.pack();
        frame.setVisible(true);;

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

    }

    public Login() {
        createUIComponents();
        btnIngresarL.addActionListener(e -> {
            String user = textField1.getText();
            String pass = new String(passwordField1.getPassword());
            if (user.equals("admin") && pass.equals("admin")) {
                JOptionPane.showMessageDialog(null, "Login successful!");
                // Aquí puedes abrir la siguiente ventana o realizar otra acción
                new RegistroProcesos();
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos, por favor ingrese nuevamente.");
            }
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(btnIngresarL);
            frame.dispose(); // Cierra la ventana de login
        });

        btnCancel.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
        });

        Conexion cn = new Conexion();
        cn.conectar();

    }
}
