package pl.wypozyczalnia.baza;

import pl.wypozyczalnia.model.Klient;

import java.util.ArrayList;
import java.util.List;

public class KlientDaoImpl  implements KlientDao
{
    @Override
    public List<Klient> pobierzWszystkie()
    {
        // TODO - SELECT * FROM Klient
        return new ArrayList<>();
    }
    @Override
    public Klient pobierzPoId(int id)
    {
        // TODO – SELECT * FROM Klient WHERE id = ?
        return null;
    }

    @Override
    public void dodaj(Klient klient)
    {
        // TODO – INSERT INTO Klient
    }

    @Override
    public void aktualizuj(Klient klient)
    {
        // TODO – UPDATE Klient SET ... WHERE id = ?
    }

    @Override
    public void usun(int id)
    {
        // TODO – DELETE FROM Klient WHERE id = ?
    }

    @Override
    public List<Klient> szukaj(String nazwisko)
    {
        // TODO – SELECT * FROM Klient WHERE nazwisko LIKE ?
        return new ArrayList<>();
    }
}
