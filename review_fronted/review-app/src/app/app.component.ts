import { Component } from '@angular/core';
import { Router, ActivatedRoute, Params, Event, NavigationStart } from '@angular/router';

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
  constructor(private route: ActivatedRoute, private router: Router){

  }
  ngOnInit() {
  	// this.currentPage = this.router.location.path();
  	console.log(this)
	this.router.events.subscribe((event:Event) => {
	    if(event instanceof NavigationStart) {
	    	console.log(event)
	    	this.currentPage = event.url.replace("/", "");
        this.noHeader = event.url === "/login" || event.url === "/register";
	    }
	});
  }
}
