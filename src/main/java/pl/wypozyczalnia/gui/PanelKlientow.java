package pl.wypozyczalnia.gui;

import pl.wypozyczalnia.model.Klient;

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

    private List<Klient> klienci = new ArrayList<>(); // TYMCZASOWO BO NIE MA BAZY

    public PanelKlientow()
    {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dodajPrzykladoweDane();

        add(tworzPanelGorny(), BorderLayout.NORTH);
        add(tworzTabele(), BorderLayout.SOUTH);

        odswiezTabele(klienci);
    }

    private void dodajPrzykladoweDane()
    {
        klienci.add(new Klient(1, "Łukasz", "Dębowski", "000000001", "lukasz@email.com", "GFS123"));
        klienci.add(new Klient(2, "Michał", "Bzymek", "000000002", "michal@email.com", "KSM531"));
        klienci.add(new Klient(3, "Karolina", "Łaciata", "000000003", "karolina@email.com", "MSK987"));
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
        btnWszyscy.addActionListener(e -> odswiezTabele(klienci));
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
        String fraza = poleSzukaj.getText().toLowerCase().trim();
        if (fraza.isEmpty())
        {
            odswiezTabele(klienci);
            return;
        }
        List<Klient> wyniki = new ArrayList<>();
        for (Klient k : klienci)
        {
            if (k.getNazwisko().toLowerCase().contains(fraza))
            {
                wyniki.add(k);
            }
        }
        odswiezTabele(wyniki);
    }

}
