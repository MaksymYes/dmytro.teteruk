package ua.nure.kn.teteruk.usermanagment.gui;

import ua.nure.kn.teteruk.usermanagment.User;
import ua.nure.kn.teteruk.usermanagment.db.util.Messages;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {"ID", Messages.getString("AddPanel.first_name"),
            Messages.getString("AddPanel.last_name")};
    private static final Class[] COLUMN_CLASSES = {Long.class, String.class, String.class};

    private List<User> users;

    public UserTableModel(Collection<User> users) {
        this.users = new ArrayList(users);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = users.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return user.getId();
            case 1:
                return user.getFirstName();
            case 2:
                return user.getLastName();
        }
        return null;
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        return COLUMN_CLASSES[columnIndex];
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    @Override
    public int getColumnCount() {
        return COLUMN_CLASSES.length;
    }
}
