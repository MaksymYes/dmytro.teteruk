package ua.nure.kn.teteruk.usermanagment.db;

import static java.util.Objects.nonNull;
import static ua.nure.kn.teteruk.usermanagment.db.constants.ExceptionConstants.CANNOT_CLOSE_RESOURCES_EXCEPTION;
import static ua.nure.kn.teteruk.usermanagment.db.constants.ExceptionConstants.QUERY_EXCEPTION;
import static ua.nure.kn.teteruk.usermanagment.db.constants.ExceptionConstants.UNEXPECTED_COUNT_OF_ROWS;
import static ua.nure.kn.teteruk.usermanagment.db.constants.SqlConstants.CALL_IDENTITY;
import static ua.nure.kn.teteruk.usermanagment.db.constants.SqlConstants.CREATE;
import static ua.nure.kn.teteruk.usermanagment.db.constants.SqlConstants.DELETE;
import static ua.nure.kn.teteruk.usermanagment.db.constants.SqlConstants.FIND;
import static ua.nure.kn.teteruk.usermanagment.db.constants.SqlConstants.FIND_ALL;
import static ua.nure.kn.teteruk.usermanagment.db.constants.SqlConstants.FIND_BY_NAME;
import static ua.nure.kn.teteruk.usermanagment.db.constants.SqlConstants.UPDATE;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import ua.nure.kn.teteruk.usermanagment.User;
import ua.nure.kn.teteruk.usermanagment.db.exception.DatabaseException;

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
        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            int k = 1;
            statement.setString(k++, user.getFirstName());
            statement.setString(k++, user.getLastName());
            statement.setDate(k++, new Date(user.getDateOfBirth().getTime()));
            statement.setLong(k, user.getId());

            validateResult(statement.executeUpdate());
        } catch (SQLException e) {
            throw new DatabaseException(QUERY_EXCEPTION, e);
        }
    }

    @Override
    public void delete(User user) throws DatabaseException {
        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {

            statement.setLong(1, user.getId());

            validateResult(statement.executeUpdate());
        } catch (SQLException e) {
            throw new DatabaseException(QUERY_EXCEPTION, e);
        }
    }

    @Override
    public User find(Long id) throws DatabaseException {
        ResultSet resultSet = null;
        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(FIND)) {

            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                int k = 1;
                user.setId(resultSet.getLong(k++));
                user.setFirstName(resultSet.getString(k++));
                user.setLastName(resultSet.getString(k++));
                user.setDateOfBirth(resultSet.getDate(k));
                return user;
            }
        } catch (SQLException e) {
            throw new DatabaseException(QUERY_EXCEPTION, e);
        } finally {
            tryToCloseResultSet(resultSet);
        }
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

    @Override
    public Collection<User> find(String firstName, String lastName) throws DatabaseException {
        Collection<User> result = new LinkedList<>();
        User user;
        ResultSet resultSet = null;
        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME)) {

            statement.setString(1, firstName);
            statement.setString(2, lastName);
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
