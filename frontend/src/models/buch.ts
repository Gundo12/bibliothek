import { Medium, MediumStatus, MediumTyp } from './medium';
import { Standort } from './standort';

export class Buch extends Medium {
  isbn: number;
  seitenanzahl: number;
  standort: Standort;

  constructor(buch: {
    mediumId: number;
    titel: string;
    autor: string;
    kategorie: string;
    bildLink: string;
    status: MediumStatus;
    mediumTyp: MediumTyp;
    isbn: number;
    seitenanzahl: number;
    standort: Standort;
  }) {
    super(buch);
    this.isbn = buch.isbn;
    this.seitenanzahl = buch.seitenanzahl;
    this.standort = buch.standort;
  }
}
