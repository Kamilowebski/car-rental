package pl.wypozyczalnia;

import pl.wypozyczalnia.gui.GlowneOkno;


import javax.swing.*;

public class Main {
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(GlowneOkno::new);
    }
}
