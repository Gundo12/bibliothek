/**
 * Dieses Skript behandelt die Anzeige und Suche von Medienergebnissen sowie die Reservierungslogik.
 * Initialisiert die Suchfeld- und Button-Events und verarbeitet die Suchparameter.
 */
import { Buch } from '../models/buch';
import { Medium, MediumStatus } from '../models/medium';
import { Reservierung } from '../models/reservierung';
import { fetchMediumById, fetchMediumByInput } from '../controller/medium.controller';
import { formatDatum, getReservierungByMediumId, postReservierung } from '../controller/reservieren.controller';

export {};

/**
 * Initialisiert die Sucheingabe, verarbeitet Suchparameter und setzt Event Listener.
 */
document.addEventListener('DOMContentLoaded', () => {
  const suchEingabe = document.getElementById('searchInputKatalog') as HTMLInputElement | null;
  const suchButton = document.getElementById('buttonSearch');
  const urlParameter = new URLSearchParams(window.location.search);
  const suchBegriff = urlParameter.get('search');

  if (suchEingabe) {
    if (suchBegriff) {
      suchEingabe.value = decodeURIComponent(suchBegriff);
      generiereErgebnisHtml(suchBegriff);
    } else {
      suchEingabe.placeholder = 'Gib einen Suchbegriff ein';
    }

    suchEingabe.addEventListener('keydown', (e) => {
      if (e.key === 'Enter') sucheBehandeln();
    });
  } else {
    console.error('Suchfeld auf der Ergebnisseite nicht gefunden.');
  }

  suchButton?.addEventListener('click', sucheBehandeln);
});

/**
 * Liefert den Wert eines Eingabefelds anhand der ID.
 * @param id - Die ID des Eingabefelds
 * @returns Der getrimmte Wert als String
 */
function getEingabeWertNachId(id: string): string {
  const eingabe = document.getElementById(id) as HTMLInputElement | null;
  return eingabe?.value.trim() ?? '';
}

/**
 * Gibt eine CSS-Klasse für den Status eines Mediums zurück.
 * @param status - Der Status des Mediums
 * @returns CSS-Klassenname als String
 */
function holeStatusKlasse(status: string): string {
  switch (status.toUpperCase()) {
    case 'VERFUEGBAR':
      return 'text-green-500';
    case 'RESERVIERT':
      return 'text-yellow-500';
    case 'AUSGELIEHEN':
      return 'text-red-500';
    default:
      return 'text-gray-500';
  }
}

/**
 * Aktualisiert die Überschrift der Suchergebnisse.
 * @param suchBegriff - Der aktuelle Suchbegriff
 * @param anzahl - Die Anzahl der gefundenen Ergebnisse
 */
function aktualisiereUeberschrift(suchBegriff: string, anzahl: number): void {
  const ueberschrift = document.getElementById('resultsHeadline');
  if (ueberschrift) {
    ueberschrift.innerHTML = `Suchergebnisse für <span class="font-bold italic">"${suchBegriff}"</span> (${anzahl} Ergebnis${
      anzahl !== 1 ? 'se' : ''
    })`;
  }
}

/**
 * Behandelt die Suche, wenn der Benutzer auf den Suchbutton klickt oder die Eingabetaste drückt.
 */
function sucheBehandeln(): void {
  const suchBegriff = getEingabeWertNachId('searchInputKatalog');
  if (suchBegriff.length > 0) {
    generiereErgebnisHtml(suchBegriff);
  } else {
    console.warn('Bitte Suchbegriff eingeben.');
  }
}

/**
 * Generiert die HTML für die Suchergebnisse basierend auf dem Suchbegriff.
 * @param suchBegriff - Der Suchbegriff
 */
