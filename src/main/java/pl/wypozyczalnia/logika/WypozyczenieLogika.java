package pl.wypozyczalnia.logika;

import pl.wypozyczalnia.model.Samochod;
import pl.wypozyczalnia.model.Wypozyczenie;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Klasa odpowiedzialna za logikę biznesową.
 */
public class WypozyczenieLogika
{
    public static double obliczKoszt(Wypozyczenie wypozyczenie, Samochod samochod)
    {
        LocalDate start = wypozyczenie.getDataWypozyczenia();
        LocalDate koniec = (wypozyczenie.getDataZwrotu() != null ? wypozyczenie.getDataZwrotu() : wypozyczenie.getDataPlanowanegoZwrotu());
        long dni = ChronoUnit.DAYS.between(start, koniec);
        if (dni < 1)
        {
            dni = 1;
        }
        return dni * samochod.getCenaZaDzien();
    }

    public static boolean sprawdzDostepnosc(Samochod samochod)
    {
        return samochod.isDostepny();
    }

    public static boolean sprawdzDaty(LocalDate dataWypozyczenia, LocalDate dataPlanowanegoZwrotu)
    {
        if (dataWypozyczenia == null || dataPlanowanegoZwrotu == null)
        {
            return false;
        }
        return dataPlanowanegoZwrotu.isAfter(dataWypozyczenia);
    }
}
