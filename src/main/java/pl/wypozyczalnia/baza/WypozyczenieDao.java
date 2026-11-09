package pl.wypozyczalnia.baza;

import pl.wypozyczalnia.model.Wypozyczenie;
import java.util.List;

public interface WypozyczenieDao
{
    List<Wypozyczenie> pobierzWszystkie();

    List<Wypozyczenie> pobierzAktywne();

    List<Wypozyczenie> pobierzPoKliencie(int id);

    void dodaj(Wypozyczenie wypozyczenie);

    void zaktualizujZwrot(Wypozyczenie wypozyczenie);
}
