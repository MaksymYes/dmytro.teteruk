package ua.nure.kn.teteruk.usermanagment.gui;

import ua.nure.kn.teteruk.usermanagment.db.DAOFactory;
import ua.nure.kn.teteruk.usermanagment.db.UserDao;
import ua.nure.kn.teteruk.usermanagment.gui.util.Messages;

import javax.swing.*;
import java.awt.*;

import static java.util.Objects.isNull;

public class MainFrame extends JFrame {

    private static final int FRAME_HEIGHT = 400;
    private static final int FRAME_WIDTH = 600;

    private JPanel contentPanel;
    private AddPanel addPanel;
    private BrowsePanel browsePanel;
    private UserDao dao;
    private EditPanel editPanel;
    private DetailPanel detailPanel;

    public MainFrame() {
        dao = DAOFactory.getInstance().getUserDao();
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

    public void showBrowsePanel() {
        showPanel(getBrowsePanel());
    }

    public void showEditPanel() {
        showPanel(getEditPanel());
    }

    public void showDetailPanel() {
        showPanel(getDetailPanel());
    }

    private void showPanel(JPanel panel) {
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setVisible(true);
        panel.repaint();
    }

    private AddPanel getAddPanel() {
        if (isNull(addPanel)) {
            addPanel = new AddPanel(this);
        }
        return addPanel;
    }

    private BrowsePanel getBrowsePanel() {
        if (isNull(browsePanel)) {
            browsePanel = new BrowsePanel(this);
        }
        browsePanel.initTable();
        return browsePanel;
    }

    private EditPanel getEditPanel() {
        JTable userTable = browsePanel.getUserTable();
        Long selectedUserId = (Long) userTable.getValueAt(userTable.getSelectedRow(), 0);
        if (isNull(editPanel)) {
            editPanel = new EditPanel(this, selectedUserId);
        }
        editPanel.setUser(selectedUserId);
        return editPanel;
    }

    private JPanel getDetailPanel() {
        JTable userTable = browsePanel.getUserTable();
        Long selectedUserId = (Long) userTable.getValueAt(userTable.getSelectedRow(), 0);
        if (isNull(detailPanel)) {
            detailPanel = new DetailPanel(this, selectedUserId);
        }
        detailPanel.setUser(selectedUserId);
        return detailPanel;
    }

    private JPanel getContentPanel() {
        if (isNull(contentPanel)) {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
        }
        return contentPanel;
    }

    public UserDao getDao() {
        return dao;
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
