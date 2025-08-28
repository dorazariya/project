package view.windows;

import javax.swing.*;
import java.awt.*;
import control.Court;
import enums.Gender;
import enums.Specialization;
import model.Lawyer;
import java.util.Date;
import utils.UtilsMethods;

public class AddLawyerFrame extends JInternalFrame {
    private final Court court;
    private final JTextField id = new JTextField(10);
    private final JTextField fn = new JTextField(10);
    private final JTextField ln = new JTextField(10);
    private final JTextField birth = new JTextField(10); // dd/MM/yyyy
    private final JTextField address = new JTextField(12);
    private final JTextField phone = new JTextField(10);
    private final JTextField email = new JTextField(12);
    private final JComboBox<Gender> gender = new JComboBox<>(Gender.values());
    private final JComboBox<Specialization> spec = new JComboBox<>(Specialization.values());
    private final JTextField license = new JTextField(8);
    private final JTextField salary = new JTextField(8);

    public AddLawyerFrame(Court court) {
        super("Add Lawyer", true, true, true, true);
        this.court = court;
        setSize(520, 360);

        JPanel p = new JPanel(new GridLayout(0,2,6,6));
        p.add(new JLabel("ID:"));         p.add(id);
        p.add(new JLabel("First Name:")); p.add(fn);
        p.add(new JLabel("Last Name:"));  p.add(ln);
        p.add(new JLabel("Birth (dd/MM/yyyy):")); p.add(birth);
        p.add(new JLabel("Address:"));    p.add(address);
        p.add(new JLabel("Phone:"));      p.add(phone);
        p.add(new JLabel("Email:"));      p.add(email);
        p.add(new JLabel("Gender:"));     p.add(gender);
        p.add(new JLabel("Specialization:")); p.add(spec);
        p.add(new JLabel("License #:"));  p.add(license);
        p.add(new JLabel("Salary:"));     p.add(salary);

        JButton addBtn = new JButton("Add");
        addBtn.addActionListener(e -> onAdd());
        add(p, BorderLayout.CENTER);
        add(addBtn, BorderLayout.SOUTH);
    }

    private void onAdd() {
        try {
            int _id = Integer.parseInt(id.getText().trim());
            String _fn = fn.getText().trim();
            String _ln = ln.getText().trim();
            Date _bd = UtilsMethods.parseDate(birth.getText().trim()); // כבר קיימת אצלך ב-utils
            String _addr = address.getText().trim();
            String _phone = phone.getText().trim();
            String _email = email.getText().trim();
            Gender _gender = (Gender) gender.getSelectedItem();
            Specialization _spec = (Specialization) spec.getSelectedItem();
            int _license = Integer.parseInt(license.getText().trim());
            double _salary = Double.parseDouble(salary.getText().trim());

            if (_salary < 0) throw new exceptions.NegativeSalaryException();
            if (_bd.after(UtilsMethods.parseDate("26/11/2024")))
                throw new exceptions.FutureDateException();

            Lawyer l = new Lawyer(_id, _fn, _ln, _bd, _addr, _phone, _email, _gender, _spec, _license, _salary, null);
            boolean ok = court.addLawyer(l);
            if (!ok) throw new exceptions.ObjectAlreadyExistsException("Lawyer with ID " + _id);

            JOptionPane.showMessageDialog(this, "Lawyer added.");
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Numeric fields invalid.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (exceptions.NegativeSalaryException |
                 exceptions.FutureDateException |
                 exceptions.ObjectAlreadyExistsException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Unexpected: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
