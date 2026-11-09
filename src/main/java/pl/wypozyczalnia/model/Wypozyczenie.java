package pl.wypozyczalnia.model;

import java.time.LocalDate;

public class Wypozyczenie
{
    private int id;
    private int idSamochodu;
    private int idKlienta;
    private LocalDate dataWypozyczenia;
    private LocalDate dataPlanowanegoZwrotu;
    private LocalDate dataZwrotu;
    private double koszt;

    public Wypozyczenie(int id, int idSamochodu, int idKlienta, LocalDate dataWypozyczenia, LocalDate dataPlanowanegoZwrotu)
    {
        this.id = id;
        this.idSamochodu = idSamochodu;
        this.idKlienta = idKlienta;
        this.dataWypozyczenia = dataWypozyczenia;
        this.dataPlanowanegoZwrotu = dataPlanowanegoZwrotu;
        this.dataZwrotu = null;
        this.koszt = 0.0;
    }

    public int getId()
    {
        return id;
    }
    public int getIdSamochodu()
    {
        return idSamochodu;
    }
    public int getIdKlienta()
    {
        return idKlienta;
    }
    public LocalDate getDataWypozyczenia()
    {
        return dataWypozyczenia;
    }
    public LocalDate getDataPlanowanegoZwrotu()
    {
        return dataPlanowanegoZwrotu;
    }
    public LocalDate getDataZwrotu()
    {
        return dataZwrotu;
    }
    public double getKoszt()
    {
        return koszt;
    }

    public void setDataZwrotu(LocalDate dataZwrotu)
    {
        this.dataZwrotu = dataZwrotu;
    }
    public void setKoszt(double koszt)
    {
        this.koszt = koszt;
    }

    public boolean isZakonczone()
    {
        return dataZwrotu != null;
    }

    @Override
    public String toString()
    {
        return "[" + id + "] samochod#" + idSamochodu + " klient#" + idKlienta + " | od " + dataWypozyczenia + " do " + dataPlanowanegoZwrotu + (isZakonczone() ? " | zwrot: " + dataZwrotu + " | koszt: " + koszt + " zł" : " | AKTYWNE");
    }
}
