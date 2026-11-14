package pl.wypozyczalnia.baza;

import java.sql.Connection;
import java.sql.DriverManager; // Dodano: tworzy połączenie JDBC na podstawie adresu i danych logowania.
import java.sql.SQLException;

/**
 * Zarządzanie połączeniem z bazą danych.
 */
public class DatabaseConnection
{
    private static final String URL = "jdbc:mariadb://localhost:3306/wypozyczalnia"; // Dodano: wskazuje bazę wypozyczalnia na lokalnym XAMPP.
    private static final String USER = "root"; // Dodano: ustawia domyślnego użytkownika XAMPP.
    private static final String PASSWORD = ""; // Dodano: ustawia domyślne puste hasło XAMPP.

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(URL, USER, PASSWORD); // Dodano: otwiera i zwraca połączenie z bazą.
    }

    public static boolean sprawdzPolaczenie() // Dodano: udostępnia prosty test dostępności bazy.
    {
        try (Connection polaczenie = getConnection()) // Dodano: otwiera połączenie i zamknie je automatycznie po teście.
        {
            boolean dziala = polaczenie.isValid(2); // Dodano: sprawdza, czy połączenie odpowiada w ciągu dwóch sekund.
            System.out.println(dziala ? "Polaczenie z baza działa." : "Polaczenie z baza nie odpowiada."); // Dodano: wyświetla wynik testu w konsoli.
            return dziala; // Dodano: zwraca wynik sprawdzenia.
        }
        catch (SQLException ex) // Dodano: obsługuje błąd połączenia lub brak odpowiedzi bazy.
        {
            System.err.println("Nie udalo sie polaczyć z baza: " + ex.getMessage()); // Dodano: wyświetla przyczynę błędu w konsoli.
            return false; // Dodano: zwraca false, gdy nie można połączyć się z bazą.
        }
    }

    public static void inicjalizujBaze()
    {
        // TODO
    }
}
