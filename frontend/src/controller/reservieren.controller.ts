/**
 * Service für Reservierungsoperationen: Reservieren, Abfragen und Datumsformatierung.
 * Stellt Funktionen bereit, um Reservierungen zu erstellen, abzufragen und Datumswerte zu formatieren.
 */
import { Reservierung } from '../models/reservierung';

/**
 * Sendet eine Reservierung an die API.
 * @param nutzerId - Die ID des Nutzers
 * @param mediumId - Die ID des Mediums
 * @returns Promise mit der Antwort der API
 */
export async function postReservierung(nutzerId: number, mediumId: number) {
  const response = await fetch(`http://localhost:8080/api/reservierungen?nutzerId=${nutzerId}&mediumId=${mediumId}`, { method: 'post' });
  const responseData = response.json();
  return responseData;
}

/**
 * Holt eine Reservierung zu einer Medium-ID von der API.
 * @param mediumId - Die ID des Mediums
 * @returns Promise mit Reservierungsdaten oder null, falls keine Reservierung existiert
 */
export async function getReservierungByMediumId(mediumId: number) {
  const response = await fetch(`http://localhost:8080/api/reservierungen/${mediumId}`);
  if (!response.ok || response.status === 204) {
    console.warn(`Keine Reservierung für Medium ID ${mediumId}`);
    return null;
  }
  const text = await response.text();
  if (!text) {
    return null;
  }
  try {
    return JSON.parse(text);
  } catch (e) {
    console.error('Fehler beim Parsen der JSON-Antwort:', e);
    return null;
  }
}

/**
 * Holt alle Reservierungen eines Nutzers von der API.
 * @param nutzerId - Die ID des Nutzers
 * @returns Promise mit einer Liste von Reservierungen
 * @throws Fehler, falls die Reservierungen nicht geladen werden können
 */
export async function getReservierungenByNutzerId(nutzerId: number): Promise<Reservierung[]> {
  const response = await fetch(`http://localhost:8080/api/reservierungen/nutzer/${nutzerId}`);
  if (!response.ok) {
    throw new Error('Fehler beim Laden der Reservierungen');
  }
  const reservierungen = await response.json();
  console.log(reservierungen);
  return reservierungen;
}

/**
 * Formatiert ein Datum im deutschen Format (TT.MM.JJJJ).
 * @param datum - Das Datum als String
 * @returns Das formatierte Datum als String
 */
export function formatDatum(datum: string): string {
  const date = new Date(datum);
  return date.toLocaleDateString('de-DE', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
  });
}
