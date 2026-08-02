export type Gender = 'MALE' | 'FEMALE';

export type Nationality = 'POLISH' | 'GEORGIAN' | 'MOLDOVAN' | 'RUSSIAN' | 'UKRAINIAN';

export type MobilityLevel = 'FULL' | 'CANE' | 'ROLLATOR' | 'WHEELCHAIR' | 'BEDRIDDEN';

export type DementiaLevel = 'NONE' | 'MILD' | 'ADVANCED';

export type CareCapability =
  | 'TRANSFER'
  | 'TRANSFER_WITH_LIFT'
  | 'CATHETER'
  | 'STOMA'
  | 'DIAPERS'
  | 'PEG'
  | 'OXYGEN'
  | 'INSULIN'
  | 'INJECTION'
  | 'ALZHEIMER'
  | 'DEMENTIA'
  | 'PARKINSON'
  | 'MS'
  | 'COOKING'
  | 'CLEANING'
  | 'SHOPPING'
  | 'NIGHT_CARE';
