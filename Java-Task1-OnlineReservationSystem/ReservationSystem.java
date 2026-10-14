import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReservationSystem extends JFrame {
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnLogin;

    public ReservationSystem() {
        setTitle("Online Reservation System - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblUser = new JLabel("Username:");
        lblUser.setBounds(50, 40, 100, 25);
        add(lblUser);

        tfUsername = new JTextField();
        tfUsername.setBounds(150, 40, 180, 25);
        add(tfUsername);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setBounds(50, 90, 100, 25);
        add(lblPass);

        pfPassword = new JPasswordField();
        pfPassword.setBounds(150, 90, 180, 25);
        add(pfPassword);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(150, 140, 100, 30);
        add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = tfUsername.getText();
                String password = new String(pfPassword.getPassword());

                if (username.equals("admin") && password.equals("12345")) {
                    JOptionPane.showMessageDialog(null, "Login Successful!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Credentials! Access Denied.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ReservationSystem().setVisible(true);
            }
        });
    }
}