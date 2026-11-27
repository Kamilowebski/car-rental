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
    private final int idKlienta;

    /**
     * @param rodzic  okno nadrzędne
     * @param klient  klient do edycji lub null przy dodawaniu
     */
    public DialogKlient(JFrame rodzic, Klient klient)
    {
        super(rodzic, klient == null ? "Dodaj klienta" : "Edytuj klienta", true);
        idKlienta = klient == null ? 0 : klient.getId();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(tworzFormularz(klient), BorderLayout.CENTER);
        add(tworzPrzyciski(), BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(rodzic);
    }

    private JPanel tworzFormularz(Klient k)
    {
        JPanel panel = new JPanel(new GridLayout(5, 2, 12, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(16, 18, 0, 18));

        poleImie       = new JTextField(k != null ? k.getImie() : "", 18);
        poleNazwisko   = new JTextField(k != null ? k.getNazwisko() : "", 18);
        poleTelefon    = new JTextField(k != null ? k.getTelefon() : "", 18);
        poleEmail      = new JTextField(k != null ? k.getEmail() : "", 18);
        polePrawoJazdy = new JTextField(k != null ? k.getNumerPrawaJazdy() : "", 18);

        panel.add(new JLabel("Imię:"));           panel.add(poleImie);
        panel.add(new JLabel("Nazwisko:"));       panel.add(poleNazwisko);
        panel.add(new JLabel("Telefon:"));        panel.add(poleTelefon);
        panel.add(new JLabel("Email:"));          panel.add(poleEmail);
        panel.add(new JLabel("Nr prawa jazdy:")); panel.add(polePrawoJazdy);

        return panel;
    }

    private JPanel tworzPrzyciski()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 10));
        JButton btnZapisz = new JButton("Zapisz");
        JButton btnAnuluj = new JButton("Anuluj");

        btnZapisz.addActionListener(e -> zapisz());
        btnAnuluj.addActionListener(e -> dispose());

        panel.add(btnAnuluj);
        panel.add(btnZapisz);
        getRootPane().setDefaultButton(btnZapisz);
        return panel;
    }

    private void zapisz()
    {
        String imie = poleImie.getText().trim();
        String nazwisko = poleNazwisko.getText().trim();
        String prawoJazdy = polePrawoJazdy.getText().trim();
        if (imie.isEmpty() || nazwisko.isEmpty() || prawoJazdy.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Wpisz imię, nazwisko i numer prawa jazdy.",
                    "Brak danych", JOptionPane.WARNING_MESSAGE);
            return;
        }
        wynik = new Klient(idKlienta, imie, nazwisko, poleTelefon.getText().trim(),
                poleEmail.getText().trim(), prawoJazdy);
        dispose();
    }

    /** @return wypełniony klient lub null jeśli anulowano */
    public Klient getKlient()
    {
        return wynik;
    }
}
