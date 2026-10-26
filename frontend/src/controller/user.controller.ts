/**
 * Service für das Laden eines Nutzers anhand der ID von der API.
 * Stellt eine Funktion bereit, um Nutzerdaten zu laden und als Nutzer-Objekt zurückzugeben.
 */

import { Nutzer } from '../models/user';

/**
 * Holt die Nutzerdaten von der API und gibt ein Nutzer-Objekt zurück.
 * @param id - Die ID des Nutzers
 * @returns Promise mit Nutzer-Objekt
 */
export async function fetchNutzer(id: number) {
  const response = await fetch(`http://localhost:8080/api/nutzer/${id}`);
  const nutzer = await response.json();
  return new Nutzer(nutzer);
}
