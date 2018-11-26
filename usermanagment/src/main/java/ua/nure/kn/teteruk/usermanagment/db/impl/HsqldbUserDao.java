package ua.nure.kn.teteruk.usermanagment.db.impl;

import ua.nure.kn.teteruk.usermanagment.User;
import ua.nure.kn.teteruk.usermanagment.db.UserDao;
import ua.nure.kn.teteruk.usermanagment.db.exception.DatabaseException;

import java.util.Collection;

public class HsqldbUserDao implements UserDao {
    @Override
    public User create(User user) throws DatabaseException {
        return null;
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
}
