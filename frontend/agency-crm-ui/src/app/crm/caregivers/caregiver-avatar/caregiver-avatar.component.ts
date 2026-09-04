import { Component, Input, OnChanges, OnDestroy, SimpleChanges } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { CaregiverService } from '../../services/caregiver.service';

@Component({
  selector: 'app-caregiver-avatar',
  standalone: true,
  imports: [MatIconModule],
  templateUrl: './caregiver-avatar.component.html',
  styleUrl: './caregiver-avatar.component.scss'
})
export class CaregiverAvatarComponent implements OnChanges, OnDestroy {

  @Input({ required: true }) caregiverId!: number;
  @Input() hasPhoto = false;
  @Input() size = 40;

  photoUrl: string | null = null;

  constructor(private readonly caregiverService: CaregiverService) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['caregiverId'] || changes['hasPhoto']) {
      this.revokeUrl();
      if (this.hasPhoto && this.caregiverId) {
        this.caregiverService.getPhoto(this.caregiverId).subscribe({
          next: (blob) => this.photoUrl = URL.createObjectURL(blob),
          error: () => this.photoUrl = null
        });
      } else {
        this.photoUrl = null;
      }
    }
  }

  ngOnDestroy(): void {
    this.revokeUrl();
  }

  private revokeUrl(): void {
    if (this.photoUrl) {
      URL.revokeObjectURL(this.photoUrl);
      this.photoUrl = null;
    }
  }
}
