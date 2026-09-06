import { Component, OnInit } from '@angular/core';
import { DecimalPipe, DatePipe } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { DashboardData } from './models/dashboard-data';
import { EnumLabelPipe } from '../shared/pipes/enum-label.pipe';
import {DashboardService} from './services/dashboard.service';

interface BarItem {
  label: string;
  value: number;
  percent: number;
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [MatCardModule, MatIconModule, DecimalPipe, DatePipe, EnumLabelPipe],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {

  data: DashboardData | null = null;
  loading = true;
  error = false;

  statusGradient = '';
  openPercent = 0;

  closeReasonBars: BarItem[] = [];
  nationalityBars: BarItem[] = [];
  dementiaBars: BarItem[] = [];

  maxTrendCount = 1;

  constructor(private readonly dashboardService: DashboardService) {
  }

  ngOnInit(): void {
    this.dashboardService.getDashboard().subscribe({
      next: (data) => {
        this.data = data;
        this.buildCharts(data);
        this.loading = false;
      },
      error: (err) => {
        console.error('Error loading dashboard:', err);
        this.loading = false;
        this.error = true;
      }
    });
  }

  private buildCharts(data: DashboardData): void {
    const open = data.statusBreakdown['OPEN'] ?? 0;
    const closed = data.statusBreakdown['CLOSED'] ?? 0;
    const total = open + closed;
    this.openPercent = total > 0 ? Math.round((open / total) * 100) : 0;
    this.statusGradient = `conic-gradient(var(--o1jobs-teal) 0% ${this.openPercent}%, #e0e0dc ${this.openPercent}% 100%)`;

    this.closeReasonBars = this.toBars(data.closeReasonBreakdown);
    this.nationalityBars = this.toBars(data.nationalityBreakdown);
    this.dementiaBars = this.toBars(data.dementiaExperienceBreakdown);

    this.maxTrendCount = Math.max(1, ...data.monthlyTrend.map(m => m.count));
  }

  private toBars(breakdown: Record<string, number>): BarItem[] {
    const entries = Object.entries(breakdown);
    const max = Math.max(1, ...entries.map(([, v]) => v));
    return entries
      .map(([label, value]) => ({ label, value, percent: Math.round((value / max) * 100) }))
      .sort((a, b) => b.value - a.value);
  }

  monthLabel(month: string): string {
    const monthNames = ['Jan', 'Feb', 'Mär', 'Apr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Okt', 'Nov', 'Dez'];
    const parts = month.split('-');
    const monthIndex = Number(parts[1]) - 1;
    return monthNames[monthIndex] ?? month;
  }

  barHeightPercent(count: number): number {
    return Math.round((count / this.maxTrendCount) * 100);
  }

  get assignedCaregivers() {
    return this.data?.caregiverAvailability.filter(c => c.assigned) ?? [];
  }

  get idleCaregivers() {
    return this.data?.caregiverAvailability.filter(c => !c.assigned) ?? [];
  }
}
