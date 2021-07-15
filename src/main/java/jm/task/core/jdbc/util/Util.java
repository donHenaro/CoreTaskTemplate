package jm.task.core.jdbc.util;

 //Класс Util должен содержать логику настройки соединения с базой данных

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.metamodel.MetadataSources;

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

    public static void close() throws SQLException {
        if(!connection.isClosed()){
            connection.close();
        }
        System.out.println("Соединение закрыто");
    }

    //=================== Hibernate ====================
    protected static SessionFactory buildSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
            throw new ExceptionInInitializerError("Initial SessionFactory failed" + e);
        }
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}
