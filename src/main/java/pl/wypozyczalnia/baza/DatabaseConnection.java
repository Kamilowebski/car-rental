package pl.wypozyczalnia.baza;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection
{
    private static final String URL = System.getenv().getOrDefault(
            "CAR_RENTAL_DB_URL", "jdbc:mariadb://localhost:3306/wypozyczalnia");
    private static final String USER = System.getenv().getOrDefault("CAR_RENTAL_DB_USER", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("CAR_RENTAL_DB_PASSWORD", "");

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
