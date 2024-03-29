import { Component } from '@angular/core';
import { Router,Params } from '@angular/router';

import {PropertyService} from '../_services/property.service';
import { StateService } from '../_services/state.service';
import { CityService } from '../_services/city.service';
import {Property} from '../_models/property';
import {State} from '../_models/state';
import {City} from '../_models/city';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  states: State[] = [];
  cities: City[] = [];
  properties: Property[] = [];
  stateSelected = false;
  status :string | undefined;
  stateId: number | undefined;
  cityId: number | undefined;
  constructor(private propertyService: PropertyService,private stateService: StateService,private cityService: CityService, private router: Router){}
  
  ngOnInit() {
    this.stateService.getStates().subscribe((data: State[]) => {
      this.states = data;
    });
    this.getLast4Properties();
  }

  onStateChange(event: any) {
    this.stateId = event.value;
    if (this.stateId !== undefined) {
      this.cityService.getCitiesByState(this.stateId)
        .subscribe(cities => {
          this.cities = cities;
          this.stateSelected = true;
        });
    }
    this.cityId = undefined;
  }

  getLast4Properties(): void {
    this.propertyService.getLast8Properties()
      .subscribe(properties => {
        this.properties = properties;
        this.properties.forEach(property => {
          this.propertyService.getFirstPhotoByPropertyId(property.id)
            .subscribe(photo => property.photos = [photo]);
        });
      });
  }

  applyFilter() {
    const queryParams: Params = {
      status: this.status,
      stateId: this.stateId,
      cityId: this.cityId
    };
  
    this.router.navigate(['/propertysearch'], { queryParams });
  }
  
  viewPropertyDetails(propertyId: number): void {
    this.router.navigate(['/property', propertyId]);
  }
}
