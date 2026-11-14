package pl.wypozyczalnia.baza;

import pl.wypozyczalnia.model.Wypozyczenie;
import java.sql.Connection; // Dodano: obsługuje połączenia i transakcje z bazą.
import java.sql.Date; // Dodano: mapuje daty Java na typ DATE w bazie.
import java.sql.PreparedStatement; // Dodano: wykonuje zapytania z parametrami.
import java.sql.ResultSet; // Dodano: odczytuje wyniki zapytań.
import java.sql.SQLException; // Dodano: obsługuje błędy SQL.
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacja operacji bazodanowych dla wypożyczeń.
 */
public class WypozyczenieDaoImpl implements WypozyczenieDao
{

    @Override
    public List<Wypozyczenie> pobierzWszystkie()
    {
        return wykonajZapytanie("SELECT id, id_samochodu, id_klienta, data_wypozyczenia, data_planowanego_zwrotu, data_zwrotu, koszt FROM wypozyczenie"); // Dodano: pobiera całą historię wypożyczeń.
    }

    @Override
    public List<Wypozyczenie> pobierzAktywne()
    {
        return wykonajZapytanie("SELECT id, id_samochodu, id_klienta, data_wypozyczenia, data_planowanego_zwrotu, data_zwrotu, koszt FROM wypozyczenie WHERE data_zwrotu IS NULL"); // Dodano: pobiera tylko aktywne wypożyczenia.
    }

    @Override
    public List<Wypozyczenie> pobierzPoKliencie(int idKlienta)
    {
        return wykonajZapytanie("SELECT id, id_samochodu, id_klienta, data_wypozyczenia, data_planowanego_zwrotu, data_zwrotu, koszt FROM wypozyczenie WHERE id_klienta = ?", idKlienta); // Dodano: pobiera historię wskazanego klienta.
    }

    @Override
    public void dodaj(Wypozyczenie wypozyczenie)
    {
        String sql = "INSERT INTO wypozyczenie (id_samochodu, id_klienta, data_wypozyczenia, data_planowanego_zwrotu, data_zwrotu, koszt) VALUES (?, ?, ?, ?, NULL, 0.00)"; // Dodano: przygotowuje zapytanie tworzące aktywne wypożyczenie.
        try (Connection polaczenie = DatabaseConnection.getConnection()) // Dodano: otwiera połączenie dla transakcji wypożyczenia.
        {
            polaczenie.setAutoCommit(false); // Dodano: łączy zapis wypożyczenia i rezerwację auta w jedną transakcję.
            try // Dodano: grupuje operacje, które muszą zostać zatwierdzone razem.
            {
                try (PreparedStatement rezerwacja = polaczenie.prepareStatement("UPDATE samochod SET dostepny = FALSE WHERE id = ? AND dostepny = TRUE")) // Dodano: rezerwuje tylko dostępny samochód.
                {
                    rezerwacja.setInt(1, wypozyczenie.getIdSamochodu()); // Dodano: ustawia ID samochodu do rezerwacji.
                    if (rezerwacja.executeUpdate() != 1) throw new IllegalStateException("Samochód nie istnieje lub jest już niedostępny."); // Dodano: przerywa zapis, jeśli nie udało się zarezerwować auta.
                }
                try (PreparedStatement zapytanie = polaczenie.prepareStatement(sql)) // Dodano: przygotowuje zapis wypożyczenia.
                {
                    zapytanie.setInt(1, wypozyczenie.getIdSamochodu()); // Dodano: ustawia ID wypożyczanego auta.
                    zapytanie.setInt(2, wypozyczenie.getIdKlienta()); // Dodano: ustawia ID klienta.
                    zapytanie.setDate(3, Date.valueOf(wypozyczenie.getDataWypozyczenia())); // Dodano: ustawia datę rozpoczęcia.
                    zapytanie.setDate(4, Date.valueOf(wypozyczenie.getDataPlanowanegoZwrotu())); // Dodano: ustawia planowaną datę zwrotu.
                    zapytanie.executeUpdate(); // Dodano: zapisuje wypożyczenie.
                }
                polaczenie.commit(); // Dodano: zatwierdza zapis wypożyczenia i rezerwację auta.
            }
            catch (SQLException | RuntimeException ex) // Dodano: przechwytuje błędy operacji transakcyjnych.
            {
                polaczenie.rollback(); // Dodano: cofa obie zmiany, jeśli któraś operacja się nie powiedzie.
                throw ex; // Dodano: przekazuje błąd do obsługi poniżej.
            }
        }
        catch (SQLException ex) { throw new IllegalStateException("Nie udało się zapisać wypożyczenia.", ex); } // Dodano: przekazuje błąd bazy jako wyjątek aplikacji.
    }

