import { Component,OnInit , ViewChild, AfterViewInit} from '@angular/core';
import {PropertyService} from '../_services/property.service';

import {Property} from '../_models/property';
import {Page} from '../_models/page';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { Router } from '@angular/router';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatPaginator, PageEvent } from '@angular/material/paginator';



@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.scss']
})

export class PropertyListComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = [
    'name',
    'status',
    'bedrooms',
    'bathrooms',
    'size',
    'price',
    'city',
    'actions'
  ];
  dataSource = new MatTableDataSource<Property>();
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  totalElements: number = 0;
  pageSize:number=10;
  filterText: string = '';



  constructor(private propertyService: PropertyService, private router: Router,private dialog: MatDialog, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.loadPage(0, 10);
  }
  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
  }
  loadPage(pageIndex: number, pageSize: number): void {
    this.propertyService.getAll(pageIndex, pageSize, this.filterText).subscribe((page: Page<Property>) => {
      this.dataSource.data = page.content;
        this.totalElements = page.totalElements;
      
    });
  }
  onPageChanged(event: any): void {
    const pageIndex = event.pageIndex;
    const pageSize = event.pageSize;
    this.loadPage(pageIndex, pageSize);
  }
  applyFilter(): void {
    this.loadPage(0, 10);
  }
  viewPropertyDetails(propertyId: number): void {
    this.router.navigate(['/property', propertyId]);
  }
  updateProperty(propertyId: number): void {
    this.router.navigate(['/updateproperty', propertyId]);
  }
  deleteProperty(id: number): void {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: 'Are you sure you want to delete this property?'
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        const previousTotalElements = this.totalElements;
        this.propertyService.deleteProperty(id).subscribe(
          () => {
            console.log('Property deleted successfully.');
  
            this.snackBar.open('Property deleted successfully.', 'Close', {
              duration: 3000, // Duration in milliseconds
              panelClass: ['success-snackbar'] // Apply custom styles to the snackbar
            });
            this.totalElements -= 1; // Reduce 1 from totalElements

            const currentPageIndex = Math.floor(previousTotalElements / this.pageSize);
            const isValidPageIndex = currentPageIndex < Math.ceil(this.totalElements / this.pageSize);
  
            if (!isValidPageIndex) {
              const previousPageIndex = Math.max(currentPageIndex - 1, 0);
              this.paginator.pageIndex = previousPageIndex; // Update the pagination component
            }
  
            // Load the data for the current or previous page
            this.loadPage(this.paginator.pageIndex, this.paginator.pageSize);
            
          },
          (error) => {
            console.error('Error deleting property:', error);
          }
        );
      }
    });
  }
  
}