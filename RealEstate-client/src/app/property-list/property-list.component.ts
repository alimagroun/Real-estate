import { Component, OnInit ,ViewChild } from '@angular/core';
import { Observable } from 'rxjs';
import{Property} from '../model/property';
import { PropertyService } from "../service/property.service";
import { Router } from '@angular/router';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from '@angular/material/sort';

@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.css']
})
export class PropertyListComponent implements OnInit {
  displayedColumns: string[] = ['property_id', 'numberOfBedrooms', 'numberOfBathrooms', 'numberOfPartialBathrooms','numberofGarages','property_date','first_name','deleteAction'];
  dataSource = new MatTableDataSource<Property>();

  users: Observable<Property[]>;

  person = { 'first_name': 'Johny', 'last_name':'Carson' };

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort , {static: false}) sort: MatSort;

  constructor(private propertyService: PropertyService,
    private router: Router) {
  
  }
  ngOnInit() {

  
   this.propertyService.getPropertyList().subscribe(property =>{
    this.dataSource.data=property;

    this.dataSource.sortingDataAccessor = (item, property) => {
      switch (property) {
        case 'first_name': return  item.user.first_name;
        default: return item[property];
   }}
   this.dataSource.filterPredicate = (data: any, filter) => {
    const dataStr =JSON.stringify(data).toLowerCase();
    return dataStr.indexOf(filter) != -1; 
  }
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
   console.log(property);
  });



  }
  

  applyFilter(event: Event) {

   // this.dataSource.filterPredicate = (data: Property, filter: string) => {
    //  return data.user.first_name.toLocaleLowerCase().includes(filter);

    // }
  
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();



    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }





}
