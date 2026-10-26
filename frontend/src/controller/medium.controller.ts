/**
 * Service für das Laden und Mappen von Medien (Bücher und andere Medientypen) von der API.
 * Stellt Funktionen bereit, um Medien anhand von ID oder Suchbegriff zu laden und korrekt zu typisieren.
 */
import { Buch } from '../models/buch';
import { Medium } from '../models/medium';

/**
 * Wandelt ein API-Objekt in ein Buch- oder Medium-Objekt um.
 * @param m - Das Medienobjekt aus der API
 * @returns Instanz von Buch oder Medium
 */
function mappeZuMedium(m: any): Buch | Medium {
  switch (m.mediumTyp?.toUpperCase()) {
    case 'BUCH':
      return new Buch(m);
    default:
      return new Medium(m);
  }
}

/**
 * Holt ein Medium anhand der ID von der API und gibt das passende Objekt zurück.
 * @param id - Die ID des Mediums
 * @returns Promise mit Buch oder Medium
 * @throws Fehler, falls das Medium nicht geladen werden kann
 */
export async function fetchMediumById(id: number): Promise<Buch | Medium> {
  const response = await fetch(`http://localhost:8080/api/medien/${id}`);
  if (!response.ok) {
    throw new Error('Fehler beim Laden des Mediums');
  }
  const m = await response.json();
  return mappeZuMedium(m);
}

/**
 * Sucht Medien anhand eines Suchbegriffs über die API und gibt eine Liste typisierter Objekte zurück.
 * @param input - Der Suchbegriff
 * @returns Promise mit Array von Buch- oder Medium-Objekten
 * @throws Fehler, falls die Medien nicht geladen werden können
 */
export async function fetchMediumByInput(input: string): Promise<(Buch | Medium)[]> {
  const response = await fetch(`http://localhost:8080/api/medien/suche?begriff=${encodeURIComponent(input)}`);

  if (!response.ok) {
    throw new Error('Fehler beim Laden der Medien');
  }

  const medien = await response.json();

  return medien.map((m: any) => mappeZuMedium(m));
}
