package jm.task.core.jdbc.util;

 //Класс Util должен содержать логику настройки соединения с базой данных

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    //JDBC fields
    private static final String URL = "jdbc:mysql://localhost:3306/testUser?useSSL=false";
    private static final String USER = "root";
    private static final String PWD = "root";
    private static Connection connection;

    //Hybernate field
    private static SessionFactory sessionFactory;

    private Util(){
    }
    //=================== JDBC ====================
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



    //=================== Hibernate ====================
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }

    //=================== Общее закрытие ====================
    public static void close() {
        try {
            connection.close();  // Закрываем Connection если не закрыто
            System.err.println("=================[Connection закрыт]=================");
        } catch (SQLException | NullPointerException ignored) {
        }

        if (sessionFactory != null) {
            getSessionFactory().close();
            System.err.println("=================[Сессия закрыта]=================");
        }

    }
}
