package pl.wypozyczalnia.gui;

import pl.wypozyczalnia.logika.WypozyczenieLogika;
import pl.wypozyczalnia.model.Klient;
import pl.wypozyczalnia.model.Samochod;
import pl.wypozyczalnia.model.Wypozyczenie;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.function.Function;

public class DialogWypozyczenie extends JDialog
{
    private final JComboBox<Samochod> samochody;
    private final JComboBox<Klient> klienci;
    private final JTextField polePoczatku = new JTextField(LocalDate.now().toString(), 10);
    private final JTextField poleZwrotu = new JTextField(LocalDate.now().plusDays(1).toString(), 10);
    private Wypozyczenie wynik;

    public DialogWypozyczenie(JFrame rodzic, List<Samochod> dostepne, List<Klient> osoby)
    {
        super(rodzic, "Nowe wypożyczenie", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        samochody = new JComboBox<>(dostepne.toArray(Samochod[]::new));
        klienci = new JComboBox<>(osoby.toArray(Klient[]::new));
        ustawEtykiety(samochody, auto -> auto.getMarka() + " " + auto.getModel()
                + " (" + auto.getNumerRejestracyjny() + ")");
        ustawEtykiety(klienci, klient -> klient.getImie() + " " + klient.getNazwisko()
                + " (#" + klient.getId() + ")");

        JPanel formularz = new JPanel(new GridLayout(4, 2, 12, 10));
        formularz.setBorder(BorderFactory.createEmptyBorder(16, 18, 0, 18));
        formularz.add(new JLabel("Samochód"));
        formularz.add(samochody);
        formularz.add(new JLabel("Klient"));
        formularz.add(klienci);
        formularz.add(new JLabel("Początek (RRRR-MM-DD)"));
        formularz.add(polePoczatku);
        formularz.add(new JLabel("Planowany zwrot (RRRR-MM-DD)"));
        formularz.add(poleZwrotu);
        add(formularz, BorderLayout.CENTER);

        JPanel przyciski = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 10));
        JButton zapisz = new JButton("Zapisz");
        JButton anuluj = new JButton("Anuluj");
        zapisz.addActionListener(e -> zapisz());
        anuluj.addActionListener(e -> dispose());
        przyciski.add(anuluj);
        przyciski.add(zapisz);
        add(przyciski, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(zapisz);
        pack();
        setLocationRelativeTo(rodzic);
    }

    private static <T> void ustawEtykiety(JComboBox<T> pole, Function<T, String> etykieta)
    {
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        pole.setRenderer((lista, wartosc, indeks, wybrany, fokus) ->
                renderer.getListCellRendererComponent(lista, wartosc == null ? "" : etykieta.apply(wartosc),
                        indeks, wybrany, fokus));
    }

    private void zapisz()
    {
        LocalDate poczatek;
        LocalDate zwrot;
        try
        {
            poczatek = LocalDate.parse(polePoczatku.getText().trim());
            zwrot = LocalDate.parse(poleZwrotu.getText().trim());
        }
        catch (DateTimeParseException ex)
        {
            JOptionPane.showMessageDialog(this, "Wpisz poprawne daty w formacie RRRR-MM-DD.");
            return;
        }
        if (!WypozyczenieLogika.sprawdzDaty(poczatek, zwrot))
        {
            JOptionPane.showMessageDialog(this, "Planowany zwrot musi przypadać po dacie rozpoczęcia.");
            return;
        }

        Samochod samochod = (Samochod) samochody.getSelectedItem();
        Klient klient = (Klient) klienci.getSelectedItem();
        wynik = new Wypozyczenie(0, samochod.getId(), klient.getId(), poczatek, zwrot);
        dispose();
    }

    public Wypozyczenie getWypozyczenie()
    {
        return wynik;
    }
}
