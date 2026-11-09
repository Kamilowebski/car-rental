package pl.wypozyczalnia.baza;

import pl.wypozyczalnia.model.Klient;
import java.util.List;

/**
 * Interfejs definiujący operace bazodanowe dla klientów.
 */
public interface KlientDao
{
    /** Zwraca całą listę klientów */
    List<Klient> pobierzWszystkie();

    /** Zwraca klienta o danym id */
    Klient pobierzPoId(int id);

    /** Dodaje klienta do bazy */
    void dodaj(Klient klient);

    /** Aktualizuje rekord klienta w bazie */
    void aktualizuj(Klient klient);

    /** Usuwa klienta z bazy */
    void usun(int id);

    /** Zwraca  klientów którch nazwisko zgadza się z parametrem */
    List<Klient> szukaj(String nazwisko);
}
