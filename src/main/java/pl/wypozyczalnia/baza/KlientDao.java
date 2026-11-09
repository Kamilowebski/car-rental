package pl.wypozyczalnia.baza;

import pl.wypozyczalnia.model.Klient;
import java.util.List;

public interface KlientDao
{
    List<Klient> pobierzWszystkie();

    Klient pobierzPoId(int id);

    void dodaj(Klient klient);

    void aktualizuj(Klient klient);

    void usun(int id);

    List<Klient> szukaj(String nazwisko);
}
