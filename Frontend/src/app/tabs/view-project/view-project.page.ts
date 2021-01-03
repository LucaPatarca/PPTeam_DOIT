import { ActivatedRoute } from '@angular/router';
import { Component } from '@angular/core';
import { MenuController } from '@ionic/angular';
import{Router} from "@angular/router";
import { HttpClient,HttpHeaders } from '@angular/common/http';


@Component({
  selector: 'app-view-project',
  templateUrl: './view-project.page.html',
  styleUrls: ['./view-project.page.scss'],
})

export class ViewProjectPage  {
data:any;
isClosed;
  constructor(
    private route:ActivatedRoute , private menuCtrl:MenuController, public router:Router, private http:HttpClient ) { 
    this.data = this.route.snapshot.params;
    if(this.data.isClosed==true){
      this.isClosed="true";
    }else{
      this.isClosed="false";
    }
    this.menuCtrl.enable(false);

    console.log(this.isClosed);

  }

  onClick(){
    this.menuCtrl.enable(true);
  }

  modify(data:String){
    this.router.navigate(['/modify-project', data]);
    this.onClick();
  }

  close(data:any){

    this.http.put("http://localhost:8080/api/projects/close/" + this.data.organizationName + "." + this.data.name , null)
    .subscribe(
      res => {
        console.log('Close successful Project with Id: ' + this.data.organizationName + '.' + this.data.name, res);	
      }, 
      err => { 
        console.log('There was an error!', err); 
      }
    );

    this.router.navigate(['/view-project', data]);
    this.onClick();
  }

  delete(data:any){

    this.http.delete("http://localhost:8080/api/projects/delete/" + this.data.organizationName + "." + this.data.name)
    .subscribe(
      res => {
        console.log('Delete successful Project with Id: ' + this.data.organizationName + '.' + this.data.name, res);	
      }, 
      err => { 
        console.log('There was an error!', err); 
      }
    );

    this.router.navigate(['/list-of-projects']);
    this.onClick();
  }

}


