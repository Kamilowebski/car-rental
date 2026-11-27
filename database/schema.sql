CREATE DATABASE IF NOT EXISTS wypozyczalnia
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE wypozyczalnia;

CREATE TABLE IF NOT EXISTS samochod (
    id INT AUTO_INCREMENT PRIMARY KEY,
    marka VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    rok_produkcji INT NOT NULL,
    numer_rejestracyjny VARCHAR(32) NOT NULL UNIQUE,
    cena_za_dobe DECIMAL(12, 2) NOT NULL,
    dostepny BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS klient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    imie VARCHAR(100) NOT NULL,
    nazwisko VARCHAR(100) NOT NULL,
    telefon VARCHAR(32),
    email VARCHAR(254),
    numer_prawa_jazdy VARCHAR(64) NOT NULL UNIQUE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS wypozyczenie (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_samochodu INT NOT NULL,
    id_klienta INT NOT NULL,
    data_wypozyczenia DATE NOT NULL,
    data_planowanego_zwrotu DATE NOT NULL,
    data_zwrotu DATE,
    koszt DECIMAL(12, 2) NOT NULL DEFAULT 0,
    FOREIGN KEY (id_samochodu) REFERENCES samochod(id),
    FOREIGN KEY (id_klienta) REFERENCES klient(id)
) ENGINE=InnoDB;
