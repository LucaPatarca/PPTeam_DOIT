import { GlobalsService } from 'src/app/services/globals.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MenuController } from '@ionic/angular';
import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';


@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.page.html',
  styleUrls: ['./create-user.page.scss'],
})
export class CreateUserPage implements OnInit {

  validations_form: FormGroup;
  name:string;
  email:string;
  email_already_exists:string = "";
  constructor(private menuCtrl:MenuController,
    public formBuilder:FormBuilder,
    private http:HttpClient,
    private globals:GlobalsService,
    private router:Router
    ) {
      this.router.routeReuseStrategy.shouldReuseRoute = () => false;
      this.menuCtrl.enable(false);
      this.validations_form = this.formBuilder.group({
       
        name: ['', Validators.required],
        email: ['', Validators.compose([
          Validators.required,
          Validators.pattern('^[A-Za-z0-9._%+-]{3,}@[a-zA-Z]{3,}([.]{1}[a-zA-Z]{2,}|[.]{1}[a-zA-Z]{2,}[.]{1}[a-zA-Z]{2,})')
        ])],
      });
    }
  ngOnInit(): void {
    console.log("ci siamo ");
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
            console.log("ok");
            var newUser = {"name":this.name,mail:this.email};
            this.http.post(this.globals.createUserApiUrl,newUser,{ headers: new HttpHeaders(), responseType: 'json'}).subscribe(
              res => {
                      console.log('Successfully created new User');
                      this.back();
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

    back(){
      this.router.navigate(['/home'], { queryParams: { 'refresh': 1 } });
      this.menuCtrl.enable(true);
    }
}