async function generiereErgebnisHtml(suchBegriff: string): Promise<void> {
  try {
    const medien: (Buch | Medium)[] = await fetchMediumByInput(suchBegriff);
    const ergebnisContainer = document.getElementById('resultsContainer');

    if (!ergebnisContainer) {
      console.error('Ergebnis-Container nicht gefunden.');
      return;
    }

    ergebnisContainer.innerHTML = '';

    // Reservierungsdaten für alle Medien vorab laden
    const reservierungen = await Promise.all(
      medien.map(async (medium) => {
        try {
          return await holeReserviertBis(medium.mediumId);
        } catch (error) {
          return null;
        }
      }),
    );

    medien.forEach((medium, idx) => {
      const reservierung = reservierungen[idx];
      if (medium instanceof Buch) {
        const standortHtml = `
         <p class="text-gray-600 mb-1">
        Standort: ${medium.standort.stockwerk} – Regal ${medium.standort.regal}, Fach ${medium.standort.fach}
      </p>
    `;
        const mediumHtml = `
      <div class="bg-white rounded-lg shadow-md p-6 flex">
          <div class="w-32 h-40 bg-gray-200 rounded-md mr-6 flex-shrink-0 flex items-center justify-center overflow-hidden">
            <img src="${medium.bildLink}" alt="Cover von ${medium.titel}" class="object-cover w-full h-full">
          </div>
          <div>
            <h3 class="text-lg font-semibold text-blue-700 mb-1">${medium.titel}</h3>
            <p class="text-gray-600 mb-1">Autor: ${medium.autor}</p>
            <p id= "statusId${medium.mediumId}" class="font-semibold ${holeStatusKlasse(medium.status)}">${medium.status} ${
          reservierung && reservierung.reserviertBis ? ' ' + formatDatum(reservierung.reserviertBis) : ''
        }</p>
            ${standortHtml}
            <div class="mt-2">
              <a href="#" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-md mr-2 text-sm">Details</a>
               ${erstelleReservierenButtonHTML(medium.status, medium.mediumId)}
            </div>
          </div> 
        </div>
      `;
        ergebnisContainer.insertAdjacentHTML('beforeend', mediumHtml);
      }
    });

    hoereReservierenKlick();
    aktualisiereUeberschrift(suchBegriff, medien.length);
  } catch (error) {
    console.error('Fehler beim Laden der Medien:', error);
  }
}

/**
 * Setzt Event Listener für alle Reservieren-Buttons.
 */
function hoereReservierenKlick(): void {
  let btnReservieren = document.querySelectorAll('.reserve');

  btnReservieren.forEach((btn) => {
    btn.addEventListener('click', function (e) {
      reservieren(btn);
    });
  });
}

/**
 * Reserviert ein Medium und aktualisiert den Status im UI.
 * @param btn - Der Button, der geklickt wurde
 * @returns Das reservierte Medium als Buch oder Medium
 */
async function reservieren(btn: any): Promise<Buch | Medium> {
  console.log('ww');

  await postReservierung(1, btn.dataset.mediumid);

  const medium = await fetchMediumById(btn.dataset.mediumid);

  const statusSpan = document.getElementById(`statusId${medium.mediumId}`);
  if (statusSpan) {
    const reservierung = await holeReserviertBis(medium.mediumId);
    statusSpan.textContent = `${medium.status} bis ${formatDatum(reservierung.reserviertBis)}`;
    statusSpan.className = 'font-semibold text-yellow-500';
  }

  aktualisiereReservierenButton(btn);

  if (medium instanceof Buch) {
    return new Buch(medium);
  } else {
    console.error('Das Medium ist kein Buch.');
    return new Medium(medium);
  }
}

/**
 * Holt die Reservierungsdaten für ein Medium.
 * @param mediumId - Die ID des Mediums
 * @returns Promise mit Reservierungsdaten
 */
async function holeReserviertBis(mediumId: number): Promise<Reservierung> {
  const reservierung = await getReservierungByMediumId(mediumId);
  return reservierung;
}

/**
 * Erstellt das HTML für den Reservieren-Button abhängig vom Status.
 * @param status - Der Status des Mediums
 * @param mediumId - Die ID des Mediums
 * @returns HTML-String für den Button
 */
function erstelleReservierenButtonHTML(status: string, mediumId: number): string {
  const deaktiviert = ['NICHT_VERFUEGBAR', 'RESERVIERT', 'AUSGELIEHEN'].includes(status);

  return `
    <button 
      data-mediumid="${mediumId}" 
      class="reserve ${getButtonKlassen(deaktiviert)}"
      ${deaktiviert ? 'disabled' : ''}
    >
      ${deaktiviert ? 'Reserviert' : 'Reservieren'}
    </button>`;
}

/**
 * Gibt die CSS-Klassen für den Reservieren-Button zurück.
 * @param deaktiviert - Ob der Button deaktiviert ist
 * @returns CSS-Klassen als String
 */
function getButtonKlassen(deaktiviert: boolean): string {
  return [
    'bg-yellow-500',
    'text-white',
    'font-bold',
    'py-2',
    'px-4',
    'rounded-md',
    'mr-2',
    'text-sm',
    deaktiviert ? 'opacity-50 cursor-not-allowed pointer-events-none' : 'hover:bg-yellow-700',
  ].join(' ');
}

/**
 * Aktualisiert den Reservieren-Button nach erfolgreicher Reservierung.
 * @param btn - Der Button, der aktualisiert werden soll
 */
function aktualisiereReservierenButton(btn: HTMLButtonElement): void {
  btn.disabled = true;
  btn.className = getButtonKlassen(true);
  btn.textContent = 'Reserviert';
}
