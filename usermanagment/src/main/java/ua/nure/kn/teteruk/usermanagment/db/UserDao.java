package ua.nure.kn.teteruk.usermanagment.db;

import ua.nure.kn.teteruk.usermanagment.User;
import ua.nure.kn.teteruk.usermanagment.db.exception.DatabaseException;

import java.util.Collection;

public interface UserDao {

    User create(User user) throws DatabaseException;

    void update(User user);

    void delete(User user);

    User find(Long id);

    Collection<User> findAll();
}
