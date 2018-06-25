import { Component } from '@angular/core';
import { Router, ActivatedRoute, Params, Event, NavigationStart } from '@angular/router';
import {LoginService} from './service/login.service';

@Component({
  selector: 'body',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less'],
  host:     {'[class.no-header]':'noHeader'}
})
export class AppComponent {
  title = 'app';
  private currentPage;
  private noHeader: boolean = false;
  constructor(private route: ActivatedRoute, private router: Router, private app: LoginService){
    //this.app.authenticate(undefined, undefined);
  }
  ngOnInit() {
  	// this.currentPage = this.router.location.path();
	this.router.events.subscribe((event:Event) => {
	    if(event instanceof NavigationStart) {
	    	this.currentPage = event.url.replace("/", "");
        this.noHeader = event.url === "/login" || event.url === "/register";
	    }
	});
  }
}
