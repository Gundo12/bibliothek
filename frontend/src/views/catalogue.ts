/**
 * Dieses Skript behandelt die Suchfunktion im Katalog.
 * Es reagiert auf Button-Klicks und Enter-Tastendrücke im Suchfeld und leitet zur Ergebnisseite weiter.
 */
export {};

document.addEventListener('DOMContentLoaded', () => {
  /**
   * Initialisiert die Sucheingabe und den Suchbutton.
   * Bei Klick oder Enter wird zur Ergebnisseite mit dem Suchbegriff weitergeleitet.
   */
  const suchEingabe = document.getElementById('searchInputKatalog') as HTMLInputElement | null;
  const suchButton = document.getElementById('searchButtonKatalog') as HTMLButtonElement | null;

  if (suchButton && suchEingabe) {
    suchButton.addEventListener('click', () => {
      const suchBegriff = suchEingabe.value.trim();
      if (suchBegriff) {
        window.location.href = `results.html?search=${encodeURIComponent(suchBegriff)}`;
      } else {
        alert('Bitte gib einen Suchbegriff ein.');
      }
    });

    suchEingabe.addEventListener('keypress', (event) => {
      if (event.key === 'Enter') {
        suchButton.click();
      }
    });
  } else {
    console.error('Suchfeld oder Button im Katalog nicht gefunden.');
  }
});
