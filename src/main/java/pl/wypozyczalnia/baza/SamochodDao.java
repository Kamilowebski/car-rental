package pl.wypozyczalnia.baza;

import pl.wypozyczalnia.model.Samochod;
import java.util.List;

public interface SamochodDao
{
    /** Pobiera wszystkie samochodu */
    List<Samochod> pobierzWszystkie();

    /** Pobiera tylko dostępne */
    List<Samochod> pobierzDostepne();

    /** Pobierz po id */
    Samochod pobierzPoId(int id);

    /** Dodaj nowy sa*/
    void dodaj(Samochod samochod);

    void aktualizuj(Samochod samochod);

    void usun(int id);

    List<Samochod> szukaj(String fraza);
}
