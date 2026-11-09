package pl.wypozyczalnia.baza;

import pl.wypozyczalnia.model.Samochod;
import java.util.List;

/**
 * Interfejs definiujący operacje bazodanowe dla samochodów.
 */
public interface SamochodDao
{
    /** Zwraca całą liste samochodów */
    List<Samochod> pobierzWszystkie();

    /** Zwraca listę samochodów dostępnych. */
    List<Samochod> pobierzDostepne();

    /** Zwraca samochód o danym id. */
    Samochod pobierzPoId(int id);

    /** Dodaje nowy samochód do bazy danych. */
    void dodaj(Samochod samochod);

    /** Aktualizuje samochód w bazie danych. */
    void aktualizuj(Samochod samochod);

    /** Usuwa samochód o danym id z bazy danych */
    void usun(int id);

    /** Zwraca liste samochodów które zawierają w modelu albo w marce daną frazę */
    List<Samochod> szukaj(String fraza);
}
