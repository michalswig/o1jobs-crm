import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CaregiverAvatarComponent } from './caregiver-avatar.component';

describe('CaregiverAvatarComponent', () => {
  let component: CaregiverAvatarComponent;
  let fixture: ComponentFixture<CaregiverAvatarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CaregiverAvatarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CaregiverAvatarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
