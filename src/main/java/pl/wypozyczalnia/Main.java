package pl.wypozyczalnia;

public class Main {
    public static void main(String[] args)
    {
        Samochod s1 = new Samochod(1, "Volvo", "S60", 2015, 50, true);
        Samochod s2 = new Samochod(2, "Seat", "Ibiza", 2003, 15, false);
        System.out.println(s1);
        System.out.println(s2);
    }
}
