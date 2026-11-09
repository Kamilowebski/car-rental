package pl.wypozyczalnia.baza;

import pl.wypozyczalnia.model.Samochod;
import java.util.ArrayList;
import java.util.List;

public class SamochodDaoImpl implements SamochodDao
{
    @Override
    public List<Samochod> pobierzWszystkie()
    {
        // TODO - SELECT * FROM Samochod
        return new ArrayList<>();
    }

    @Override
    public List<Samochod> pobierzDostepne()
    {
        // TODO - SELECT * FROM Samochod WHERE dostepny = 1
        return new ArrayList<>();
    }

    @Override
    public Samochod pobierzPoId(int id)
    {
        // TODO - SELECT * FROM Samochod WHERE id = ?
        return null;
    }

    @Override
    public void dodaj(Samochod samochod)
    {
        // TODO - INSERT INTO Samochod
    }

    @Override
    public void aktualizuj(Samochod samochod)
    {
        // TODO - UPDATE Samochod SET .. WHERE id = ?
    }

    @Override
    public void usun(int id)
    {
        // TODO - DELETE FROM Samochod WHERE id = ?
    }

    @Override
    public List<Samochod> szukaj(String fraza)
    {
        // TODO - SELECT * FROM Samochod WHERE marka LIKE ? OR model LIKE ?
        return new ArrayList<>();
    }

}
