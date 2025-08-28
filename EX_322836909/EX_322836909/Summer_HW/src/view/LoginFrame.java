package view;

import javax.swing.*;
import java.awt.*;
import control.Court;

public class LoginFrame extends JFrame {
    private final Court court;
    private final JTextField idField = new JTextField(12);

    public enum Role { ADMIN, JUDGE, LAWYER }

    public LoginFrame(Court court) {
        super("HRS - Login");
        this.court = court;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360, 160);
        setLocationRelativeTo(null);

        JPanel p = new JPanel(new GridLayout(3,2,8,8));
        p.add(new JLabel("ID:"));
        p.add(idField);

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> doLogin());
        p.add(new JLabel());
        p.add(loginBtn);

        add(p);
    }

    private void doLogin() {
        String idText = idField.getText().trim();
        if (!idText.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "ID must be numeric.","Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = Integer.parseInt(idText);

        if (id == 123456789) {
            new MainFrame(court, Role.ADMIN, null).setVisible(true);
            dispose();
            return;
        }
        if (court.getRealJudge(id) != null) {
            new MainFrame(court, Role.JUDGE, court.getRealJudge(id)).setVisible(true);
            dispose();
        } else if (court.getRealLawyer(id) != null) {
            new MainFrame(court, Role.LAWYER, court.getRealLawyer(id)).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "User not found.","Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
