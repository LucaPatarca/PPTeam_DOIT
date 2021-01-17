import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ViewSkillPage } from './view-skill.page';

describe('ViewSkillPage', () => {
  let component: ViewSkillPage;
  let fixture: ComponentFixture<ViewSkillPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewSkillPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ViewSkillPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
