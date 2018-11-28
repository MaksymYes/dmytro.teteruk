package ua.nure.kn.teteruk.usermanagment.db;

import ua.nure.kn.teteruk.usermanagment.User;
import ua.nure.kn.teteruk.usermanagment.db.exception.DatabaseException;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

import static java.util.Objects.nonNull;
import static ua.nure.kn.teteruk.usermanagment.db.constants.ExceptionConstants.*;
import static ua.nure.kn.teteruk.usermanagment.db.constants.SqlConstants.*;

class HsqldbUserDao implements UserDao {

    private ConnectionFactory connectionFactory;

    public HsqldbUserDao() {
    }

    public HsqldbUserDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public User create(User user) throws DatabaseException {
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE)) {
            int k = 1;
            statement.setString(k++, user.getFirstName());
            statement.setString(k++, user.getLastName());
            statement.setDate(k, new Date(user.getDateOfBirth().getTime()));

            validateResult(statement.executeUpdate());

            callableStatement = connection.prepareCall(CALL_IDENTITY);
            resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DatabaseException(QUERY_EXCEPTION, e);
        } finally {
            tryToCloseResultSet(resultSet);
            if (nonNull(callableStatement)) {
                try {
                    callableStatement.close();
                } catch (SQLException e) {
                    throw new DatabaseException(CANNOT_CLOSE_RESOURCES_EXCEPTION, e);
                }
            }
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
    public Collection<User> findAll() throws DatabaseException {
        Collection<User> result = new LinkedList<>();
        User user;
        ResultSet resultSet = null;
        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                int k = 1;
                user.setId(resultSet.getLong(k++));
                user.setFirstName(resultSet.getString(k++));
                user.setLastName(resultSet.getString(k++));
                user.setDateOfBirth(resultSet.getDate(k));
                result.add(user);
            }
        } catch (SQLException e) {
            throw new DatabaseException(QUERY_EXCEPTION, e);
        } finally {
            tryToCloseResultSet(resultSet);
        }
        return result;
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    private void tryToCloseResultSet(ResultSet resultSet) throws DatabaseException {
        if (nonNull(resultSet)) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new DatabaseException(CANNOT_CLOSE_RESOURCES_EXCEPTION, e);
            }
        }
    }

    private void validateResult(int n) throws DatabaseException {
        if (n != 1) {
            throw new DatabaseException(UNEXPECTED_COUNT_OF_ROWS + n);
        }
    }
}
