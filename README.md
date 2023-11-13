# Laboration 2: REST API i Spring Boot med MySQL-databas

Skapa ett Spring Boot 3.x.x Maven-projekt med Java 21 och följande beroenden:
- Spring Web
- Spring Security
- Spring Data JPA
- MySQL Driver
- Validation
- Spring Boot Actuator

## Uppgift G-nivå (Grundnivå)
Skapa en REST API med Spring Boot och MySQL för att hantera intressepunkter som tillhör olika kategorier. API:et ska använda JSON.

### Databasstruktur:
1. **Kategori:**
    - Varje kategori ska ha ett namn, en symbol, och en beskrivning.

2. **Plats:**
    - Varje plats ska ha ett namn (obligatoriskt), tillhöra en befintlig kategori, ha ett användar-ID för den användare som lagt till platsen, en status för privat/publik (standardinställning), datum och tid för senaste ändring, en beskrivning, koordinater som spatial data, samt datum och tid för när platsen skapades.

### API Endpoints:
1. **Kategorier:**
    - **GET:** Hämta alla kategorier eller en specifik kategori.
    - **POST:** Skapa en ny kategori (kräver adminroll). Namnet får inte kollidera med en befintlig kategori.

2. **Platser:**
    - **GET:**
        - Hämta alla publika platser eller en specifik publik plats (för anonyma användare).
        - Hämta alla publika platser inom en specifik kategori.
        - Hämta alla platser (både publika och privata) som tillhör den inloggade användaren.
        - Hämta alla platser inom en viss yta (radie från ett centrum eller hörn på en kvadrat).
    - **POST:** Skapa en ny plats (kräver inloggning).
    - **PUT/PATCH:** Uppdatera en befintlig plats (kräver inloggning). Vilka fält ska kunna uppdateras?
    - **DELETE:** Ta bort en befintlig plats (kräver inloggning).

### Säkerhet:
- Anonyma användare kan endast hämta publika platser.
- Inloggade användare kan hämta både publika platser och sina egna privata platser.
- Användare med administratörsroll kan lägga till nya kategorier.
- Auktorisering ska ske via OAuth2 med JWT eller BasicAuth med användarnamn/lösenord.

### Övriga detaljer att fundera över:
- Felhantering och HTTP-statuskoder? [Länk](https://www.baeldung.com/problem-spring-web)
- Versionshantering av API:et?
- Paginering och request limits?

## VG-nivå (Väl Godkänd)
Allt från G-nivån plus:
- Tester för minst 2 controller endpoints.
- Hämta information från ett annat API, t.ex., [geocode.maps.co](https://geocode.maps.co/), för att översätta våra positioner till en adress för platsen.
- Om hämtning av platsinfo misslyckas, försök igen med [retry](https://www.baeldung.com/spring-retry).
