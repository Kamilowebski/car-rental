package pl.wypozyczalnia;

import com.formdev.flatlaf.FlatLightLaf;
import pl.wypozyczalnia.baza.DatabaseConnection;
import pl.wypozyczalnia.gui.GlowneOkno;

import javax.swing.*;
import java.awt.Color;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args)
    {
        ustawWyglad();
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

    private static void ustawWyglad()
    {
        System.setProperty("flatlaf.useNativeLibrary", "false");
        FlatLightLaf.setup();
        Color akcent = new Color(35, 90, 185);
        UIManager.put("Button.arc", 10);
        UIManager.put("Component.arc", 10);
        UIManager.put("TextComponent.arc", 10);
        UIManager.put("Table.rowHeight", 32);
        UIManager.put("TabbedPane.tabHeight", 40);
        UIManager.put("TabbedPane.underlineColor", akcent);
        UIManager.put("Button.default.background", akcent);
        UIManager.put("Button.default.foreground", Color.WHITE);
    }
}
