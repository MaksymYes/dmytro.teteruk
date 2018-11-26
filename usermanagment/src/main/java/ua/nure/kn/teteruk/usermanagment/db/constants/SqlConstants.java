package ua.nure.kn.teteruk.usermanagment.db.constants;

public class SqlConstants {
    private SqlConstants() {
    }

    public static final String CREATE = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
    public static final String UPDATE = "";
    public static final String DELETE = "";
    public static final String FIND = "";
    public static final String FIND_ALL = "";
}
