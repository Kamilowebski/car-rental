package pl.wypozyczalnia.baza;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Zarządzanie połączeniem z bazą danych.
 */
public class DatabaseConnection
{
    public static Connection getConnection() throws SQLException
    {
        //TODO Podłaczenie do bazy danych
        throw new UnsupportedOperationException("Baza danych nie jest jeszcze podłączona.");
    }

    public static void inicjalizujBaze()
    {
        // TODO
    }
}
