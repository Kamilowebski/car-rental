package pl.wypozyczalnia.gui;

import javax.swing.*;

/**
 * Główne okno z zakładkami.
 */
public class GlowneOkno extends JFrame
{
    public GlowneOkno()
    {
        setTitle("Wypożyczalnia samochodów");
        setSize(1000,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Panel z zakładkami
        JTabbedPane zakladki = new JTabbedPane();
        zakladki.addTab("Samochody", new PanelSamochodow());
        zakladki.addTab("Klienci", new PanelKlientow());
        zakladki.addTab("Wypozyczenia", new JPanel());

        add(zakladki);
        setVisible(true);
    }
}
