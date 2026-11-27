# Wypożyczalnia samochodów

Aplikacja Java Swing korzysta z MariaDB dostarczanej w pakiecie XAMPP. Projekt Maven znajduje się w głównym katalogu repozytorium.

## Pierwsze uruchomienie na Windows

1. Zainstaluj XAMPP, JDK 27 i Maven.
2. W **XAMPP Control Panel** uruchom moduł **MySQL**. Domyślny port to `3306`.
3. Jeżeli potrzebujesz nowej bazy, w katalogu repozytorium wykonaj w PowerShellu:

   ```powershell
   Get-Content -Raw .\database\schema.sql | & 'C:\xampp\mysql\bin\mysql.exe' --host=127.0.0.1 --port=3306 --user=root
   ```

   Dostosuj ścieżkę do instalacji XAMPP. Jeśli konto wymaga hasła, dodaj opcję `--password`, aby klient o nie zapytał. Skrypt tworzy brakującą bazę i tabele. Jeśli istnieją już tabele zgodne ze schematem, możesz od razu przejść do uruchomienia.
4. Uruchom aplikację:

   ```powershell
   mvn compile exec:java
   ```

   W IntelliJ IDEA możesz otworzyć główny `pom.xml` i uruchomić klasę `pl.wypozyczalnia.Main`.

## Ustawienia połączenia

| Zmienna środowiskowa | Wartość domyślna |
| --- | --- |
| `CAR_RENTAL_DB_URL` | `jdbc:mariadb://localhost:3306/wypozyczalnia` |
| `CAR_RENTAL_DB_USER` | `root` |
| `CAR_RENTAL_DB_PASSWORD` | puste hasło |

Jeśli lokalna instalacja używa innego portu, konta lub hasła, ustaw odpowiednią zmienną w PowerShellu lub w konfiguracji uruchomieniowej IntelliJ. Przykładowo `$env:CAR_RENTAL_DB_URL = 'jdbc:mariadb://localhost:3307/wypozyczalnia'` wybiera port `3307`.

## Dokumentacja

- [Instrukcja obsługi i sprawozdanie (PDF)](output/pdf/sprawozdanie-instrukcja-obslugi.pdf)
- [Dokumentacja techniczna](docs/dokumentacja-techniczna.md)
