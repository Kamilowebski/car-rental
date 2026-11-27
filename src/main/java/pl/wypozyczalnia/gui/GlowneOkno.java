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
        setSize(1040, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Panel z zakładkami
        JTabbedPane zakladki = new JTabbedPane();
        PanelSamochodow samochody = new PanelSamochodow();
        PanelWypozyczen wypozyczenia = new PanelWypozyczen();
        zakladki.addTab("Samochody", samochody);
        zakladki.addTab("Klienci", new PanelKlientow());
        zakladki.addTab("Wypożyczenia", wypozyczenia);
        zakladki.addChangeListener(e -> {
            if (zakladki.getSelectedComponent() == samochody)
            {
                samochody.odswiezZBazy();
            }
            else if (zakladki.getSelectedComponent() == wypozyczenia)
            {
                wypozyczenia.odswiezZBazy();
            }
        });

        add(zakladki);
        setVisible(true);
    }
}
