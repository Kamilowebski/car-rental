# Wypożyczalnia samochodów

Aplikacja Java Swing korzysta z MariaDB dostarczanej w pakiecie XAMPP. Projekt Maven znajduje się w głównym katalogu repozytorium.

## Uruchomienie na drugim komputerze z Windows

### 1. Zainstaluj narzędzia i pobierz projekt

- Zainstaluj [JDK 27 dla Windows](https://www.oracle.com/java/technologies/javase/jdk27-archive-downloads.html).
- Zainstaluj [XAMPP dla Windows](https://www.apachefriends.org/download.html). Poniższe polecenia zakładają instalację w `C:\xampp`.
- Do uruchomienia wybierz IntelliJ IDEA albo Maven w terminalu. IntelliJ ma wbudowanego Mavena. Do uruchomienia z PowerShella zainstaluj [Apache Maven](https://maven.apache.org/install.html).

Skopiuj katalog projektu na drugi komputer albo pobierz go z GitHuba przez **Code → Download ZIP** i rozpakuj. Jeśli masz Git, możesz też użyć poleceń:

```powershell
git clone https://github.com/Kamilowebski/car-rental.git
cd car-rental
```

Otwórz PowerShell w rozpakowanym lub sklonowanym katalogu. W tym miejscu powinny być plik `pom.xml` oraz katalogi `src` i `database`.

### 2. Uruchom XAMPP i utwórz pustą bazę

1. Otwórz **XAMPP Control Panel** i kliknij **Start** przy **MySQL**. Pod tą nazwą XAMPP uruchamia MariaDB. Aplikacja potrzebuje tylko tego modułu.
2. W PowerShellu, w katalogu projektu, wykonaj:

```powershell
$mysql = 'C:\xampp\mysql\bin\mysql.exe'
Get-Content -Raw .\database\schema.sql | & $mysql --host=127.0.0.1 --port=3306 --user=root --default-character-set=utf8mb4
& $mysql --host=127.0.0.1 --port=3306 --user=root --execute 'USE wypozyczalnia; SHOW TABLES;'
```

Drugie polecenie powinno wypisać tabele `klient`, `samochod` i `wypozyczenie`. Przy pierwszym imporcie tabele są puste. Ponowny import schematu tworzy wyłącznie brakujące elementy. Jeżeli XAMPP jest w innym katalogu, popraw ścieżkę w zmiennej `$mysql`. Jeżeli konto ma hasło, dodaj `--password` do poleceń klienta MariaDB i ustaw to samo hasło dla aplikacji zgodnie z sekcją poniżej.

### 3. Uruchom aplikację

**IntelliJ IDEA**

1. Wybierz **File → Open**, wskaż główny plik `pom.xml` i wybierz **Open as Project**. Poczekaj, aż Maven pobierze zależności.
2. W **File → Project Structure → Project** ustaw **Project SDK** na JDK 27. Jeżeli nie ma go na liście, użyj **Add SDK → JDK** i wskaż katalog instalacji JDK.
3. Otwórz `src/main/java/pl/wypozyczalnia/Main.java` i kliknij zielony trójkąt obok metody `main`.

**PowerShell**

Ustaw `JAVA_HOME` na katalog JDK 27. Dodaj `%JAVA_HOME%\bin` oraz folder `bin` Mavena do zmiennej `Path`, a następnie otwórz nowe okno PowerShella. Przejdź do katalogu projektu i wykonaj:

```powershell
java -version
mvn -version
mvn compile exec:java
```

Pierwsze dwa polecenia powinny wskazać Javę z wersją główną `27`. Po trzecim otworzy się okno wypożyczalni. Puste zakładki są prawidłowe na nowej bazie. Aby sprawdzić działanie, dodaj samochód i klienta, utwórz wypożyczenie i zapisz zwrot. Przy kolejnych uruchomieniach wystarczy włączyć **MySQL** w XAMPP i ponownie uruchomić aplikację.

## Ustawienia połączenia

| Zmienna środowiskowa | Wartość domyślna |
| --- | --- |
| `CAR_RENTAL_DB_URL` | `jdbc:mariadb://localhost:3306/wypozyczalnia` |
| `CAR_RENTAL_DB_USER` | `root` |
| `CAR_RENTAL_DB_PASSWORD` | puste hasło |

Jeśli lokalna instalacja używa innego portu, konta lub hasła, ustaw odpowiednie zmienne przed uruchomieniem. W PowerShellu użyj składni `$env:CAR_RENTAL_DB_USER = 'nazwa_konta'`. W IntelliJ wejdź w **Run → Edit Configurations → Environment variables**. Jeśli MariaDB działa na innym porcie, użyj tego portu zarówno w poleceniach importu, jak i w adresie JDBC.

## Dokumentacja

- [Instrukcja obsługi i sprawozdanie (PDF)](output/pdf/sprawozdanie-instrukcja-obslugi.pdf)
- [Dokumentacja techniczna](docs/dokumentacja-techniczna.md)
