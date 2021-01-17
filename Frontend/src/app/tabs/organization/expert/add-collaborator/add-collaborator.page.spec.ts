import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { AddCollaboratorPage } from './add-collaborator.page';

describe('AddCollaboratorPage', () => {
  let component: AddCollaboratorPage;
  let fixture: ComponentFixture<AddCollaboratorPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddCollaboratorPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(AddCollaboratorPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
