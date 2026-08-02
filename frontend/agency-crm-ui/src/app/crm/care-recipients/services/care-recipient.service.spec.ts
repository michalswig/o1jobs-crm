import { TestBed } from '@angular/core/testing';

import { CareRecipientService } from './care-recipient.service';

describe('CareRecipientService', () => {
  let service: CareRecipientService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CareRecipientService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
