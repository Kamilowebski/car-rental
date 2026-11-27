# Dokumentacja techniczna

## Cel i środowisko

Wypożyczalnia samochodów to aplikacja desktopowa do ewidencji floty, klientów oraz wypożyczeń i zwrotów. Jest zbudowana w Javie 27 z użyciem Swing i Mavena. Dane przechowuje MariaDB uruchamiana w XAMPP. Instrukcja instalacji, import schematu i zmienne połączenia są opisane w [README](../README.md).

## Struktura kodu

Kod znajduje się w `src/main/java/pl/wypozyczalnia`. Klasa `Main` sprawdza połączenie z MariaDB i uruchamia interfejs na wątku zdarzeń Swing.

| Pakiet | Odpowiedzialność |
| --- | --- |
| `gui` | Okno główne, zakładki z tabelami, formularze oraz komunikaty o błędach. |
| `logika` | Walidacja kolejności dat i obliczanie kosztu zwrotu w `WypozyczenieLogika`. |
| `model` | Obiekty `Samochod`, `Klient` i `Wypozyczenie`. |
| `baza` | Konfiguracja JDBC, interfejsy i implementacje DAO, mapowanie rekordów SQL oraz transakcje wypożyczenia i zwrotu. |

## Schemat bazy

Definicja dla nowej instalacji znajduje się w [database/schema.sql](../database/schema.sql). Każda z trzech tabel ma klucz główny `id`. Silnik InnoDB obsługuje transakcje i klucze obce.

| Tabela | Pola i reguły |
| --- | --- |
| `samochod` | Marka, model, rok produkcji, unikalny numer rejestracyjny, stawka za dobę i dostępność. |
| `klient` | Imię, nazwisko, opcjonalny telefon i e-mail oraz unikalny numer prawa jazdy. |
| `wypozyczenie` | Identyfikatory samochodu i klienta, data rozpoczęcia, planowany zwrot, rzeczywisty zwrot i koszt. Każdy wpis odwołuje się do jednego klienta i jednego samochodu. |

Brak rzeczywistej daty zwrotu oznacza aktywne wypożyczenie. Klucze obce w dostarczonym schemacie chronią historię przed usunięciem powiązanego samochodu lub klienta.

## Operacje

1. **Samochody i klienci.** Zakładki pobierają dane przez DAO. Dodanie, edycja i usunięcie zapisują zmianę przez JDBC. Można wyszukać samochód po marce lub modelu, a klienta po nazwisku.
2. **Nowe wypożyczenie.** Formularz pobiera dostępne samochody i klientów. Planowany zwrot musi wypadać po dacie rozpoczęcia. DAO w jednej transakcji wyłącza dostępność samochodu i zapisuje wypożyczenie. Warunek dostępności w SQL zabezpiecza przed równoczesnym wypożyczeniem tego samego samochodu.
3. **Zwrot.** Po zaznaczeniu aktywnego wpisu aplikacja proponuje dzisiejszą datę i oblicza opłatę według aktualnej stawki samochodu. Naliczana jest co najmniej jedna doba. Po potwierdzeniu DAO w jednej transakcji zapisuje datę i koszt oraz przywraca dostępność samochodu.
4. **Błędy.** Nieudana transakcja jest wycofywana. Interfejs informuje o błędach połączenia lub zapisu. Zapytania z danymi z formularzy używają parametrów JDBC.

## Weryfikacja

- Budowanie projektu poleceniem `mvn clean verify`.
- Test interfejsu na czystym schemacie MariaDB w XAMPP, obejmujący dodawanie i edycję danych, wypożyczenie, zmianę dostępności, zwrot i odczyt danych po ponownym połączeniu.
