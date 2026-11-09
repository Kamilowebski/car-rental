package pl.wypozyczalnia.gui;

import pl.wypozyczalnia.model.Klient;
import javax.swing.*;
import java.awt.*;

/**
 * Dialog dodawania i edycji klienta.
 */
public class DialogKlient extends JDialog
{

    private JTextField poleImie, poleNazwisko, poleTelefon, poleEmail, polePrawoJazdy;
    private Klient wynik = null;

    /**
     * @param rodzic  okno nadrzędne
     * @param klient  klient do edycji lub null przy dodawaniu
     */
    public DialogKlient(JFrame rodzic, Klient klient)
    {
        super(rodzic, klient == null ? "Dodaj klienta" : "Edytuj klienta", true);
        setSize(350, 320);
        setLocationRelativeTo(rodzic);
        setLayout(new BorderLayout(10, 10));

        add(tworzFormularz(klient), BorderLayout.CENTER);
        add(tworzPrzyciski(), BorderLayout.SOUTH);
    }

    private JPanel tworzFormularz(Klient k)
    {
        JPanel panel = new JPanel(new GridLayout(5, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        poleImie       = new JTextField(k != null ? k.getImie() : "");
        poleNazwisko   = new JTextField(k != null ? k.getNazwisko() : "");
        poleTelefon    = new JTextField(k != null ? k.getTelefon() : "");
        poleEmail      = new JTextField(k != null ? k.getEmail() : "");
        polePrawoJazdy = new JTextField(k != null ? k.getNumerPrawaJazdy() : "");

        panel.add(new JLabel("Imię:"));           panel.add(poleImie);
        panel.add(new JLabel("Nazwisko:"));       panel.add(poleNazwisko);
        panel.add(new JLabel("Telefon:"));        panel.add(poleTelefon);
        panel.add(new JLabel("Email:"));          panel.add(poleEmail);
        panel.add(new JLabel("Nr prawa jazdy:")); panel.add(polePrawoJazdy);

        return panel;
    }

    private JPanel tworzPrzyciski()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnZapisz = new JButton("Zapisz");
        JButton btnAnuluj = new JButton("Anuluj");

        btnZapisz.addActionListener(e -> zapisz());
        btnAnuluj.addActionListener(e -> dispose());

        panel.add(btnZapisz);
        panel.add(btnAnuluj);
        return panel;
    }

    private void zapisz()
    {
        // TODO – walidacja i zapis
    }

    /** @return wypełniony klient lub null jeśli anulowano */
    public Klient getKlient()
    {
        return wynik;
    }
}