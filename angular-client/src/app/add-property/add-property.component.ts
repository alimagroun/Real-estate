import { Component, OnInit } from '@angular/core';
import { UntypedFormControl, UntypedFormGroup } from '@angular/forms';

import { DashboardChartsData, IChartProps } from './dashboard-charts-data';

import {AuthService} from '../_services/auth.service';
import {PropertyService} from '../_services/property.service';
import {StateService} from '../_services/state.service';
import {CityService} from '../_services/city.service';
import {User} from '../_models/user';
import {Property} from '../_models/property';
import {State} from '../_models/state';
import {City} from '../_models/city';

import { Observable } from 'rxjs';
interface IUser {
  name: string;
  state: string;
  registered: string;
  country: string;
  usage: number;
  period: string;
  payment: string;
  activity: string;
  avatar: string;
  status: string;
  color: string;
}

@Component({
  selector: 'app-add-property',
  templateUrl: './add-property.component.html',
  styleUrls: ['./add-property.component.scss']
})
export class AddPropertyComponent implements OnInit {

  form: any = {
    bathrooms: null,
    bedrooms: null,
    description: null,
    name: null,
    size: null,
    status: null,
    price: null
  };
  states?: State[];
  cities?: City[];
property : Property = new Property();


  users1?: User[];
  currentTutorial: User = {};
  currentIndex = -1;
  title = '';
  properties?: Property[];

  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';



  constructor(private chartsData: DashboardChartsData, private authService: AuthService, private propertyService: PropertyService,private stateService : StateService,private cityService :CityService) {
  }



  onStateChange(event: any) {
    // Retrieve selected state
   
    const selectedStateId = event.target.value;

      // Check if selectedStateId is not undefined
  if (selectedStateId !== undefined) {
  
    // Call the getCitiesByState() method from your CityService to get cities based on stateId
    this.cityService.getCitiesByState(selectedStateId)
      .subscribe(cities => {
        this.cities = cities; // Update the cities array with retrieved cities
      });
  }
  }

 
  onSubmit(): void {
 

    this.propertyService.createProperty(this.property).subscribe({
      next: data => {
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      },
      error: err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    });
    this.property = new Property();
  }

  public users: IUser[] = [
    {
      name: 'Yiorgos Avraamu',
      state: 'New',
      registered: 'Jan 1, 2021',
      country: 'Us',
      usage: 50,
      period: 'Jun 11, 2021 - Jul 10, 2021',
      payment: 'Mastercard',
      activity: '10 sec ago',
      avatar: './assets/img/avatars/1.jpg',
      status: 'success',
      color: 'success'
    },
    {
      name: 'Avram Tarasios',
      state: 'Recurring ',
      registered: 'Jan 1, 2021',
      country: 'Br',
      usage: 10,
      period: 'Jun 11, 2021 - Jul 10, 2021',
      payment: 'Visa',
      activity: '5 minutes ago',
      avatar: './assets/img/avatars/2.jpg',
      status: 'danger',
      color: 'info'
    },
    {
      name: 'Quintin Ed',
      state: 'New',
      registered: 'Jan 1, 2021',
      country: 'In',
      usage: 74,
      period: 'Jun 11, 2021 - Jul 10, 2021',
      payment: 'Stripe',
      activity: '1 hour ago',
      avatar: './assets/img/avatars/3.jpg',
      status: 'warning',
      color: 'warning'
    },
    {
      name: 'Enéas Kwadwo',
      state: 'Sleep',
      registered: 'Jan 1, 2021',
      country: 'Fr',
      usage: 98,
      period: 'Jun 11, 2021 - Jul 10, 2021',
      payment: 'Paypal',
      activity: 'Last month',
      avatar: './assets/img/avatars/4.jpg',
      status: 'secondary',
      color: 'danger'
    },
    {
      name: 'Agapetus Tadeáš',
      state: 'New',
      registered: 'Jan 1, 2021',
      country: 'Es',
      usage: 22,
      period: 'Jun 11, 2021 - Jul 10, 2021',
      payment: 'ApplePay',
      activity: 'Last week',
      avatar: './assets/img/avatars/5.jpg',
      status: 'success',
      color: 'primary'
    },
    {
      name: 'Friderik Dávid',
      state: 'New',
      registered: 'Jan 1, 2021',
      country: 'Pl',
      usage: 43,
      period: 'Jun 11, 2021 - Jul 10, 2021',
      payment: 'Amex',
      activity: 'Yesterday',
      avatar: './assets/img/avatars/6.jpg',
      status: 'info',
      color: 'dark'
    }
  ];
  public mainChart: IChartProps = {};
  public chart: Array<IChartProps> = [];
  public trafficRadioGroup = new UntypedFormGroup({
    trafficRadio: new UntypedFormControl('Month')
  });

  ngOnInit(): void {
    this.initCharts();
    this.retrieveUsers();
    this.retrieveProperties();

    this.stateService.getStates().subscribe(
      (states: State[]) => {
        this.states = states; // Store the emitted states in the local variable
      },
      (error: any) => {
        console.error(error); // Handle error if necessary
      }
    );





  }
  retrieveUsers():void{
    this.authService.getAll()
    .subscribe({
      next: (data) => {
        this.users1 = data;
        console.log(data);
      },
      error: (e) => console.error(e)
    });
  }

  retrieveProperties():void{
    this.propertyService.getAll()
    .subscribe({
      next:(data) =>{
        this.properties=data;
        console.log(data);
      },
      error: (e)=> console.error(e)
    })
  }










  initCharts(): void {
    this.mainChart = this.chartsData.mainChart;
  }

  setTrafficPeriod(value: string): void {
    this.trafficRadioGroup.setValue({ trafficRadio: value });
    this.chartsData.initMainChart(value);
    this.initCharts();
  }


}
