package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;


/*  1.Создание таблицы User(ов)
    2.Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
    3.Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
    4.Очистка таблицы User(ов)
    5.Удаление таблицы
*/

public class Main {
    public static void main(String[] args) {

        UserService serv = new UserServiceImpl();

        serv.createUsersTable();

        serv.saveUser("Иван", "Иванов", (byte) 20);
        serv.saveUser("Петр", "Семенов", (byte) 25);
        serv.saveUser("Сергей", "Михайлов", (byte) 24);
        serv.saveUser("Николай", "Стариков", (byte) 35);

        serv.getAllUsers().forEach(System.out::println);

        serv.cleanUsersTable();
        serv.dropUsersTable();

        Util.close();

    }
}
