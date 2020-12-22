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
isClosed;
  constructor(
    private route:ActivatedRoute , private menuCtrl:MenuController ) { 
    this.data = this.route.snapshot.params;
    if(this.data.isClosed==true){
      this.isClosed="true";
    }else{
      this.isClosed="false";
    }
    this.menuCtrl.enable(false);
  }

  onClick(){
    this.menuCtrl.enable(true);
  }

}


