package pl.wypozyczalnia.baza;

import pl.wypozyczalnia.model.Klient;
import java.util.List;

/**
 * Interfejs definiujący operacje odczytu i zapisu danych klientów.
 */
public interface KlientDao
{
    /** Zwraca wszystkich klientów zapisanych w bazie. */
    List<Klient> pobierzWszystkie();

    /** Zwraca klienta o podanym ID albo null, gdy nie istnieje. */
    Klient pobierzPoId(int id);

    /** Dodaje nowego klienta do bazy. */
    void dodaj(Klient klient);

    /** Aktualizuje dane istniejącego klienta. */
    void aktualizuj(Klient klient);

    /** Usuwa klienta o podanym ID. */
    void usun(int id);

    /** Zwraca klientów, których nazwisko zawiera podaną frazę. */
    List<Klient> szukaj(String nazwisko);
}
