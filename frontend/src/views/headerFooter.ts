/**
 * Dieses Skript stellt Funktionen zur Generierung von Header, Footer und Suchleiste bereit.
 * Die Komponenten werden als HTML-Strings zurückgegeben und können in die Seite eingebunden werden.
 */
export {};

/**
 * Gibt das HTML für den Seiten-Header zurück.
 * @returns HTML-String für den Header
 */
export function generiereHeader() {
  return `<header class="bg-blue-600 text-white py-4">
  <div class="container mx-auto px-4 flex items-center justify-between">
    <div class="flex items-center">
      <a href="http://localhost:3000/" class="text-xl font-bold">
        <span class="text-yellow-400">Stadt</span>bibliothek
      </a>
      ${generiereSuchleiste()}
    </div>
    <nav class="space-x-4">
      <a href="http://localhost:3000/" class="hover:text-yellow-300">Startseite</a>
      <a href="http://localhost:3000/catalogue.html" class="hover:text-yellow-300">Katalog</a>
      <a href="#" class="hover:text-yellow-300">Veranstaltungen</a>
      <a href="http://localhost:3000/userAccountList.html" class="hover:text-yellow-300">Mein Konto</a>
      <a href="#" class="hover:text-yellow-300">Hilfe</a>
    </nav>
  </div>
</header>
`;
}

/**
 * Gibt die Suchleiste nur auf der Ergebnisseite zurück.
 * @returns HTML-String für die Suchleiste
 */
export function generiereSuchleiste(): string {
  if (window.location.pathname === '/results.html') {
    return `<div class="ml-6 relative w-96">
            <input
              id="searchInputKatalog"
              type="text"
              class="bg-blue-700 text-white rounded-full py-2 pl-4 pr-10 focus:outline-none focus:ring-2 focus:ring-yellow-400 w-full"
              placeholder="Titel, Autor, Stichwort..."
              value="Das gesuchte Buch"
            />
            <button id="buttonSearch" class="absolute right-2 top-1/2 transform -translate-y-1/2 text-white">
              <svg class="h-5 w-5 fill-current" viewBox="0 0 20 20">
                <path
                  fill-rule="evenodd"
                  d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
                  clip-rule="evenodd"
                />
              </svg>
            </button>
          </div>`;
  } else {
    return '';
  }
}

/**
 * Gibt das HTML für den Seiten-Footer zurück.
 * @returns HTML-String für den Footer
 */
function generiereFooter() {
  return `<footer class="bg-blue-800 text-gray-300 py-6 mt-12">
  <div
    class="container mx-auto px-4 grid grid-cols-1 md:grid-cols-3 gap-6 text-sm"
  >
    <div>
      <h3 class="font-semibold mb-2">Kontakt</h3>
      <p>Musterstraße 1</p>
      <p>12345 Bibliothekstadt</p>
      <p>Tel.: 01234 / 56789</p>
    </div>
    <div>
      <h3 class="font-semibold mb-2">Öffnungszeiten</h3>
      <p>Mo-Fr: 9-18 Uhr</p>
      <p>Sa: 10-14 Uhr</p>
    </div>
    <div>
      <h3 class="font-semibold mb-2">Links</h3>
      <ul class="list-none space-y-1">
        <li><a href="#" class="hover:text-white">Impressum</a></li>
        <li><a href="#" class="hover:text-white">Datenschutz</a></li>
        <li><a href="#" class="hover:text-white">Barrierefreiheit</a></li>
      </ul>
    </div>
  </div>
</footer>`;
}

document.addEventListener('DOMContentLoaded', () => {
  const header = document.getElementById('header-placeholder');
  const footer = document.getElementById('footer-placeholder');

  if (header) {
    header.innerHTML = generiereHeader();
  }

  if (footer) {
    footer.innerHTML = generiereFooter();
  }
});
