import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Page } from '../../../shared/models/page.model';
import { AssignmentService, CloseAssignmentPayload } from '../services/assignment.service';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatSortModule, MatSort } from '@angular/material/sort';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { CloseAssignmentDialogComponent } from '../../../core/dialogs/close-assignment-dialog/close-assignment-dialog.component';
import { EnumLabelPipe } from '../../../shared/pipes/enum-label.pipe';
import {Assignment} from '../models/assignment';

@Component({
  selector: 'app-assignment-list',
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
  templateUrl: './assignment-list.component.html',
  styleUrl: './assignment-list.component.scss'
})
export class AssignmentListComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['city', 'startDate', 'status', 'salaryMonthlyNet', 'actions'];
  dataSource = new MatTableDataSource<Assignment>([]);
  totalElements = 0;
  pageSize = 20;
  pageIndex = 0;
  filterClientId: number | null = null;

  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private readonly assignmentService: AssignmentService,
    private readonly dialog: MatDialog,
    private readonly route: ActivatedRoute,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.route.queryParamMap.subscribe(params => {
      const clientIdParam = params.get('clientId');
      this.filterClientId = clientIdParam ? Number(clientIdParam) : null;
      this.pageIndex = 0;
      this.loadAssignments();
    });
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
  }

  loadAssignments(): void {
    this.assignmentService.getAll(this.pageIndex, this.pageSize, this.filterClientId ?? undefined).subscribe({
      next: (page: Page<Assignment>) => {
        this.dataSource.data = page.content;
        this.totalElements = page.totalElements;
      },
      error: (err) => console.error('Error fetching assignments:', err)
    });
  }

  clearFilter(): void {
    this.router.navigate(['/assignments']);
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadAssignments();
  }

  onClose(id: number): void {
    const dialogRef = this.dialog.open<CloseAssignmentDialogComponent, void, CloseAssignmentPayload | null>(
      CloseAssignmentDialogComponent
    );

    dialogRef.afterClosed().subscribe((payload) => {
      if (payload) {
        this.assignmentService.close(id, payload).subscribe({
          next: () => this.loadAssignments(),
          error: (err) => console.error('Error closing assignment:', err)
        });
      }
    });
  }
}
