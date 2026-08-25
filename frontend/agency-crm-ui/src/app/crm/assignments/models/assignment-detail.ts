import { Client } from '../../models/client.model';
import { Caregiver } from '../../caregivers/models/caregiver.model';
import { LanguageLevel, AssignmentStatus, AssignmentCloseReason, AccommodationType } from '../../../shared/models/domain-enums.model';
import {CareRecipient} from '../../care-recipients/models/care-recipient';

export interface AssignmentDetail {
  id: number;
  startDate: string;
  city: string;
  streetAddress: string | null;
  salaryMonthlyNet: number;
  languageLevel: LanguageLevel;
  requirements: string | null;
  status: AssignmentStatus;
  closeReason: AssignmentCloseReason | null;
  closeNotes: string | null;
  client: Client;
  careRecipient: CareRecipient;
  caregiver: Caregiver | null;
  accommodationType: AccommodationType | null;
  ownBathroom: boolean;
  ownRoom: boolean;
}
