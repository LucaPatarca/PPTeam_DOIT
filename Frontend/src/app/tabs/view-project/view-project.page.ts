import { ActivatedRoute } from '@angular/router';
import { Component } from '@angular/core';

@Component({
  selector: 'app-view-project',
  templateUrl: './view-project.page.html',
  styleUrls: ['./view-project.page.scss'],
})

export class ViewProjectPage  {
data:String="";
  constructor(
    private route:ActivatedRoute  ) { 
    console.log(this.route.snapshot.params);
  }
}
