import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { Client, Page } from '../../models/client.model';
import { ClientService } from '../../services/client.service';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatSortModule, MatSort } from '@angular/material/sort';
import { MatPaginatorModule, MatPaginator, PageEvent } from '@angular/material/paginator';
import {MatAnchor} from '@angular/material/button';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-client-list',
  imports: [MatTableModule, MatSortModule, MatPaginatorModule, MatAnchor, RouterLink],
  templateUrl: './client-list.component.html',
  styleUrl: './client-list.component.scss'
})
export class ClientListComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['name', 'email', 'city', 'country', 'actions'];
  dataSource = new MatTableDataSource<Client>([]);
  totalElements = 0;
  pageSize = 20;
  pageIndex = 0;

  @ViewChild(MatSort) sort!: MatSort;

  constructor(private readonly clientService: ClientService) {
  }

  ngOnInit(): void {
    this.loadClients();
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
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
