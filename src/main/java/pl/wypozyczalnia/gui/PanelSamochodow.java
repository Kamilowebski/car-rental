package pl.wypozyczalnia.gui;

import pl.wypozyczalnia.model.Samochod;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PanelSamochodow extends JPanel
{
    private JTable tabela;
    private DefaultTableModel modelTabeli;
    private JTextField poleSzukaj;

    private List<Samochod> samochody = new ArrayList<>();

    public PanelSamochodow()
    {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        dodajPrzykladoweDane();
        add(tworzPanelGorny(), BorderLayout.NORTH);
        add(tworzTabele(), BorderLayout.CENTER);
        add(tworzPanelPrzyciskow(), BorderLayout.SOUTH);

        odswiezTabele(samochody);
    }

    private void dodajPrzykladoweDane()
    {
        samochody.add(new Samochod(1, "Toyota", "Yaris", 2020, "Ki 1234", 150, true));
        samochody.add(new Samochod(2, "Peugeot", "407", 2004, "TBU 0272", 15, true));
        samochody.add(new Samochod(3, "Volkswagen", "Polo", 1998, "TBU 1234", 1, true));
    }

    private JPanel tworzPanelGorny()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("Szukaj: "));
        poleSzukaj = new JTextField(20);
        panel.add(poleSzukaj);

        JButton btnSzukaj = new JButton("Szukaj");
        btnSzukaj.addActionListener(e -> szukaj());
        panel.add(btnSzukaj);

        JButton btnPokazWszystkie = new JButton("Pokaz wszystkie");
        btnPokazWszystkie.addActionListener(e -> odswiezTabele(samochody));
        panel.add(btnPokazWszystkie);

        return panel;
    }

    private JScrollPane tworzTabele()
    {
        String[] kolumny = {"ID", "Marka", "Model", "Rok", "Nr rejestracyjny", "Cena za dobę", "Dostępny"};
        modelTabeli = new DefaultTableModel(kolumny, 0) {
            @Override
            public boolean isCellEditable(int row, int col)
            {
                return false;
            }
        };
        tabela = new JTable(modelTabeli);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getTableHeader().setReorderingAllowed(false);

        return new JScrollPane(tabela);
    }

    private JPanel tworzPanelPrzyciskow()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnDodaj = new JButton("Dodaj");
        btnDodaj.addActionListener(e -> dodajSamochod());

        JButton btnEdytuj = new JButton("Edytuj");
        btnEdytuj.addActionListener(e -> edytujSamochod());

        JButton btnUsun = new JButton("Usuń");
        btnUsun.addActionListener(e -> usunSamochod());

        panel.add(btnDodaj);
        panel.add(btnEdytuj);
        panel.add(btnUsun);

        return panel;
    }

    private void odswiezTabele(List<Samochod> lista)
    {
        modelTabeli.setRowCount(0);
        for (Samochod s : lista)
        {
            modelTabeli.addRow(new Object[]
                    {
                            s.getId(),
                            s.getMarka(),
                            s.getModel(),
                            s.getRokProdukcji(),
                            s.getNumerRejestracyjny(),
                            s.getCenaZaDzien() + " zł",
                            s.isDostepny() ? "Tak" : "Nie"
                    });
        }
    }

    private void szukaj()
    {
        String fraza = poleSzukaj.getText().toLowerCase().trim();
        if (fraza.isEmpty())
        {
            odswiezTabele(samochody);
            return;
        }
        List<Samochod> wyniki = new ArrayList<>();
        for (Samochod s : samochody)
        {
            if (s.getMarka().toLowerCase().contains(fraza) || s.getModel().toLowerCase().contains(fraza))
            {
                wyniki.add(s);
            }
        }
        odswiezTabele(wyniki);
    }

    private void dodajSamochod()
    {
        DialogSamochod dialog = new DialogSamochod((JFrame)SwingUtilities.getWindowAncestor(this), null);
        dialog.setVisible(true);

        Samochod nowy = dialog.getSamochod();
        if (nowy != null)
        {
            nowy = new Samochod
                    (
                            samochody.size() + 1,
                            nowy.getMarka(),
                            nowy.getModel(),
                            nowy.getRokProdukcji(),
                            nowy.getNumerRejestracyjny(),
                            nowy.getCenaZaDzien(),
                            true
                    );
            samochody.add(nowy);
            odswiezTabele(samochody);
        }
    }

    private void edytujSamochod()
    {
        int wiersz = tabela.getSelectedRow();
        if (wiersz == -1)
        {
            JOptionPane.showMessageDialog(this, "Zaznacz samochód do edycji", "Brak wyboru", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Samochod wybrany = samochody.get(wiersz);
        DialogSamochod dialog = new DialogSamochod((JFrame)SwingUtilities.getWindowAncestor(this), wybrany);
        dialog.setVisible(true);

        Samochod zaktualizowany = dialog.getSamochod();
        if (zaktualizowany != null)
        {
            samochody.set(wiersz, new Samochod(
                    wybrany.getId(),
                    zaktualizowany.getMarka(),
                    zaktualizowany.getModel(),
                    zaktualizowany.getRokProdukcji(),
                    zaktualizowany.getNumerRejestracyjny(),
                    zaktualizowany.getCenaZaDzien(),
                    true
            ));
            odswiezTabele(samochody);
        }
    }

    private void usunSamochod()
    {
        int wiersz = tabela.getSelectedRow();
        if (wiersz == -1)
        {
            JOptionPane.showMessageDialog(this, "Zaznacz samochód do usunięcia", "Brak wyboru", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int potwierdz = JOptionPane.showConfirmDialog(this, "Czy napewno chcesz usunąć samochód?", "Potwierdzenie", JOptionPane.YES_NO_OPTION);
        if (potwierdz == JOptionPane.YES_OPTION)
        {
            samochody.remove(wiersz);
            odswiezTabele(samochody);
        }
    }

}
