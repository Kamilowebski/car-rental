package pl.wypozyczalnia.model;

public class Klient
{
    private int id;
    private String imie;
    private String nazwisko;
    private String telefon;
    private String email;
    private String numerPrawaJazdy;

    public Klient(int id, String imie, String nazwisko, String telefon, String email, String numerPrawaJazdy)
    {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.telefon = telefon;
        this.email = email;
        this.numerPrawaJazdy = numerPrawaJazdy;
    }

    public int getId()
    {
        return id;
    }
    public String getImie()
    {
        return imie;
    }
    public String getNazwisko()
    {
        return nazwisko;
    }
    public String getTelefon()
    {
        return telefon;
    }
    public String getEmail()
    {
        return email;
    }
    public String getNumerPrawaJazdy()
    {
        return numerPrawaJazdy;
    }

    public void setImie(String imie)
    {
        this.imie = imie;
    }
    public void setNazwisko(String nazwisko)
    {
        this.nazwisko = nazwisko;
    }
    public void setTelefon(String telefon)
    {
        this.telefon = telefon;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public void setNumerPrawaJazdy(String numerPrawaJazdy)
    {
        this.numerPrawaJazdy = numerPrawaJazdy;
    }

    @Override
    public String toString()
    {
        return "[" + id + "] " + imie + " " + nazwisko + " | tel: " + telefon + " | email: " + email;
    }
}
