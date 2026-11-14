package pl.wypozyczalnia.gui;

import javax.swing.*;

/**
 * Dialog tworzenia nowego wypożyczenia.
 */
public class DialogWypozyczenie extends JDialog
{

    public DialogWypozyczenie(JFrame rodzic)
    {
        super(rodzic, "Nowe wypożyczenie", true);
        setSize(400, 300);
        setLocationRelativeTo(rodzic);

        // TODO – formularz: wybór klienta, samochodu, daty
    }
}