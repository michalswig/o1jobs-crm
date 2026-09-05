export type IntermediaryType = 'PARTNER' | 'CAREGIVER' | 'EMPLOYEE' | 'OTHER';

export interface Intermediary {
  id: number;
  intermediaryType: IntermediaryType;
  name: string;
  country: string | null;
  city: string | null;
  postalCode: string | null;
  streetAddress: string | null;
  email: string;
  phone: string;
  notes: string | null;
}
