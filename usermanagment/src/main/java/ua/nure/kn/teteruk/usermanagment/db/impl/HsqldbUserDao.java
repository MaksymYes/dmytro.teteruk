package ua.nure.kn.teteruk.usermanagment.db.impl;

import ua.nure.kn.teteruk.usermanagment.User;
import ua.nure.kn.teteruk.usermanagment.db.ConnectionFactory;
import ua.nure.kn.teteruk.usermanagment.db.UserDao;
import ua.nure.kn.teteruk.usermanagment.db.exception.DatabaseException;

import java.sql.*;
import java.util.Collection;

import static ua.nure.kn.teteruk.usermanagment.db.constants.ExceptionConstants.*;
import static ua.nure.kn.teteruk.usermanagment.db.constants.SqlConstants.CALL_IDENTITY;
import static ua.nure.kn.teteruk.usermanagment.db.constants.SqlConstants.CREATE;

public class HsqldbUserDao implements UserDao {

    private ConnectionFactory factory;

    public HsqldbUserDao(ConnectionFactory factory) {
        this.factory = factory;
    }

    @Override
    public User create(User user) throws DatabaseException {
        Connection connection = factory.createConnection();
        try (PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            int k = 1;
            statement.setString(k++, user.getFirstName());
            statement.setString(k++, user.getLastName());
            statement.setDate(k, new Date(user.getDateOfBirth().getTime()));

            validateResult(statement.executeUpdate());

            CallableStatement callableStatement = connection.prepareCall(CALL_IDENTITY);
            ResultSet resultSet = callableStatement.getResultSet();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DatabaseException(QUERY_EXCEPTION, e);
        } finally {
            tryToCloseConnection(connection);
        }
        return user;
    }

    @Override
    public void update(User user) throws DatabaseException {

    }

    @Override
    public void delete(User user) throws DatabaseException {

    }

    @Override
    public User find(Long id) throws DatabaseException {
        return null;
    }

    @Override
    public Collection<User> findAll() {
        return null;
    }

    private void tryToCloseConnection(Connection connection) throws DatabaseException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DatabaseException(CANNOT_CLOSE_CONNECTION_EXCEPTION, e);
        }
    }

    private void validateResult(int n) throws DatabaseException {
        if (n != 1) {
            throw new DatabaseException(UNEXPECTED_COUNT_OF_ROWS + n);
        }
    }
}
