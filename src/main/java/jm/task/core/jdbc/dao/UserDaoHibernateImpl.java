package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session sess;

    public UserDaoHibernateImpl() {
        sess = Util.getSessionFactory().openSession();
        System.err.println("=================[Используем Hibernate]=================");
    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS USERS (" +
                "       ID INT NOT NULL AUTO_INCREMENT, " +
                "       NAME VARCHAR(20) NOT NULL, " +
                "       LASTNAME VARCHAR(20) NOT NULL, " +
                "       AGE INT NOT NULL, " +
                "       PRIMARY KEY (ID)) engine=innodb;";
        sess.createSQLQuery(sql).addEntity(User.class).executeUpdate();
        System.out.println("Создана таблица USERS");
    }

    @Override
    public void dropUsersTable() {
        sess.createSQLQuery("DROP TABLE IF EXISTS USERS").executeUpdate();
        System.out.println("Удалена таблица USERS");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        sess.getTransaction().begin();
        sess.save(new User(name, lastName, age));
        sess.getTransaction().commit();
        System.out.printf("User [%s, %s, %d лет] добавлен. \n", name, lastName, age);
    }

    @Override
    public void removeUserById(long id) {
        sess.delete(sess.load(User.class, id));
        System.out.printf("ID : %d удален. \n", id);
    }

    @Override
    @SuppressWarnings("unchecked")  // в таблице 100% User'ы
    public List<User> getAllUsers() {
        return (List<User>) sess.createQuery("FROM User").list();
    }

    @Override
    public void cleanUsersTable() {
        sess.createSQLQuery("DELETE FROM USERS;").executeUpdate();
        System.out.println("Очищена таблица USERS");
    }
}
