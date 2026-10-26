/**
 * Dieses Skript lädt und zeigt die Reservierungen eines Nutzers im Benutzerkonto an.
 * Nach dem Laden der Seite wird die Reservierungsliste generiert und im DOM angezeigt.
 */
import { Reservierung } from '../models/reservierung';
import { formatDatum, getReservierungenByNutzerId } from '../controller/reservieren.controller';

document.addEventListener('DOMContentLoaded', () => {
  /**
   * Startet die Generierung der Reservierungsliste nach dem Laden der Seite.
   */
  generiereReservierungenListe();
});

/**
 * Holt die Reservierungen für den Dummy-User und rendert sie im DOM.
 * @returns Promise<void>
 */
async function generiereReservierungenListe(): Promise<void> {
  try {
    const reservierungenContainer = document.getElementById('reservierungenContainer');
    // Derzeit nur 1 Dummy User mit der Id 1
    const reservierungen: Reservierung[] = await getReservierungenByNutzerId(1);

    if (!reservierungenContainer) {
      console.error('Ergebnis-Container nicht gefunden.');
      return;
    }

    // Jede Reservierung wird als HTML-Block hinzugefügt
    reservierungen.forEach((reservierung) => {
      const reservierungHtml = `<div class="border border-gray-200 rounded-md p-4 flex items-start">
              <div class="w-24 h-32 bg-gray-200 rounded-sm mr-4 flex-shrink-0 flex items-center justify-center text-gray-400 text-xs">
                <img src="${reservierung.medium.bildLink}" alt="Cover von ${reservierung.medium.titel}" class="object-cover w-full h-full">
              </div>
              <div>
                <h3 class="text-lg font-semibold text-blue-700">${reservierung.medium.titel}</h3>
                <p class="text-gray-600 text-sm">${reservierung.medium.autor}</p>
                <p class="text-gray-500 text-xs"> Reserviert am ${formatDatum(reservierung.reservierungsdatum.toString())}</p>
                <p class="${addDateStyle(formatDatum(reservierung.reserviertBis))} font-semibold text-sm ">Reserviert bis: ${formatDatum(
        reservierung.reserviertBis,
      )} </p>
                <div class="mt-2">
                  <button class="bg-red-500 hover:bg-red-700 text-white font-bold py-1 px-3 rounded-md text-sm">Stornieren</button>
                  <button class="bg-gray-200 hover:bg-gray-300 text-gray-700 font-bold py-1 px-3 rounded-md ml-2 text-sm">Details</button>
                </div>
              </div>
            </div>`;
      reservierungenContainer?.insertAdjacentHTML('beforeend', reservierungHtml);
    });
  } catch (error) {
    console.error('Fehler beim Laden der Medien:', error);
  }
}

/**
 * Gibt eine CSS-Klasse zurück, um das Ablaufdatum optisch hervorzuheben.
 * @param datum - Das Datum als String
 * @returns CSS-Klassenname als String
 */
function addDateStyle(datum: string) {
  const tag = datum.substring(0, 2);
  const monat = datum.substring(3, 5);
  const jahr = datum.substring(6, 10);
  const formatiertesDatum = jahr.concat('-', monat, '-', tag);
  const umgewandeltesDatum = new Date(formatiertesDatum);
  const heute = new Date();
  const diffTime = Math.abs(Math.round(umgewandeltesDatum.getTime() - heute.getTime()));
  const diffTage = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

  if (diffTage < 2) {
    return 'text-red-500';
  } else if (diffTage < 3) {
    return 'text-yellow-500';
  } else {
    return 'text-green-500';
  }
}
