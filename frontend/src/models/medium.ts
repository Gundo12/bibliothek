export enum MediumStatus {
  VERFUEGBAR = 'VERFUEGBAR',
  AUSGELIEHEN = 'AUSGELIEHEN',
  RESERVIERT = 'RESERVIERT',
}

export enum MediumTyp {
  BUCH = 'BUCH',
  ZEITSCHRIFT = 'ZEITSCHRIFT',
  EBOOK = 'EBOOK',
  DVD = 'DVD',
}
export class Medium {
  mediumId: number;
  titel: string;
  autor: string;
  kategorie: string;
  bildLink: string;
  status: MediumStatus;
  mediumTyp: MediumTyp;

  constructor({ mediumId, titel, autor, kategorie, bildLink, status, mediumTyp }: Medium) {
    this.mediumId = mediumId;
    this.titel = titel;
    this.autor = autor;
    this.kategorie = kategorie;
    this.bildLink = bildLink;
    this.status = status;
    this.mediumTyp = mediumTyp;
  }
}
