package jm.task.core.jdbc;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;
import java.sql.SQLException;

/*  Создание таблицы User(ов)
    Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
    Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
    Очистка таблицы User(ов)
    Удаление таблицы
*/
public class Main {
    public static void main(String[] args) throws SQLException {

        UserDaoJDBCImpl daoJDBC = new UserDaoJDBCImpl();

        daoJDBC.createUsersTable();

        daoJDBC.saveUser("Иван", "Иванов", (byte) 20);
        daoJDBC.saveUser("Петр", "Семенов", (byte) 25);
        daoJDBC.saveUser("Сергей", "Михайлов", (byte) 24);
        daoJDBC.saveUser("Николай", "Стариков", (byte) 35);

        daoJDBC.getAllUsers().forEach(System.out::println);

        daoJDBC.cleanUsersTable();
        daoJDBC.dropUsersTable();

        Util.close();

    }
}
