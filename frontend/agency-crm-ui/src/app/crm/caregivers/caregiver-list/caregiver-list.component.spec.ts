import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CaregiverListComponent } from './caregiver-list.component';

describe('CaregiverListComponent', () => {
  let component: CaregiverListComponent;
  let fixture: ComponentFixture<CaregiverListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CaregiverListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CaregiverListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
