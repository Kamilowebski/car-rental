package pl.wypozyczalnia.gui;

import pl.wypozyczalnia.model.Klient;
import pl.wypozyczalnia.baza.KlientDao; // Dodano: udostępnia istniejące operacje odczytu klientów z bazy.
import pl.wypozyczalnia.baza.KlientDaoImpl; // Dodano: wybiera implementację DAO korzystającą z JDBC.

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelKlientow extends JPanel
{
    private JTable tabela;
    private DefaultTableModel modelTabeli;
    private JTextField poleSzukaj;

    private List<Klient> klienci = new ArrayList<>(); // Dodano: przechowuje listę odczytaną z bazy.
    private final KlientDao klientDao = new KlientDaoImpl(); // Dodano: łączy panel z operacjami odczytu w DAO.

    public PanelKlientow()
    {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(tworzPanelGorny(), BorderLayout.NORTH);
        add(tworzTabele(), BorderLayout.SOUTH);

        odswiezZBazy(); // Dodano: wczytuje klientów z bazy przy otwarciu zakładki.
    }

    private JPanel tworzPanelGorny()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("Szukaj po nazwisku"));
        poleSzukaj = new JTextField(20);
        panel.add(poleSzukaj);

        JButton btnSzukaj = new JButton("Szukaj");
        btnSzukaj.addActionListener(e -> szukaj());
        panel.add(btnSzukaj);

        JButton btnWszyscy = new JButton("Pokaż wszystkich");
        btnWszyscy.addActionListener(e -> odswiezZBazy()); // Dodano: ponownie pobiera pełną listę klientów z bazy.
        panel.add(btnWszyscy);

        return panel;
    }

    private JScrollPane tworzTabele()
    {
        String[] kolumny = {"ID", "Imię", "Nazwisko", "Telefon", "Email", "Nr prawa jazdy"};
        modelTabeli = new DefaultTableModel(kolumny, 0)
        {
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

    private void odswiezTabele(List<Klient> lista)
    {
        modelTabeli.setRowCount(0);
        for (Klient k : lista)
        {
            modelTabeli.addRow(new Object[]
                    {
                            k.getId(),
                            k.getImie(),
                            k.getNazwisko(),
                            k.getTelefon(),
                            k.getEmail(),
                            k.getNumerPrawaJazdy()
                    });
        }
    }

    private void szukaj()
    {
        String fraza = poleSzukaj.getText().trim(); // Dodano: pobiera frazę do wyszukania w bazie.
        if (fraza.isEmpty())
        {
            odswiezZBazy(); // Dodano: dla pustej frazy pokazuje wszystkich klientów.
            return;
        }
        wykonajBezpiecznie(() -> { klienci = klientDao.szukaj(fraza); odswiezTabele(klienci); }); // Dodano: wyszukuje klientów w bazie i wyświetla wynik.
    }

    private void odswiezZBazy() // Dodano: pobiera aktualną listę klientów z DAO.
    {
        wykonajBezpiecznie(() -> { klienci = klientDao.pobierzWszystkie(); odswiezTabele(klienci); }); // Dodano: synchronizuje listę i tabelę z bazą.
    }

    private void wykonajBezpiecznie(Runnable operacja) // Dodano: obsługuje błędy odczytu z bazy.
    {
        try { operacja.run(); } // Dodano: wykonuje odczyt danych klientów.
        catch (RuntimeException ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Błąd bazy danych", JOptionPane.ERROR_MESSAGE); } // Dodano: pokazuje błąd połączenia lub zapytania.
    }

}
