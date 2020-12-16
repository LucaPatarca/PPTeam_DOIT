import { ActivatedRoute } from '@angular/router';
import { Component } from '@angular/core';
import { MenuController } from '@ionic/angular';


@Component({
  selector: 'app-view-project',
  templateUrl: './view-project.page.html',
  styleUrls: ['./view-project.page.scss'],
})

export class ViewProjectPage  {
data:any;
  constructor(
    private route:ActivatedRoute , private menuCtrl:MenuController ) { 
    this.data = this.route.snapshot.params;
    this.menuCtrl.enable(false);
  }
}
