package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection cn;

    public UserDaoJDBCImpl() {
        try {
            cn = Util.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.err.println("=================[Используем JDBC]=================");
    }

    public void createUsersTable() {
        try (Statement st = cn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS USERS (" +
                    "       ID INT NOT NULL AUTO_INCREMENT, " +
                    "       NAME VARCHAR(20) NOT NULL, " +
                    "       LASTNAME VARCHAR(20) NOT NULL, " +
                    "       AGE INT NOT NULL, " +
                    "       PRIMARY KEY (ID)) engine=innodb;";
            st.executeUpdate(sql);
            System.out.println("Создана таблица USERS");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement st = cn.createStatement()) {
            st.executeUpdate("DROP TABLE IF EXISTS USERS");
            System.out.println("Удалена таблица USERS");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO USERS (NAME, LASTNAME, AGE) VALUES (?, ?, ?)")) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.execute();

            System.out.printf("User : %s, %s, %d добавлен. \n", name, lastName, age);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement ps = cn.prepareStatement("DELETE FROM USERS WHERE ID = ?;")) {
            ps.setLong(1, id);
            ps.execute();

            System.out.printf("ID : %d удален. \n", id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> ret;
        try (Statement st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet res = st.executeQuery("SELECT * FROM USERS;");) {
            res.last();
            ret = new ArrayList(res.getRow());
            res.beforeFirst();
            while (res.next()) {
                User usr = new User();
                usr.setId(res.getLong("ID"));
                usr.setName(res.getString("NAME"));
                usr.setLastName(res.getString("LASTNAME"));
                usr.setAge(res.getByte("AGE"));
                ret.add(usr);
            }
            return ret;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void cleanUsersTable() {
        try (Statement st = cn.createStatement()) {
            st.executeUpdate("DELETE FROM USERS;");
            System.out.println("Очищена таблица USERS");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
