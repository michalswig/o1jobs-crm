import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CaregiverFormComponent } from './caregiver-form.component';

describe('CaregiverFormComponent', () => {
  let component: CaregiverFormComponent;
  let fixture: ComponentFixture<CaregiverFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CaregiverFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CaregiverFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
