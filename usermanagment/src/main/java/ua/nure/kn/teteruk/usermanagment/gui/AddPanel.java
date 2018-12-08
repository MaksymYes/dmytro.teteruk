package ua.nure.kn.teteruk.usermanagment.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.util.Objects.isNull;

public class AddPanel extends JPanel implements ActionListener {

    private MainFrame parent;
    private JPanel buttonPanel;
    private JPanel fieldPanel;
    private JButton okButton;
    private JButton cancelButton;

    public AddPanel(MainFrame mainFrame) {
        parent = mainFrame;
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setName("addPanel");
        add(getFieldPanel(), BorderLayout.NORTH);
        add(getButtonPanel(), BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private JPanel getButtonPanel() {
        if(isNull(buttonPanel)) {
            buttonPanel = new JPanel();
            buttonPanel.add(getOkButton(), null);
            buttonPanel.add(getCancelButton(), null);
        }
        return buttonPanel;
    }

    private JPanel getFieldPanel() {
        if(isNull(fieldPanel)) {
            fieldPanel = new JPanel();
        }
        return fieldPanel;
    }

    private JButton getOkButton() {
        if(isNull(okButton)) {
            okButton = new JButton();
            cancelButton.setText("Ок");
            cancelButton.setName("okButton");
            cancelButton.setActionCommand("ok");
            cancelButton.addActionListener(this);
        }
        return okButton;
    }

    private JButton getCancelButton() {
        if(isNull(cancelButton)) {
            cancelButton = new JButton();
            cancelButton.setText("Отмена");
            cancelButton.setName("cancelButton");
            cancelButton.setActionCommand("cancel");
            cancelButton.addActionListener(this);
        }
        return cancelButton;
    }
}
