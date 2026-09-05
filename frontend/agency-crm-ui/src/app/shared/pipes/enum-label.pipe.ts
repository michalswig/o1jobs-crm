import { Pipe, PipeTransform } from '@angular/core';

/**
 * Centralna mapa niemieckich etykiet dla surowych wartości enumów z backendu.
 * Jedno źródło prawdy - żeby nie tłumaczyć tych samych wartości osobno
 * w każdym komponencie (listy, formularze, szczegóły).
 */
export const ENUM_LABELS_DE: Record<string, string> = {
  // Gender
  MALE: 'Männlich',
  FEMALE: 'Weiblich',

  // Nationality
  POLISH: 'Polnisch',
  GEORGIAN: 'Georgisch',
  MOLDOVAN: 'Moldawisch',
  RUSSIAN: 'Russisch',
  UKRAINIAN: 'Ukrainisch',

  // MobilityLevel
  FULL: 'Vollständig mobil',
  CANE: 'Gehstock',
  ROLLATOR: 'Rollator',
  WHEELCHAIR: 'Rollstuhl',
  BEDRIDDEN: 'Bettlägerig',

  // DementiaLevel / LanguageLevel (współdzielą NONE)
  NONE: 'Keine',
  MILD: 'Leicht',
  ADVANCED: 'Fortgeschritten',

  // CareCapability
  TRANSFER: 'Transfer',
  TRANSFER_WITH_LIFT: 'Transfer mit Lifter',
  CATHETER: 'Katheterpflege',
  STOMA: 'Stomapflege',
  DIAPERS: 'Inkontinenzversorgung',
  PEG: 'PEG-Sondenpflege',
  OXYGEN: 'Sauerstofftherapie',
  INSULIN: 'Insulingabe',
  INJECTION: 'Injektionen',
  ALZHEIMER: 'Alzheimer-Erfahrung',
  DEMENTIA: 'Demenzerfahrung',
  PARKINSON: 'Parkinson-Erfahrung',
  MS: 'MS-Erfahrung',
  COOKING: 'Kochen',
  CLEANING: 'Reinigung',
  SHOPPING: 'Einkaufen',
  NIGHT_CARE: 'Nachtwache',

  // LanguageLevel (reszta)
  BASIC: 'Grundkenntnisse',
  COMMUNICATIVE: 'Kommunikationsfähig',
  GOOD: 'Gut',
  VERY_GOOD: 'Sehr gut',

  // AssignmentStatus
  OPEN: 'Offen',
  CLOSED: 'Geschlossen',

  // AssignmentCloseReason
  FAMILY_CANCELLED: 'Familie hat storniert',
  SENIOR_PASSED: 'Senior verstorben',
  NURSING_HOME: 'Umzug ins Pflegeheim',
  OTHER: 'Sonstiges',

  // AccommodationType
  HOUSE: 'Haus',
  APARTMENT: 'Wohnung',

  // UserRole
  ADMIN: 'Administrator',
  MANAGER: 'Manager',
  RECRUITER: 'Recruiter',

  // IntermediaryType
  PARTNER: 'Partner',
  CAREGIVER: 'Betreuerinnen-Vermittler',
  EMPLOYEE: 'Mitarbeiter',
};

@Pipe({
  name: 'enumLabel',
  standalone: true
})
export class EnumLabelPipe implements PipeTransform {
  transform(value: string | null | undefined): string {
    if (!value) {
      return '';
    }
    return ENUM_LABELS_DE[value] ?? value;
  }
}
