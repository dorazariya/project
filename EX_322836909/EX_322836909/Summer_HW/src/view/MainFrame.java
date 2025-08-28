package view;

import javax.swing.*;
import java.awt.*;
import control.Court;
import view.LoginFrame.Role;
import view.windows.*;

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
    }

    private JMenuBar buildMenu() {
        JMenuBar mb = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenuItem save = new JMenuItem("Save Court");
        save.addActionListener(e -> {
            try {
                view.support.CourtStore.save(court);
                JOptionPane.showMessageDialog(this, "Saved.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Save failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        file.add(save);
        mb.add(file);

        if (role == Role.ADMIN) {
            JMenu admin = new JMenu("Admin");
            admin.add(menuItem("Add Lawyer", () -> open(new AddLawyerFrame(court))));
            admin.add(menuItem("Add Judge",  () -> open(new AddJudgeFrame(court))));
            admin.add(menuItem("Add Department", () -> open(new AddDepartmentFrame(court))));
            admin.addSeparator();
            admin.add(menuItem("Remove...", () -> open(new RemoveEntitiesFrame(court))));
            mb.add(admin);

            JMenu queries = new JMenu("Queries");
            queries.add(menuItem("Appoint New Manager", () -> open(new AppointManagerFrame(court))));
            mb.add(queries);
        }

        if (role == Role.JUDGE) {
            JMenu judge = new JMenu("Judge");
            judge.add(menuItem("My Cases",     () -> open(new MyCasesJudgeFrame(court, currentUser))));
            judge.add(menuItem("Add Verdict",  () -> open(new AddVerdictFrame(court, currentUser))));
            JMenu queries = new JMenu("Queries");
            queries.add(menuItem("Case Duration Δ (Lawyer)", () -> open(new DiffCaseDurationFrame(court))));
            mb.add(judge); mb.add(queries);
        }

        if (role == Role.LAWYER) {
            JMenu lawyer = new JMenu("Lawyer");
            lawyer.add(menuItem("My Cases", () -> open(new MyCasesLawyerFrame(court, currentUser))));
            JMenu queries = new JMenu("Queries");
            queries.add(menuItem("Cases Before Date", () -> open(new CasesBeforeDateFrame(court))));
            queries.add(menuItem("Closed per Department", () -> open(new ClosedPerDeptFrame(court))));
            mb.add(lawyer); mb.add(queries);
        }
        return mb;
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
}
