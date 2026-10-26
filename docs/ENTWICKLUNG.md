# Lokale Entwicklung

## Voraussetzungen

- Java 21
- Node.js mit npm
- PostgreSQL auf `localhost:5432` sowie eine Datenbank `bucherei`

Die Zugangsdaten werden nicht im Repository gespeichert. Vor dem Backend-Start
müssen `DB_USERNAME`, `DB_PASSWORD` und `DEMO_USER_PASSWORD` gesetzt werden.
Optional kann `DB_URL` verwendet werden; ohne diese Variable verbindet sich die
Anwendung mit `jdbc:postgresql://localhost:5432/bucherei`. Die Konfiguration ist
ausschließlich für die lokale Demonstration gedacht. Sie erzeugt und löscht das
Schema bei jedem Lauf (`create-drop`) und initialisiert Demo-Medien sowie den
Nutzer `admin@example.com`.

## Starten

```powershell
cd backend/Bücherei
$env:DB_USERNAME = "<eigener-datenbankbenutzer>"
$env:DB_PASSWORD = "<eigenes-datenbankpasswort>"
$env:DEMO_USER_PASSWORD = "<eigenes-demo-passwort>"
.\mvnw.cmd spring-boot:run
```

```powershell
cd frontend
npm install
npm start
```

Das Frontend wird auf Port 3000 und die API auf Port 8080 erwartet. Die URL der API ist in den TypeScript-Controller-Modulen fest eingetragen.

## Prüfungen

```powershell
cd frontend
npm test
npm run lint
npm run build
```

```powershell
cd backend/Bücherei
.\mvnw.cmd test
```

## Bekannte Grenzen

- Kein Login und keine Rechteprüfung; die Benutzeroberfläche nutzt Nutzer-ID `1`.
- Demo-Passwort wird nicht gehasht und kann in API-Antworten erscheinen.
- Kein Container- oder Cloud-Deployment und keine CI-Konfiguration.
- Keine Migrationen oder dauerhafte lokale Datenhaltung wegen `create-drop`.
- Der Frontend-Build und die Laufzeit hängen für Styling bzw. Demo-Cover von externen URLs ab.

