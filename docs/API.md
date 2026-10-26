# REST-API

Die Spring-Boot-Anwendung stellt eine synchrone HTTP-Schnittstelle für Medien,
Nutzer, Reservierungen und Ausleihen bereit.

| Eigenschaft | Wert |
| --- | --- |
| Basis-URL | `http://localhost:8080` |
| API-Präfix | `/api` |
| Datenformat | JSON |
| Authentisierung | nicht implementiert |
| OpenAPI | `/v3/api-docs` |
| Swagger UI | `/swagger-ui/index.html` |

Die dokumentierten Endpunkte entsprechen den expliziten Spring-MVC-Controllern
im Repository. Die API verwendet JPA-Entities direkt als Ein- und
Ausgabeobjekte; separate DTOs existieren nicht.

## 1. Medien

| Methode | Pfad | Beschreibung | Erfolgsantwort |
| --- | --- | --- | --- |
| `GET` | `/api/medien` | Alle Medien abrufen | `200 OK` mit JSON-Liste |
| `GET` | `/api/medien/{id}` | Medium anhand seiner ID abrufen | `200 OK` mit Medium |
| `GET` | `/api/medien/suche?begriff={text}` | Titel und Autoren ohne Beachtung der Groß-/Kleinschreibung durchsuchen | `200 OK` mit JSON-Liste |
| `POST` | `/api/medien` | Medium speichern | `200 OK` mit gespeichertem Medium |

Beispiel eines Buchs:

```json
{
  "mediumId": 2,
  "titel": "Der Prozess",
  "autor": "Franz Kafka",
  "kategorie": "Roman",
  "bildLink": "https://picsum.photos/200/300?random=2",
  "status": "VERFUEGBAR",
  "mediumTyp": "BUCH",
  "isbn": "9781234567890",
  "seitenzahl": 256,
  "standort": {
    "id": 4,
    "stockwerk": "OG1",
    "regal": "ABC",
    "fach": "3"
  }
}
```

Mögliche Medienstatus sind `VERFUEGBAR`, `RESERVIERT`, `AUSGELIEHEN` und
`NICHT_VERFUEGBAR`.

Der Controller des `POST`-Endpunkts erwartet den abstrakten Java-Typ `Medium`.
Da keine Jackson-Konfiguration zur Auswahl eines konkreten Untertyps vorhanden
ist, ist das Anlegen eines Buchs über diesen Endpunkt derzeit nicht zuverlässig
nutzbar.

## 2. Nutzer

| Methode | Pfad | Beschreibung | Erfolgsantwort |
| --- | --- | --- | --- |
| `POST` | `/api/nutzer/registrieren` | Neuen Nutzer registrieren | `200 OK` mit gespeichertem Nutzer |
| `GET` | `/api/nutzer/{id}` | Nutzer anhand seiner ID abrufen | `200 OK` mit Nutzer |

Beispiel einer Registrierung:

```http
POST /api/nutzer/registrieren
Content-Type: application/json
```

```json
{
  "name": "Erika Musterfrau",
  "email": "erika@example.com",
  "passwort": "<eigenes-passwort>",
  "adresse": "Musterstraße 1",
  "geburtsdatum": "1994-05-12",
  "telefonnummer": "0123456789"
}
```

Das Backend setzt `registrierungsdatum` auf das aktuelle Datum und lehnt eine
bereits vorhandene E-Mail-Adresse durch eine Fachausnahme ab. Das Passwort wird
derzeit weder gehasht noch aus der JSON-Antwort ausgeblendet.

## 3. Reservierungen

| Methode | Pfad | Beschreibung | Erfolgsantwort |
| --- | --- | --- | --- |
| `POST` | `/api/reservierungen?nutzerId={nutzerId}&mediumId={mediumId}` | Verfügbares Medium reservieren | `200 OK` mit Reservierung |
| `GET` | `/api/reservierungen/nutzer/{nutzerId}` | Reservierungen eines Nutzers abrufen | `200 OK` mit JSON-Liste |
| `GET` | `/api/reservierungen/{mediumId}` | Reservierung zu einem Medium abrufen | `200 OK` oder `204 No Content` |
| `PUT` | `/api/reservierungen/{reservierungsId}/stornieren` | Offene Reservierung stornieren | `200 OK` mit Reservierung |

Das Erstellen und Stornieren benötigt keinen Request-Body. Nutzer- und
Medien-ID werden als Query-Parameter beziehungsweise Pfadparameter übertragen.

Gekürztes Beispiel der Antwort; die verschachtelten Entities enthalten weitere
Felder:

```http
POST /api/reservierungen?nutzerId=1&mediumId=2
```

```json
{
  "id": 7,
  "nutzer": {
    "nutzerId": 1,
    "name": "Max Mustermann",
    "email": "admin@example.com"
  },
  "medium": {
    "mediumId": 2,
    "titel": "Der Prozess",
    "status": "RESERVIERT"
  },
  "reservierungsdatum": "2026-10-26",
  "reserviertBis": "2026-10-29T12:40:00",
  "status": "OFFEN"
}
```

Eine neue Reservierung erhält den Status `OFFEN` und eine Frist von `3880`
Minuten. Weitere Reservierungsstatus sind `STORNIERT` und `ABGEHOLT`.

## 4. Ausleihen

| Methode | Pfad | Beschreibung | Erfolgsantwort |
| --- | --- | --- | --- |
| `POST` | `/api/ausleihen` | Ausleihe anlegen | `200 OK` mit Ausleihe |
| `GET` | `/api/ausleihen/nutzer/{nutzerId}` | Ausleihen eines Nutzers abrufen | `200 OK` mit JSON-Liste |
| `PUT` | `/api/ausleihen/{ausleiheId}/verlaengern` | Rückgabedatum verlängern | `200 OK` mit aktualisierter Ausleihe |

Für eine neue Ausleihe werden mindestens Nutzer und Medium referenziert. Fehlt
das Rückgabedatum, verwendet das Backend eine Ausleihdauer von 14 Tagen.

```http
POST /api/ausleihen
Content-Type: application/json
```

```json
{
  "nutzer": { "nutzerId": 1 },
  "medium": { "mediumId": 2 },
  "rueckgabedatum": "2026-11-09"
}
```

Für eine Verlängerung genügt das neue Rückgabedatum:

```http
PUT /api/ausleihen/3/verlaengern
Content-Type: application/json
```

```json
{
  "rueckgabedatum": "2026-11-23"
}
```

Mögliche Ausleihstatus sind `AKTIV`, `VERLAENGERT` und `ZURUECKGEGEBEN`. Eine
Rückgabeoperation ist aktuell nicht implementiert.

## 5. Fachregeln

- Reserviert werden können nur Medien mit dem Status `VERFUEGBAR`.
- Eine Reservierung setzt das Medium auf `RESERVIERT`.
- Beim Stornieren wird eine offene Reservierung auf `STORNIERT` und das Medium
  auf `VERFUEGBAR` gesetzt.
- Ausleihbar sind Medien mit dem Status `VERFUEGBAR` oder `RESERVIERT`.
- Eine vorhandene offene Reservierung wird beim Ausleihen als `ABGEHOLT`
  markiert; eine Prüfung auf denselben Nutzer findet nicht statt.
- Eine Verlängerung ist nur auf ein Datum nach dem bisherigen Rückgabedatum
  möglich.
- Der Scheduler prüft alle 60 Sekunden die Reservierungsfrist, löscht
  abgelaufene Reservierungen und setzt die zugehörigen Medien auf
  `VERFUEGBAR`.

