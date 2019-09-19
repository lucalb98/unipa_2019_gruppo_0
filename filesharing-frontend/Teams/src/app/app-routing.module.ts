import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './views/dashboard/dashboard.component';
import {RegistrationComponent} from "./views/registration/registration.component";
import {LoginComponent} from "./views/login/login.component";
import {TeamsComponent} from "./views/teams/teams.component";
import {BucketsComponent} from "./views/buckets/buckets.component";
import {BucketDetailComponent} from "./views/bucket-detail/bucket-detail.component";
import {FoldersComponent} from "./views/folders/folders.component";
import {AppAuthGuard} from "./keycloak/AuthGuard";
import {OtpDetailComponent} from "./views/otp-detail/otp-detail.component";
import {UrlDetailComponent} from "./views/url-detail/url-detail.component";


const routes: Routes = [
  {
    path:'url/:token',
    component:UrlDetailComponent

  },

  {
    path:'otp/:token/:email',
    component:OtpDetailComponent
  },
  {
    path:'login',
    component: LoginComponent
  },
  {
    path:'registration',
    component: RegistrationComponent
  },
  {
    path:'',
    component: DashboardComponent,
    canActivate:[AppAuthGuard],
    children:[
      {
        path:'',
        component:TeamsComponent,
        canActivate:[AppAuthGuard]
      },
      {
        path:':team',
        component:BucketsComponent,
        canActivate:[AppAuthGuard]
      },
      {
        path:':team/:bucket',
        children:[
          {
            path:'**',
            component:BucketDetailComponent,
            canActivate:[AppAuthGuard]
          },
        ]
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
