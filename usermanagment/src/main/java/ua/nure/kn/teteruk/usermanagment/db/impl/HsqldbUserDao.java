package ua.nure.kn.teteruk.usermanagment.db.impl;

import ua.nure.kn.teteruk.usermanagment.User;
import ua.nure.kn.teteruk.usermanagment.db.ConnectionFactory;
import ua.nure.kn.teteruk.usermanagment.db.UserDao;
import ua.nure.kn.teteruk.usermanagment.db.exception.DatabaseException;

import java.sql.*;
import java.util.Collection;
import java.util.Objects;

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
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = factory.createConnection();
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
            tryToCloseOther(callableStatement, resultSet);
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

    private void tryToCloseOther(CallableStatement callableStatement, ResultSet resultSet) throws DatabaseException {
        if (Objects.nonNull(callableStatement) && Objects.nonNull(resultSet)) {
            try {
                callableStatement.close();
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
