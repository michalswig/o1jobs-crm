export type Gender = 'MALE' | 'FEMALE';

export type Nationality = 'POLISH' | 'GEORGIAN' | 'MOLDOVAN' | 'RUSSIAN' | 'UKRAINIAN';

export interface Caregiver {
  id: number;
  firstName: string;
  lastName: string;
  gender: Gender;
  birthDate: string;
  weightKg: number;
  heightCm: number;
  phone: string;
  email: string | null;
  nationality: Nationality;
  careerStartDate: string;
  hasDriverLicense: boolean;
  smoker: boolean;
  medicalQualificationNotes: string | null;
  recruiterNotes: string | null;
}
