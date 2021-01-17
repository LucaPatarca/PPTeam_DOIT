import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ModifyProjectPage } from './modify-project.page';

describe('ModifyProjectPage', () => {
  let component: ModifyProjectPage;
  let fixture: ComponentFixture<ModifyProjectPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModifyProjectPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ModifyProjectPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
