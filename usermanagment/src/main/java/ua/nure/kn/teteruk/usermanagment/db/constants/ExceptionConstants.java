package ua.nure.kn.teteruk.usermanagment.db.constants;

public class ExceptionConstants {
    private ExceptionConstants() {
    }

    public static final String QUERY_EXCEPTION = "Exception occurred during execution query";
    public static final String CANNOT_CLOSE_RESOURCES_EXCEPTION = "Cannot close resources";
    public static final String UNEXPECTED_COUNT_OF_ROWS = "Number of expected rows: ";
    public static final String CANNOT_CREATE_INSTANCE = "Cannot create instance of UserDao";
}
