import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CareRecipientFormComponent } from './care-recipient-form.component';

describe('CareRecipientFormComponent', () => {
  let component: CareRecipientFormComponent;
  let fixture: ComponentFixture<CareRecipientFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CareRecipientFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CareRecipientFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
