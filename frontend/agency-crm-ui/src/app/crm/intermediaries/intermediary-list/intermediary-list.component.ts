import {AfterViewInit, Component, inject, OnInit, ViewChild} from '@angular/core';
import {IntermediaryService} from '../services/intermediary.service';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatPaginatorModule, PageEvent} from '@angular/material/paginator';
import {MatAnchor, MatButton} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatCardModule} from '@angular/material/card';
import {RouterLink} from '@angular/router';
import {MatDialog} from '@angular/material/dialog';
import {ConfirmDialogComponent, ConfirmDialogData} from '../../../core/dialogs/confirm-dialog/confirm-dialog.component';
import {Page} from '../../../shared/models/page.model';
import {EnumLabelPipe} from '../../../shared/pipes/enum-label.pipe';
import {Intermediary} from '../models/intermediary.interface';

@Component({
  selector: 'app-intermediary-list',
  standalone: true,
  imports: [MatTableModule, MatSortModule, MatPaginatorModule, MatAnchor, RouterLink, MatButton, MatIconModule, MatCardModule, EnumLabelPipe],
  templateUrl: './intermediary-list.component.html',
  styleUrl: './intermediary-list.component.scss'
})
export class IntermediaryListComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['name', 'intermediaryType', 'email', 'phone', 'actions'];
  dataSource = new MatTableDataSource<Intermediary>([]);
  totalElements = 0;
  pageSize = 20;
  pageIndex = 0;

  private readonly dialog = inject(MatDialog);

  @ViewChild(MatSort) sort!: MatSort;

  constructor(private readonly intermediaryService: IntermediaryService) {
  }

  ngOnInit(): void {
    this.loadIntermediaries();
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
  }

  onDeactivate(id: number): void {
    const dialogRef = this.dialog.open<ConfirmDialogComponent, ConfirmDialogData, boolean>(
      ConfirmDialogComponent,
      {
        data: {
          title: 'Partner deaktivieren',
          message: 'Möchten Sie diesen Partner wirklich deaktivieren? Diese Aktion kann nicht rückgängig gemacht werden.'
        }
      }
    );

    dialogRef.afterClosed().subscribe((confirmed) => {
      if (confirmed) {
        this.intermediaryService.deactivate(id).subscribe({
          next: () => this.loadIntermediaries(),
          error: (err) => console.error('Error deactivating intermediary:', err)
        });
      }
    });
  }

  loadIntermediaries(): void {
    this.intermediaryService.getAll(this.pageIndex, this.pageSize).subscribe({
      next: (page: Page<Intermediary>) => {
        this.dataSource.data = page.content;
        this.totalElements = page.totalElements;
      },
      error: (err) => console.error('Error fetching intermediaries:', err)
    });
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadIntermediaries();
  }
}
