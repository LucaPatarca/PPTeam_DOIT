import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { UserSubmissionPage } from './user-submission.page';

describe('UserSubmissionPage', () => {
  let component: UserSubmissionPage;
  let fixture: ComponentFixture<UserSubmissionPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserSubmissionPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(UserSubmissionPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
