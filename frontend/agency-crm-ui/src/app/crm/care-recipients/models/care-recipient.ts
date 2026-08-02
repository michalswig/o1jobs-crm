import { Gender, MobilityLevel, DementiaLevel, CareCapability } from '../../../shared/models/domain-enums.model';

export interface CareRecipient {
  id: number;
  firstName: string;
  lastName: string;
  dateOfBirth: string;
  heightCm: number;
  weightKg: number;
  gender: Gender;
  mobilityLevel: MobilityLevel;
  dementiaLevel: DementiaLevel;
  diseasesNotes: string | null;
  smoker: boolean;
  hasPets: boolean;
  petsNotes: string | null;
  liftingAidsNotes: string | null;
  medicalNotes: string | null;
  requiredCapabilities: CareCapability[];
  clientId: number;
}
