package ua.nure.kn.teteruk.usermanagment.gui;

import ua.nure.kn.teteruk.usermanagment.db.util.Messages;

import javax.swing.*;
import java.awt.*;

import static java.util.Objects.isNull;

public class MainFrame extends JFrame {

    private static final int FRAME_HEIGHT = 400;
    private static final int FRAME_WIDTH = 600;

    private JPanel contentPanel;
    private AddPanel addPanel;

    public JPanel getBrowsePanel() {
        if (isNull(browsePanel)) {
            browsePanel = new BrowsePanel(this);
        }
        return browsePanel;
    }

    private JPanel browsePanel;

    public MainFrame() {
        initialize();
    }

    private void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle(Messages.getString("MainFrame.user_management"));
        this.setContentPane(getContentPanel());
    }

    public void showAddPanel() {
        showPanel(getAddPanel());
    }

    private void showPanel(JPanel panel) {
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().setVisible(true);
        panel.repaint();
    }

    private AddPanel getAddPanel() {
        if(isNull(addPanel)) {
            addPanel = new AddPanel(this);
        }
        return addPanel;
    }

    private JPanel getContentPanel() {
        if (isNull(contentPanel)) {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
        }
        return contentPanel;
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