    @Override
    public void zaktualizujZwrot(Wypozyczenie wypozyczenie)
    {
        if (wypozyczenie.getDataZwrotu() == null) throw new IllegalArgumentException("Data zwrotu nie może być pusta."); // Dodano: wymaga daty zwrotu przed zapisem.
        try (Connection polaczenie = DatabaseConnection.getConnection()) // Dodano: otwiera połączenie dla transakcji zwrotu.
        {
            polaczenie.setAutoCommit(false); // Dodano: łączy zapis zwrotu i przywrócenie dostępności auta.
            try // Dodano: grupuje operacje, które muszą zostać zatwierdzone razem.
            {
                try (PreparedStatement zapytanie = polaczenie.prepareStatement("UPDATE wypozyczenie SET data_zwrotu = ?, koszt = ? WHERE id = ? AND data_zwrotu IS NULL")) // Dodano: aktualizuje tylko aktywne wypożyczenie.
                {
                    zapytanie.setDate(1, Date.valueOf(wypozyczenie.getDataZwrotu())); // Dodano: zapisuje faktyczną datę zwrotu.
                    zapytanie.setDouble(2, wypozyczenie.getKoszt()); // Dodano: zapisuje koszt wypożyczenia.
                    zapytanie.setInt(3, wypozyczenie.getId()); // Dodano: wskazuje zwracane wypożyczenie.
                    if (zapytanie.executeUpdate() != 1) throw new IllegalStateException("Wypożyczenie nie istnieje lub zostało już zwrócone."); // Dodano: przerywa operację, jeśli rekord nie był aktywny.
                }
                try (PreparedStatement zapytanie = polaczenie.prepareStatement("UPDATE samochod SET dostepny = TRUE WHERE id = ?")) // Dodano: przygotowuje oznaczenie auta jako dostępnego.
                {
                    zapytanie.setInt(1, wypozyczenie.getIdSamochodu()); // Dodano: ustawia ID zwróconego auta.
                    zapytanie.executeUpdate(); // Dodano: przywraca dostępność samochodu.
                }
                polaczenie.commit(); // Dodano: zatwierdza zwrot i dostępność auta.
            }
            catch (SQLException | RuntimeException ex) // Dodano: przechwytuje błędy operacji transakcyjnych.
            {
                polaczenie.rollback(); // Dodano: cofa obie zmiany, jeśli któraś operacja się nie powiedzie.
                throw ex; // Dodano: przekazuje błąd do obsługi poniżej.
            }
        }
        catch (SQLException ex) { throw new IllegalStateException("Nie udało się zapisać zwrotu.", ex); } // Dodano: przekazuje błąd bazy jako wyjątek aplikacji.
    }

    private List<Wypozyczenie> wykonajZapytanie(String sql, Object... parametry) // Dodano: wspólnie pobiera wypożyczenia z bazy.
    {
        List<Wypozyczenie> wypozyczenia = new ArrayList<>(); // Dodano: tworzy listę wynikową.
        try (Connection polaczenie = DatabaseConnection.getConnection(); PreparedStatement zapytanie = polaczenie.prepareStatement(sql)) // Dodano: otwiera połączenie i przygotowuje SELECT.
        {
            for (int i = 0; i < parametry.length; i++) zapytanie.setObject(i + 1, parametry[i]); // Dodano: ustawia parametry zapytania.
            try (ResultSet wynik = zapytanie.executeQuery()) // Dodano: wykonuje SELECT i otwiera wynik.
            {
                while (wynik.next()) // Dodano: odczytuje każdy znaleziony rekord.
                {
                    Wypozyczenie wypozyczenie = new Wypozyczenie(wynik.getInt("id"), wynik.getInt("id_samochodu"), wynik.getInt("id_klienta"), wynik.getDate("data_wypozyczenia").toLocalDate(), wynik.getDate("data_planowanego_zwrotu").toLocalDate()); // Dodano: mapuje podstawowe kolumny na obiekt wypożyczenia.
                    Date dataZwrotu = wynik.getDate("data_zwrotu"); // Dodano: odczytuje opcjonalną datę zwrotu.
                    if (dataZwrotu != null) wypozyczenie.setDataZwrotu(dataZwrotu.toLocalDate()); // Dodano: ustawia datę zwrotu dla zakończonego wypożyczenia.
                    wypozyczenie.setKoszt(wynik.getDouble("koszt")); // Dodano: odczytuje zapisany koszt.
                    wypozyczenia.add(wypozyczenie); // Dodano: dodaje obiekt do listy wynikowej.
                }
            }
        }
        catch (SQLException ex) { throw new IllegalStateException("Nie udało się pobrać wypożyczeń z bazy.", ex); } // Dodano: przekazuje błąd odczytu jako wyjątek aplikacji.
        return wypozyczenia; // Dodano: zwraca pobraną listę.
    }
}
