package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.Session;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session sess;

    public UserDaoHibernateImpl() {
        //sess = Util.getSessionFactory().openSession();
        //Сессия - для одноразового использования, нужно закрывать каждый раз
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
        sess = Util.getSessionFactory().openSession(); //----------------
        sess.createSQLQuery(sql).addEntity(User.class).executeUpdate();
        sess.close();//-------------
        System.out.println("Создана таблица USERS");
    }

    @Override
    public void dropUsersTable() {
        sess = Util.getSessionFactory().openSession();//---------------
        sess.createSQLQuery("DROP TABLE IF EXISTS USERS").executeUpdate();
        sess.close();//-------------
        System.out.println("Удалена таблица USERS");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        sess = Util.getSessionFactory().openSession();//-----------------
        sess.getTransaction().begin();
            sess.save(new User(name, lastName, age));
        sess.getTransaction().commit();
        sess.close();//----------------
        System.out.printf("User [%s, %s, %d лет] добавлен. \n", name, lastName, age);
    }

    @Override
    public void removeUserById(long id) {
        sess = Util.getSessionFactory().openSession();//---------------
        User usr = (User) sess.get(User.class, id);
        if (usr != null) {
            sess.getTransaction().begin();
            sess.delete(usr);
            sess.getTransaction().commit();
            System.out.printf("ID : %d удален. \n", id);
        } else {
            System.err.printf("ID [%d] отсутствует. \n", id);
        }
        sess.close();//-------------------
    }

    @Override
    @SuppressWarnings("unchecked")  // в таблице 100% User'ы
    public List<User> getAllUsers() {
        sess = Util.getSessionFactory().openSession();
        List<User> ret = sess.createQuery("FROM User").list();
        sess.close();//--------------
        return ret;
        //return (List<User>) sess.createQuery("FROM User").list();
    }

    @Override
    public void cleanUsersTable() {
        sess = Util.getSessionFactory().openSession();
        sess.createSQLQuery("DELETE FROM USERS;").executeUpdate();
        sess.close();
        System.out.println("Очищена таблица USERS");
    }
}
