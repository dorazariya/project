package view;

import javax.swing.*;
import java.awt.*;
import control.Court;
import view.LoginFrame.Role;


public class MainFrame extends JFrame {
    private final Court court;
    private final Role role;
    private final Object currentUser;
    private final JDesktopPane desktop = new JDesktopPane();

    public MainFrame(Court court, Role role, Object currentUser) {
        super("HRS - " + role);
        this.court = court;
        this.role = role;
        this.currentUser = currentUser;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setJMenuBar(buildMenu());
        add(desktop, BorderLayout.CENTER);

        // דרישת המסמך: כשה-Admin נכנס – לטעון את הנתונים מהקלט
        if (role == Role.ADMIN) {
            try {
                // מנצל את מנגנון ה-CSV שכבר כתבת ב-view.Main
                view.Main.main(new String[0]); // ימלא את Court (Singleton) + ייצור output.txt
                JOptionPane.showMessageDialog(this, "System data loaded from INPUT.csv");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to auto-load INPUT.csv: " + ex.getMessage(),
                        "Load Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JMenuBar buildMenu() {
        JMenuBar mb = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenuItem save = new JMenuItem("Save Court");
        save.addActionListener(e -> {
            try {
                
                JOptionPane.showMessageDialog(this, "Saved.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Save failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        JMenuItem logout = new JMenuItem("Logout");
        logout.addActionListener(e -> {
            dispose();
            new LoginFrame(court).setVisible(true);
        });
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));

        file.add(save);
        file.addSeparator();
        file.add(logout);
        file.add(exit);
        mb.add(file);

     
       
    }

    private JMenuItem menuItem(String name, Runnable r) {
        JMenuItem mi = new JMenuItem(name);
        mi.addActionListener(e -> r.run());
        return mi;
    }

    private void open(JInternalFrame f) {
        desktop.add(f);
        f.setVisible(true);
    }


public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(() -> {
        control.Court court = control.Court.getInstance();
        new view.LoginFrame(court).setVisible(true);
    });
  }
}
