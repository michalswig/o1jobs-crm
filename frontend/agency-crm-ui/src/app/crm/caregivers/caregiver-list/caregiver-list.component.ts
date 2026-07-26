import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Page } from '../../../shared/models/page.model';
import { CaregiverService } from '../../services/caregiver.service';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatSortModule, MatSort } from '@angular/material/sort';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ConfirmDialogComponent, ConfirmDialogData } from '../../../core/dialogs/confirm-dialog/confirm-dialog.component';
import {Caregiver} from '../models/caregiver.model';

@Component({
  selector: 'app-caregiver-list',
  standalone: true,
  imports: [
    RouterLink,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    MatButtonModule,
    MatDialogModule
  ],
  templateUrl: './caregiver-list.component.html',
  styleUrl: './caregiver-list.component.scss'
})
export class CaregiverListComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['lastName', 'firstName', 'phone', 'nationality', 'actions'];
  dataSource = new MatTableDataSource<Caregiver>([]);
  totalElements = 0;
  pageSize = 20;
  pageIndex = 0;

  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private readonly caregiverService: CaregiverService,
    private readonly dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadCaregivers();
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
  }

  loadCaregivers(): void {
    this.caregiverService.getAll(this.pageIndex, this.pageSize).subscribe({
      next: (page: Page<Caregiver>) => {
        this.dataSource.data = page.content;
        this.totalElements = page.totalElements;
      },
      error: (err) => console.error('Error fetching caregivers:', err)
    });
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadCaregivers();
  }

  onDeactivate(id: number): void {
    const dialogRef = this.dialog.open<ConfirmDialogComponent, ConfirmDialogData, boolean>(
      ConfirmDialogComponent,
      {
        data: {
          title: 'Dezaktywacja opiekunki',
          message: 'Czy na pewno chcesz dezaktywować tę opiekunkę? Tej operacji nie można cofnąć.'
        }
      }
    );

    dialogRef.afterClosed().subscribe((confirmed) => {
      if (confirmed) {
        this.caregiverService.deactivate(id).subscribe({
          next: () => this.loadCaregivers(),
          error: (err) => console.error('Error deactivating caregiver:', err)
        });
      }
    });
  }
}
