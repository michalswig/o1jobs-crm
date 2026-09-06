export interface MonthlyCount {
  month: string;
  count: number;
}

export interface CaregiverAvailability {
  caregiverId: number;
  fullName: string;
  assigned: boolean;
}

export interface PartnerSummary {
  partnerId: number;
  partnerName: string;
  clientNames: string[];
  totalContractValue: number;
}

export interface AssignmentSummary {
  assignmentId: number;
  city: string;
  startDate: string;
  clientName: string;
  contractValue: number | null;
}

export interface DashboardData {
  openAssignmentsCount: number;
  careRecipientsWithoutAssignmentCount: number;
  totalMonthlyContractValue: number;
  statusBreakdown: Record<string, number>;
  closeReasonBreakdown: Record<string, number>;
  nationalityBreakdown: Record<string, number>;
  dementiaExperienceBreakdown: Record<string, number>;
  openAssignmentsMissingDocumentCount: number;
  monthlyTrend: MonthlyCount[];
  caregiverAvailability: CaregiverAvailability[];
  partnerBreakdown: PartnerSummary[];
  assignmentsStartingThisMonth: AssignmentSummary[];
}
