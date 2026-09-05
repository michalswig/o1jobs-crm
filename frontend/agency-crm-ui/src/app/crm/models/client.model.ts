export interface Client {
  id: number;
  name: string;
  email: string;
  phoneNumber: string;
  country: string;
  city: string;
  postalCode: string;
  streetAddress: string;
  notes: string | null;
  intermediary_id: number | null;
  intermediaryName: string | null;
}
