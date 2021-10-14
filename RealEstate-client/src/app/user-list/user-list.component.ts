import { Component, OnInit ,ViewChild} from '@angular/core';
import { Observable } from 'rxjs';
import{User} from '../model/user';
import { UserService } from "../service/user.service";
import { Router } from '@angular/router';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from '@angular/material/sort';



@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  displayedColumns: string[] = ['id_user', 'first_name', 'last_name', 'email','phone'];
  dataSource = new MatTableDataSource<User>();

  users: Observable<User[]>;


  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort , {static: false}) sort: MatSort;

  constructor(private userService: UserService,
    private router: Router) {
  }
  ngOnInit() {

  
   this.userService.getUsersList().subscribe(user =>{
    this.dataSource.data=user;
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
   
  });



  }
  
  


  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
}




