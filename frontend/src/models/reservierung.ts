import { Medium } from './medium';
import { Nutzer } from './user';

export interface Reservierung {
  id: number;
  nutzer: Nutzer;
  medium: Medium;
  reservierungsdatum: Date;
  status: string;
  reserviertBis: string;
}
