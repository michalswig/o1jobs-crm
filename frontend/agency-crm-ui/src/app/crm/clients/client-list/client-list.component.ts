import {AfterViewInit, Component, inject, OnInit, ViewChild} from '@angular/core';
import {Client} from '../../models/client.model';
import {ClientService} from '../../services/client.service';
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

@Component({
  selector: 'app-client-list',
  imports: [MatTableModule, MatSortModule, MatPaginatorModule, MatAnchor, RouterLink, MatButton, MatIconModule, MatCardModule],
  templateUrl: './client-list.component.html',
  styleUrl: './client-list.component.scss'
})
export class ClientListComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['name', 'email', 'city', 'country', 'actions'];
  dataSource = new MatTableDataSource<Client>([]);
  totalElements = 0;
  pageSize = 20;
  pageIndex = 0;

  private readonly dialog = inject(MatDialog);

  @ViewChild(MatSort) sort!: MatSort;

  constructor(private readonly clientService: ClientService) {
  }

  ngOnInit(): void {
    this.loadClients();
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
  }

  onDeactivate(id: number): void {
    const dialogRef = this.dialog.open<ConfirmDialogComponent, ConfirmDialogData, boolean>(
      ConfirmDialogComponent,
      {
        data: {
          title: 'Kunde deaktivieren',
          message: 'Möchten Sie diesen Kunden wirklich deaktivieren? Diese Aktion kann nicht rückgängig gemacht werden.'
        }
      }
    );

    dialogRef.afterClosed().subscribe((confirmed) => {
      if (confirmed) {
        this.clientService.deactivate(id).subscribe({
          next: () => this.loadClients(),
          error: (err) => console.error('Error deactivating client:', err)
        });
      }
    });
  }

  loadClients(): void {
    this.clientService.getAll(this.pageIndex, this.pageSize).subscribe({
      next: (page: Page<Client>) => {
        this.dataSource.data = page.content;
        this.totalElements = page.totalElements;
      },
      error: (err) => console.error('Error fetching Clients:', err)
    });
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadClients();
  }
}
