package pl.wypozyczalnia.baza;

import pl.wypozyczalnia.model.Wypozyczenie;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacja operacji bazodanowych dla wypożyczeń.
 */
public class WypozyczenieDaoImpl implements WypozyczenieDao
{

    @Override
    public List<Wypozyczenie> pobierzWszystkie()
    {
        // TODO – SELECT * FROM Wypozyczenie
        return new ArrayList<>();
    }

    @Override
    public List<Wypozyczenie> pobierzAktywne()
    {
        // TODO – SELECT * FROM Wypozyczenie WHERE data_zwrotu IS NULL
        return new ArrayList<>();
    }

    @Override
    public List<Wypozyczenie> pobierzPoKliencie(int idKlienta)
    {
        // TODO – SELECT * FROM Wypozyczenie WHERE id_klienta = ?
        return new ArrayList<>();
    }

    @Override
    public void dodaj(Wypozyczenie wypozyczenie)
    {
        // TODO – INSERT INTO Wypozyczenie
    }

    @Override
    public void zaktualizujZwrot(Wypozyczenie wypozyczenie)
    {
        // TODO – UPDATE Wypozyczenie SET data_zwrotu = ?, koszt = ? WHERE id = ?
    }
}