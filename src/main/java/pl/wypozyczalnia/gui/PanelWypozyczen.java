package pl.wypozyczalnia.gui;

import pl.wypozyczalnia.baza.KlientDao;
import pl.wypozyczalnia.baza.KlientDaoImpl;
import pl.wypozyczalnia.baza.SamochodDao;
import pl.wypozyczalnia.baza.SamochodDaoImpl;
import pl.wypozyczalnia.baza.WypozyczenieDao;
import pl.wypozyczalnia.baza.WypozyczenieDaoImpl;
import pl.wypozyczalnia.logika.WypozyczenieLogika;
import pl.wypozyczalnia.model.Klient;
import pl.wypozyczalnia.model.Samochod;
import pl.wypozyczalnia.model.Wypozyczenie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Panel zakładki Wypożyczenia – lista, nowe wypożyczenie, zwrot.
 */
public class PanelWypozyczen extends JPanel
{

    private JTable tabela;
    private DefaultTableModel modelTabeli;
    private final WypozyczenieDao wypozyczenieDao = new WypozyczenieDaoImpl();
    private final SamochodDao samochodDao = new SamochodDaoImpl();
    private final KlientDao klientDao = new KlientDaoImpl();
    private List<Wypozyczenie> wypozyczenia = List.of();

    public PanelWypozyczen()
    {
        setLayout(new BorderLayout(0, 14));
        setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        add(tworzTabele(), BorderLayout.CENTER);
        add(tworzPanelPrzyciskow(), BorderLayout.SOUTH);
    }

    private JScrollPane tworzTabele()
    {
        String[] kolumny = {"ID", "Samochód", "Klient",
                "Data wypożyczenia", "Planowany zwrot",
                "Data zwrotu", "Koszt", "Status"};
        modelTabeli = new DefaultTableModel(kolumny, 0)
        {
            @Override
            public boolean isCellEditable(int row, int col)
            {
                return false;
            }
        };

        tabela = new JTable(modelTabeli);
        tabela.setFillsViewportHeight(true);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(42);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(185);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getTableHeader().setReorderingAllowed(false);

        return new JScrollPane(tabela);
    }

    private JPanel tworzPanelPrzyciskow()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnNowe   = new JButton("Nowe wypożyczenie");
        JButton btnZwrot  = new JButton("Zwrot samochodu");

        btnNowe.addActionListener(e -> noweWypozyczenie());
        btnZwrot.addActionListener(e -> zwrotSamochodu());

        panel.add(btnNowe);
        panel.add(btnZwrot);
        return panel;
    }

    private void noweWypozyczenie()
    {
        wykonajBezpiecznie(() -> {
            List<Samochod> samochody = samochodDao.pobierzDostepne();
            List<Klient> klienci = klientDao.pobierzWszystkie();
            if (samochody.isEmpty() || klienci.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Do wypożyczenia potrzebujesz dostępnego samochodu i klienta.");
                return;
            }

            DialogWypozyczenie dialog = new DialogWypozyczenie(
                    (JFrame) SwingUtilities.getWindowAncestor(this), samochody, klienci);
            dialog.setVisible(true);
            Wypozyczenie wynik = dialog.getWypozyczenie();
            if (wynik != null)
            {
                wypozyczenieDao.dodaj(wynik);
                wczytajTabele();
            }
        });
    }

    private void zwrotSamochodu()
    {
        int wiersz = tabela.getSelectedRow();
        if (wiersz < 0)
        {
            JOptionPane.showMessageDialog(this, "Zaznacz wypożyczenie w tabeli.");
            return;
        }
        Wypozyczenie wybrane = wypozyczenia.get(tabela.convertRowIndexToModel(wiersz));
        if (wybrane.isZakonczone())
        {
            JOptionPane.showMessageDialog(this, "To wypożyczenie zostało już zakończone.");
            return;
        }
        LocalDate dzisiaj = LocalDate.now();
        if (dzisiaj.isBefore(wybrane.getDataWypozyczenia()))
        {
            JOptionPane.showMessageDialog(this, "Data zwrotu nie może poprzedzać daty wypożyczenia.");
            return;
        }

        wykonajBezpiecznie(() -> {
            Samochod samochod = samochodDao.pobierzPoId(wybrane.getIdSamochodu());
            if (samochod == null)
            {
                throw new IllegalStateException("Nie znaleziono samochodu przypisanego do wypożyczenia.");
            }

            Wypozyczenie zwrot = new Wypozyczenie(wybrane.getId(), wybrane.getIdSamochodu(), wybrane.getIdKlienta(),
                    wybrane.getDataWypozyczenia(), wybrane.getDataPlanowanegoZwrotu());
            zwrot.setDataZwrotu(dzisiaj);
            zwrot.setKoszt(WypozyczenieLogika.obliczKoszt(zwrot, samochod));
            String komunikat = "Zapisać zwrot z datą %s? Koszt wynosi %.2f zł."
                    .formatted(zwrot.getDataZwrotu(), zwrot.getKoszt());
            if (JOptionPane.showConfirmDialog(this, komunikat, "Zwrot samochodu",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                wypozyczenieDao.zaktualizujZwrot(zwrot);
                wczytajTabele();
            }
        });
    }

    void odswiezZBazy()
    {
        wykonajBezpiecznie(this::wczytajTabele);
    }

    private void wczytajTabele()
    {
        Map<Integer, String> samochody = new HashMap<>();
        for (Samochod samochod : samochodDao.pobierzWszystkie())
        {
            samochody.put(samochod.getId(), samochod.getMarka() + " " + samochod.getModel()
                    + " (" + samochod.getNumerRejestracyjny() + ")");
        }
        Map<Integer, String> klienci = new HashMap<>();
        for (Klient klient : klientDao.pobierzWszystkie())
        {
            klienci.put(klient.getId(), klient.getImie() + " " + klient.getNazwisko());
        }

        wypozyczenia = wypozyczenieDao.pobierzWszystkie();
        modelTabeli.setRowCount(0);
        for (Wypozyczenie wypozyczenie : wypozyczenia)
        {
            modelTabeli.addRow(new Object[] {
                    wypozyczenie.getId(),
                    samochody.getOrDefault(wypozyczenie.getIdSamochodu(), "#" + wypozyczenie.getIdSamochodu()),
                    klienci.getOrDefault(wypozyczenie.getIdKlienta(), "#" + wypozyczenie.getIdKlienta()),
                    wypozyczenie.getDataWypozyczenia(), wypozyczenie.getDataPlanowanegoZwrotu(),
                    wypozyczenie.getDataZwrotu(),
                    wypozyczenie.isZakonczone() ? "%.2f zł".formatted(wypozyczenie.getKoszt()) : "",
                    wypozyczenie.isZakonczone() ? "Zakończone" : "Aktywne"
            });
        }
    }

    private void wykonajBezpiecznie(Runnable operacja)
    {
        try
        {
            operacja.run();
        }
        catch (IllegalStateException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Błąd bazy danych", JOptionPane.ERROR_MESSAGE);
        }
    }
}
