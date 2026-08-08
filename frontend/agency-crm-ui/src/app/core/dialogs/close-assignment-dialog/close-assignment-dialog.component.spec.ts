import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CloseAssignmentDialogComponent } from './close-assignment-dialog.component';

describe('CloseAssignmentDialogComponent', () => {
  let component: CloseAssignmentDialogComponent;
  let fixture: ComponentFixture<CloseAssignmentDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CloseAssignmentDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CloseAssignmentDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
