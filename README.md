# Turistica (backend)
#### **Turistica** - aplikacja webowa wspomagająca organizację wyjazdów turystycznych (część backendowa).

Praca inżynierska.

## Wykorzystane technologie (backend)
- MySQL (w wersji 8.0.23)
- Java (w wersji 11.0.10)
- Spring (w wersji 5.3.12)
- Spring Boot (w wersji 2.5.6) - w tym Spring Boot Starter FreeMarker
- Hibernate (w wersji 5.4.32)
- JUnit 5


## Zaimplementowane funkcjonalności
#### Każdy użytkownik aplikacji (niezależnie od swojej roli w systemie):
- Przeglądanie ofert wyjazdowych.
- Filtrowanie dostępnych wyjazdów po datach.
- Pozyskiwanie szczegółowych informacji o wskazanym wyjeździe.

#### Użytkownik niezalogowany:
- Rejestracja i logowanie do systemu.

#### Zwykły zalogowany użytkownik:
- Zapisywanie i wypisywanie się z wyjazdów (każdej akcji towarzyszy wysłanie maila użytkownikowi).
- Zmiana hasła.

#### Przewodnik:
- Tworzenie nowych ofert wyjazdów.
- Edycja ofert już istniejących.
- Zmiana hasła.

#### Administrator:
- Przeglądanie kont innych przewodników i administratorów.
- Dodawanie nowego konta przewodnika/administratora (jednorazowe hasło wysłane zostaje na e-maila nowego użytkownika systemu).
- Edycja istniejącego konta przewodnika/administratora.
- Usuwanie kont.
- Zmiana hasła.


### Dalszy rozwój aplikacji (instrukcje)
- [Instrukcja wdrożeniowa](https://github.com/Paullo99/turistica-backend/blob/master/turistica_instrukcja_wdrozeniowa.pdf) objaśnia wdrożenie aplikacji Turistica
z wykorzystaniem platformy Heroku.
- [Instrukcja programisty](https://github.com/Paullo99/turistica-backend/blob/master/turistica_instrukcja_programisty.pdf) zawiera najważniejsze kroki
potrzebne do dalszej pracy nad projektem i rozwijania aplikacji Turistica.
- [Instrukcja obsługi]() wraz z poglądowymi zrzutami ekranu znajduje się w README repozytorium [Turistica-frontend]().
