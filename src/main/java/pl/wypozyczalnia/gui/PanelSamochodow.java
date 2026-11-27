package pl.wypozyczalnia.gui;

import pl.wypozyczalnia.model.Samochod;
import pl.wypozyczalnia.baza.SamochodDao; // Dodano: udostępnia operacje samochodów w bazie.
import pl.wypozyczalnia.baza.SamochodDaoImpl; // Dodano: wybiera implementację DAO korzystającą z JDBC.

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
    private final SamochodDao samochodDao = new SamochodDaoImpl(); // Dodano: łączy panel samochodów z DAO.

    private List<Samochod> samochody = new ArrayList<>();

    public PanelSamochodow()
    {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(tworzPanelGorny(), BorderLayout.NORTH);
        add(tworzTabele(), BorderLayout.CENTER);
        add(tworzPanelPrzyciskow(), BorderLayout.SOUTH);

        odswiezZBazy(); // Dodano: wczytuje listę samochodów z bazy po utworzeniu tabeli.
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
        btnPokazWszystkie.addActionListener(e -> odswiezZBazy()); // Dodano: odświeża listę z bazy.
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
            odswiezZBazy(); // Dodano: przy pustym wyszukiwaniu pokazuje wszystkie auta z bazy.
            return;
        }
        wykonajBezpiecznie(() -> { samochody = samochodDao.szukaj(fraza); odswiezTabele(samochody); }); // Dodano: szuka samochodów w bazie i pokazuje wyniki.
    }

    private void dodajSamochod()
    {
        DialogSamochod dialog = new DialogSamochod((JFrame)SwingUtilities.getWindowAncestor(this), null);
        dialog.setVisible(true);

        Samochod nowy = dialog.getSamochod();
        if (nowy != null)
        {
            Samochod doZapisu = nowy; // Dodano: przechowuje dane formularza do zapisu.
            wykonajBezpiecznie(() -> { samochodDao.dodaj(doZapisu); odswiezZBazy(); }); // Dodano: zapisuje auto w bazie i pobiera aktualną listę.
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
            Samochod doZapisu = new Samochod(
                    wybrany.getId(),
                    zaktualizowany.getMarka(),
                    zaktualizowany.getModel(),
                    zaktualizowany.getRokProdukcji(),
                    zaktualizowany.getNumerRejestracyjny(),
                    zaktualizowany.getCenaZaDzien(),
                    wybrany.isDostepny()
            ); // Dodano: zachowuje aktualny stan dostępności edytowanego auta.
            wykonajBezpiecznie(() -> { samochodDao.aktualizuj(doZapisu); odswiezZBazy(); }); // Dodano: zapisuje edycję w bazie i odświeża tabelę.
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
            wykonajBezpiecznie(() -> { samochodDao.usun(samochody.get(wiersz).getId()); odswiezZBazy(); }); // Dodano: usuwa auto z bazy i odświeża tabelę.
        }
    }

    void odswiezZBazy() // Dodano: pobiera aktualne samochody z DAO.
    {
        wykonajBezpiecznie(() -> { samochody = samochodDao.pobierzWszystkie(); odswiezTabele(samochody); }); // Dodano: synchronizuje listę panelu i tabelę z bazą.
    }

    private void wykonajBezpiecznie(Runnable operacja) // Dodano: obsługuje błędy odczytu i zapisu.
    {
        try { operacja.run(); } // Dodano: wykonuje wybraną operację bazodanową.
        catch (RuntimeException ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Błąd bazy danych", JOptionPane.ERROR_MESSAGE); } // Dodano: pokazuje komunikat, gdy zapis lub odczyt się nie powiedzie.
    }

}
