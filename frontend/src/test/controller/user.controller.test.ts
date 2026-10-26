import { fetchNutzer } from '../../controller/user.controller';
import { Nutzer } from '../../models/user';

global.fetch = jest.fn();

describe('fetchNutzer', () => {
  it('should fetch and return a Nutzer instance', async () => {
    const mockNutzerData = {
      id: 1,
      name: 'Max Mustermann',
      email: 'max@example.com',
      passwort: 'geheim',
      adresse: 'Musterstraße 1',
      geburtsdatum: '1990-01-01T00:00:00.000Z',
      regiestrirungsdatum: '2023-01-01T00:00:00.000Z',
      telefonnummer: '123456789',
    };

    (fetch as jest.Mock).mockResolvedValue({
      json: jest.fn().mockResolvedValue(mockNutzerData),
    });

    const result = await fetchNutzer(1);

    expect(result).toBeInstanceOf(Nutzer);
    expect(result.email).toBe('max@example.com');
  });
});
