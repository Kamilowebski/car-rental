package pl.wypozyczalnia.model;

public class Samochod
{
    private int id;
    private String marka;
    private String model;
    private String numerRejestracyjny;
    private int rokProdukcji;
    private double cenaZaDzien;
    private boolean dostepny;

    public Samochod(int id, String marka, String model, int rokProdukcji, String numerRejestracyjny, double cenaZaDzien, boolean dostepny)
    {
        this.id = id;
        this.marka = marka;
        this.model = model;
        this.rokProdukcji = rokProdukcji;
        this.numerRejestracyjny = numerRejestracyjny;
        this.cenaZaDzien = cenaZaDzien;
        this.dostepny = dostepny;
    }
    public int getId()
    {
        return id;
    }
    public String getMarka()
    {
        return marka;
    }
    public String getModel()
    {
        return model;
    }
    public int getRokProdukcji()
    {
        return rokProdukcji;
    }
    public boolean isDostepny()
    {
        return dostepny;
    }
    public void setDostepny(boolean dostepny)
    {
        this.dostepny = dostepny;
    }
    public void setCenaZaDzien(double cenaZaDzien)
    {
        this.cenaZaDzien = cenaZaDzien;
    }

    @Override
    public String toString()
    {
        return "[" + id + "] " + marka + " " + model + " (" + rokProdukcji + ") - " + cenaZaDzien + " zł/dzień" + (dostepny ? " dostepny" : " niedostepny");
    }
}
