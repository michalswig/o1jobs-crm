import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { AssignmentService, CloseAssignmentPayload } from '../services/assignment.service';
import { CareCapability } from '../../../shared/models/domain-enums.model';
import { CloseAssignmentDialogComponent } from '../../../core/dialogs/close-assignment-dialog/close-assignment-dialog.component';
import {AssignmentDetail} from '../models/assignment-detail';

@Component({
  selector: 'app-assignment-detail',
  standalone: true,
  imports: [RouterLink, MatButtonModule, MatDialogModule],
  templateUrl: './assignment-detail.component.html',
  styleUrl: './assignment-detail.component.scss'
})
export class AssignmentDetailComponent implements OnInit {

  assignment: AssignmentDetail | null = null;
  assignmentId!: number;

  constructor(
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly assignmentService: AssignmentService,
    private readonly dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.assignmentId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadDetail();
  }

  loadDetail(): void {
    this.assignmentService.getDetailById(this.assignmentId).subscribe({
      next: (detail) => this.assignment = detail,
      error: (err) => console.error('Error loading assignment detail:', err)
    });
  }

  isCovered(capability: CareCapability): boolean {
    if (!this.assignment?.caregiver) {
      return false;
    }
    return this.assignment.caregiver.capabilities.includes(capability);
  }

  onClose(): void {
    const dialogRef = this.dialog.open<CloseAssignmentDialogComponent, void, CloseAssignmentPayload | null>(
      CloseAssignmentDialogComponent
    );

    dialogRef.afterClosed().subscribe((payload) => {
      if (payload) {
        this.assignmentService.close(this.assignmentId, payload).subscribe({
          next: () => this.loadDetail(),
          error: (err) => console.error('Error closing assignment:', err)
        });
      }
    });
  }
}
