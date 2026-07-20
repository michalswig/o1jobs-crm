import {Component, OnInit} from '@angular/core';
import {Client, Page} from '../../models/client.model';
import {ClientService} from '../../services/client.service';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-client-list',
  imports: [CommonModule],
  templateUrl: './client-list.component.html',
  styleUrl: './client-list.component.scss'
})
export class ClientListComponent implements OnInit {
  clients: Client[] = [];
  totalElements = 0;

  constructor(private clientService: ClientService) {
  }

  ngOnInit(): void {
    this.clientService.getAll().subscribe({
      next: (page: Page<Client>) => {
        this.clients = page.content;
        this.totalElements = page.totalElements;
      },
      error: (err) => console.error('Error fetching Clients:', err)
    });
  }

}
