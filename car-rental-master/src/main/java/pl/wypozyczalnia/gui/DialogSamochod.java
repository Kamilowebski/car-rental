package pl.wypozyczalnia.gui;

import pl.wypozyczalnia.model.Samochod;

import javax.swing.*;
import java.awt.*;

public class DialogSamochod extends JDialog
{
    private JTextField poleMarka, poleModel, poleRok, poleRejestracji, poleCena;
    private Samochod wynik = null;

    public DialogSamochod(JFrame rodzic, Samochod samochod)
    {
        super(rodzic, samochod == null ? "Dodaj samochód" : "Edytuj samochód", true);
        setSize(350, 300);
        setLocationRelativeTo(rodzic);
        setLayout(new BorderLayout(10, 10));

        add(tworzFormularz(samochod), BorderLayout.CENTER);
        add(tworzPrzyciski(), BorderLayout.SOUTH);
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

    private JPanel tworzFormularz(Samochod s)
    {
        JPanel panel = new JPanel(new GridLayout(5, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        poleMarka = new JTextField(s != null ? s.getMarka() : "");
        poleModel = new JTextField(s != null ? s.getModel() : "");
        poleRok = new JTextField(s != null ? String.valueOf(s.getRokProdukcji()) : "");
        poleRejestracji = new JTextField(s != null ? s.getNumerRejestracyjny() : "");
        poleCena = new JTextField(s != null ? String.valueOf(s.getCenaZaDzien()) : "");

        panel.add(new JLabel("Marka: "));
        panel.add(poleMarka);
        panel.add(new JLabel("Model:"));
        panel.add(poleModel);
        panel.add(new JLabel("Rok produkcji:"));
        panel.add(poleRok);
        panel.add(new JLabel("Nr rejestracji:"));
        panel.add(poleRejestracji);
        panel.add(new JLabel("Cena za dobę:"));
        panel.add(poleCena);

        return panel;
    }

    private void zapisz()
    {
        String marka = poleMarka.getText().trim();
        String model = poleModel.getText().trim();
        String rejestracja = poleRejestracji.getText().trim();
        if (marka.isEmpty() || model.isEmpty() || rejestracja.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Wypełnij wszystkie pola", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int rok;
        double cena;
        try
        {
            rok = Integer.parseInt(poleRok.getText().trim());
            cena = Double.parseDouble(poleCena.getText().trim());
        } catch(NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(this, "Rok i cena muszą być liczbami", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }
        wynik = new Samochod(0, marka, model, rok, rejestracja, cena, true);
        dispose();
    }

    public Samochod getSamochod()
    {
        return wynik;
    }
}
