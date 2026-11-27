package pl.wypozyczalnia;

import pl.wypozyczalnia.baza.DatabaseConnection;
import pl.wypozyczalnia.gui.GlowneOkno;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args)
    {
        try (Connection polaczenie = DatabaseConnection.getConnection())
        {
            if (!polaczenie.isValid(2))
            {
                throw new SQLException("Serwer MariaDB nie odpowiada.");
            }
        }
        catch (SQLException ex)
        {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,
                    "Nie udało się otworzyć bazy danych. Sprawdź MySQL w panelu XAMPP "
                            + "i konfigurację w README.\n" + ex.getMessage(),
                    "Błąd bazy danych", JOptionPane.ERROR_MESSAGE));
            return;
        }
        SwingUtilities.invokeLater(GlowneOkno::new);
    }
}
