import {ComponentFixture, TestBed} from '@angular/core/testing';

import {InlineAppointmentComponent} from './inline-appointment.component';

describe('SaveExportComponent', () => {
  let component: InlineAppointmentComponent;
  let fixture: ComponentFixture<InlineAppointmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [InlineAppointmentComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InlineAppointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
