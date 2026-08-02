import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CareRecipientListComponent } from './care-recipient-list.component';

describe('CareRecipientListComponent', () => {
  let component: CareRecipientListComponent;
  let fixture: ComponentFixture<CareRecipientListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CareRecipientListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CareRecipientListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
