import { Stockwerk } from './stockwerk';

export interface Standort {
  id: number;
  stockwerk: Stockwerk;
  regal: string;
  fach: string;
}
