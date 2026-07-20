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
}

export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
  first: boolean;
  last: boolean;
}
