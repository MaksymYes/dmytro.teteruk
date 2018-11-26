package ua.nure.kn.teteruk.usermanagment.db;

import ua.nure.kn.teteruk.usermanagment.User;

import java.util.Collection;

public interface UserDao {

    User create(User user);

    void update(User user);

    void delete(User user);

    User find(Long id);

    Collection<User> findAll();
}
