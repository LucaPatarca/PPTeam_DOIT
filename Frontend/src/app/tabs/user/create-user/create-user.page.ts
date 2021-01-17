import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from 'src/app/services/globals.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MenuController, AlertController, NavController } from '@ionic/angular';
import { Component } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';



@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.page.html',
  styleUrls: ['./create-user.page.scss'],
})
export class CreateUserPage  {

  validations_form: FormGroup;
  name:string;
  email:string;
  email_already_exists:string = "";
  constructor(private menuCtrl:MenuController,
    public formBuilder:FormBuilder,
    private http:HttpClient,
    private globals:GlobalsService,
    private nav:NavController,
    private alertCtrl:AlertController,
    private dataService:DataService
    ) {
      this.menuCtrl.enable(false);
      this.validations_form = this.formBuilder.group({
       
        name: ['', Validators.required],
        email: ['', Validators.compose([
          Validators.required,
          Validators.pattern('^[A-Za-z0-9._%+-]{3,}@[a-zA-Z]{3,}([.]{1}[a-zA-Z]{2,}|[.]{1}[a-zA-Z]{2,}[.]{1}[a-zA-Z]{2,})')
        ])],
      });
    }

    validation_messages = {
      'name': [
        { type: 'required', message: 'Name is required.' }
      ],
      'email': [
        { type: 'required', message: 'Email is required.' },
        { type: 'pattern', message: 'Please enter a valid email.' }
      ],
    };
  
    onSubmit(){
      this.http.post(this.globals.userExistApiUrl,this.email).subscribe(
        res => {
          if(res==false){
            var newUser = {"name":this.name,mail:this.email};
            this.http.post(this.globals.createUserApiUrl,newUser,{ headers: new HttpHeaders(), responseType: 'json'}).subscribe(
              async res => {
                      console.log('Successfully created new User');
                      this.onBack();
                      this.dataService.setUser(this.email)
                      const alert = await this.alertCtrl.create({
                        cssClass: 'my-custom-class',
                        header: 'Perfetto',
                        message: 'LogIn eseguito.',
                        buttons: ['OK']
                      });
                    
                      await alert.present();
                     }, 
              err => { 
                      console.log('oops some error in User'); 
          }
        );
        }else{
            this.email_already_exists = this.email+" : email already exists";
        }
        }, 
        err => { 
          console.log(err);
          console.log('oops some error in User'); 
        }
      );
    }

    onBack(){
      this.nav.navigateBack(['/home'], { queryParams: { 'refresh': 1 } });
      this.menuCtrl.enable(true);
    }
}
