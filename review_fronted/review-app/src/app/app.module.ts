import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule }   from '@angular/forms';

import { AppComponent } from './app.component';
import { HeaderComponent } from './components/shared/header/header.component';
import { FooterComponent } from './components/shared/footer/footer.component';
import { RegisterComponent } from './components/pages/register/register.component';
//import { appRoutes } from './app.route';
import { LoginComponent } from './components/pages/login/login.component';
import { HomeComponent } from './components/pages/home/home.component';
import { HttpClientModule }    from '@angular/common/http';

const appRoutes: Routes = [
  { path: 'register', component:  RegisterComponent},
  { path: 'login', component:  LoginComponent},
  { path: 'home', component:  HomeComponent},
  // { path: 'hero/:id',      component: HeroDetailComponent },
  // {
  //   path: 'heroes',
  //   component: HeroListComponent,
  //   data: { title: 'Heroes List' }
  // },
  // { path: '',
  //   redirectTo: '/heroes',
  //   pathMatch: 'full'
  // },
  // { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    RegisterComponent,
    LoginComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes, { useHash: false }),
    FormsModule,
    HttpClientModule      
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
