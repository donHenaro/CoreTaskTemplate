package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session sess;

    public UserDaoHibernateImpl() {
        sess = Util.getSessionFactory().openSession();
    }

    @Override
    public void createUsersTable() {

    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS USERS";
        sess.createSQLQuery(sql);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        sess.getTransaction().begin();
        sess.save(new User(name, lastName, age));
        sess.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        return sess.createQuery("FROM users").list();
    }

    @Override
    public void cleanUsersTable() {

    }
}
