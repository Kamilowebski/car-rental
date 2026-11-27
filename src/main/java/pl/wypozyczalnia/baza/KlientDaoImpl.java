package pl.wypozyczalnia.baza;

import pl.wypozyczalnia.model.Klient;

import java.sql.Connection; // Dodano: udostępnia połączenie do bazy.
import java.sql.PreparedStatement; // Dodano: wykonuje zapytania z parametrami.
import java.sql.ResultSet; // Dodano: odczytuje wyniki zapytań.
import java.sql.SQLException; // Dodano: obsługuje błędy SQL.
import java.util.ArrayList;
import java.util.List;

public class KlientDaoImpl  implements KlientDao
{
    @Override
    public List<Klient> pobierzWszystkie()
    {
        return wykonajZapytanie("SELECT id, imie, nazwisko, telefon, email, numer_prawa_jazdy FROM klient"); // Dodano: pobiera wszystkich klientów z tabeli.
    }
    @Override
    public Klient pobierzPoId(int id)
    {
        List<Klient> wynik = wykonajZapytanie("SELECT id, imie, nazwisko, telefon, email, numer_prawa_jazdy FROM klient WHERE id = ?", id); // Dodano: wyszukuje klienta po ID.
        return wynik.isEmpty() ? null : wynik.get(0); // Dodano: zwraca null, jeśli klienta nie znaleziono.
    }

    @Override
    public void dodaj(Klient klient)
    {
        String sql = "INSERT INTO klient (imie, nazwisko, telefon, email, numer_prawa_jazdy) VALUES (?, ?, ?, ?, ?)"; // Dodano: przygotowuje zapytanie dodające klienta.
        wykonajZmiane(sql, klient.getImie(), klient.getNazwisko(), klient.getTelefon(), klient.getEmail(), klient.getNumerPrawaJazdy()); // Dodano: zapisuje dane nowego klienta.
    }

    @Override
    public void aktualizuj(Klient klient)
    {
        String sql = "UPDATE klient SET imie = ?, nazwisko = ?, telefon = ?, email = ?, numer_prawa_jazdy = ? WHERE id = ?"; // Dodano: przygotowuje zapytanie aktualizujące klienta.
        wykonajZmiane(sql, klient.getImie(), klient.getNazwisko(), klient.getTelefon(), klient.getEmail(), klient.getNumerPrawaJazdy(), klient.getId()); // Dodano: zapisuje zmienione dane klienta.
    }

    @Override
    public void usun(int id)
    {
        wykonajZmiane("DELETE FROM klient WHERE id = ?", id); // Dodano: usuwa klienta o wskazanym ID.
    }

    @Override
    public List<Klient> szukaj(String nazwisko)
    {
        return wykonajZapytanie("SELECT id, imie, nazwisko, telefon, email, numer_prawa_jazdy FROM klient WHERE nazwisko LIKE ?", "%" + nazwisko.trim() + "%"); // Dodano: wyszukuje klientów po fragmencie nazwiska.
    }

    private List<Klient> wykonajZapytanie(String sql, Object... parametry) // Dodano: wspólnie pobiera rekordy klientów z bazy.
    {
        List<Klient> klienci = new ArrayList<>(); // Dodano: tworzy listę wynikową.
        try (Connection polaczenie = DatabaseConnection.getConnection(); PreparedStatement zapytanie = polaczenie.prepareStatement(sql)) // Dodano: otwiera połączenie i przygotowuje zapytanie.
        {
            for (int i = 0; i < parametry.length; i++) zapytanie.setObject(i + 1, parametry[i]); // Dodano: ustawia parametry zapytania.
            try (ResultSet wynik = zapytanie.executeQuery()) // Dodano: wykonuje SELECT i otwiera wynik.
            {
                while (wynik.next()) klienci.add(new Klient(wynik.getInt("id"), wynik.getString("imie"), wynik.getString("nazwisko"), wynik.getString("telefon"), wynik.getString("email"), wynik.getString("numer_prawa_jazdy"))); // Dodano: przekształca każdy wiersz w obiekt Klient.
            }
        }
        catch (SQLException ex) { throw new IllegalStateException("Nie udało się pobrać klientów z bazy.", ex); } // Dodano: przekazuje błąd SQL jako wyjątek aplikacji.
        return klienci; // Dodano: zwraca pobraną listę klientów.
    }

    private void wykonajZmiane(String sql, Object... parametry) // Dodano: wspólnie zapisuje, aktualizuje i usuwa klientów.
    {
        try (Connection polaczenie = DatabaseConnection.getConnection(); PreparedStatement zapytanie = polaczenie.prepareStatement(sql)) // Dodano: otwiera połączenie i przygotowuje zmianę.
        {
            for (int i = 0; i < parametry.length; i++) zapytanie.setObject(i + 1, parametry[i]); // Dodano: ustawia wartości zapytania.
            zapytanie.executeUpdate(); // Dodano: wykonuje zmianę w tabeli.
        }
        catch (SQLException ex) { throw new IllegalStateException("Nie udało się zmienić danych klienta w bazie.", ex); } // Dodano: przekazuje błąd SQL jako wyjątek aplikacji.
    }
}
