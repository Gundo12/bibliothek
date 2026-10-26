export class Nutzer {
  id: number;
  name: string;
  email: string;
  passwort: string;
  adresse: string;
  geburtsdatum: Date;
  regiestrirungsdatum: Date;
  telefonnummer: string;

  constructor({ id, name, email, passwort, adresse, geburtsdatum, regiestrirungsdatum, telefonnummer }: Nutzer) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.passwort = passwort;
    this.adresse = adresse;
    this.geburtsdatum = geburtsdatum;
    this.regiestrirungsdatum = regiestrirungsdatum;
    this.telefonnummer = telefonnummer;
  }
}
