import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { AddExpertPage } from './add-expert.page';

describe('AddExpertPage', () => {
  let component: AddExpertPage;
  let fixture: ComponentFixture<AddExpertPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddExpertPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(AddExpertPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
