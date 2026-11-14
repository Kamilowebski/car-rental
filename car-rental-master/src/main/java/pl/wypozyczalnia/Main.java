package pl.wypozyczalnia;

import pl.wypozyczalnia.baza.DatabaseConnection; // Dodano: udostępnia test połączenia z bazą.
import pl.wypozyczalnia.gui.GlowneOkno;


import javax.swing.*;

public class Main {
    public static void main(String[] args)
    {
        DatabaseConnection.sprawdzPolaczenie(); // Dodano: wyświetla wynik sprawdzenia bazy w konsoli przy starcie.
        SwingUtilities.invokeLater(GlowneOkno::new);
    }
}
