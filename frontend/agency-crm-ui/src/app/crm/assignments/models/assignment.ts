import { LanguageLevel, AssignmentStatus, AssignmentCloseReason } from '../../../shared/models/domain-enums.model';

export interface Assignment {
  id: number;
  clientId: number;
  careRecipientId: number;
  startDate: string;
  city: string;
  streetAddress: string | null;
  salaryMonthlyNet: number;
  languageLevel: LanguageLevel;
  requirements: string | null;
  status: AssignmentStatus;
  closeReason: AssignmentCloseReason | null;
  closeNotes: string | null;
  caregiverId: number | null;
}
