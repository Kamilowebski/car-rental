package pl.wypozyczalnia;

import pl.wypozyczalnia.logika.WypozyczenieLogika;
import pl.wypozyczalnia.model.Samochod;
import pl.wypozyczalnia.model.Klient;
import pl.wypozyczalnia.model.Wypozyczenie;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args)
    {
        Samochod s1 = new Samochod(1, "Alfa Romeo", "159", 2025, "TKI0173", 30, true);

        Klient k1 = new Klient(1, "Lukasz", "Debowski", "987678564", "lukasz@email.com", "PJK29321");

        Wypozyczenie w1 = new Wypozyczenie(1, s1.getId(), k1.getId(), LocalDate.now(), LocalDate.now().plusDays(5));

        System.out.println(s1);
        System.out.println(k1);
        System.out.println(w1);

        w1.setDataZwrotu(LocalDate.now().plusDays(5));
        double koszt = WypozyczenieLogika.obliczKoszt(w1, s1);
        System.out.println("Koszt wypozyczenia samochodu: " + koszt + " zł");
        System.out.println("Dostępny: " + WypozyczenieLogika.sprawdzDostepnosc(s1));

    }
}
