-- Uruchom ten skrypt jednokrotnie w pustej bazie wypozyczalnia_demo.
START TRANSACTION;

INSERT INTO samochod (id, marka, model, rok_produkcji, numer_rejestracyjny, cena_za_dobe, dostepny)
VALUES
    (1, 'Toyota', 'Yaris', 2022, 'DEMO 01', 130.00, FALSE),
    (2, 'Škoda', 'Fabia', 2023, 'DEMO 02', 140.00, FALSE),
    (3, 'Hyundai', 'i30', 2021, 'DEMO 03', 160.00, FALSE),
    (4, 'Kia', 'Ceed', 2022, 'DEMO 04', 170.00, FALSE),
    (5, 'Opel', 'Corsa', 2020, 'DEMO 05', 120.00, TRUE),
    (6, 'Ford', 'Focus', 2021, 'DEMO 06', 155.00, TRUE),
    (7, 'Renault', 'Clio', 2023, 'DEMO 07', 135.00, TRUE),
    (8, 'Volkswagen', 'Golf', 2022, 'DEMO 08', 175.00, TRUE),
    (9, 'Dacia', 'Duster', 2024, 'DEMO 09', 190.00, TRUE),
    (10, 'Mazda', 'CX-30', 2023, 'DEMO 10', 210.00, TRUE);

INSERT INTO klient (id, imie, nazwisko, telefon, email, numer_prawa_jazdy)
VALUES
    (1, 'Alicja', 'Kowalska', NULL, 'alicja.kowalska@example.com', 'DEMO-PJ-01'),
    (2, 'Bartosz', 'Nowak', NULL, 'bartosz.nowak@example.com', 'DEMO-PJ-02'),
    (3, 'Celina', 'Zielińska', NULL, 'celina.zielinska@example.com', 'DEMO-PJ-03'),
    (4, 'Damian', 'Wójcik', NULL, 'damian.wojcik@example.com', 'DEMO-PJ-04'),
    (5, 'Eliza', 'Kamińska', NULL, 'eliza.kaminska@example.com', 'DEMO-PJ-05'),
    (6, 'Filip', 'Lewandowski', NULL, 'filip.lewandowski@example.com', 'DEMO-PJ-06'),
    (7, 'Gabriela', 'Mazur', NULL, 'gabriela.mazur@example.com', 'DEMO-PJ-07'),
    (8, 'Hubert', 'Krawczyk', NULL, 'hubert.krawczyk@example.com', 'DEMO-PJ-08'),
    (9, 'Iga', 'Dąbrowska', NULL, 'iga.dabrowska@example.com', 'DEMO-PJ-09'),
    (10, 'Jakub', 'Król', NULL, 'jakub.krol@example.com', 'DEMO-PJ-10');

INSERT INTO wypozyczenie (id_samochodu, id_klienta, data_wypozyczenia, data_planowanego_zwrotu, data_zwrotu, koszt)
VALUES
    (1, 1, CURDATE() - INTERVAL 2 DAY, CURDATE() + INTERVAL 2 DAY, NULL, 0.00),
    (2, 2, CURDATE() - INTERVAL 1 DAY, CURDATE() + INTERVAL 1 DAY, NULL, 0.00),
    (3, 3, CURDATE(), CURDATE() + INTERVAL 3 DAY, NULL, 0.00),
    (4, 4, CURDATE() - INTERVAL 4 DAY, CURDATE() - INTERVAL 1 DAY, NULL, 0.00),
    (5, 5, CURDATE() - INTERVAL 8 DAY, CURDATE() - INTERVAL 6 DAY, CURDATE() - INTERVAL 6 DAY, 240.00),
    (6, 6, CURDATE() - INTERVAL 12 DAY, CURDATE() - INTERVAL 9 DAY, CURDATE() - INTERVAL 9 DAY, 465.00),
    (7, 7, CURDATE() - INTERVAL 10 DAY, CURDATE() - INTERVAL 8 DAY, CURDATE() - INTERVAL 9 DAY, 135.00),
    (8, 8, CURDATE() - INTERVAL 14 DAY, CURDATE() - INTERVAL 11 DAY, CURDATE() - INTERVAL 10 DAY, 700.00),
    (9, 9, CURDATE() - INTERVAL 16 DAY, CURDATE() - INTERVAL 14 DAY, CURDATE() - INTERVAL 14 DAY, 380.00),
    (10, 10, CURDATE() - INTERVAL 20 DAY, CURDATE() - INTERVAL 16 DAY, CURDATE() - INTERVAL 16 DAY, 840.00);

COMMIT;
