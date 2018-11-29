package ua.nure.kn.teteruk.usermanagment.db.constants;

public class SqlConstants {
    private SqlConstants() {
    }

    public static final String CALL_IDENTITY = "call IDENTITY()";

    public static final String CREATE = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
    public static final String UPDATE = "UPDATE users SET firstname=?, lastname=?, dateofbirth=? WHERE id=?";
    public static final String DELETE = "DELETE FROM users WHERE id=?";
    public static final String FIND = "SELECT * FROM users WHERE id=?";
    public static final String FIND_ALL = "SELECT * FROM users";
}
