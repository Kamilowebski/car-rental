package pl.wypozyczalnia.baza;

import pl.wypozyczalnia.model.Samochod;
import java.sql.Connection; // Dodano: udostępnia połączenie do bazy.
import java.sql.PreparedStatement; // Dodano: wykonuje zapytania z parametrami.
import java.sql.ResultSet; // Dodano: odczytuje wyniki zapytań.
import java.sql.SQLException; // Dodano: obsługuje błędy SQL.
import java.util.ArrayList;
import java.util.List;

public class SamochodDaoImpl implements SamochodDao
{
    @Override
    public List<Samochod> pobierzWszystkie()
    {
        return wykonajZapytanie("SELECT id, marka, model, rok_produkcji, numer_rejestracyjny, cena_za_dobe, dostepny FROM samochod"); // Dodano: pobiera wszystkie samochody.
    }

    @Override
    public List<Samochod> pobierzDostepne()
    {
        return wykonajZapytanie("SELECT id, marka, model, rok_produkcji, numer_rejestracyjny, cena_za_dobe, dostepny FROM samochod WHERE dostepny = TRUE"); // Dodano: pobiera tylko dostępne samochody.
    }

    @Override
    public Samochod pobierzPoId(int id)
    {
        List<Samochod> wynik = wykonajZapytanie("SELECT id, marka, model, rok_produkcji, numer_rejestracyjny, cena_za_dobe, dostepny FROM samochod WHERE id = ?", id); // Dodano: wyszukuje samochód po ID.
        return wynik.isEmpty() ? null : wynik.get(0); // Dodano: zwraca null, gdy samochód nie istnieje.
    }

    @Override
    public void dodaj(Samochod samochod)
    {
        String sql = "INSERT INTO samochod (marka, model, rok_produkcji, numer_rejestracyjny, cena_za_dobe, dostepny) VALUES (?, ?, ?, ?, ?, ?)"; // Dodano: przygotowuje zapytanie dodające samochód.
        wykonajZmiane(sql, samochod.getMarka(), samochod.getModel(), samochod.getRokProdukcji(), samochod.getNumerRejestracyjny(), samochod.getCenaZaDzien(), samochod.isDostepny()); // Dodano: zapisuje dane samochodu.
    }

    @Override
    public void aktualizuj(Samochod samochod)
    {
        String sql = "UPDATE samochod SET marka = ?, model = ?, rok_produkcji = ?, numer_rejestracyjny = ?, cena_za_dobe = ?, dostepny = ? WHERE id = ?"; // Dodano: przygotowuje zapytanie aktualizujące samochód.
        wykonajZmiane(sql, samochod.getMarka(), samochod.getModel(), samochod.getRokProdukcji(), samochod.getNumerRejestracyjny(), samochod.getCenaZaDzien(), samochod.isDostepny(), samochod.getId()); // Dodano: zapisuje zmienione dane samochodu.
    }

    @Override
    public void usun(int id)
    {
        wykonajZmiane("DELETE FROM samochod WHERE id = ?", id); // Dodano: usuwa samochód o wskazanym ID.
    }

    @Override
    public List<Samochod> szukaj(String fraza)
    {
        String wzorzec = "%" + fraza.trim() + "%"; // Dodano: tworzy wzorzec wyszukiwania fragmentu tekstu.
        return wykonajZapytanie("SELECT id, marka, model, rok_produkcji, numer_rejestracyjny, cena_za_dobe, dostepny FROM samochod WHERE marka LIKE ? OR model LIKE ?", wzorzec, wzorzec); // Dodano: szuka samochodów po marce lub modelu.
    }

    private List<Samochod> wykonajZapytanie(String sql, Object... parametry) // Dodano: wspólnie pobiera samochody z bazy.
    {
        List<Samochod> samochody = new ArrayList<>(); // Dodano: tworzy listę wynikową.
        try (Connection polaczenie = DatabaseConnection.getConnection(); PreparedStatement zapytanie = polaczenie.prepareStatement(sql)) // Dodano: otwiera połączenie i przygotowuje zapytanie.
        {
            for (int i = 0; i < parametry.length; i++) zapytanie.setObject(i + 1, parametry[i]); // Dodano: ustawia parametry zapytania.
            try (ResultSet wynik = zapytanie.executeQuery()) // Dodano: wykonuje SELECT i otwiera wynik.
            {
                while (wynik.next()) samochody.add(new Samochod(wynik.getInt("id"), wynik.getString("marka"), wynik.getString("model"), wynik.getInt("rok_produkcji"), wynik.getString("numer_rejestracyjny"), wynik.getDouble("cena_za_dobe"), wynik.getBoolean("dostepny"))); // Dodano: zamienia każdy wiersz bazy na obiekt Samochod.
            }
        }
        catch (SQLException ex) { throw new IllegalStateException("Nie udało się pobrać samochodów z bazy.", ex); } // Dodano: przekazuje błąd SQL jako wyjątek aplikacji.
        return samochody; // Dodano: zwraca pobrane samochody.
    }

    private void wykonajZmiane(String sql, Object... parametry) // Dodano: wspólnie zapisuje, aktualizuje i usuwa samochody.
    {
        try (Connection polaczenie = DatabaseConnection.getConnection(); PreparedStatement zapytanie = polaczenie.prepareStatement(sql)) // Dodano: otwiera połączenie i przygotowuje zmianę.
        {
            for (int i = 0; i < parametry.length; i++) zapytanie.setObject(i + 1, parametry[i]); // Dodano: ustawia wartości zapytania.
            zapytanie.executeUpdate(); // Dodano: wykonuje zmianę w tabeli.
        }
        catch (SQLException ex) { throw new IllegalStateException("Nie udało się zmienić danych samochodu w bazie.", ex); } // Dodano: przekazuje błąd SQL jako wyjątek aplikacji.
    }

}
