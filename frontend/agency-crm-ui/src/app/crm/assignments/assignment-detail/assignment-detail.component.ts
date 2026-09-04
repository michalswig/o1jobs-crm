import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { DatePipe, DecimalPipe } from '@angular/common';
import { EnumLabelPipe } from '../../../shared/pipes/enum-label.pipe';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { HttpErrorResponse } from '@angular/common/http';
import { AssignmentService, CloseAssignmentPayload } from '../services/assignment.service';
import { CareCapability } from '../../../shared/models/domain-enums.model';
import { CloseAssignmentDialogComponent } from '../../../core/dialogs/close-assignment-dialog/close-assignment-dialog.component';
import {AssignmentDetail} from '../models/assignment-detail';
import {AssignmentDocument} from '../models/assignment-document';
import { CaregiverAvatarComponent } from '../../caregivers/caregiver-avatar/caregiver-avatar.component';

@Component({
  selector: 'app-assignment-detail',
  standalone: true,
  imports: [RouterLink, MatButtonModule, MatIconModule, MatCardModule, MatDialogModule, DatePipe, DecimalPipe, EnumLabelPipe, CaregiverAvatarComponent],
  templateUrl: './assignment-detail.component.html',
  styleUrl: './assignment-detail.component.scss'
})
export class AssignmentDetailComponent implements OnInit {

  assignment: AssignmentDetail | null = null;
  assignmentId!: number;

  assignmentDocument: AssignmentDocument | null = null;
  selectedFile: File | null = null;
  uploadError: string | null = null;
  uploading = false;

  constructor(
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly assignmentService: AssignmentService,
    private readonly dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.assignmentId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadDetail();
    this.loadDocument();
  }

  loadDetail(): void {
    this.assignmentService.getDetailById(this.assignmentId).subscribe({
      next: (detail) => this.assignment = detail,
      error: (err) => console.error('Error loading assignment detail:', err)
    });
  }

  loadDocument(): void {
    this.assignmentService.getDocumentMetadata(this.assignmentId).subscribe({
      next: (doc) => this.assignmentDocument = doc,
      error: (err: HttpErrorResponse) => {
        if (err.status === 404) {
          // brak umowy - stan normalny, nie błąd
          this.assignmentDocument = null;
        } else {
          console.error('Error loading document metadata:', err);
        }
      }
    });
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.selectedFile = input.files?.length ? input.files[0] : null;
    this.uploadError = null;
  }

  onUpload(): void {
    if (!this.selectedFile) {
      return;
    }
    if (this.selectedFile.type !== 'application/pdf') {
      this.uploadError = 'Es sind nur PDF-Dateien zulässig.';
      return;
    }

    this.uploading = true;
    this.assignmentService.uploadDocument(this.assignmentId, this.selectedFile).subscribe({
      next: (doc) => {
        this.assignmentDocument = doc;
        this.selectedFile = null;
        this.uploading = false;
      },
      error: (err: HttpErrorResponse) => {
        this.uploadError = err.status === 400
          ? 'Die hochgeladene Datei ist ungültig.'
          : 'Datei konnte nicht hochgeladen werden. Bitte versuchen Sie es erneut.';
        this.uploading = false;
      }
    });
  }

  onDownload(): void {
    this.assignmentService.downloadDocument(this.assignmentId).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const anchor = document.createElement('a');
        anchor.href = url;
        anchor.download = this.assignmentDocument?.fileName ?? 'umowa.pdf';
        anchor.click();
        window.URL.revokeObjectURL(url);
      },
      error: (err) => console.error('Error downloading document:', err)
    });
  }

  onDeleteDocument(): void {
    if (!confirm('Möchten Sie den Vertrag wirklich löschen?')) {
      return;
    }
    this.assignmentService.deleteDocument(this.assignmentId).subscribe({
      next: () => this.assignmentDocument = null,
      error: (err) => console.error('Error deleting document:', err)
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
