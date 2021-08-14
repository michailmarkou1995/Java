import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SaveExportComponent} from './save-export.component';

describe('SaveExportComponent', () => {
  let component: SaveExportComponent;
  let fixture: ComponentFixture<SaveExportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SaveExportComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SaveExportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
