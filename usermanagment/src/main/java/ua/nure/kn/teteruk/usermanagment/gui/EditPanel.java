package ua.nure.kn.teteruk.usermanagment.gui;

import ua.nure.kn.teteruk.usermanagment.User;
import ua.nure.kn.teteruk.usermanagment.db.exception.DatabaseException;
import ua.nure.kn.teteruk.usermanagment.db.util.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;

import static java.util.Objects.isNull;

public class EditPanel extends JPanel implements ActionListener {

    private static final int ROWS = 3;
    private static final int COLS = 2;

    private MainFrame parent;
    private JPanel buttonPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel fieldPanel;
    private JTextField firstNameField;
    private Color bgColor;
    private JTextField dateOfBirthField;
    private JTextField lastNameField;
    private User selectedUser;

    public EditPanel(MainFrame parent, Long selectedUser) {
        this.parent = parent;
        setUser(selectedUser);
        initialize();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if ("ok".equalsIgnoreCase(actionCommand)) {
            User user = new User();
            user.setId(selectedUser.getId());
            user.setFirstName(getFirstNameField().getText());
            user.setLastName(getLastNameField().getText());
            DateFormat dateFormat = DateFormat.getDateInstance();
            try {
                user.setDateOfBirth(dateFormat.parse(getDateOfBirthField().getText()));
            } catch (ParseException e1) {
                getDateOfBirthField().setBackground(Color.RED);
                return;
            }
            try {
                parent.getDao().update(user);
            } catch (DatabaseException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        clearFields();
        this.setVisible(false);
        parent.showBrowsePanel();
    }

    private void clearFields() {
        getFirstNameField().setText("");
        getFirstNameField().setBackground(bgColor);

        getLastNameField().setText("");
        getLastNameField().setBackground(bgColor);

        getDateOfBirthField().setText("");
        getDateOfBirthField().setBackground(bgColor);
    }

    private void initialize() {
        setLayout(new BorderLayout());
        add(getFieldPanel(), BorderLayout.NORTH);
        add(getButtonsPanel(), BorderLayout.SOUTH);
        setName("redactPanel");
    }

    private JPanel getFieldPanel() {
        if (isNull(fieldPanel)) {
            fieldPanel = new JPanel();
            fieldPanel.setLayout(new GridLayout(ROWS, COLS));
            addLabeledField(fieldPanel, Messages.getString("AddPanel.first_name"), getFirstNameField());
            addLabeledField(fieldPanel, Messages.getString("AddPanel.last_name"), getLastNameField());
            addLabeledField(fieldPanel, Messages.getString("AddPanel.date_of_birth"), getDateOfBirthField());
        }
        return fieldPanel;
    }

    private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setLabelFor(textField);
        panel.add(label);
        panel.add(textField);
    }

    private JPanel getButtonsPanel() {
        if (isNull(buttonPanel)) {
            buttonPanel = new JPanel();
            buttonPanel.add(getOkButton(), null);
            buttonPanel.add(getCancelButton(), null);
        }
        return buttonPanel;
    }

    private JButton getOkButton() {
        if (isNull(okButton)) {
            okButton = new JButton();
            okButton.setText(Messages.getString("AddPanel.ok"));
            okButton.setName("okButton");
            okButton.setActionCommand("ok");
            okButton.addActionListener(this);
        }
        return okButton;
    }

    private JButton getCancelButton() {
        if (isNull(cancelButton)) {
            cancelButton = new JButton();
            cancelButton.setText(Messages.getString("AddPanel.cancel"));
            cancelButton.setName("cancelButton");
            cancelButton.setActionCommand("cancel");
            cancelButton.addActionListener(this);
        }
        return cancelButton;
    }

    private JTextField getFirstNameField() {
        if (isNull(firstNameField)) {
            firstNameField = new JTextField();
            firstNameField.setText(selectedUser.getFirstName());
            firstNameField.setName("firstNameField");
        }
        return firstNameField;
    }

    private JTextField getLastNameField() {
        if (isNull(lastNameField)) {
            lastNameField = new JTextField();
            lastNameField.setText(selectedUser.getLastName());
            lastNameField.setName("lastNameField");
        }
        return lastNameField;
    }

    private JTextField getDateOfBirthField() {
        if (isNull(dateOfBirthField)) {
            dateOfBirthField = new JTextField();
            dateOfBirthField.setText(selectedUser.getDateOfBirth().toString());
            dateOfBirthField.setName("dateOfBirthField");
        }
        return dateOfBirthField;
    }

    public void setUser(Long id) {
        try {
            selectedUser = parent.getDao().find(id);
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
