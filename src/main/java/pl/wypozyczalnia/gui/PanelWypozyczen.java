package pl.wypozyczalnia.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel zakładki Wypożyczenia – lista, nowe wypożyczenie, zwrot.
 */
public class PanelWypozyczen extends JPanel
{

    private JTable tabela;
    private DefaultTableModel modelTabeli;

    public PanelWypozyczen()
    {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
        // TODO – dialog nowego wypożyczenia
    }

    private void zwrotSamochodu()
    {
        // TODO – dialog zwrotu samochodu
    }
}