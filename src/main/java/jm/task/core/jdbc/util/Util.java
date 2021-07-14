package jm.task.core.jdbc.util;

 //Класс Util должен содержать логику настройки соединения с базой данных

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/testUser?useSSL=false"; //
    private static final String USER = "root";
    private static final String PWD = "root";
    private static Connection connection;

    private Util(){
    }

    public static Connection getConnection() throws ClassNotFoundException,  SQLException {
        if(connection == null){
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection= DriverManager.getConnection(URL, USER, PWD);
            System.out.println("Получен Connection");
        } else {
            System.out.println("Используется готовый Connection");
        }
        return connection;
    }

    public static void close() throws SQLException {
        if(!connection.isClosed()){
            connection.close();
        }
        System.out.println("Соединение закрыто");
    }
}
