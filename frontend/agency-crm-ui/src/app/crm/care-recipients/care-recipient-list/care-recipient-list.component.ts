import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Page } from '../../../shared/models/page.model';
import { CareRecipientService } from '../services/care-recipient.service';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatSortModule, MatSort } from '@angular/material/sort';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ConfirmDialogComponent, ConfirmDialogData } from '../../../core/dialogs/confirm-dialog/confirm-dialog.component';
import { EnumLabelPipe } from '../../../shared/pipes/enum-label.pipe';
import {CareRecipient} from '../models/care-recipient';

@Component({
  selector: 'app-care-recipient-list',
  standalone: true,
  imports: [
    RouterLink,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatDialogModule,
    EnumLabelPipe
  ],
  templateUrl: './care-recipient-list.component.html',
  styleUrl: './care-recipient-list.component.scss'
})
export class CareRecipientListComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['lastName', 'firstName', 'mobilityLevel', 'dementiaLevel', 'actions'];
  dataSource = new MatTableDataSource<CareRecipient>([]);
  totalElements = 0;
  pageSize = 20;
  pageIndex = 0;

  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private readonly careRecipientService: CareRecipientService,
    private readonly dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadCareRecipients();
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
  }

  loadCareRecipients(): void {
    this.careRecipientService.getAll(this.pageIndex, this.pageSize).subscribe({
      next: (page: Page<CareRecipient>) => {
        this.dataSource.data = page.content;
        this.totalElements = page.totalElements;
      },
      error: (err) => console.error('Error fetching care recipients:', err)
    });
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadCareRecipients();
  }

  onDeactivate(id: number): void {
    const dialogRef = this.dialog.open<ConfirmDialogComponent, ConfirmDialogData, boolean>(
      ConfirmDialogComponent,
      {
        data: {
          title: 'Pflegebedürftigen deaktivieren',
          message: 'Möchten Sie diesen Pflegebedürftigen wirklich deaktivieren? Diese Aktion kann nicht rückgängig gemacht werden.'
        }
      }
    );

    dialogRef.afterClosed().subscribe((confirmed) => {
      if (confirmed) {
        this.careRecipientService.deactivate(id).subscribe({
          next: () => this.loadCareRecipients(),
          error: (err) => console.error('Error deactivating care recipient:', err)
        });
      }
    });
  }
}
