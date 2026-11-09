package pl.wypozyczalnia.baza;

import pl.wypozyczalnia.model.Wypozyczenie;
import java.util.List;

/**
 * Interfejs definiujący operacje bazodanowe dla wypożyczeń.
 */
public interface WypozyczenieDao
{
    /** Zwraca listę wszystkich wypożyczeń. */
    List<Wypozyczenie> pobierzWszystkie();

    /** Zwraca tylko aktywne wypożyczenia. */
    List<Wypozyczenie> pobierzAktywne();

    /** Zwraca liste wypożyczeń dla danego klienta */
    List<Wypozyczenie> pobierzPoKliencie(int id);

    /** Dodaje wypożyczenie do bazy danych */
    void dodaj(Wypozyczenie wypozyczenie);

    /** Aktualizuje rekord w bazie danych */
    void zaktualizujZwrot(Wypozyczenie wypozyczenie);
}
